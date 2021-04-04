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

	public FileBrowserActions(FileBrowser browser)
	{
		quit = new QuitAction(browser);
		showHidden = new ShowHiddenFilesAction(browser);
		goUp = new GoUpAction(browser);
		goHome = new GoHomeAction(browser);
		refresh = new RefreshAction(browser);
	}

}
