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

package de.topobyte.jafito.filemanager.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import de.topobyte.system.utils.SystemPaths;
import lombok.Getter;

public class FileBrowserConfig
{

	@Getter
	private List<Bookmark> bookmarks = new ArrayList<>();

	public void load()
			throws ParserConfigurationException, SAXException, IOException
	{
		Path dirConfig = SystemPaths.HOME.resolve(".config/jafito");
		Path fileBookmarks = dirConfig.resolve("bookmarks.xml");
		if (Files.exists(fileBookmarks)) {
			BookmarksReader.read(this, fileBookmarks);
		}
	}

}
