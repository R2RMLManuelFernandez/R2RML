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

package view.addMappingItem;

import java.awt.event.ActionListener;
import java.util.EventListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.TransferHandler;

import model.mapping.MappingElement;
import net.miginfocom.swing.MigLayout;

/**
 * View to add a mapping term to the mapping. Observer
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class ViewAddMappingItem extends JPanel implements Observer {

	private static final long serialVersionUID = -3097073698486756385L;

	private MappingElement model;
	private MappingTextField textFieldOntology;
	private MappingTextField textFieldDatabase;
	private JButton buttonAddMapping;
	
	/**
	 * Create the panel.
	 */
	public ViewAddMappingItem() {
		setLayout(new MigLayout("", "[grow][grow]", "[][50.00][10.00][]"));
		
		textFieldOntology = new MappingTextField();
		textFieldOntology.setHorizontalAlignment(SwingConstants.CENTER);
		add(textFieldOntology, "cell 0 1,grow");
		textFieldOntology.setColumns(10);
		
		textFieldDatabase = new MappingTextField();
		textFieldDatabase.setHorizontalAlignment(SwingConstants.CENTER);
		add(textFieldDatabase, "cell 1 1,grow");
		textFieldDatabase.setColumns(10);
		
		buttonAddMapping = new JButton("Add Mapping");
		buttonAddMapping.setName("buttonAddMapping");
		add(buttonAddMapping, "cell 0 3 2 1,alignx center");

	}

	/**
	 * @param controller
	 */
	public void setController(EventListener controller) {
		buttonAddMapping.addActionListener((ActionListener) controller);
	}
	
	/**
	 * @param mappingElement
	 */
	public void setModel(MappingElement mappingElement) {
		this.model = mappingElement;
		textFieldOntology.setModel(mappingElement);
		textFieldDatabase.setModel(mappingElement);
		mappingElement.addObserver(this);
	}
	
	/**
	 * @param transferhandler
	 */
	public void setOntologyTransferHandler(TransferHandler transferhandler) {
		textFieldOntology.setTransferHandler(transferhandler);
	}
	
	/**
	 * @param transferhandler
	 */
	public void setDatabaseTransferHandler(TransferHandler transferhandler) {
		textFieldDatabase.setTransferHandler(transferhandler);
	}
	
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if (model.getOntologyElement().equals(null)) {
			System.out.println("El elemneto de la ontologia del Mapping es null");
		}
		String ontElementName = model.getOntologyElement().getDisplayName();
		textFieldOntology.setText(ontElementName);
		String columnName = model.getDatabaseColumn().getColumnName();
		textFieldDatabase.setText(columnName);
		repaint();
	}
}
