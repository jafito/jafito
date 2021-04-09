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

package de.topobyte.jafito.filemanager.parameterizedcommands;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class ParameterizedCommandLine
{

	@Getter
	private List<Part> parts = new ArrayList<>();

	@Getter
	private List<String> variableNames;

	public ParameterizedCommandLine(List<Part> parts,
			List<String> variableNames)
	{
		this.parts = parts;
		this.variableNames = variableNames;
	}

}
