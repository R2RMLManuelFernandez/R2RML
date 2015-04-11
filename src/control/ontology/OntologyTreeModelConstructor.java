/**
 * 
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
	
	//data model for the JTree
	private DefaultTreeModel ontologyTreeModel;
	
	//the Thing class of the ontology model
	private OntologyClass Thing;
	
	/**
	 * @param thing
	 */
	public OntologyTreeModelConstructor(OntologyClass thing) {
		this.Thing = thing;
		//sets the thing as the root of the tree model
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(thing);		
		this.ontologyTreeModel = new DefaultTreeModel(rootNode);
		//creates the tree model hanging from the root(thing)
		this.createOntologyTreeModel(rootNode);
	}

	/**
	 * @return the ontologyTreeModel
	 */
	public DefaultTreeModel getOntologyTreeModel() {
		return ontologyTreeModel;
	}

	/**
	 * @param rootNode
	 */
	private void createOntologyTreeModel(DefaultMutableTreeNode rootNode) {
		if (Thing.hasSubclasses()) {
			ArrayList<OntologyClass> subClasses = Thing.getSubclasses();
			addClassesToTree(subClasses, rootNode);
		}
	}
	
	/**
	 * @param classes
	 * @param node
	 */
	private void addClassesToTree(ArrayList<OntologyClass> classes, DefaultMutableTreeNode node) {
		//count the index of the last class added to the model
		int treeIndex = 0;
		//add the classes as node childs of the node 
		for (OntologyClass clazz : classes) {
			//creates and inserts the new node
			DefaultMutableTreeNode classNode = new DefaultMutableTreeNode(clazz);
			ontologyTreeModel.insertNodeInto(classNode , node, treeIndex++);
			//inserts the data properties if the class as child nodes of the node starting at index 0.
			//classIndex is the the position of the position of the last child added to the node
			int classIndex = this.addDataPropertiesToTree(clazz.getDataProperties(), classNode, 0);
			//inserts the data properties if the class as child nodes of the node
			this.addObjectPropertiesToTree(clazz.getObjectProperties(), classNode, classIndex);
			//recursively adds the childs of the classes just added to the tree
			this.addClassesToTree(clazz.getSubclasses(), classNode);
		}
	}
	
	/**
	 * @param dataProperties
	 * @param node
	 * @param index
	 * @return
	 */
	private int addDataPropertiesToTree(ArrayList<OntologyDataProperty> dataProperties, DefaultMutableTreeNode node, int index) {
		int classIndex = index;
		if (!dataProperties.isEmpty()) {
			for (OntologyDataProperty dataProperty : dataProperties) {
				DefaultMutableTreeNode dataPropertyNode = new DefaultMutableTreeNode(dataProperty);
				ontologyTreeModel.insertNodeInto(dataPropertyNode , node, classIndex++);
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
	private int addObjectPropertiesToTree(ArrayList<OntologyObjectProperty> objectProperties, DefaultMutableTreeNode node, int index) {
		int tableIndex = index;
		if (!objectProperties.isEmpty()) {
			for (OntologyObjectProperty objectProperty : objectProperties) {
				DefaultMutableTreeNode objectPropertyNode = new DefaultMutableTreeNode(objectProperty);
				ontologyTreeModel.insertNodeInto(objectPropertyNode , node, tableIndex++);
			}
		}

		return tableIndex;
	}
}
