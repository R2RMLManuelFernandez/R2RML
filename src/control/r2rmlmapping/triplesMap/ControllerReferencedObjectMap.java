/*
 * Copyright 2015 Manuel Fern�ndez P�rez
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
import view.triplesMap.PredicateObject.ViewObjectReferenced;

/**
 * @author Manuel Fernandez Perez
 * 
 * Controller for the ReferncedObjectMap view *
 */
public class ControllerReferencedObjectMap implements ActionListener {

	private ViewObjectReferenced view;
	private ReferencingObjectMap model;
	
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
			System.out.println("ControllerReferencedObjectMap --> action Delete");
			model.getPredicateObjectMap().deleteObjectMap(model);
		}
		else if (source.getName().equals("Delete Join Condition")) {
			model.removeJoinCondition(view.getJoinConditionSelected());
		}
		else if (source.getName().equals("Add Join Condition")) {
			model.addJoinCondition(new JoinCondition());
	        System.out.println("ControllerReferencedObjectMap --> Deberia habere a�adido una JoinCondition");
		}
	}

}
