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

import model.r2rmlmapping.triplesMap.JoinCondition;
import model.r2rmlmapping.triplesMap.ObjectMap;
import model.r2rmlmapping.triplesMap.ReferencingObjectMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import view.triplesMap.PredicateObject.ViewObjectReferenced;

/**
 * @author Manuel Fernandez Perez
 * 
 * Controller for the ReferncedObjectMap view *
 */
public class ControllerReferencedObjectMap implements ActionListener {

	private ViewObjectReferenced view;
	private ReferencingObjectMap model;
	
	private static Logger logger = LoggerFactory.getLogger(ControllerReferencedObjectMap.class);
	
	public ControllerReferencedObjectMap(ViewObjectReferenced viewObjectRef,
			ObjectMap objectMap) {
		this.view = viewObjectRef;
		this.model = (ReferencingObjectMap) objectMap;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Component source = (Component) e.getSource();
		changeModel(source);
		
	}

	private void changeModel(Component source) {
		if (source.getName().equals("Delete")) {
			logger.debug("ControllerReferencedObjectMap --> action Delete");
			model.getPredicateObjectMap().deleteObjectMap(model);
		}
		else if (source.getName().equals("Delete Join Condition")) {
			model.removeJoinCondition(view.getJoinConditionSelected());
			logger.debug("ControllerReferencedObjectMap --> Deberia haberse eliminado una JoinCondition");
		}
		else if (source.getName().equals("Add Join Condition")) {
			model.addJoinCondition(new JoinCondition());
			logger.debug("ControllerReferencedObjectMap --> Deberia haberse añadido una JoinCondition");
		}
	}

}
