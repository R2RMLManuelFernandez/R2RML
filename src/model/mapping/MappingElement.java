/**
 * 
 */
package model.mapping;

import java.util.Observable;

import model.database.Column;
import model.ontology.OntologyElement;


/**
 * Represents a term of a R2RML mapping
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class MappingElement extends Observable {

	private Column databaseColumn = null;
	private OntologyElement ontologyElement = null;
	
	public MappingElement(Column databaseColumn,
			OntologyElement ontologyElement) {
		super();
		this.databaseColumn = databaseColumn;
		this.ontologyElement = ontologyElement;
	}
	
	public MappingElement() {
		super();
	}

	/**
	 * @return the databaseColumn
	 */
	public Column getDatabaseColumn() {
		return databaseColumn;
	}

	/**
	 * @param databaseColumn the databaseColumn to set
	 */
	public void setDatabaseColumn(Column databaseColumn) {
		this.databaseColumn = databaseColumn;
		setChanged();
        notifyObservers();

	}

	/**
	 * @return the ontologyElement
	 */
	public OntologyElement getOntologyElement() {
		return ontologyElement;
	}

	/**
	 * @param ontologyElement the ontologyElement to set
	 */
	public void setOntologyElement(OntologyElement ontologyElement) {
		this.ontologyElement = ontologyElement;
		setChanged();
        notifyObservers();

	}

}
