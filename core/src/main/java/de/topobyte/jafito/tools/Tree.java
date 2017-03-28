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

	private int numDirectories = 0;
	private int numFiles = 0;

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
		recurse(path, stack);

		System.out.println();
		System.out.println(String.format("%d directories, %d files",
				numDirectories, numFiles));
	}

	private void tree(Path path, Path relative, Stack<Boolean> stack,
			boolean isLast) throws IOException
	{
		print(path, relative, stack, isLast);

		recurse(path, stack);
	}

	private void recurse(Path path, Stack<Boolean> stack) throws IOException
	{
		List<Path> files = Util.getFiles(path, false);
		for (int i = 0; i < files.size(); i++) {
			Path sub = files.get(i);
			Path relativeSub = path.relativize(sub);
			boolean subIsLast = i + 1 >= files.size();

			stack.push(subIsLast);
			if (Files.isDirectory(sub)) {
				numDirectories += 1;
				tree(sub, relativeSub, stack, subIsLast);
			} else {
				print(sub, relativeSub, stack, subIsLast);
				numFiles += 1;
			}
			stack.pop();
		}
	}

	private void print(Path file, Path relative, Stack<Boolean> stack,
			boolean isLast)
	{
		String prefix = prefix(stack, isLast);
		String name = relative.toString();

		if (Files.isDirectory(file)) {
			terminal.print(Ansi.Color.BLACK, false, prefix);
			terminal.println(Ansi.Color.BLUE, true, name);
		} else {
			System.out.print(prefix);
			System.out.println(name);
		}
	}

	private String prefix(Stack<Boolean> stack, boolean isLast)
	{
		int depth = stack.size();
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
