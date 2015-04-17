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

package view.addMappingItem;

import javax.swing.JTextField;

import model.mapping.MappingElement;

/**
 * Auxiliar: view for the model of the mapping element 
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class MappingTextField extends JTextField {

	private static final long serialVersionUID = -2161715131382328708L;

	private MappingElement model;
	
	public MappingTextField() {
	}

	/**
	 * @return the model
	 */
	public MappingElement getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(MappingElement model) {
		this.model = model;
	}

}
