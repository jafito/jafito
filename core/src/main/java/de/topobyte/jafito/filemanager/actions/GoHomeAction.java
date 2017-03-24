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

import de.topobyte.jafito.filemanager.FileBrowser;
import de.topobyte.system.utils.SystemPaths;

public class GoHomeAction extends FileBrowserAction
{

	private static final long serialVersionUID = 1L;

	public GoHomeAction(FileBrowser browser)
	{
		super(browser, "Go Home", "Navigate to your home directory",
				"org/freedesktop/tango/22x22/actions/go-home.png");
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		browser.go(SystemPaths.HOME);
	}

}
