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
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;

import org.fusesource.jansi.Ansi;

import de.topobyte.adt.misc.Stack;
import de.topobyte.jafito.util.Terminal;
import de.topobyte.jafito.util.Util;

public class Tree
{

	private static final String ERROR_ACCESS_DENIED = " [error opening dir]";

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
		Stack<Boolean> stack = new Stack<>();

		try {
			List<Path> files = Util.getFiles(path, false);
			terminal.println(Ansi.Color.BLUE, true, path.toString());

			// only here if listing files worked
			recurse(path, stack, files);
		} catch (AccessDeniedException e) {
			terminal.print(Ansi.Color.BLUE, true, path.toString());
			System.out.print(ERROR_ACCESS_DENIED);
			System.out.println();
		} catch (NoSuchFileException e) {
			terminal.print(Ansi.Color.BLACK, false, path.toString());
			System.out.print(ERROR_ACCESS_DENIED);
			System.out.println();
		}

		System.out.println();
		System.out.println(String.format("%d directories, %d files",
				numDirectories, numFiles));
	}

	private void tree(Path path, Path relative, Stack<Boolean> stack,
			boolean isLast) throws IOException
	{
		if (Files.isSymbolicLink(path)) {
			Path target = Files.readSymbolicLink(path);
			printSymlink(path, relative, stack, isLast, target);
			return;
		}

		try {
			List<Path> files = Util.getFiles(path, false);

			println(path, relative, stack, isLast);
			recurse(path, stack, files);
		} catch (AccessDeniedException e) {
			println(path, relative, stack, isLast, ERROR_ACCESS_DENIED);
		}
	}

	private void recurse(Path path, Stack<Boolean> stack, List<Path> files)
			throws IOException
	{
		for (int i = 0; i < files.size(); i++) {
			Path sub = files.get(i);
			Path relativeSub = path.relativize(sub);
			boolean subIsLast = i + 1 >= files.size();

			stack.push(subIsLast);
			if (Files.isSymbolicLink(sub)) {
				Path target = Files.readSymbolicLink(sub);
				printSymlink(sub, relativeSub, stack, subIsLast, target);
			} else if (Files.isDirectory(sub)) {
				numDirectories += 1;
				tree(sub, relativeSub, stack, subIsLast);
			} else {
				println(sub, relativeSub, stack, subIsLast);
				numFiles += 1;
			}
			stack.pop();
		}
	}

	private void printSymlink(Path file, Path relative, Stack<Boolean> stack,
			boolean isLast, Path target)
	{
		print(file, relative, stack, isLast, " -> ");
		String targetName = target.toString();
		if (Files.isDirectory(target)) {
			terminal.print(Ansi.Color.BLUE, true, targetName);
		} else {
			terminal.print(Ansi.Color.BLACK, false, targetName);
		}
		System.out.println();
	}

	private void println(Path file, Path relative, Stack<Boolean> stack,
			boolean isLast)
	{
		print(file, relative, stack, isLast);
		System.out.println();
	}

	private void println(Path file, Path relative, Stack<Boolean> stack,
			boolean isLast, String suffix)
	{
		print(file, relative, stack, isLast, suffix);
		System.out.println();
	}

	private void print(Path file, Path relative, Stack<Boolean> stack,
			boolean isLast)
	{
		print(file, relative, stack, isLast, null);
	}

	private void print(Path file, Path relative, Stack<Boolean> stack,
			boolean isLast, String suffix)
	{
		String prefix = prefix(stack, isLast);
		String name = relative.toString();

		if (Files.isSymbolicLink(file)) {
			System.out.print(prefix);
			terminal.print(Ansi.Color.CYAN, true, name);
		} else if (Files.isDirectory(file)) {
			System.out.print(prefix);
			terminal.print(Ansi.Color.BLUE, true, name);
		} else if (Files.isExecutable(file)) {
			System.out.print(prefix);
			terminal.print(Ansi.Color.GREEN, true, name);
		} else {
			System.out.print(prefix);
			System.out.print(name);
		}

		if (suffix != null) {
			System.out.print(suffix);
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
				buffer.append("│   ");
			}
		}
		if (isLast) {
			buffer.append("└── ");
		} else {
			buffer.append("├── ");
		}
		return buffer.toString();
	}

}
