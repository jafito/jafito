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

package de.topobyte.jafito.filemanager.launch;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import de.topobyte.awt.util.GridBagConstraintsEditor;
import de.topobyte.jafito.filemanager.actions.DirCommandAction;
import de.topobyte.jafito.filemanager.config.Command;
import de.topobyte.swing.util.list.ArrayListModel;

public class LaunchDialog extends JDialog implements ActionListener
{

	private static final long serialVersionUID = 1L;

	public LaunchDialog()
	{
		setTitle("Run…");
		setup();
	}

	private void setup()
	{
		List<DirCommandAction> actions = new ArrayList<>();
		actions.add(new DirCommandAction(null, new Command("Nemo", "nemo")));
		actions.add(
				new DirCommandAction(null, new Command("Thunar", "thunar")));
		actions.add(new DirCommandAction(null,
				new Command("Konqueror", "konqueror")));

		JPanel panel = new JPanel(new GridBagLayout());
		setContentPane(panel);

		GridBagConstraintsEditor c = new GridBagConstraintsEditor();
		c.fill(GridBagConstraints.BOTH).weight(1, 1);

		JList<DirCommandAction> list = new JList<>();
		JScrollPane jsp = new JScrollPane(list);
		panel.add(jsp, c.getConstraints());

		ArrayListModel<DirCommandAction> model = new ArrayListModel<>();
		model.addAll(actions, 0);
		list.setModel(model);

		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		list.setCellRenderer(new CommandCellRenderer());
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));

		JPanel buttonGrid = new JPanel();
		buttonGrid.setLayout(new GridLayout(1, 2));

		JButton buttonCancel = new JButton("Cancel");
		JButton buttonOk = new JButton("Ok");

		buttonGrid.add(buttonCancel);
		buttonGrid.add(buttonOk);

		buttons.add(Box.createHorizontalGlue());
		buttons.add(buttonGrid);

		c.gridY(1);
		c.weightY(0);
		panel.add(buttons, c.getConstraints());

		buttonCancel.setActionCommand("cancel");
		buttonOk.setActionCommand("ok");
		buttonCancel.addActionListener(this);
		buttonOk.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals("ok")) {
			ok();
		} else if (e.getActionCommand().equals("cancel")) {
			cancel();
		}
	}

	private void ok()
	{
		dispose();
	}

	private void cancel()
	{
		dispose();
	}

}
