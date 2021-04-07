// Copyright 2021 Sebastian Kuerten
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

package de.topobyte.jafito.filemanager.launch;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import de.topobyte.jafito.filemanager.FileBrowser;
import de.topobyte.jafito.filemanager.actions.DirCommandAction;
import de.topobyte.jafito.filemanager.config.Command;
import de.topobyte.jafito.filemanager.config.FileBrowserConfig;
import de.topobyte.system.utils.SystemPaths;

public class TestLaunchDialog
{

	public static void main(String[] args)
	{
		FileBrowserConfig config = new FileBrowserConfig();
		FileBrowser browser = new FileBrowser(SystemPaths.HOME, config);

		List<DirCommandAction> actions = new ArrayList<>();
		actions.add(new DirCommandAction(browser, new Command("Nemo", "nemo")));
		actions.add(
				new DirCommandAction(browser, new Command("Thunar", "thunar")));
		actions.add(new DirCommandAction(browser,
				new Command("Konqueror", "konqueror")));

		LaunchDialog dialog = new LaunchDialog(null, actions);
		dialog.setVisible(true);
		dialog.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}

		});
	}

}
