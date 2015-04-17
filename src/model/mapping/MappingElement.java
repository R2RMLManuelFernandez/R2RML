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
