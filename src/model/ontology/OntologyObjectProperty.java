/*
 * Copyright 2015 Manuel Fern�ndez P�rez
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
 * Class representing an ontology object proprety 
 *
 * @author Manuel
 *
 */
public class OntologyObjectProperty extends OntologyElement {
	
	private ArrayList<OntologyObjectProperty> subObjectProperties = null;

	public OntologyObjectProperty(String IRI, String displayName, String nameSpace){
		super(IRI, displayName, nameSpace);
		this.subObjectProperties = new ArrayList<OntologyObjectProperty>();
	}
	
	/**
	 * @param subObjectProperty
	 */
	public void addSubObjectProperty (OntologyObjectProperty subObjectProperty) {
		this.subObjectProperties.add(subObjectProperty);
	}
	
	/**
	 * @param subObjectProperties
	 */
	public void addSubObjectProperties (Set<OntologyObjectProperty> subObjectProperties) {
		this.subObjectProperties.addAll(subObjectProperties);
	}
	
	/**
	 * @return
	 */
	public ArrayList<OntologyObjectProperty> getSubObjectProperties() {
		return subObjectProperties;
	}
	
	/**
	 * @return
	 */
	public Boolean hasSubObjectProperties() {
		return this.subObjectProperties.isEmpty()? false : true;
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
