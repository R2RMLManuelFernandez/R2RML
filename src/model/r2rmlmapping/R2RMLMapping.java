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
import java.util.Iterator;
import java.util.Observable;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.r2rmlmapping.triplesMap.ReferencingObjectMap;
import model.r2rmlmapping.triplesMap.TriplesMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import view.R2RMLMain;

/**
 * Represents a R2RML mapping
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class R2RMLMapping extends Observable {
	
	private String baseIRI;

	private ArrayList<TriplesMap> triplesMaps = null;
	
	private static Logger logger = LoggerFactory.getLogger(R2RMLMain.class);

	public R2RMLMapping() {

		this.triplesMaps = new ArrayList<TriplesMap>(0);

        
	}

	public R2RMLMapping(int initialCapacity) {
		
		super();
		this.triplesMaps = new ArrayList<TriplesMap>(initialCapacity);
		setChanged();
		notifyObservers();

	}
	
	public R2RMLMapping(ArrayList<TriplesMap> mapping) {
		
		super();
		this.triplesMaps = mapping;
		setChanged();
		notifyObservers();

	}

	/**
	 * @param element
	 */
	public void addTriplesMap(TriplesMap element) {
		
		this.triplesMaps.add(element);
		setChanged();
		notifyObservers();
		logger.trace("R2RMLMapping --> Disparado add triples map a modelo r2rml");
	}

	/**
	 * @param element
	 */
	public void addTriplesMapInPosistion(int pos, TriplesMap element) {
		
		this.triplesMaps.add(pos, element);
		setChanged();
		notifyObservers();
		logger.trace("R2RMLMapping --> Disparado add triples map en posicion a modelo r2rml");
	}

	
	/**
	 * @param element
	 */
	public void replaceTriplesMap(int pos, TriplesMap element) {
		
		this.triplesMaps.remove(pos);
		this.triplesMaps.add(pos, element);
		setChanged();
		notifyObservers();
		logger.trace("R2RMLMapping --> Disparado replace triples map en modelo r2rml");
	}
	
	/**
	 * @param element
	 * @return
	 */
	public Boolean removeTriplesMap(TriplesMap element) {

		if (this.triplesMaps.contains(element)) {
			
			this.triplesMaps.remove(element);
			setChanged();
			notifyObservers();
/*			for (TriplesMap triplesMap : triplesMaps) {
				ArrayList<ReferencingObjectMap>refObjectMapsInTriplesMap = triplesMap.getReferencingObjectMapsInTriplesMap();
				for (ReferencingObjectMap referencingObjectMap : refObjectMapsInTriplesMap) {
					if (referencingObjectMap.getParentTriplesMap().equals(element)) {
						logger.trace("Preguntar al usuario si quiere borrar el triplesMap");
						if (true) {//"quiere borrarlo"
							referencingObjectMap.getPredicateObjectMap().deleteObjectMap(referencingObjectMap);
						}
						else {
							logger.trace("Advertir al usuario de las consecuencias de no borrar el triplesMap");
						}
					}
				}
			}*/
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
	public Boolean removeTriplesMapAt(int pos, JFrame frame) {

		TriplesMap triplesMapToRemove = this.triplesMaps.get(pos);
		this.triplesMaps.remove(pos);
		setChanged();
		notifyObservers();
		
		for (TriplesMap triplesMap : triplesMaps) {
			
			ArrayList<ReferencingObjectMap>refObjectMapsInTriplesMap = triplesMap.getReferencingObjectMapsInTriplesMap();
			
			Iterator<ReferencingObjectMap> iterRefObjectMaps = refObjectMapsInTriplesMap.iterator();
			
			while (iterRefObjectMaps.hasNext()) {
				
				ReferencingObjectMap referencingObjectMap = iterRefObjectMaps.next();
				
				if (referencingObjectMap.getParentTriplesMap().equals(triplesMapToRemove)) {
					
					logger.trace("Preguntar al usuario si quiere borrar el triplesMap");
					String[] options = {"Yes, I would like to remove the unconsistent referencing objectMap from the R2RML Map", "No, keep the Referncin object map in tehe R2RML Map"};
					int delete = JOptionPane.showOptionDialog(frame, "Would you like to remove the unconsistent referencing objectMap from the R2RML Map?", "Remove inconsistent object map",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
					
					if (delete == JOptionPane.YES_OPTION) {//"quiere borrarlo"
						
						referencingObjectMap.getPredicateObjectMap().deleteObjectMap(referencingObjectMap);
						iterRefObjectMaps.remove();
						setChanged();
						notifyObservers();
						logger.trace("Debereia haberse borrado el triplesMap inconsistente");
						
					}
					else {
						
						logger.trace("Advertir al usuario de las consecuencias de no borrar el triplesMap");
						JOptionPane.showMessageDialog(frame, "The R2RML map will be inconsistent", "Warning", JOptionPane.WARNING_MESSAGE, null);
					
					}
					
				}
				
			}
			
		}
		
		return true;
			
	}
	/**
	 * 
	 */
	public void removeAllTriplesMap() {
		
		triplesMaps.removeAll(triplesMaps);
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
	 * @return the triples Map at the possition in the ArrayList of triples map
	 */
	public TriplesMap getTriplesMap(int pos) {
		
		return triplesMaps.get(pos);
		
	}
	
	/**
	 * @return the triples Map ident
	 */
	public int getTriplesMapPos(TriplesMap triplesMap) {
		
		return triplesMaps.lastIndexOf(triplesMap);
		
	}
	
	/**
	 * @return the mapping
	 */
	public ArrayList<TriplesMap> getAllTriplesMap() {
		
		return triplesMaps;
		
	}

	/**
	 * @return the number of triples maps in the r2rml mapping
	 */
	public int getTriplesMapCount() {
		
		return triplesMaps.size();
		
	}
	
	/**
	 * @return the rdfNameSpace
	 */
	public String getBaseIRI() {
		return baseIRI;
	}

	/**
	 * @param rdfNameSpace the rdfNameSpace to set
	 */
	public void setBaseIRI(String baseIRI) {
		this.baseIRI = baseIRI;
	}

}
