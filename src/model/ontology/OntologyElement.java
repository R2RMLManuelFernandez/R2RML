package model.ontology;

/**
 * Abstract class representing a component of an ontology *class, object property ordata property(
 *
 * @author Manuel
 *
 */
public abstract class OntologyElement {

	private String IRI = null;
	private String nameSpace = null;
	private String displayName = null;
	private Boolean inMapping = false;

	public OntologyElement() {
		this.IRI = "";
		this.displayName = "";
	}
	
	public OntologyElement(String IRI) {
		this.IRI = IRI;
	}
	
	public OntologyElement(String IRI, String displayName, String nameSpace) {
		this.IRI = IRI;
		this.displayName = displayName;
		this.nameSpace = nameSpace;
	}
	
	/**
	 * @return
	 */
	public String getIRI() {
		return IRI;
	}
	
	/**
	 * @return
	 */
	public String getDisplayName() {
		return displayName;
	}
	
	/**
	 * @param displayName
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	/**
	 * @return
	 */
	public String getNameSpace() {
		return nameSpace;
	}
	
	/**
	 * @param nameSpace
	 */
	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

	/**
	 * @return the inMapping
	 */
	public Boolean getInMapping() {
		return inMapping;
	}

	/**
	 * @param inMapping the inMapping to set
	 */
	public void setInMapping(Boolean inMapping) {
		this.inMapping = inMapping;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return displayName;
	}

}

