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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.topobyte.awt.util.GridBagConstraintsEditor;
import de.topobyte.jafito.filemanager.Util;
import de.topobyte.jafito.filemanager.util.ButtonList;
import lombok.Getter;

public class ParameterDialog extends JDialog
{

	private static final long serialVersionUID = 1L;

	@Getter
	private Map<String, JTextField> inputs = new HashMap<>();

	@Getter
	private ButtonList buttons;

	public ParameterDialog(List<String> variableNames)
	{
		setTitle("Define parameters");

		JPanel panel = new JPanel(new GridBagLayout());
		setContentPane(panel);

		GridBagConstraintsEditor c = new GridBagConstraintsEditor();
		c.fill(GridBagConstraints.BOTH);

		int row = 0;
		for (String name : variableNames) {
			JLabel label = new JLabel(name + ":");
			Util.defaultRightPadding(label);

			JTextField input = new JTextField(30);
			inputs.put(name, input);

			c.gridPos(0, row).weightX(0);
			panel.add(label, c.getConstraints());
			c.gridPos(1, row).weightX(1);
			panel.add(input, c.getConstraints());

			row++;
		}

		buttons = new ButtonList();
		c.gridPos(0, row).weightY(0).gridWidth(2);
		panel.add(buttons, c.getConstraints());
	}

}
