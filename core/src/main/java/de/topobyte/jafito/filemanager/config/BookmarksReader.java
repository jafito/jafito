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
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.topobyte.melon.w3cdom.DomUtil;

public class BookmarksReader
{

	public static void read(FileBrowserConfig config, Path file)
			throws ParserConfigurationException, SAXException, IOException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(file.toFile());

		NodeList bookmarkNodes = doc.getElementsByTagName("bookmark");
		for (int i = 0; i < bookmarkNodes.getLength(); i++) {
			Node node = bookmarkNodes.item(i);
			NamedNodeMap attrs = node.getAttributes();
			String name = DomUtil.getValue(attrs, "name");
			String path = DomUtil.getValue(attrs, "path");

			Bookmark bookmark = new Bookmark(name, Paths.get(path));
			config.getBookmarks().add(bookmark);
		}
	}

}
