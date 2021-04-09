// Copyright 2017 Sebastian Kuerten
//
// This file is part of jafito.
//
// jafito is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// jafito is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with jafito. If not, see <http://www.gnu.org/licenses/>.

package de.topobyte.jafito.filemanager.actions;

import java.awt.event.ActionEvent;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JTextField;

import de.topobyte.jafito.filemanager.FileBrowser;
import de.topobyte.jafito.filemanager.Util;
import de.topobyte.jafito.filemanager.config.Command;
import de.topobyte.jafito.filemanager.parameterizedcommands.Literal;
import de.topobyte.jafito.filemanager.parameterizedcommands.Parameter;
import de.topobyte.jafito.filemanager.parameterizedcommands.ParameterDialog;
import de.topobyte.jafito.filemanager.parameterizedcommands.ParameterizedCommandLine;
import de.topobyte.jafito.filemanager.parameterizedcommands.ParameterizedCommands;
import de.topobyte.jafito.filemanager.parameterizedcommands.Part;
import lombok.Getter;

public class SelectionCommandAction extends FileBrowserAction
{

	private static final long serialVersionUID = 1L;

	@Getter
	private Command command;

	public SelectionCommandAction(FileBrowser browser, Command command)
	{
		super(browser, command.getName(),
				String.format("Execute %s on the current selection",
						command.getName()),
				"org/freedesktop/tango/22x22/categories/applications-other.png");
		this.command = command;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		run();
	}

	public void run()
	{
		if (browser.getNumSelectedRows() != 1) {
			return;
		}

		Path dir = browser.getPath();
		Path input = browser.getFirstSelectedPath();

		String exec = command.getExec();

		ParameterizedCommandLine pcl = ParameterizedCommands.parse(exec);
		ParameterDialog dialog = new ParameterDialog(pcl.getVariableNames());
		dialog.setVisible(true);
		dialog.pack();
		dialog.setLocationRelativeTo(browser);

		dialog.getButtons().getButtonCancel()
				.addActionListener(e -> dialog.dispose());

		dialog.getButtons().getButtonOk().addActionListener(e -> {
			launch(dir, pcl, input, dialog.getInputs());
			dialog.dispose();
		});
	}

	private void launch(Path dir, ParameterizedCommandLine pcl, Path path,
			Map<String, JTextField> inputs)
	{
		Path relative = dir.relativize(path);

		List<String> args = new ArrayList<>();
		for (Part part : pcl.getParts()) {
			if (part instanceof Literal) {
				args.add(((Literal) part).getText());
			} else if (part instanceof Parameter) {
				Parameter p = (Parameter) part;
				String pn = p.getName();
				if (pn.equals("input")) {
					args.add(relative.toString());
				} else {
					args.add(inputs.get(pn).getText());
				}
			}
		}

		System.out.println(args);

		Util.run(args, dir);
	}

}
