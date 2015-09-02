/*
 * Copyright 2015 Manuel Fernández Pérez
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package control.r2rmlmapping.triplesMap;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import model.r2rmlmapping.triplesMap.ColumnValueObjectMap;
import model.r2rmlmapping.triplesMap.ObjectMap;
import view.triplesMap.PredicateObject.ViewObjectColumnValue;

/**
 * @author Manuel Fernandez Perez
 * 
 * Controller for the ColumnValueObjectMap view *
 */
public class ControllerColumnValueObjectMap implements ActionListener {

	private ViewObjectColumnValue view;
	private ColumnValueObjectMap model;
	
	public ControllerColumnValueObjectMap(ViewObjectColumnValue viewObjectCV,
			ObjectMap objectMap) {
		this.view = viewObjectCV;
		this.model = (ColumnValueObjectMap) objectMap;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Component source = (Component) e.getSource();
		changeModel(source);
		
	}

	private void changeModel(Component source) {
		if (source.getName().equals("Delete")) {
			System.out.println("ControllerColumnValueObjectMap --> action Delete");
			model.getPredicateObjectMap().deleteObjectMap(model);
		}
		else if(source.getName().equals("Delete Columns")) {
			ArrayList<String> cols = view.getSelectedColumns();
			model.removeColumnsByName(cols);
		}
	}

}
