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

import de.topobyte.jafito.filemanager.FileBrowser;
import de.topobyte.jafito.filemanager.config.Command;
import de.topobyte.jafito.filemanager.launch.ParameterDialog;
import de.topobyte.jafito.filemanager.launch.SelectionCommands;
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
		Path path = browser.getPath();
		String exec = command.getExec();

		ParameterDialog dialog = SelectionCommands.createParameterDialog(exec);
		dialog.setVisible(true);
		dialog.pack();
		dialog.setLocationRelativeTo(browser);

		dialog.getButtons().getButtonCancel()
				.addActionListener(e -> dialog.dispose());

		dialog.getButtons().getButtonOk()
				.addActionListener(e -> launch(exec, path));
	}

	private void launch(String exec, Path path)
	{
		// TODO: get parameters from dialog, construct command line and execute
		// List<String> parts = Splitter.on(" ").splitToList(exec);
		//
		// List<String> args = new ArrayList<>();
		// args.addAll(parts);
		// args.add(path.toString());
		// Util.run(args);
	}

}
