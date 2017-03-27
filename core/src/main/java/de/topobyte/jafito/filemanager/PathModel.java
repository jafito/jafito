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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.List;

import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;

import org.jdesktop.swingx.treetable.TreeTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.topobyte.jafito.util.Util;

public class PathModel implements TreeTableModel
{

	final static Logger logger = LoggerFactory.getLogger(PathModel.class);

	public static final int COLUMN_INDEX_NAME = 0;
	public static final int COLUMN_INDEX_SIZE = 1;
	public static final int COLUMN_INDEX_TYPE = 2;
	public static final int COLUMN_INDEX_DATE = 3;

	protected EventListenerList listenerList = new EventListenerList();

	private Path root;
	private boolean showHiddenFiles;

	public PathModel()
	{
		this(Paths.get("/"), true);
	}

	public PathModel(Path root, boolean showHiddenFiles)
	{
		this.root = root;
		this.showHiddenFiles = showHiddenFiles;
	}

	/*
	 * Columns
	 */

	@Override
	public int getColumnCount()
	{
		return 4;
	}

	@Override
	public int getHierarchicalColumn()
	{
		return 0;
	}

	@Override
	public Class<?> getColumnClass(int column)
	{
		switch (column) {
		case COLUMN_INDEX_NAME:
			return String.class;
		case COLUMN_INDEX_SIZE:
			return Long.class;
		case COLUMN_INDEX_TYPE:
			return String.class;
		case COLUMN_INDEX_DATE:
			return FileTime.class;
		default:
			return Object.class;
		}
	}

	@Override
	public String getColumnName(int column)
	{
		switch (column) {
		case COLUMN_INDEX_NAME:
			return "Name";
		case COLUMN_INDEX_SIZE:
			return "Size";
		case COLUMN_INDEX_TYPE:
			return "Type";
		case COLUMN_INDEX_DATE:
			return "Date Modified";
		default:
			return "Column " + column;
		}
	}

	/*
	 * Model
	 */

	@Override
	public Path getRoot()
	{
		return root;
	}

	@Override
	public boolean isLeaf(Object node)
	{
		if (node instanceof Path) {
			Path p = (Path) node;
			boolean isDirectory = Files.isDirectory(p);
			return !isDirectory;
		}
		return true;
	}

	@Override
	public int getChildCount(Object parent)
	{
		if (parent instanceof Path) {
			Path file = (Path) parent;
			try {
				List<Path> files = Util.getFiles(file, showHiddenFiles);
				if (files != null) {
					return files.size();
				}
			} catch (IOException e) {
				logger.error(String.format(
						"Unable to get determine child count [%s]", parent), e);
			}
		}
		return 0;
	}

	@Override
	public Path getChild(Object parent, int index)
	{
		if (parent instanceof Path) {
			Path file = (Path) parent;
			try {
				List<Path> files = Util.getFiles(file, showHiddenFiles);
				if (files != null) {
					return files.get(index);
				}
			} catch (IOException e) {
				logger.error(
						String.format("Unable to get child at index %d [%s]",
								index, parent),
						e);
			}
		}
		return null;
	}

	@Override
	public int getIndexOfChild(Object parent, Object child)
	{
		Path parentFile = (Path) parent;
		List<Path> files;
		try {
			files = Util.getFiles(parentFile, showHiddenFiles);

			for (int i = 0, len = files.size(); i < len; i++) {
				if (files.get(i).equals(child)) {
					return i;
				}
			}
		} catch (IOException e) {
			logger.error(String.format(
					"Unable to determine index of child [%s] [%s]", parent,
					child), e);
		}

		return -1;
	}

	@Override
	public Object getValueAt(Object node, int column)
	{
		if (node instanceof Path) {
			Path file = (Path) node;
			switch (column) {
			case 0:
				return name(file);
			case 1:
				return size(file);
			case 2:
				return type(file);
			case 3:
				return modificationTime(file);
			}
		}
		return null;
	}

	private Path name(Path file)
	{
		return file.getFileName();
	}

	private long size(Path file)
	{
		if (Files.isDirectory(file)) {
			return 0;
		}
		try {
			return Files.size(file);
		} catch (IOException e1) {
			return 0;
		}
	}

	private String type(Path file)
	{
		if (Files.isSymbolicLink(file)) {
			if (Files.isRegularFile(file)) {
				return "Symlink File";
			} else if (Files.isDirectory(file)) {
				return "Symlink Directory";
			}
			return "Symlink Other";
		} else if (Files.isRegularFile(file)) {
			return "File";
		} else if (Files.isDirectory(file)) {
			return "Directory";
		} else {
			return "Other";
		}
	}

	private FileTime modificationTime(Path file)
	{
		try {
			FileTime time = Files.getLastModifiedTime(file);
			return time;
		} catch (IOException e) {
			return null;
		}
	}

	/*
	 * Listeners
	 */

	@Override
	public void addTreeModelListener(TreeModelListener l)
	{
		listenerList.add(TreeModelListener.class, l);
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l)
	{
		listenerList.remove(TreeModelListener.class, l);
	}

	public TreeModelListener[] getTreeModelListeners()
	{
		return listenerList.getListeners(TreeModelListener.class);
	}

	/*
	 * Manipulation
	 */

	@Override
	public boolean isCellEditable(Object node, int column)
	{
		return false;
	}

	@Override
	public void setValueAt(Object value, Object node, int column)
	{
		// not implemented
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue)
	{
		// not implemented
	}

}
