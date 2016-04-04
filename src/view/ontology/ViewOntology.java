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

package view.ontology;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.tree.DefaultTreeModel;

import model.r2rmlmapping.triplesMap.TriplesMap;
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
/**
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
	
	private JFrame frame;
	
	private JTabbedPane tabbedPane;
	
	private TriplesMap mappingItem;
	
	/**
	 * Create the panel.
	 */
	public ViewOntology(JFrame frame) {
		
		setLayout(new MigLayout("", "[325px,grow]", "[14px][563px,grow]"));
		
		this.frame = frame;
		
		//Base Panel
		
		JLabel labelOntologyIRI = new JLabel("Ontology:");
		add(labelOntologyIRI, "cell 0 0,alignx left,aligny top");
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, "cell 0 1,grow");
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		//General Tree ---------------------------------------------------------------------------------------------

        treePanelGeneral = new ViewOntologyTree(frame);
		tabbedPane.addTab("All", null, treePanelGeneral, "All elements in ontology");
		
		//Class Tree ---------------------------------------------------------------------------------------------
		
		treePanelClasses = new ViewOntologyTree(frame);
		tabbedPane.addTab("Classes", null, treePanelClasses, "Classes");
		
		//Object Porperties Tree ---------------------------------------------------------------------------------
		
		treePanelObjectProperties = new ViewOntologyTree(frame);
		tabbedPane.addTab("Object Properties", null, treePanelObjectProperties, "Object Properties");
		
		//Data Porperties Tree ------------------------------------------------------------------------------------
		
		treePanelDataProperties = new ViewOntologyTree(frame);
		tabbedPane.addTab("Data Properties", null, treePanelDataProperties, "Data Properties");

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
	
	/**
	 * @param paramMappingItem
	 */
	public void setMappingItem(TriplesMap paramMappingItem) {
		
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
	
	/**
	 * @param e
	 */
	public void changeLabelsFragments(Boolean newShowLabesls) {

		if (ontologyModel != null) {
				
			ontologyModel.changeModelToString(newShowLabesls);
			
			tabbedPane.removeAll();
			add(tabbedPane, "cell 0 1,grow");
			tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
			
			//General Tree ---------------------------------------------------------------------------------------------

	        treePanelGeneral = new ViewOntologyTree(frame);
			tabbedPane.addTab("All", null, treePanelGeneral, "All elements in ontology");
			
			//Class Tree ---------------------------------------------------------------------------------------------
			
			treePanelClasses = new ViewOntologyTree(frame);
			tabbedPane.addTab("Classes", null, treePanelClasses, "Classes");
			
			//Object Porperties Tree ---------------------------------------------------------------------------------
			
			treePanelObjectProperties = new ViewOntologyTree(frame);
			tabbedPane.addTab("Object Properties", null, treePanelObjectProperties, "Object Properties");
			
			//Data Porperties Tree ------------------------------------------------------------------------------------
			
			treePanelDataProperties = new ViewOntologyTree(frame);
			tabbedPane.addTab("Data Properties", null, treePanelDataProperties, "Data Properties");
			
			this.modelGeneral = ontologyTreeModel.getOntologyTreeGeneralModel();
			treePanelGeneral.setTreeModel(modelGeneral);
			
			this.modelClass = ontologyTreeModel.getOntologyTreeClassModel();
			treePanelClasses.setTreeModel(modelClass);
			
			this.modelObjectProperty = ontologyTreeModel.getOntologyTreeObjectPropertiesModel();
			treePanelObjectProperties.setTreeModel(modelObjectProperty);
			
			this.modelDataProperty = ontologyTreeModel.getOntologyTreeDataPropertiesModel();
			treePanelDataProperties.setTreeModel(modelDataProperty);
				
		}
		
		treePanelGeneral.repaintTree();
		treePanelClasses.repaintTree();
		treePanelObjectProperties.repaintTree();
		treePanelDataProperties.repaintTree();
		
		setMappingItem(mappingItem);
		
	}

	/**
	 * @return the ontologyModel
	 */
	public OntologyModelConstructor getOntologyModel() {
		
		return ontologyModel;

	}
	
	

}
