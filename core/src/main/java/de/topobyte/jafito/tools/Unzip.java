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

	public void listVerbose(Path path) throws IOException
	{
		ZipFile zf = new ZipFile(path.toFile());
		Enumeration<? extends ZipEntry> entries = zf.entries();

		int numFiles = 0;
		long totalSize = 0;
		long totalCompressed = 0;

		System.out.println("Archive: " + path);
		System.out.println(
				" Length   Method    Size  Cmpr    Date    Time   CRC-32   Name");
		System.out.println(
				"--------  ------  ------- ---- ---------- ----- --------  ----");

		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			long size = entry.getSize();
			long compressed = entry.getCompressedSize();
			String name = entry.getName();
			long time = entry.getTime();
			int method = entry.getMethod();
			long crc = entry.getCrc();

			String m = null;
			if (method == ZipEntry.STORED) {
				m = "Stored";
			} else if (method == ZipEntry.DEFLATED) {
				m = "Defl:N";
			} else {
				m = "Undef.";
			}

			int ratio = ratio(size, compressed);

			String date = pattern.print(time);

			System.out.println(String.format("%8d  %s  %7d  %2d%% %s %08x  %s",
					size, m, compressed, ratio, date, crc, name));

			numFiles += 1;
			totalSize += size;
			totalCompressed += compressed;
		}
		zf.close();

		int totalRatio = ratio(totalSize, totalCompressed);

		System.out.println(
				"--------          -------  ---                            -------");
		System.out.println(String.format(
				"%8d          %7d  %2d%%                            %d files",
				totalSize, totalCompressed, totalRatio, numFiles));
	}

	private int ratio(long size, long compressed)
	{
		int ratio = 0;
		if (size != 0) {
			ratio = 100 - (int) (compressed / (double) size * 100);
		}
		return ratio;
	}

}
