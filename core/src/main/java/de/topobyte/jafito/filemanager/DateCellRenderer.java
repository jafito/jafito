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

import java.nio.file.attribute.FileTime;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateCellRenderer extends DefaultTableCellRenderer
{

	private static final long serialVersionUID = 1L;

	private DateTimeFormatter pattern = DateTimeFormat
			.forPattern("YYYY-MM-dd HH:mm");

	public DateCellRenderer()
	{
		setHorizontalAlignment(SwingConstants.RIGHT);
	}

	@Override
	protected void setValue(Object value)
	{
		FileTime size = (FileTime) value;
		String display = pattern.print(size.toMillis());
		setText(display);
	}

}
