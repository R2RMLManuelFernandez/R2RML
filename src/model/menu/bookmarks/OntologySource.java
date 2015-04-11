package model.menu.bookmarks;

/**
 * Represents an ontology source (URL or File)
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class OntologySource {

	private String source;

	/**
	 * @return the source of the ontology
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param paramSource
	 * 			 the new source of the ontology
	 */
	public void setSource(String paramSource) {
		this.source = paramSource;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return source;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		
		if (obj == null) {
			
			return false;
			
		}
		if (!(obj instanceof OntologySource)) {
			
			return false;
			
		}
		OntologySource otherSource = (OntologySource) obj;
		
		return this.getSource().equals(otherSource.getSource());
		
	}
	
}
