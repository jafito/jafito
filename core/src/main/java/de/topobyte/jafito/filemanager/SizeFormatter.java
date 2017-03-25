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

public class SizeFormatter
{

	public String format(long size)
	{
		if (size > 1000000000000L) {
			return formatSize(size, 1000000000000d, "TB");
		}
		if (size > 1000000000) {
			return formatSize(size, 1000000000d, "GB");
		}
		if (size > 1000000) {
			return formatSize(size, 1000000d, "MB");
		}
		if (size > 1000) {
			return formatSize(size, 1000d, "kB");
		}
		return Long.toString(size);
	}

	private String formatSize(long size, double d, String suffix)
	{
		double x = size / d;
		return String.format("%.1f %s", x, suffix);
	}

}
