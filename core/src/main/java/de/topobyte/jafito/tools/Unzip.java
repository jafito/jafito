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

package de.topobyte.jafito.tools;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import de.topobyte.jafito.util.Terminal;

public class Unzip
{

	private Terminal terminal;

	public Unzip()
	{
		terminal = new Terminal();
	}

	private DateTimeFormatter pattern = DateTimeFormat
			.forPattern("YYYY-MM-dd HH:mm");

	public void listShort(Path path) throws IOException
	{
		ZipFile zf = new ZipFile(path.toFile());
		Enumeration<? extends ZipEntry> entries = zf.entries();

		int numFiles = 0;
		long totalSize = 0;

		System.out.println("Archive: " + path);
		System.out.println("  Length      Date    Time    Name");
		System.out.println("---------  ---------- -----   ----");

		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			long size = entry.getSize();
			String name = entry.getName();
			long time = entry.getTime();

			String date = pattern.print(time);

			System.out.println(String.format("%9d  %s   %s", size, date, name));

			numFiles += 1;
			totalSize += size;
		}
		zf.close();

		System.out.println("---------                     -------");
		System.out.println(String.format("%9d                     %d files",
				totalSize, numFiles));
	}

}
