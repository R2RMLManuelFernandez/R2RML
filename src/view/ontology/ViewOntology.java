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

package view.ontology;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultTreeModel;

import model.mapping.MappingElement;
import net.miginfocom.swing.MigLayout;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import control.ontology.OntologyModelConstructor;
import control.ontology.OntologyTreeModelConstructor;

/**
 * Displays the ontology model in various trees
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class ViewOntology extends JPanel {

	private static final long serialVersionUID = 2335706050345687194L;

	private OntologyModelConstructor ontologyModel = null;
	private OntologyTreeModelConstructor ontologyTreeModel = null;

	private DefaultTreeModel modelGeneral;
	private DefaultTreeModel modelClass;
	private DefaultTreeModel modelObjectProperty;
	private DefaultTreeModel modelDataProperty;

	private ViewOntologyTree treePanelGeneral;
	private ViewOntologyTree treePanelClasses;
	private ViewOntologyTree treePanelObjectProperties;
	private ViewOntologyTree treePanelDataProperties;
	
	/**
	 * Create the panel.
	 */
	public ViewOntology() {
		setLayout(new MigLayout("", "[325px,grow]", "[14px][563px,grow]"));
		
		//Base Panel
		
		JLabel labelOntologyIRI = new JLabel("Ontology:");
		add(labelOntologyIRI, "cell 0 0,alignx left,aligny top");
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, "cell 0 1,grow");
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		//General Tree ---------------------------------------------------------------------------------------------

        treePanelGeneral = new ViewOntologyTree();
		tabbedPane.addTab("All", null, treePanelGeneral, "All elements in ontology");
		
		//Class Tree ---------------------------------------------------------------------------------------------
		
		treePanelClasses = new ViewOntologyTree();
		tabbedPane.addTab("Classes", null, treePanelClasses, "Classes");
		
		//Object Porperties Tree ---------------------------------------------------------------------------------
		
		treePanelObjectProperties = new ViewOntologyTree();
		tabbedPane.addTab("Object Properties", null, treePanelObjectProperties, "Object Properties");
		
		//Data Porperties Tree ------------------------------------------------------------------------------------
		
		treePanelDataProperties = new ViewOntologyTree();
		tabbedPane.addTab("Data Properties", null, treePanelDataProperties, "Data Properties");

	}
	
	/**
	 * Sets the transferhandler for the general tree
	 * 
	 * @param paramTransferHandler
	 */
	public void setOntologyTransferHandler(TransferHandler paramTransferHandler) {
		
		treePanelGeneral.setTreeTransferHandler(paramTransferHandler);
		treePanelClasses.setTreeTransferHandler(paramTransferHandler);
		treePanelObjectProperties.setTreeTransferHandler(paramTransferHandler);
		treePanelDataProperties.setTreeTransferHandler(paramTransferHandler);
		
	}
		
	/**
	 * Creates a new ontology model and loads it into the tree
	 * 
	 * @param paramOntologySource
	 * @throws OWLOntologyCreationException
	 */
	public void setOntologyModel(String paramOntologySource) throws OWLOntologyCreationException {
		
		try {
			ontologyModel = new OntologyModelConstructor(paramOntologySource);
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			throw(e);
		}
		ontologyTreeModel = new OntologyTreeModelConstructor(ontologyModel.getThing(), ontologyModel.getTopObjectProperty(),
								ontologyModel.getTopDataProperty());
		
		this.modelGeneral = ontologyTreeModel.getOntologyTreeGeneralModel();
//		this.treeAllOntology.setModel(modelGeneral);	
		treePanelGeneral.setTreeModel(modelGeneral);
		
		this.modelClass = ontologyTreeModel.getOntologyTreeClassModel();
//		this.treeClassOntology.setModel(modelClass);
		treePanelClasses.setTreeModel(modelClass);
		
		this.modelObjectProperty = ontologyTreeModel.getOntologyTreeObjectPropertiesModel();
//		this.treeObjectPropertyOntology.setModel(modelObjectProperty);
		treePanelObjectProperties.setTreeModel(modelObjectProperty);
		
		this.modelDataProperty = ontologyTreeModel.getOntologyTreeDataPropertiesModel();
//		this.treeDataPropertyOntology.setModel(modelDataProperty);
		treePanelDataProperties.setTreeModel(modelDataProperty);
		
	}
	
	public void setMappingItem(MappingElement paramMappingItem) {
		
		treePanelGeneral.setMappingItem(paramMappingItem);
		treePanelClasses.setMappingItem(paramMappingItem);
		treePanelObjectProperties.setMappingItem(paramMappingItem);
		treePanelDataProperties.setMappingItem(paramMappingItem);
		
	}

	/**
	 * Find the nodes in the ontology tree which label begins whith the pattern
	 */
	public void findInMappingNodes(String iri, Boolean dirt) {
		
		treePanelClasses.findInMappingNodes(iri, dirt);
		treePanelObjectProperties.findInMappingNodes(iri, dirt);
		treePanelDataProperties.findInMappingNodes(iri, dirt);

		treePanelGeneral.repaintTree();
		treePanelClasses.repaintTree();
		treePanelObjectProperties.repaintTree();
		treePanelDataProperties.repaintTree();
	}

}
