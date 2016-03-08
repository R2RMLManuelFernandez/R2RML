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

/**
 * Abstract class representing a component of an ontology *class, object property ordata property(
 *
 * @author Manuel
 *
 */
public abstract class OntologyElement {

	private String IRI = null;
	private String nameSpace = null;
	private String fragment = null;
	private Boolean inMapping = false;
//	private HashMap<String, String> labels = new HashMap<String, String>();
	private String label = null;
	private Boolean showLabel = false;

	public OntologyElement() {
		this.IRI = "";
		this.fragment = "";
	}
	
	public OntologyElement(String IRI) {
		this.IRI = IRI;
	}
	
	public OntologyElement(String IRI, String fragment, String nameSpace) {
		this.IRI = IRI;
		this.fragment = fragment;
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
	public String getFragment() {
		return fragment;
	}
	
	/**
	 * @param fragment
	 */
	public void setFragment(String fragment) {
		this.fragment = fragment;
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
	

//	/**
//	 * @return the labels
//	 */
//	public HashMap<String, String> getLabels() {
//		return labels;
//	}
//
//	/**
//	 * @param labels the labels to set
//	 */
//	public void addLabel(String lang, String label) {
//		this.labels.put(lang, label);
//	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param labels the labels to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	/**
	 * 
	 */
	public void changeShowLabel() {
		
		this.showLabel = !this.showLabel;
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		if (showLabel) {
			
			if (label != null) {
				
				return label;
			
			}
			else {
				
				return fragment;
				
			}
			
		}
		
		else {
			
			return fragment;
			
		}
		
	}

}

