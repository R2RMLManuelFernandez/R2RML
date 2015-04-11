package model.ontology;

/**
 * Class representing an ontology data proprety 
 *
 * @author Manuel
 *
 */
public class OntologyDataProperty extends OntologyElement {

	public OntologyDataProperty(String IRI, String displayName, String nameSpace) {
		super(IRI, displayName, nameSpace);
	}
	
	/**
	 * adds the data property to the list of data properties in the class tha has this class in its domain
	 * 
	 * @param clazz
	 */
	public void addToClass(OntologyClass clazz) {
		clazz.addDataProperty(this);
	}
}
