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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.fusesource.jansi.Ansi;

import de.topobyte.jafito.util.Terminal;
import de.topobyte.jafito.util.Util;

public class Ls
{

	private Terminal terminal;

	public Ls()
	{
		terminal = new Terminal();
	}

	public void list(List<Path> paths) throws IOException
	{
		if (paths.size() == 1) {
			list(paths.get(0));
		} else {
			for (Path path : paths) {
				System.out.println(path + "/:");
				list(path);
			}
		}
	}

	private void list(Path path) throws IOException
	{
		// TODO: use width and height for formatting
		List<Path> files = Util.getFiles(path, false);
		for (Path file : files) {
			Path relative = path.relativize(file);
			if (Files.isDirectory(file)) {
				terminal.println(Ansi.Color.BLUE, true, relative.toString());
			} else {
				System.out.println(relative);
			}
		}
	}

}
