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

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class Util
{

	public static List<Path> getFiles(Path parentFile, boolean showHiddenFiles)
			throws IOException
	{
		List<Path> list = list(parentFile, showHiddenFiles);
		Collections.sort(list,
				(Path o1, Path o2) -> String.CASE_INSENSITIVE_ORDER
						.compare(o1.toString(), o2.toString()));
		return list;
	}

	public static List<Path> list(Path directory, boolean showHiddenFiles)
			throws IOException
	{
		try (DirectoryStream<Path> stream = Files
				.newDirectoryStream(directory)) {
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
