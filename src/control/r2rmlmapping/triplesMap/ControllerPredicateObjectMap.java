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

import javax.swing.JFrame;

import model.r2rmlmapping.triplesMap.ColumnValueObjectMap;
import model.r2rmlmapping.triplesMap.ObjectMap;
import model.r2rmlmapping.triplesMap.PredicateMap;
import model.r2rmlmapping.triplesMap.PredicateObjectMap;
import model.r2rmlmapping.triplesMap.ReferenceObjectMap;
import model.r2rmlmapping.triplesMap.TriplesMap;
import view.triplesMap.PredicateObject.ParentTriplesMapSelector;
import view.triplesMap.PredicateObject.ViewPredicateObject;

/**
 * @author Manuel Fernandez Perez
 * 
 * Controller for the Predicate-Object view *
 */
public class ControllerPredicateObjectMap implements ActionListener {

	@SuppressWarnings("unused")
	private ViewPredicateObject view;
	private PredicateObjectMap model;
	private JFrame frame;
	
	/**
	 * @param paramView
	 * @param paramModel
	 */
	public ControllerPredicateObjectMap(JFrame frame, ViewPredicateObject paramView, PredicateObjectMap paramModel) {
		view = paramView;
		model = paramModel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		Component source = (Component) e.getSource();
		changeModel(source);
		
	}

	/**
	 * @param source
	 */
	private void changeModel(Component source) {

		if (source.getName().equals("Add Predicate")) {
			System.out.println("ControllerPredicateObject --> action Add Predicate");
			PredicateMap predicate = new PredicateMap(model);
			model.addPredicateMap(predicate);
		}
		else if (source.getName().equals("Add Col Obj")) {
			System.out.println("ControllerPredicateObject --> action Add Col Obj");
			ObjectMap object = new ColumnValueObjectMap(model);
			model.addObjectMap(object);
		}
		else if (source.getName().equals("Add Ref Obj")) {
			System.out.println("ControllerPredicateObject --> action Add Ref Obj");
			ArrayList<TriplesMap> posiblesParentTriplesMaps = model.getTriplesMap().getR2RmlMapping().getAllTriplesMap();
			ParentTriplesMapSelector parentTriplesMapSelector = new ParentTriplesMapSelector(frame, posiblesParentTriplesMaps.size());
			parentTriplesMapSelector.pack();
			parentTriplesMapSelector.setLocationRelativeTo(frame);
			parentTriplesMapSelector.setVisible(true);
			if (!parentTriplesMapSelector.checkCancel()) {
				int parentTriplesMapIndex = parentTriplesMapSelector.getParentTriplesMapSelected();
				ReferenceObjectMap object = new ReferenceObjectMap(model);
				object.setParentTriplesMap(model.getTriplesMap().getR2RmlMapping().getTriplesMap(parentTriplesMapIndex));
				model.addObjectMap(object);
			}
		}
		else if (source.getName().equals("Delete")) {
			System.out.println("ControllerPredicateObject --> action Delete");
			model.getTriplesMap().deletePredicateObjectMap(model);
		} 
		
	}

}
