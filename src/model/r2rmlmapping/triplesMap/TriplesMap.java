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

import model.database.Table;
import model.r2rmlmapping.R2RMLMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a triples map of a R2RML mapping
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class TriplesMap extends Observable {
	
	private Table logicalTable;
	private SubjectMap subjectMap;
	private ArrayList<PredicateObjectMap> predicateObjectMaps;	
	private R2RMLMapping r2rmlMapping;
	private ArrayList<ReferencingObjectMap> referencingObjectMapsInTriplesMap;
	
	private static Logger logger = LoggerFactory.getLogger(TriplesMap.class);

	public TriplesMap(R2RMLMapping paramR2RMLMapping, Table paramTable) {
		
		super();
		this.subjectMap = new SubjectMap();
		this.predicateObjectMaps = new ArrayList<PredicateObjectMap>();
		this.referencingObjectMapsInTriplesMap =new ArrayList<ReferencingObjectMap>(0);
		this.r2rmlMapping = paramR2RMLMapping;
		this.logicalTable = paramTable;
		setChanged();
		notifyObservers();

	}

	/**
	 * @return the logicalTable
	 */
	public Table getLogicalTable() {
		
		return logicalTable;
		
	}

	/**
	 * @param logicalTable the logicalTable to set
	 */
	public void setLogicalTable(Table logicalTable) {
		
		this.logicalTable = logicalTable;
	
	}

	/**
	 * @return the subjectMap
	 */
	public SubjectMap getSubjectMap() {
	
		return subjectMap;
	
	}

	/**
	 * @param subjectMap the subjectMap to set
	 */
	public void setSubjectMap(SubjectMap subjectMap) {
		
		this.subjectMap = subjectMap;
		setChanged();
		notifyObservers();

	}

	/**
	 * @return the predicateObjectMaps
	 */
	public ArrayList<PredicateObjectMap> getPredicateObjectMaps() {
		
		return predicateObjectMaps;
	
	}

	/**
	 * @param predicateObjectMap
	 */
	public void addPredicateObjectMap(PredicateObjectMap predicateObjectMap) {
		
		this.predicateObjectMaps.add(predicateObjectMap);
		setChanged();
		notifyObservers();

	}
	
	/**
	 * @param paramPredicateObjectMap
	 */
	public void deletePredicateObjectMap(PredicateObjectMap paramPredicateObjectMap) {
		
		this.predicateObjectMaps.remove(paramPredicateObjectMap);
		setChanged();
		notifyObservers();

	}

	/**
	 * @return the r2rmlMapping
	 */
	public R2RMLMapping getR2RmlMapping() {
		
		return r2rmlMapping;
	
	}

	/**
	 * @return the tripes map's position in teh array of the r2rmlMapping
	 */
	public int getTriplesMapsPosition() {
		
		return r2rmlMapping.getTriplesMapPos(this);
	
	}

	
	/**
	 * @return the referencingObjectMapsInTriplesMap
	 */
	public ArrayList<ReferencingObjectMap> getReferencingObjectMapsInTriplesMap() {
		
		return referencingObjectMapsInTriplesMap;
	
	}
	
	/**
	 * @param refObjectMap
	 */
	protected void addReferencingObjectMapToObjectsMapsInTriplesMap (ReferencingObjectMap refObjectMap) {
		
		referencingObjectMapsInTriplesMap.add(refObjectMap);
	
	}
	
	/**
	 * @param refObjectMap
	 */
	protected void deleteReferencingObjectMapInObjectsMapsInTriplesMap (ReferencingObjectMap refObjectMap) {
		
		referencingObjectMapsInTriplesMap.remove(refObjectMap);
	
	}

	/**
	 * 
	 */
	public void changeLabelsFragments(Boolean showLabels) {
		
		logger.trace("triplesmapmodel show labels value " + showLabels);

		this.getSubjectMap().getRdfClass().changeLabelsFragments(showLabels);
		
		for (PredicateObjectMap predObjMap : this.predicateObjectMaps){
			
			ArrayList<PredicateMap> predMaps = predObjMap.getPredicateMaps();
			
			for (PredicateMap predMap : predMaps) {
				
				predMap.getPredicateIRI().changeLabelsFragments(showLabels);
				
			}
			
		}
		
		setChanged();
		notifyObservers();
	
	}
	
}
