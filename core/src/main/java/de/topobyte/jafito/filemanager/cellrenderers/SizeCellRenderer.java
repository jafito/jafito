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

package de.topobyte.jafito.filemanager.cellrenderers;

import javax.swing.SwingConstants;

import de.topobyte.various.utils.SizeFormatter;

public class SizeCellRenderer extends OurDefaultTableCellRenderer
{

	private static final long serialVersionUID = 1L;

	private SizeFormatter formatter = new SizeFormatter();

	public SizeCellRenderer()
	{
		setHorizontalAlignment(SwingConstants.RIGHT);
	}

	@Override
	protected void setValue(Object value)
	{
		Long size = (Long) value;
		String display = formatter.format(size.longValue());
		setText(display);
	}

}
