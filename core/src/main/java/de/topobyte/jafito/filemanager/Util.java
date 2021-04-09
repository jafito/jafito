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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

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
		run(args, null);
	}

	public static void run(List<String> args, Path workingDirectory)
	{
		ProcessBuilder pb = new ProcessBuilder();
		pb.command(args);
		if (workingDirectory != null) {
			pb.directory(workingDirectory.toFile());
		}
		try {
			pb.start();
		} catch (IOException e) {
			logger.error(String.format("Error while running command '%s'",
					args.toString()), e);
		}
	}

	public static void printInputMaps(JComponent component)
	{
		Map<Integer, String> types = new LinkedHashMap<>();
		types.put(JComponent.WHEN_FOCUSED, "WHEN_FOCUSED");
		types.put(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT,
				"WHEN_ANCESTOR_OF_FOCUSED_COMPONENT");
		types.put(JComponent.WHEN_IN_FOCUSED_WINDOW, "WHEN_IN_FOCUSED_WINDOW");

		for (int type : types.keySet()) {
			String typeName = types.get(type);
			System.out.println(typeName);
			InputMap inputMap = component.getInputMap(type);
			print(inputMap);
			while (true) {
				inputMap = inputMap.getParent();
				if (inputMap == null) {
					break;
				}
				System.out.println("parent: ");
				print(inputMap);
			}
		}
	}

	private static void print(InputMap inputMap)
	{
		KeyStroke[] keys = inputMap.keys();
		if (keys != null) {
			for (KeyStroke stroke : keys) {
				Object binding = inputMap.get(stroke);
				System.out.println(String.format("%s → %s", stroke, binding));
			}
		}
	}

}
