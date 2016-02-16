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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.r2rmlmapping.triplesMap.PredicateObjectMap;
import model.r2rmlmapping.triplesMap.TriplesMap;
import view.R2RMLMain;
import view.triplesMap.ViewTriplesMap;

/**
 * Controller for the Triples Map view
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class ControllerTriplesMap implements ActionListener {

	private TriplesMap model;
	@SuppressWarnings("unused")
	private ViewTriplesMap view;
	
	private static Logger logger = LoggerFactory.getLogger(R2RMLMain.class);
	
	/**
	 * @param paramView
	 * @param paramModel
	 */
	public ControllerTriplesMap(ViewTriplesMap paramView, TriplesMap paramModel) {
	
		model = paramModel;
		view = paramView;
		
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
		
		logger.trace("ControllerTriplesMap --> Disparado cambio de modelo P-O");
        
		if (source.getName().equals("Add Predicate-Object")) {
			PredicateObjectMap predicateObjectMap = new PredicateObjectMap(0, model);
			predicateObjectMap.initPredicateObject();
			model.addPredicateObjectMap(predicateObjectMap);
		}
		else if (source.getName().equals("Update TriplesMap")) {
			if ((model.getR2RmlMapping().getAllTriplesMap().size() == 0) ||
				(model.getIdentifier() >= model.getR2RmlMapping().getAllTriplesMap().size())) {
		        model.getR2RmlMapping().addTriplesMap(model);
			}
			else {
				model.getR2RmlMapping().replaceTriplesMap(model.getIdentifier(), model);

			}

	        logger.trace("ControllerTriplesMap --> Añadido triples map a modelo r2rml");
		}
		
	}

}
