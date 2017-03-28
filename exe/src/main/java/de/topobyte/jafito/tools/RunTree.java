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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import de.topobyte.system.utils.SystemPaths;

public class RunTree
{

	public static void main(String[] args) throws IOException
	{
		List<Path> paths = new ArrayList<>();
		if (args.length == 0) {
			paths.add(SystemPaths.CWD);
		} else {
			for (String arg : args) {
				paths.add(Paths.get(arg));
			}
		}

		Tree tree = new Tree();
		tree.tree(paths);
	}

}
