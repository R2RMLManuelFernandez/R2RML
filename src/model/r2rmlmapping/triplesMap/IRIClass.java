/**
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

package model.r2rmlmapping.triplesMap;

import org.semanticweb.owlapi.model.IRI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import view.R2RMLMain;

/**
 * Represents a rdf Class of the subject map 
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class IRIClass {
	
	private IRI iri;
	private String fragment;
	private String label;
	private Boolean showLabel; 
	
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(R2RMLMain.class);
	
	public IRIClass() {
		
		this.iri = null;
		this.fragment = null;
		this.label = null;
		this.showLabel = R2RMLMain.showLabels;

	}

	/**
	 * @return the iri
	 */
	public String getIRIClassIRI() {
		
		return iri.toString();
		
	}

	/**
	 * @param iri the iri to set
	 */
	public void setIRIClassIRI(String iri) {

		this.iri = IRI.create(iri);
		this.fragment = this.iri.getFragment();
		
	}
/*
	*//**
	 * @return the fragment
	 *//*
	public String getRDFClassFragment() {
		
		return fragment;
		
	}

	*//**
	 * @return the label
	 *//*
	public String getRDFClassLabel() {
		
		return label;
		
	}
*/
	/**
	 * @param label the label to set
	 */
	public void setIRIClassLabel(String label) {
		
		this.label = label;
		
	}

	/**
	 * 
	 */
	public void changeLabelsFragments(Boolean newShowLabesls) {
	
		showLabel = newShowLabesls;
		
	}
	
	/**
	 * @return
	 */
	public String getIRIShow() {
		
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
