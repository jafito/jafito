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

import de.topobyte.jafito.filemanager.FileBrowser;

public class ShowHiddenFilesAction extends FileBrowserActionBoolean
{

	private static final long serialVersionUID = 1L;

	public ShowHiddenFilesAction(FileBrowser browser)
	{
		super(browser, "Show Hidden Files", "Toggle visibility of hidden files",
				null);
	}

	@Override
	public boolean getState()
	{
		return browser.isShowHiddenFiles();
	}

	@Override
	public void toggleState()
	{
		boolean oldValue = browser.isShowHiddenFiles();
		browser.setShowHiddenFiles(!oldValue);
	}

}
