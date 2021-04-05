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

package de.topobyte.jafito.filemanager;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

import de.topobyte.awt.util.GridBagConstraintsEditor;
import de.topobyte.jafito.filemanager.actions.DirCommandAction;
import de.topobyte.jafito.filemanager.config.Command;
import de.topobyte.swing.util.list.ArrayListModel;

public class TestLaunchDialog
{

	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Run…");

		setup(frame);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(400, 300));
		frame.setVisible(true);
	}

	private static void setup(JFrame frame)
	{
		List<DirCommandAction> actions = new ArrayList<>();
		actions.add(new DirCommandAction(null, new Command("Nemo", "nemo")));
		actions.add(
				new DirCommandAction(null, new Command("Thunar", "thunar")));
		actions.add(new DirCommandAction(null,
				new Command("Konqueror", "konqueror")));

		JPanel panel = new JPanel(new GridBagLayout());
		frame.setContentPane(panel);

		GridBagConstraintsEditor c = new GridBagConstraintsEditor();
		c.fill(GridBagConstraints.BOTH).weight(1, 1);

		JList<DirCommandAction> list = new JList<>();
		JScrollPane jsp = new JScrollPane(list);
		panel.add(jsp, c.getConstraints());

		ArrayListModel<DirCommandAction> model = new ArrayListModel<>();
		model.addAll(actions, 0);
		list.setModel(model);

		list.setCellRenderer(new ListCellRenderer<DirCommandAction>() {

			private JLabel label = new JLabel();
			{
				label.setOpaque(true);
			}

			@Override
			public Component getListCellRendererComponent(
					JList<? extends DirCommandAction> list,
					DirCommandAction action, int index, boolean isSelected,
					boolean cellHasFocus)
			{
				Icon icon = (Icon) action.getValue(Action.SMALL_ICON);
				label.setIcon(icon);
				label.setText(action.getCommand().getName());

				if (isSelected) {
					label.setBackground(list.getSelectionBackground());
					label.setForeground(list.getSelectionForeground());
				} else {
					label.setBackground(list.getBackground());
					label.setForeground(list.getForeground());
				}

				return label;
			}

		});
	}

}
