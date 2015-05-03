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

package control.ontology;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import model.ontology.OntologyClass;
import model.ontology.OntologyDataProperty;
import model.ontology.OntologyObjectProperty;

/**
 * @author Manuel
 *
 * Constructor for the data model used in the ontology tree
 * Constructs a tree schema from the ontology model
 */
public class OntologyTreeModelConstructor {
	
	//model for JTree with classes, object properties and data properties
	private DefaultTreeModel ontologyTreeGeneralModel;
	
	//model for JTree only with classes
	private DefaultTreeModel ontologyTreeClassModel;
	
	//model for JTree only with object properties
	private DefaultTreeModel ontologyTreeObjectPropertyModel;
	
	//model for JTree only with data properties
	private DefaultTreeModel ontologyTreeDataPropertyModel;
	
	//the Thing class of the ontology model
	private OntologyClass Thing;
	
    private OntologyObjectProperty topObjectProperty;
    
    private OntologyDataProperty topDataProperty;
	
	/**
	 * @param thing
	 */
	public OntologyTreeModelConstructor(OntologyClass thing, OntologyObjectProperty topOP, OntologyDataProperty topDP) {
		
		this.Thing = thing;
		this.topObjectProperty = topOP;
		this.topDataProperty = topDP;		
		//sets the thing as the root of the tree model
		DefaultMutableTreeNode rootNodeGeneral = new DefaultMutableTreeNode(thing);		
		this.ontologyTreeGeneralModel = new DefaultTreeModel(rootNodeGeneral);
		//creates the tree general model hanging from the root(thing)
		this.createOntologyTreeGeneralModel(rootNodeGeneral);
		DefaultMutableTreeNode rootNodeClass = new DefaultMutableTreeNode(thing);
		this.ontologyTreeClassModel = new DefaultTreeModel(rootNodeClass);
		//creates the tree class model hanging from the root(thing)
		this.createOntologyTreeClassModel(rootNodeClass);
		
		DefaultMutableTreeNode rootNodeObjectProperty= new DefaultMutableTreeNode(topOP);		
		this.ontologyTreeObjectPropertyModel = new DefaultTreeModel(rootNodeObjectProperty);
		this.createOntologyTreeObjectPropertyModel(rootNodeObjectProperty);
		
		DefaultMutableTreeNode rootNodeDataProperty= new DefaultMutableTreeNode(topDP);		
		this.ontologyTreeDataPropertyModel = new DefaultTreeModel(rootNodeDataProperty);
		this.createOntologyTreeDataPropertyModel(rootNodeDataProperty);
		
	}

	/**
	 * @return the ontology model with classes, object properties and data properties
	 */
	public DefaultTreeModel getOntologyTreeGeneralModel() {
		return ontologyTreeGeneralModel;
	}
	
	/**
	 * @return the ontology model only with classes
	 */
	public DefaultTreeModel getOntologyTreeClassModel() {
		return ontologyTreeClassModel;
	}
	
	/**
	 * @return the ontology model only with object properties
	 */
	public DefaultTreeModel getOntologyTreeObjectPropertiesModel() {
		return ontologyTreeObjectPropertyModel;
	}
	
	/**
	 * @return the ontology model only with data properties
	 */
	public DefaultTreeModel getOntologyTreeDataPropertiesModel() {
		return ontologyTreeDataPropertyModel;
	}
	
	/**
	 * @param rootNode
	 */
	private void createOntologyTreeGeneralModel(DefaultMutableTreeNode rootNode) {
		if (Thing.hasSubclasses()) {
			ArrayList<OntologyClass> subClasses = Thing.getSubclasses();
			addClassesToTree(ontologyTreeGeneralModel, rootNode, subClasses, true);
		}
	}

	/**
	 * @param rootNode
	 */
	private void createOntologyTreeClassModel(DefaultMutableTreeNode rootNode) {
		if (Thing.hasSubclasses()) {
			ArrayList<OntologyClass> subClasses = Thing.getSubclasses();
			addClassesToTree(ontologyTreeClassModel, rootNode, subClasses, false);
		}
	}
	
	/**
	 * @param classes
	 * @param node
	 */
	private void addClassesToTree(DefaultTreeModel treeModel, DefaultMutableTreeNode node, ArrayList<OntologyClass> classes, Boolean addProperties) {
		//count the index of the last child added to the node
		int treeIndex = 0;
		//add the classes as node childs of the node 
		for (OntologyClass clazz : classes) {
			//creates and inserts the new node
			DefaultMutableTreeNode classNode = new DefaultMutableTreeNode(clazz);
			treeModel.insertNodeInto(classNode , node, treeIndex++);
			
			if (addProperties) {
				//inserts the data properties in the class as child nodes of the node starting at index 0.
				//classIndex is the the position of the last child added to the node
				int classIndex = this.addObjectPropertiesToTree(treeModel, classNode, clazz.getObjectProperties(), 0);
				//inserts the data properties in the class as child nodes of the node
				this.addDataPropertiesToTree(treeModel, classNode, clazz.getDataProperties(), classIndex);
			}
			//recursively adds the childs of the classes just added to the tree
			this.addClassesToTree(treeModel, classNode, clazz.getSubclasses(), addProperties);
		}
	}
	
	/**
	 * @param dataProperties
	 * @param node
	 * @param index
	 * @return
	 */
	private int addDataPropertiesToTree(DefaultTreeModel treeModel, DefaultMutableTreeNode node, ArrayList<OntologyDataProperty> dataProperties, int index) {
		int classIndex = index;
		if (!dataProperties.isEmpty()) {
			for (OntologyDataProperty dataProperty : dataProperties) {
				DefaultMutableTreeNode dataPropertyNode = new DefaultMutableTreeNode(dataProperty);
				treeModel.insertNodeInto(dataPropertyNode , node, classIndex++);
			}
		}
		return classIndex;
	}
	
	/**
	 * @param objectProperties
	 * @param node
	 * @param index
	 * @return
	 */
	private int addObjectPropertiesToTree(DefaultTreeModel treeModel, DefaultMutableTreeNode node, ArrayList<OntologyObjectProperty> objectProperties, int index) {
		int tableIndex = index;
		if (!objectProperties.isEmpty()) {
			for (OntologyObjectProperty objectProperty : objectProperties) {
				DefaultMutableTreeNode objectPropertyNode = new DefaultMutableTreeNode(objectProperty);
				treeModel.insertNodeInto(objectPropertyNode , node, tableIndex++);
			}
		}

		return tableIndex;
	}
	
	/**
	 * @param rootNode
	 */
	private void createOntologyTreeObjectPropertyModel(DefaultMutableTreeNode rootNode) {
		if (topObjectProperty.hasSubObjectProperties()) {
			ArrayList<OntologyObjectProperty> subObjectProperties = topObjectProperty.getSubObjectProperties();
			addObjectPropertiesToOPTree(ontologyTreeObjectPropertyModel, rootNode, subObjectProperties);
		}
	}
	
	/**
	 * @param rootNode
	 */
	private void createOntologyTreeDataPropertyModel(DefaultMutableTreeNode rootNode) {
		if (topDataProperty.hasSubDataProperties()) {
			ArrayList<OntologyDataProperty> subDataProperties = topDataProperty.getSubDataProperties();
			addDataPropertiesToDPTree(ontologyTreeDataPropertyModel, rootNode, subDataProperties);
		}
	}
	
	/**
	 * @param classes
	 * @param node
	 */
	private void addObjectPropertiesToOPTree(DefaultTreeModel treeModel, DefaultMutableTreeNode node, ArrayList<OntologyObjectProperty> ontologyObjProp) {
		//count the index of the last child added to the node
		int treeIndex = 0;
		//add the classes as node childs of the node 
		for (OntologyObjectProperty objectProp : ontologyObjProp) {
			//creates and inserts the new node
			DefaultMutableTreeNode objectPropNode = new DefaultMutableTreeNode(objectProp);
			treeModel.insertNodeInto(objectPropNode , node, treeIndex++);
			
			//recursively adds the childs of the classes just added to the tree
			this.addObjectPropertiesToOPTree(treeModel, objectPropNode, objectProp.getSubObjectProperties());
		}
	}
	
	/**
	 * @param classes
	 * @param node
	 */
	private void addDataPropertiesToDPTree(DefaultTreeModel treeModel, DefaultMutableTreeNode node, ArrayList<OntologyDataProperty> ontologyDataProp) {
		//count the index of the last child added to the node
		int treeIndex = 0;
		//add the classes as node childs of the node 
		for (OntologyDataProperty dataProp : ontologyDataProp) {
			//creates and inserts the new node
			DefaultMutableTreeNode dataPropNode = new DefaultMutableTreeNode(dataProp);
			treeModel.insertNodeInto(dataPropNode , node, treeIndex++);
			
			//recursively adds the childs of the classes just added to the tree
			this.addDataPropertiesToDPTree(treeModel, dataPropNode, dataProp.getSubDataProperties());;
		}
	}
	
}
