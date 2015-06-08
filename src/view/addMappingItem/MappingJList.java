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

package view.addMappingItem;

import javax.swing.JList;

import model.mapping.MappingElement;

/**
 * Auxiliar: view for the model of the mapping element 
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class MappingJList extends JList<String>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5551392055096281424L;

	private MappingElement mappingElement;
	
	public MappingJList() {
	}

	/**
	 * @return the mappingElement
	 */
	public MappingElement getMappingElement() {
		return mappingElement;
	}

	/**
	 * @param mappingElement the mappingElement to set
	 */
	public void setMappingElement(MappingElement mappingElement) {
		this.mappingElement = mappingElement;
	}
	

}
