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

package model.r2rmlmapping.triplesMap;

import java.util.Observable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a predicate map of a R2RML mapping
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class PredicateMap extends Observable {

	private PredicateObjectMap predicateObjectMap;
	private IRIClass predicateIRI;
	
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(PredicateMap.class);

	public PredicateMap(PredicateObjectMap paramPredicateObjectMap) {
		
		this.predicateObjectMap = paramPredicateObjectMap;
		this.predicateIRI = new IRIClass();
		setChanged();
		notifyObservers();
		
	}
	
	/**
	 * @return the predicateIRI
	 */
	public IRIClass getPredicateIRI() {
		
		return predicateIRI;

	}

	/**
	 * @param predicateIRI the predicateIRI to set
	 */
	public void setPredicateIRI(IRIClass predicateIRI) {
		
		this.predicateIRI = predicateIRI;
		setChanged();
		notifyObservers();
		
	}
	
	/**
	 * @return the predicateObjectMap
	 */
	public PredicateObjectMap getPredicateObjectMap() {
		
		return predicateObjectMap;
	
	}

}
