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

package de.topobyte.jafito.filemanager;

import java.awt.Dimension;
import java.io.IOException;
import java.nio.file.Path;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import de.topobyte.jafito.filemanager.config.FileBrowserConfig;

public class BrowserTesting
{

	public static void test(Path path)
	{
		FileBrowserConfig config = new FileBrowserConfig();
		try {
			config.load();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			System.out.println("Error while loading configuration");
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run()
			{
				FileBrowserFrame frame = new FileBrowserFrame(path, config);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(new Dimension(800, 600));
				frame.setVisible(true);
			}
		});
	}

}
