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

package de.topobyte.jafito.filemanager.actions;

import de.topobyte.jafito.filemanager.FileBrowser;
import lombok.Getter;

public class FileBrowserActions
{

	@Getter
	private QuitAction quit;
	@Getter
	private ShowHiddenFilesAction showHidden;
	@Getter
	private GoUpAction goUp;
	@Getter
	private GoHomeAction goHome;
	@Getter
	private RefreshAction refresh;
	@Getter
	private OpenLocationAction openLocation;
	@Getter
	private DirCommandsAction dirCommands;

	public FileBrowserActions(FileBrowser browser)
	{
		quit = new QuitAction(browser);
		showHidden = new ShowHiddenFilesAction(browser);
		goUp = new GoUpAction(browser);
		goHome = new GoHomeAction(browser);
		refresh = new RefreshAction(browser);
		openLocation = new OpenLocationAction(browser);
		dirCommands = new DirCommandsAction(browser);
	}

}
