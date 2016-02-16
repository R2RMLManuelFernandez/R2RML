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

	private int identifierCounter;
	
	private String rdfNameSpace;

	private ArrayList<TriplesMap> triplesMaps = null;
	
	private static Logger logger = LoggerFactory.getLogger(R2RMLMain.class);

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
		logger.trace("R2RMLMapping --> Disparado add triples map a modelo r2rml");
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
	public Boolean removeTriplesMapAt(int ident, JFrame frame) {

		TriplesMap triplesMapToRemove = this.triplesMaps.get(ident);
		this.triplesMaps.remove(ident);
		this.identifierCounter--;
		setChanged();
		notifyObservers();
		
		for (TriplesMap triplesMap : triplesMaps) {
			
			ArrayList<ReferencingObjectMap>refObjectMapsInTriplesMap = triplesMap.getReferencingObjectMapsInTriplesMap();
//			ArrayList<ReferencingObjectMap>refObjectMapsInTriplesMap = refObjectMapsInTriplesMap.;
			
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
	

	/**
	 * @return the rdfNameSpace
	 */
	public String getRdfNameSpace() {
		return rdfNameSpace;
	}

	/**
	 * @param rdfNameSpace the rdfNameSpace to set
	 */
	public void setRdfNameSpace(String rdfNameSpace) {
		this.rdfNameSpace = rdfNameSpace;
	}

}
