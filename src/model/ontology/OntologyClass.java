package model.ontology;

import java.util.ArrayList;

/**
 * Class representing an ontology class 
 *
 * @author Manuel
 *
 */
public class OntologyClass extends OntologyElement {
	//the subclasses of the class
	private ArrayList<OntologyClass> subclasses = null;
	//the object properties which has the class as part of its domain
	private ArrayList<OntologyObjectProperty> objectProperties = null;
	//the data properties which has the class as part of its domain
	private ArrayList<OntologyDataProperty> dataProperties = null;

	public OntologyClass(String IRI, String displayName, String nameSpace) {
		super(IRI, displayName, nameSpace);
		this.subclasses = new ArrayList<OntologyClass>();
		this.dataProperties = new ArrayList<OntologyDataProperty>();
		this.objectProperties = new ArrayList<OntologyObjectProperty>();
	}

	/**
	 * @param subclass
	 */
	public void addSubclass(OntologyClass subclass) {
		this.subclasses.add(subclass);
	}
	
	/**
	 * @return
	 */
	public ArrayList<OntologyClass> getSubclasses() {
		return subclasses;
	}
	
	/**
	 * @return
	 */
	public Boolean hasSubclasses() {
		return this.subclasses.isEmpty()? false : true;
	}
	
	/**
	 * @param dataProperty
	 */
	public void addDataProperty(OntologyDataProperty dataProperty) {
		this.dataProperties.add(dataProperty);
	}
	
	/**
	 * @return
	 */
	public ArrayList<OntologyDataProperty> getDataProperties() {
		return dataProperties;
	}
	
	/**
	 * @return
	 */
	public Boolean hasDataProperties() {
		return this.dataProperties.isEmpty()? false : true;
	}
	
	/**
	 * @param objectProperty
	 */
	public void addObjectProperty(OntologyObjectProperty objectProperty) {
		this.objectProperties.add(objectProperty);
	}
	
	/**
	 * @return
	 */
	public ArrayList<OntologyObjectProperty> getObjectProperties() {
		return objectProperties;
	}
	
	/**
	 * @return
	 */
	public Boolean hasObjectProperties() {
		return this.objectProperties.isEmpty()? false : true;
	}

}
