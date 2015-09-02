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

import java.util.ArrayList;
import java.util.Observable;

/**
 * Represents a predicate-object map of a R2RML mapping
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class PredicateObjectMap extends Observable {
	
	private int identifier;
	private TriplesMap triplesMap;
	private ArrayList<PredicateMap> predicateMaps;
	private ArrayList<ObjectMap> objectMaps;
	
	public PredicateObjectMap(int paramIdentifier, TriplesMap paramTriplesMap) {
		this.identifier = paramIdentifier;
		this.triplesMap = paramTriplesMap;
		initPredicateObject();
	}
	
	private void initPredicateObject() {
		this.predicateMaps = new ArrayList<PredicateMap>();
		PredicateMap predicateMap = new PredicateMap(this);
		this.predicateMaps.add(predicateMap);
		this.objectMaps = new ArrayList<ObjectMap>();
		//al primer objeto le asigno arbitrariamente que sea del tipo colval
		//si cambia el tipo deberia cambiar el modelo del objeto
		ObjectMap objectMap = new ColumnValueObjectMap(this);
		this.objectMaps.add(objectMap);
		setChanged();
		notifyObservers();
		
	}

	/**
	 * @return the identifier
	 */
	public int getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(int identifier) {
		this.identifier = identifier;
		setChanged();
		notifyObservers();
	}
	
	/**
	 * @return the triplesMap
	 */
	public TriplesMap getTriplesMap() {
		return triplesMap;
	}

	/**
	 * @return the objectMaps
	 */
	public ArrayList<ObjectMap> getObjectMaps() {
		return objectMaps;
	}


	/**
	 * @param objectMap the objectMap to add
	 */
	public void addObjectMap(ObjectMap objectMap) {
		this.objectMaps.add(objectMap);
		setChanged();
		notifyObservers();
	}


	/**
	 * @param objectMap
	 */
	public void deleteObjectMap(ObjectMap objectMap) {
		this.objectMaps.remove(objectMap);
		setChanged();
		notifyObservers();
	}
	
	/**
	 * @return the predicateMap
	 */
	public ArrayList<PredicateMap> getPredicateMaps() {
		return predicateMaps;
	}

	/**
	 * @param predicateMap the predicateMap to add
	 */
	public void addPredicateMap(PredicateMap predicateMap) {
		this.predicateMaps.add(predicateMap);
		setChanged();
		notifyObservers();
	}
	

	public void deletePredicateMap(PredicateMap predicateMap) {
		this.predicateMaps.remove(predicateMap);
		setChanged();
		notifyObservers();
	}
	
}
