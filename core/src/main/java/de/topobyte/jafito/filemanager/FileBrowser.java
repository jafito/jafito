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
import java.io.IOException;
import java.nio.file.Path;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.TreeTableModel;

import de.topobyte.awt.util.GridBagConstraintsEditor;

public class FileBrowser extends JPanel
{

	private static final long serialVersionUID = -2494758020181769713L;

	private JXTreeTable treeTable;

	private Path path;

	private JTextField address = new JTextField();
	private JButton refresh = new JButton("refresh");

	public FileBrowser(Path path)
	{
		super(new BorderLayout());

		this.path = path;

		// file browser tree table

		treeTable = new JXTreeTable();
		JScrollPane scroller = new JScrollPane(treeTable);

		// toolbar

		JPanel toolbar = createToolbar();

		// layout
		add(scroller);
		add(toolbar, BorderLayout.NORTH);

		// init

		refreshModel();

		// actions

		refresh.addActionListener(e -> refreshModel());
	}

	private JPanel createToolbar()
	{
		JPanel bar = new JPanel(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		GridBagConstraintsEditor edit = new GridBagConstraintsEditor(c);

		edit.fill(GridBagConstraints.BOTH);
		edit.gridY(0).gridX(GridBagConstraints.RELATIVE);
		edit.weightX(1);
		bar.add(address, c);
		edit.weightX(0);
		bar.add(refresh, c);

		return bar;
	}

	private TreeTableModel createTreeModel() throws IOException
	{
		return new PathModel(path);
	}

	public void refreshModel()
	{
		try {
			treeTable.setTreeTableModel(createTreeModel());
		} catch (IOException e) {
			System.out.println("Error while refreshing model");
			e.printStackTrace();
		}
		treeTable.packColumn(treeTable.getHierarchicalColumn(), -1);
	}

}
