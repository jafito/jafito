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

package de.topobyte.jafito.util;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.Ansi.Color;
import org.fusesource.jansi.AnsiConsole;
import org.fusesource.jansi.internal.CLibrary;

import jline.TerminalFactory;

public class Terminal
{

	private int width;
	private int height;
	private boolean isTTY;

	public Terminal()
	{
		jline.Terminal terminal = TerminalFactory.get();
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

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public boolean isTTY()
	{
		return isTTY;
	}

	public void print(Color color, boolean bold, String string)
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

	public void println(Color color, boolean bold, String string)
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

}
