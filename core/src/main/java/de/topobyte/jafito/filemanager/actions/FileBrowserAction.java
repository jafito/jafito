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
import de.topobyte.swing.util.EmptyIcon;
import de.topobyte.swing.util.action.SimpleAction;

/**
 * An abstract base class for actions that work on a {@link FileBrowser}.
 * 
 * @author Sebastian Kuerten
 */
public abstract class FileBrowserAction extends SimpleAction
{

	private static final long serialVersionUID = 1L;

	protected FileBrowser browser;

	public FileBrowserAction(FileBrowser browser, String name,
			String description, String icon)
	{
		super(name, description, icon);
		this.browser = browser;
		if (icon == null) {
			setIcon(new EmptyIcon(22));
		}
	}

}
