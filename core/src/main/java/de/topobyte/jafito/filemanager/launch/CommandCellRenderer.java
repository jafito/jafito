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

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import de.topobyte.jafito.filemanager.actions.DirCommandAction;

public class CommandCellRenderer implements ListCellRenderer<DirCommandAction>
{

	private JLabel label = new JLabel();
	{
		label.setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(
			JList<? extends DirCommandAction> list, DirCommandAction action,
			int index, boolean isSelected, boolean cellHasFocus)
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

}
