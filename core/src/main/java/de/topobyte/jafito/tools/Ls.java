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
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.Ansi.Color;
import org.fusesource.jansi.AnsiConsole;
import org.fusesource.jansi.internal.CLibrary;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import jline.Terminal;
import jline.TerminalFactory;

public class Ls
{

	private int width;
	private int height;
	private boolean isTTY;

	public Ls()
	{
		Terminal terminal = TerminalFactory.get();
		width = terminal.getWidth();
		height = terminal.getHeight();

		isTTY = false;
		try {
			if (CLibrary.isatty(CLibrary.STDOUT_FILENO) != 0) {
				isTTY = true;
			}
		} catch (NoClassDefFoundError | UnsatisfiedLinkError ignore) {
			// ignore errors, assume not on TTY
		}
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
		List<Path> files = getFiles(path);
		for (Path file : files) {
			Path relative = path.relativize(file);
			if (Files.isDirectory(file)) {
				println(Ansi.Color.BLUE, true, relative.toString());
			} else {
				System.out.println(relative);
			}
		}
	}

	private void println(Color color, boolean bold, String string)
	{
		if (isTTY) {
			Ansi ansi = Ansi.ansi();
			ansi.fg(color);
			if (bold) {
				ansi.bold();
			}
			ansi.a(string);
			ansi.reset();
			AnsiConsole.out.println(ansi);
		} else {
			System.out.println(string);
		}
	}

	private List<Path> getFiles(Path parentFile) throws IOException
	{
		List<Path> list = list(parentFile, false);
		Collections.sort(
				list,
				(Path o1, Path o2) -> String.CASE_INSENSITIVE_ORDER.compare(
						o1.toString(), o2.toString()));
		return list;
	}

	private List<Path> list(Path directory, boolean showHiddenFiles)
			throws IOException
	{
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
			List<Path> list = Lists.newArrayList(stream);
			if (showHiddenFiles) {
				return list;
			}

			list = Lists.newArrayList(Iterables.filter(list, a -> {
				try {
					return !Files.isHidden(a);
				} catch (IOException e) {
					return false;
				}
			}));

			return list;
		}
	}

}
