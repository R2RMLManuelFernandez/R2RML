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

package model.r2rmlmapping;

import java.util.ArrayList;
import java.util.Observable;

import model.r2rmlmapping.triplesMap.TriplesMap;

/**
 * Represents a R2RML mapping
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class R2RMLMapping extends Observable {

	private int identifierCounter;

	private ArrayList<TriplesMap> triplesMaps = null;

	public R2RMLMapping() {

		this.triplesMaps = new ArrayList<TriplesMap>();
		this.identifierCounter = 0;
        
	}

	public R2RMLMapping(ArrayList<TriplesMap> mapping) {
		
		super();
		this.triplesMaps = mapping;
		this.identifierCounter = mapping.size() - 1;
		setChanged();
		notifyObservers();

	}

	/**
	 * @param element
	 */
	public void addTriplesMap(TriplesMap element) {
		
		this.triplesMaps.add(element);
		this.identifierCounter++;
		setChanged();
		notifyObservers();
        System.out.println("R2RMLMapping --> Disparado add triples map a modelo r2rml");
	}
	
	/**
	 * @param element
	 */
	public void replaceTriplesMap(int ident, TriplesMap element) {
		
		this.triplesMaps.remove(ident);
		this.triplesMaps.add(ident, element);
		setChanged();
		notifyObservers();
        System.out.println("R2RMLMapping --> Disparado replace triples map en modelo r2rml");
	}
	
	/**
	 * @param element
	 * @return
	 */
	public Boolean removeTriplesMap(TriplesMap element) {

		if (this.triplesMaps.contains(element)) {
			
			this.triplesMaps.remove(element);
			this.identifierCounter--;
			setChanged();
			notifyObservers();
			return true;
			
		}
		else {
			
			return false;
			
		}
			
	}
	
	/**
	 * @param element
	 * @return
	 */
	public Boolean removeTriplesMapAt(int ident) {

		this.triplesMaps.remove(ident);
		this.identifierCounter--;
		setChanged();
		notifyObservers();
		return true;
			
	}
	/**
	 * 
	 */
	public void removeAllTriplesMap() {
		
		triplesMaps.removeAll(triplesMaps);
		this.identifierCounter = 0;
		setChanged();
		notifyObservers();

	}
	
	/**
	 * @return
	 */
	public Boolean hasTriplesMap() {
		
		return this.triplesMaps.isEmpty()? false : true;
		
	}

	/**
	 * @return the triples Map
	 */
	public TriplesMap getTriplesMap(int identifierCounter) {
		
		return triplesMaps.get(identifierCounter);
		
	}
	
	/**
	 * @return the triples Map ident
	 */
	public int getTriplesMapIdent(TriplesMap element) {
		
		return triplesMaps.lastIndexOf(element);
		
	}
	/**
	 * @return the mapping
	 */
	public ArrayList<TriplesMap> getAllTriplesMap() {
		
		return triplesMaps;
		
	}
	
	/**
	 * @return the identifierCounter
	 */
	public int getIdentifierCounter() {
		return identifierCounter;
	}
}
