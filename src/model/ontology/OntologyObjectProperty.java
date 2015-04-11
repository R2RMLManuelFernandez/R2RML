package model.ontology;

/**
 * Class representing an ontology object proprety 
 *
 * @author Manuel
 *
 */
public class OntologyObjectProperty extends OntologyElement {

	public OntologyObjectProperty(String IRI, String displayName, String nameSpace){
		super(IRI, displayName, nameSpace);
	}
	
	/**
	 * adds the object property to the list of data properties in the class tha has this class in its domain
	 * 
	 * @param clazz
	 */
	public void addToClass(OntologyClass clazz) {
		clazz.addObjectProperty(this);
	}
	
}
