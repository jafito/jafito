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

package de.topobyte.jafito.filemanager;

import java.nio.file.Path;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import de.topobyte.jafito.filemanager.actions.QuitAction;
import de.topobyte.jafito.filemanager.actions.ShowHiddenFilesAction;

public class FileBrowserFrame extends JFrame
{

	private static final long serialVersionUID = 1L;

	public FileBrowserFrame(Path path)
	{
		super("File Manager");

		FileBrowser browser = new FileBrowser(path);
		getContentPane().add(browser);

		// menu

		JMenuBar menuBar = createMenu(browser);
		setJMenuBar(menuBar);
	}

	private JMenuBar createMenu(FileBrowser browser)
	{
		JMenu menuFile = new JMenu("File");
		JMenu menuView = new JMenu("View");

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(menuFile);
		menuBar.add(menuView);

		QuitAction quit = new QuitAction(browser);
		ShowHiddenFilesAction showHidden = new ShowHiddenFilesAction(browser);

		menuFile.add(quit);

		menuView.add(new JCheckBoxMenuItem(showHidden));

		return menuBar;
	}

}
