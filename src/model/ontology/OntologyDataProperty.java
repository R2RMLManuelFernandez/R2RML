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
import java.util.Set;

/**
 * Class representing an ontology data proprety 
 *
 * @author Manuel
 *
 */
public class OntologyDataProperty extends OntologyElement {
	
	private ArrayList<OntologyDataProperty> subDataProperties = null;

	public OntologyDataProperty(String IRI, String displayName, String nameSpace) {
		
		super(IRI, displayName, nameSpace);
		this.subDataProperties = new ArrayList<OntologyDataProperty>();

	}
	
	/**
	 * @param subDataProperty
	 */
	public void addSubDataProperty (OntologyDataProperty subDataProperty) {
		this.subDataProperties.add(subDataProperty);
	}
	
	/**
	 * @param subDataProperties
	 */
	public void addSubDataProperties (Set<OntologyDataProperty> subDataProperties) {
		this.subDataProperties.addAll(subDataProperties);
	}
	
	/**
	 * @return
	 */
	public ArrayList<OntologyDataProperty> getSubDataProperties() {
		return subDataProperties;
	}
	
	/**
	 * @return
	 */
	public Boolean hasSubDataProperties() {
		return this.subDataProperties.isEmpty()? false : true;
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
