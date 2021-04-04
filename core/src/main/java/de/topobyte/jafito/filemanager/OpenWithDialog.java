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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.topobyte.awt.util.GridBagConstraintsEditor;
import lombok.Getter;

public class OpenWithDialog
{

	public static class Result
	{

		@Getter
		private boolean valid = false;
		@Getter
		private String command = null;

	}

	public static Result showDialog(FileBrowser browser)
	{
		JPanel panel = new JPanel(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		GridBagConstraintsEditor ce = new GridBagConstraintsEditor(c);

		JLabel labelCommand = new JLabel("Command:");
		JTextField inputCommand = new JTextField(20);

		Util.defaultRightPadding(Arrays.asList(labelCommand));

		ce.anchor(GridBagConstraints.LINE_START);

		ce.gridX(0);
		panel.add(labelCommand, c);
		ce.gridX(1);
		panel.add(inputCommand, c);

		JOptionPane optionPane = new JOptionPane(panel,
				JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION) {

			private static final long serialVersionUID = 1L;

			@Override
			public void selectInitialValue()
			{
				inputCommand.requestFocusInWindow();
			}

		};

		optionPane.createDialog(browser, "Open file...").setVisible(true);

		Result result = new Result();

		if (optionPane.getValue() == null) {
			return result;
		}

		if (((int) optionPane.getValue()) == JOptionPane.YES_OPTION) {
			result.command = inputCommand.getText();
			result.valid = true;
		}

		return result;
	}

}
