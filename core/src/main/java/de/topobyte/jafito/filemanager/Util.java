// Copyright 2021 Sebastian Kuerten
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

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.topobyte.swing.util.BorderHelper;

public class Util
{

	final static Logger logger = LoggerFactory.getLogger(Util.class);

	public static void defaultRightPadding(
			Iterable<? extends JComponent> components)
	{
		for (JComponent component : components) {
			defaultRightPadding(component);
		}
	}

	public static void defaultRightPadding(JComponent component)
	{
		BorderHelper.addEmptyBorder(component, 0, 0, 0, 5);
	}

	public static void run(String command, Path file)
	{
		List<String> args = new ArrayList<>();
		args.add(command);
		args.add(file.toString());
		run(args);
	}

	public static void run(List<String> args)
	{
		ProcessBuilder pb = new ProcessBuilder();
		pb.command(args);
		try {
			pb.start();
		} catch (IOException e) {
			logger.error(String.format("Error while running command '%s'",
					args.toString()), e);
		}
	}

}
