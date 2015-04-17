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
