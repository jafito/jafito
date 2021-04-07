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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import de.topobyte.awt.util.GridBagConstraintsEditor;
import de.topobyte.jafito.filemanager.actions.DirCommandAction;
import de.topobyte.swing.util.list.ArrayListModel;

public class LaunchDialog extends JDialog implements ActionListener
{

	private static final long serialVersionUID = 1L;

	private List<DirCommandAction> actions;

	private JList<DirCommandAction> list;
	private JButton buttonOk;

	public LaunchDialog(Component parent, List<DirCommandAction> actions)
	{
		this.actions = actions;
		setTitle("Run…");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(new Dimension(400, 300));
		setLocationRelativeTo(parent);
		init();
	}

	private void init()
	{
		JPanel panel = new JPanel(new GridBagLayout());
		setContentPane(panel);

		GridBagConstraintsEditor c = new GridBagConstraintsEditor();
		c.fill(GridBagConstraints.BOTH).weight(1, 1);

		list = new JList<>();
		JScrollPane jsp = new JScrollPane(list);
		panel.add(jsp, c.getConstraints());

		ArrayListModel<DirCommandAction> model = new ArrayListModel<>();
		model.addAll(actions, 0);
		list.setModel(model);

		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		list.addListSelectionListener(e -> {
			if (e.getValueIsAdjusting()) {
				return;
			}
			syncButtonState();
		});

		list.setCellRenderer(new CommandCellRenderer());
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));

		JPanel buttonGrid = new JPanel();
		buttonGrid.setLayout(new GridLayout(1, 2));

		JButton buttonCancel = new JButton("Cancel");
		buttonOk = new JButton("Ok");

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

		syncButtonState();

		list.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (e.getButton() == MouseEvent.BUTTON1
						&& e.getClickCount() == 2) {
					ok();
				}
			}

		});

		list.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					ok();
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					cancel();
				}
			}

		});
	}

	private void syncButtonState()
	{
		int selected = list.getSelectedIndex();
		boolean valid = selected != -1;
		buttonOk.setEnabled(valid);
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
		run();
		dispose();
	}

	private void cancel()
	{
		dispose();
	}

	private void run()
	{
		DirCommandAction command = list.getSelectedValue();
		command.run();
	}

}
