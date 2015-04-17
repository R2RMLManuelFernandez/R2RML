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
import java.util.Vector;

/**
 * Represents a R2RML mapping
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class Mapping extends Observable {

	private Vector<MappingElement> mapping = null;

	public Mapping() {
		
		this.mapping = new Vector<MappingElement>();
		setChanged();
        notifyObservers();
        
	}

	public Mapping(Vector<MappingElement> mapping) {
		
		super();
		this.mapping = mapping;
		setChanged();
        notifyObservers();
        
	}

	/**
	 * @param element
	 */
	public void addMapping(MappingElement element) {
		
		this.mapping.add(element);
		setChanged();
        notifyObservers();
        
	}
	
	/**
	 * @param element
	 * @return
	 */
	public Boolean removeMapping(MappingElement element) {

		if (this.mapping.contains(element)) {
			
			this.mapping.remove(element);
			setChanged();
	        notifyObservers();
	        
			return true;
			
		}
		else {
			
			return false;
			
		}
			
	}
	
	/**
	 * 
	 */
	public void removeAllMappings() {
		
		mapping.removeAll(mapping);
		
	}
	
	/**
	 * @return
	 */
	public Boolean hasMapp() {
		
		return this.mapping.isEmpty()? true : false;
		
	}

	/**
	 * @return the mapping
	 */
	public Vector<MappingElement> getMapping() {
		
		return mapping;
		
	}
	
}
