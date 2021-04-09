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
import java.util.ArrayList;
import java.util.List;

import de.topobyte.jafito.filemanager.FileBrowser;
import de.topobyte.jafito.filemanager.config.Command;
import de.topobyte.jafito.filemanager.launch.LaunchDialog;

public class DirCommandsAction extends FileBrowserAction
{

	private static final long serialVersionUID = 1L;

	public DirCommandsAction(FileBrowser browser)
	{
		super(browser, "Launch in dir",
				"Select an application to launch in the current directory",
				"tango-icons/categories/applications-other.png");
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		List<Command> dirCommands = browser.getConfig().getDirCommands();
		List<DirCommandAction> actions = new ArrayList<>();
		for (Command command : dirCommands) {
			actions.add(new DirCommandAction(browser, command));
		}

		LaunchDialog dialog = new LaunchDialog(browser, actions);
		dialog.setVisible(true);
	}

}
