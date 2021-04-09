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

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.nio.file.Path;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import de.topobyte.jafito.filemanager.actions.BookmarkAction;
import de.topobyte.jafito.filemanager.actions.DirCommandAction;
import de.topobyte.jafito.filemanager.actions.FileBrowserActions;
import de.topobyte.jafito.filemanager.actions.SelectionCommandAction;
import de.topobyte.jafito.filemanager.config.Bookmark;
import de.topobyte.jafito.filemanager.config.Command;
import de.topobyte.jafito.filemanager.config.FileBrowserConfig;
import de.topobyte.swing.util.EmptyIcon;
import de.topobyte.swing.util.ImageLoader;
import de.topobyte.swing.util.JMenus;

public class FileBrowserFrame extends JFrame
{

	private static final long serialVersionUID = 1L;

	public FileBrowserFrame(Path path, FileBrowserConfig config)
	{
		super("File Manager");

		FileBrowser browser = new FileBrowser(path, config);
		getContentPane().add(browser);

		// menu

		JMenuBar menuBar = createMenu(browser);
		setJMenuBar(menuBar);
	}

	private JMenuBar createMenu(FileBrowser browser)
	{
		FileBrowserConfig config = browser.getConfig();

		FileBrowserActions actions = browser.getActions();

		JMenu menuFile = new JMenu("File");
		JMenu menuView = new JMenu("View");
		JMenu menuNavigate = new JMenu("Navigate");
		JMenu menuRun = new JMenu("Run");
		JMenu menuDebug = new JMenu("Debug");

		JMenu menuRunDirectory = new JMenu("Directory");
		JMenu menuRunSelection = new JMenu("Selection");
		menuRunDirectory.setIcon(new EmptyIcon(22));
		menuRunSelection.setIcon(new EmptyIcon(22));

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(menuFile);
		menuBar.add(menuView);
		menuBar.add(menuNavigate);
		menuBar.add(menuRun);
		menuBar.add(menuDebug);

		menuFile.add(actions.getQuit());

		JMenus.addCheckbox(menuView, actions.getShowHidden(),
				InputEvent.CTRL_DOWN_MASK, KeyEvent.VK_H);
		JMenus.addItem(menuView, actions.getRefresh(),
				InputEvent.CTRL_DOWN_MASK, KeyEvent.VK_R);

		JMenus.addItem(menuNavigate, actions.getGoUp(),
				InputEvent.ALT_DOWN_MASK, KeyEvent.VK_UP);
		JMenus.addItem(menuNavigate, actions.getGoHome(),
				InputEvent.ALT_DOWN_MASK, KeyEvent.VK_HOME);
		JMenus.addItem(menuNavigate, actions.getOpenLocation(),
				InputEvent.CTRL_DOWN_MASK, KeyEvent.VK_L);

		JMenu menuFavorites = new JMenu("Favorites");
		menuFavorites
				.setIcon(ImageLoader.load("tango-icons/misc/bookmark.png"));
		menuNavigate.add(menuFavorites);

		for (Bookmark bookmark : config.getBookmarks()) {
			menuFavorites.add(new BookmarkAction(browser, bookmark));
		}

		menuRun.add(menuRunDirectory);
		menuRun.add(menuRunSelection);

		JMenus.addItem(menuRunDirectory, actions.getDirCommands(), 0,
				KeyEvent.VK_F3);
		for (Command command : config.getDirCommands()) {
			JMenus.addItem(menuRunDirectory,
					new DirCommandAction(browser, command));
		}
		for (Command command : config.getSelectionCommands()) {
			JMenus.addItem(menuRunSelection,
					new SelectionCommandAction(browser, command));
		}

		JMenus.addItem(menuDebug, actions.getDebugPrintKeyBindings(),
				InputEvent.CTRL_DOWN_MASK, KeyEvent.VK_F2);

		return menuBar;
	}

}
