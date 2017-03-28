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

import de.topobyte.adt.misc.Stack;
import de.topobyte.jafito.util.Terminal;
import de.topobyte.jafito.util.Util;

public class Tree
{

	private Terminal terminal;

	public Tree()
	{
		terminal = new Terminal();
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
		terminal.println(Ansi.Color.BLUE, true, path.toString());
		tree(path, 1, stack);
	}

	private void tree(Path path, int depth, Stack<Boolean> stack)
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
				terminal.print(Ansi.Color.BLACK, false, prefix);
				terminal.println(Ansi.Color.BLUE, true, name);
			} else {
				System.out.print(prefix);
				System.out.println(name);
			}

			if (Files.isDirectory(file)) {
				stack.push(isLast);
				tree(file, depth + 1, stack);
				stack.pop();
			}
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
