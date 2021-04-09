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

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import de.topobyte.jafito.filemanager.config.Command;
import de.topobyte.jafito.filemanager.parameterizedcommands.ParameterDialog;
import de.topobyte.jafito.filemanager.parameterizedcommands.ParameterizedCommandLine;
import de.topobyte.jafito.filemanager.parameterizedcommands.ParameterizedCommands;

public class TestParameterDialog
{

	public static void main(String[] args)
	{
		Command command = new Command("zip files", "zip $output $input");

		ParameterizedCommandLine pcl = ParameterizedCommands
				.parse(command.getExec());
		ParameterDialog dialog = new ParameterDialog(pcl.getVariableNames());

		dialog.setVisible(true);
		dialog.pack();
		dialog.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}

		});
	}

}
