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
import org.fusesource.jansi.Ansi.Color;
import org.fusesource.jansi.AnsiConsole;
import org.fusesource.jansi.internal.CLibrary;

import de.topobyte.adt.misc.Stack;
import de.topobyte.jafito.util.Util;
import jline.Terminal;
import jline.TerminalFactory;

public class Tree
{

	private int width;
	private int height;
	private boolean isTTY;

	public Tree()
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

	public void tree(List<Path> paths) throws IOException
	{
		if (paths.size() == 1) {
			tree(paths.get(0));
		} else {
			for (Path path : paths) {
				tree(path);
			}
		}
	}

	private void tree(Path path) throws IOException
	{
		Stack<Boolean> stack = new Stack<Boolean>();
		println(Ansi.Color.BLUE, true, path.toString());
		tree1(path, 1, stack);
	}

	private void tree1(Path path, int depth, Stack<Boolean> stack)
			throws IOException
	{
		List<Path> files = Util.getFiles(path, false);
		for (int i = 0; i < files.size(); i++) {
			Path file = files.get(i);
			Path relative = path.relativize(file);
			boolean isLast = i + 1 >= files.size();

			String prefix = prefix(depth, stack, isLast);
			String name = relative.toString();

			if (Files.isDirectory(file)) {
				print(Ansi.Color.BLACK, false, prefix);
				println(Ansi.Color.BLUE, true, name);
			} else {
				System.out.print(prefix);
				System.out.println(name);
			}

			if (Files.isDirectory(file)) {
				stack.push(isLast);
				tree1(file, depth + 1, stack);
				stack.pop();
			}
		}
	}

	private void print(Color color, boolean bold, String string)
	{
		if (isTTY) {
			Ansi ansi = Ansi.ansi();
			ansi.fg(color);
			if (bold) {
				ansi.bold();
			}
			ansi.a(string);
			ansi.reset();
			AnsiConsole.out.print(ansi);
		} else {
			System.out.print(string);
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

	private String prefix(int depth, Stack<Boolean> stack, boolean isLast)
	{
		StringBuilder buffer = new StringBuilder();
		List<Boolean> values = stack.asList();
		for (int i = 0; i < depth - 1; i++) {
			if (values.get(i)) {
				buffer.append("    ");
			} else {
				buffer.append("|   ");
			}
		}
		if (isLast) {
			buffer.append("`-- ");
		} else {
			buffer.append("|-- ");
		}
		return buffer.toString();
	}

}
