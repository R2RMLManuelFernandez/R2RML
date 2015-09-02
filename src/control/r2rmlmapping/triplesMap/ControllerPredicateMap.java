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

import model.r2rmlmapping.triplesMap.PredicateMap;
import view.triplesMap.PredicateObject.ViewPredicate;

/**
 * Controller for the Predicate Map view
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class ControllerPredicateMap implements ActionListener {

	@SuppressWarnings("unused")
	private ViewPredicate view;
	private PredicateMap model;
	
	/**
	 * @param paramView
	 * @param paramModel
	 */
	public ControllerPredicateMap(ViewPredicate paramView, PredicateMap paramModel) {
		this.view = paramView;
		this.model = paramModel;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Component source = (Component) e.getSource();
		changeModel(source);

		
	}

	/**
	 * @param source
	 */
	private void changeModel(Component source) {
		
		if (source.getName().equals("Delete")) {
			this.model.getPredicateObjectMap().deletePredicateMap(model);
		}
		
	}

}
