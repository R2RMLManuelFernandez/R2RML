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

package control.addMappingItem;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.r2rmlmapping.R2RMLMapping;
import model.r2rmlmapping.triplesMap.TriplesMap;
import view.ontology.ViewOntology;
import view.tableMapping.ViewTableMapping;

/**
 * Controller for the view of the table mapping
 *
 * @author Manuel Fernandez Perez
 * 
 */
public class ControllerAddNewMapping implements ActionListener {

	private TriplesMap mappingItem;
	private R2RMLMapping mappingModel;
	private ViewTableMapping viewMapping;
	private ViewOntology viewOntology;
	
	public ControllerAddNewMapping(R2RMLMapping mappingModel, TriplesMap mappingElement) {
		mappingItem = mappingElement;
		this.mappingModel = mappingModel;
	}
	
	/**
	 * Adds the new mapping element to mapping
	 * 
	 * @param source
	 */
	private void changeModel(Component source) {
		if (source.getName().equals("buttonAddMapping")) {
			TriplesMap item = copy(mappingItem);
			String dirtyIRI = mappingItem.getOntologyElement().getIRI();
			viewOntology.findInMappingNodes(dirtyIRI, true);
			//System.out.println("changeModel: " + dirtyIRI);
			mappingModel.addMapping(item);
		}
		// TODO controlar que pasa si el mappingItem es null
	}
	
	/**
	 * @param view
	 */
	public void setViewMapping(ViewTableMapping view) {
		viewMapping = view;
	}
	
	/**
	 * @param viewOntology the viewOntology to set
	 */
	public void setViewOntology(ViewOntology viewOntology) {
		this.viewOntology = viewOntology;
	}
	
    /**
     * Defensive copy used in changeModel.
     * 
     * @param item
     * @return
     */
    private TriplesMap copy(TriplesMap item) {
        TriplesMap newItem = new TriplesMap(item.getDatabaseColumns(), item.getOntologyElement());
        return newItem;
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Component source = (Component) e.getSource();
		changeModel(source);
		viewMapping.repaint();
	}

}
