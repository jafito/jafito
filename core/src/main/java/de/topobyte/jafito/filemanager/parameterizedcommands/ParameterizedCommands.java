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

package de.topobyte.jafito.filemanager.parameterizedcommands;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Splitter;

public class ParameterizedCommands
{

	public static ParameterizedCommandLine parse(String spec)
	{
		List<Part> parts = new ArrayList<>();

		List<String> split = Splitter.on(" ").splitToList(spec);

		List<String> variableNames = new ArrayList<>();
		for (String part : split) {
			if (!part.startsWith("$")) {
				parts.add(new Literal(part));
				continue;
			}
			String name = part.substring(1);
			parts.add(new Parameter(name));
			if (name.equals("input")) {
				continue;
			}
			variableNames.add(name);
		}

		return new ParameterizedCommandLine(parts, variableNames);
	}

}
