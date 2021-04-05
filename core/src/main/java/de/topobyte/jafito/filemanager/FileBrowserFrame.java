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
import de.topobyte.jafito.filemanager.actions.FileBrowserActions;
import de.topobyte.jafito.filemanager.config.Bookmark;
import de.topobyte.jafito.filemanager.config.FileBrowserConfig;
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

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(menuFile);
		menuBar.add(menuView);
		menuBar.add(menuNavigate);

		menuFile.add(actions.getQuit());

		JMenus.addCheckbox(menuView, actions.getShowHidden(),
				InputEvent.CTRL_DOWN_MASK, KeyEvent.VK_H);

		JMenus.addItem(menuNavigate, actions.getGoUp(),
				InputEvent.ALT_DOWN_MASK, KeyEvent.VK_UP);
		JMenus.addItem(menuNavigate, actions.getGoHome(),
				InputEvent.ALT_DOWN_MASK, KeyEvent.VK_HOME);
		JMenus.addItem(menuNavigate, actions.getOpenLocation(),
				InputEvent.CTRL_DOWN_MASK, KeyEvent.VK_L);

		JMenu menuFavorites = new JMenu("Favorites");
		menuFavorites.setIcon(ImageLoader
				.load("org/freedesktop/tango/22x22/actions/bookmark-new.png"));
		menuNavigate.add(menuFavorites);

		for (Bookmark bookmark : config.getBookmarks()) {
			menuFavorites.add(new BookmarkAction(browser, bookmark));
		}

		return menuBar;
	}

}
