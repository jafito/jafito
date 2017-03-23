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

import javax.swing.Action;

import de.topobyte.jafito.filemanager.FileBrowser;
import de.topobyte.swing.util.action.SimpleAction;

/**
 * An abstract base class for actions that work on a {@link FileBrowser} and
 * manipulate a boolean value.
 * 
 * @author Sebastian Kuerten
 */
public abstract class FileBrowserActionBoolean extends SimpleAction
{

	private static final long serialVersionUID = 1L;

	protected FileBrowser browser;

	public FileBrowserActionBoolean(FileBrowser browser, String name,
			String description)
	{
		super(name, description);
		this.browser = browser;
	}

	@Override
	public Object getValue(String key)
	{
		if (key.equals(Action.SELECTED_KEY)) {
			return getValue();
		}
		return super.getValue(key);
	}

	@Override
	public void actionPerformed(ActionEvent event)
	{
		boolean oldValue = getValue();
		boolean newValue = !oldValue;
		setValue(newValue);
		firePropertyChange(Action.SELECTED_KEY, oldValue, newValue);
	}

	protected abstract boolean getValue();

	protected abstract void setValue(boolean value);

}
