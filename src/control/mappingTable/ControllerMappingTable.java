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

package control.mappingTable;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;

import model.mapping.MappingTable;
import model.ontology.OntologyElement;
import view.ontology.ViewOntologyTree;
import view.tableMapping.ViewTableMapping;

/**
 * Controller class for the view and model of the mapping
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class ControllerMappingTable implements ActionListener {

	private MappingTable model;
	private ViewTableMapping view;
	private ViewOntologyTree viewOntology;
	private OntologyElement dirtyElement;
	
	public ControllerMappingTable(MappingTable m) {
		this.model = m;
	}

	/**
	 * @param source
	 */
	private void changeModel(Component source) {
		if (source.getName().equals("btnDeleteMapping")) {
			JTable table = view.getTable();
			int row = table.getSelectedRow();
			if (row != -1)
				dirtyElement = (OntologyElement) model.getValueAt(row, 0);
				String dirtyIRI = dirtyElement.getIRI();
				viewOntology.findInMappingNodes(dirtyIRI, false);
				model.removeMappingElement(row);
				
		}
		else if (source.getName().equals("btnDeleteAll")) {
			int rows = model.getRowCount();
			for (int i = 0; i < rows; i++) {
				dirtyElement = (OntologyElement) model.getValueAt(0, 0);
				String dirtyIRI = dirtyElement.getIRI();
				viewOntology.findInMappingNodes(dirtyIRI, false);
				model.removeMappingElement(0);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Component source = (Component)e.getSource();
		changeModel(source);
	}

	/**
	 * @return
	 */
	public MappingTable getModel() {
		return model;
	}

	/**
	 * @param view
	 */
	public void setView(ViewTableMapping view) {
		this.view = view;
	}
	
	/**
	 * @param viewOntology the viewOntology to set
	 */
	public void setViewOntology(ViewOntologyTree viewOntology) {
		this.viewOntology = viewOntology;
	}
	
}
