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

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.tree.TreePath;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.TreeTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.topobyte.awt.util.GridBagConstraintsEditor;
import de.topobyte.jafito.filemanager.OpenWithDialog.Result;
import de.topobyte.jafito.filemanager.actions.FileBrowserActions;
import de.topobyte.jafito.filemanager.actions.OpenSelectedAction;
import de.topobyte.jafito.filemanager.cellrenderers.DateCellRenderer;
import de.topobyte.jafito.filemanager.cellrenderers.OurDefaultTableCellRenderer;
import de.topobyte.jafito.filemanager.cellrenderers.SizeCellRenderer;
import de.topobyte.jafito.filemanager.config.FileBrowserConfig;
import lombok.Getter;

public class FileBrowser extends JPanel
{

	private static final long serialVersionUID = -2494758020181769713L;

	final static Logger logger = LoggerFactory.getLogger(FileBrowser.class);

	public static interface LocationListener
	{

		public void locationChanged(Path newLocation);

	}

	@Getter
	private FileBrowserActions actions;

	private JXTreeTable treeTable;

	@Getter
	private Path path;
	@Getter
	private FileBrowserConfig config;
	private boolean showHiddenFiles = true;

	@Getter
	private JTextField address = new JTextField();
	private JButton up;
	private JButton home;
	private JButton refresh;

	private List<LocationListener> locationListeners = new ArrayList<>();

	private TableCellRenderer defaultRenderer = new OurDefaultTableCellRenderer();
	private SizeCellRenderer sizeRenderer = new SizeCellRenderer();
	private DateCellRenderer dateRenderer = new DateCellRenderer();

	private static final String ID_ACTION_OPEN_SELECTED = "openSelected";

	public FileBrowser(Path path, FileBrowserConfig config)
	{
		super(new BorderLayout());

		this.path = path;
		this.config = config;

		actions = new FileBrowserActions(this);

		// file browser tree table

		treeTable = new JXTreeTable();
		JScrollPane scroller = new JScrollPane(treeTable);
		treeTable.addMouseListener(new TreeMouseHandler());

		treeTable.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
				.getParent().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
						ID_ACTION_OPEN_SELECTED);
		treeTable.getActionMap().put(ID_ACTION_OPEN_SELECTED,
				new OpenSelectedAction(this));

		// toolbar

		up = toolbarButton(actions.getGoUp());
		home = toolbarButton(actions.getGoHome());
		refresh = toolbarButton(actions.getRefresh());

		JPanel toolbar = createToolbar();

		// layout
		add(scroller);
		add(toolbar, BorderLayout.NORTH);

		// init

		updateAddressText();
		refreshModel();

		// actions

		address.addActionListener(e -> goToAddressLocation());
	}

	private JButton toolbarButton(Action action)
	{
		JButton button = new JButton(action);
		button.setHideActionText(true);
		button.setMargin(new Insets(2, 2, 2, 2));
		return button;
	}

	private JPanel createToolbar()
	{
		JPanel bar = new JPanel(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		GridBagConstraintsEditor edit = new GridBagConstraintsEditor(c);

		edit.fill(GridBagConstraints.BOTH);
		edit.gridY(0).gridX(GridBagConstraints.RELATIVE);

		edit.weightX(0);
		bar.add(up, c);
		bar.add(home, c);
		edit.weightX(1);
		bar.add(address, c);
		edit.weightX(0);
		bar.add(refresh, c);

		return bar;
	}

	private TreeTableModel createTreeModel() throws IOException
	{
		return new PathModel(path, showHiddenFiles);
	}

	private void updateAddressText()
	{
		address.setText(path.toString());
	}

	public void goUp()
	{
		Path parent = path.getParent();
		if (parent == null) {
			return;
		}
		tryGoToPath(parent);
	}

	public void go(Path parent)
	{
		tryGoToPath(parent);
	}

	public void refreshModel()
	{
		try {
			treeTable.setTreeTableModel(createTreeModel());
		} catch (IOException e) {
			logger.error("Error while refreshing model", e);
		}
		treeTable.packColumn(treeTable.getHierarchicalColumn(), -1);

		// setup column renderers

		treeTable.setDefaultRenderer(Object.class, defaultRenderer);

		TableColumn sizeColumn = treeTable
				.getColumn(PathModel.COLUMN_INDEX_SIZE);
		sizeColumn.setCellRenderer(sizeRenderer);

		TableColumn dateColumn = treeTable
				.getColumn(PathModel.COLUMN_INDEX_DATE);
		dateColumn.setCellRenderer(dateRenderer);
	}

	private void goToAddressLocation()
	{
		String text = address.getText();
		Path path = Paths.get(text);
		tryGoToPath(path);
	}

	private void tryGoToPath(Path path)
	{
		if (!Files.exists(path)) {
			return;
		}
		if (!Files.isDirectory(path)) {
			return;
		}
		if (!Files.isReadable(path)) {
			return;
		}
		this.path = path;

		updateAddressText();
		refreshModel();
		fireLocationListeners();
	}

	public boolean isShowHiddenFiles()
	{
		return showHiddenFiles;
	}

	public void setShowHiddenFiles(boolean showHiddenFiles)
	{
		this.showHiddenFiles = showHiddenFiles;
		refreshModel();
	}

	/*
	 * listeners
	 */

	public void addLocationListener(LocationListener listener)
	{
		locationListeners.add(listener);
	}

	public void removeLocationListener(LocationListener listener)
	{
		locationListeners.remove(listener);
	}

	private void fireLocationListeners()
	{
		Path newLocation = path;
		for (LocationListener listener : locationListeners) {
			listener.locationChanged(newLocation);
		}
	}

	/*
	 * mouse handling
	 */

	private class TreeMouseHandler extends MouseAdapter
	{

		@Override
		public void mousePressed(MouseEvent e)
		{
			// The event is consumed by the tree table if the click was on the
			// expand / collapse control in the tree column. In that case we
			// stop processing to avoid double clicks on the control to trigger
			// a path change.
			if (e.isConsumed()) {
				return;
			}
			// Handle double clicks
			if (e.getClickCount() == 2) {
				TreePath treePath = treeTable.getPathForLocation(e.getX(),
						e.getY());
				if (treePath == null) {
					return;
				}
				Path last = (Path) treePath.getLastPathComponent();
				open(last);
			}
		}

	}

	/*
	 * Mouse/keyboard helpers
	 */

	public void openSelected()
	{
		if (treeTable.getSelectedRowCount() == 1) {
			int row = treeTable.getSelectedRow();
			TreePath treePath = treeTable.getPathForRow(row);
			Path last = (Path) treePath.getLastPathComponent();
			open(last);
		}
	}

	public void open(Path path)
	{
		if (Files.isDirectory(path)) {
			tryGoToPath(path);
		} else {
			String filename = path.getFileName().toString();
			boolean isPdf = filename.endsWith(".pdf");
			boolean isPng = filename.endsWith(".png");
			boolean isJpg = filename.endsWith(".jpg");
			if (isPdf) {
				openPdf(path);
			} else if (isPng || isJpg) {
				openImage(path);
			} else {
				showOpenWithDialog(path);
			}
		}
	}

	private void showOpenWithDialog(Path file)
	{
		Result result = OpenWithDialog.showDialog(this);
		if (result.isValid()) {
			Util.run(result.getCommand(), file);
		}
	}

	private void openPdf(Path file)
	{
		Util.run("evince", file);
	}

	private void openImage(Path file)
	{
		Util.run("eog", file);
	}

	/*
	 * Debugging
	 */

	public void printKeyBindings()
	{
		Util.printInputMaps(treeTable);
	}

}
