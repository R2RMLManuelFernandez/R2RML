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

/**
 * Represents a triples map of a R2RML mapping
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class TriplesMap extends Observable {
	
	private int identifier;
	private Table logicalTable;
	private SubjectMap subjectMap;
	private ArrayList<PredicateObjectMap> predicateObjectMaps;	
	private String ontologyNameSpace;
	private R2RMLMapping r2rmlMapping;

	public TriplesMap(int identifier, R2RMLMapping paramR2rmlMapping, Table paramTable) {
		
		super();
		this.identifier = identifier;
		this.subjectMap = new SubjectMap();
		this.predicateObjectMaps = new ArrayList<PredicateObjectMap>();
		this.r2rmlMapping = paramR2rmlMapping;
		this.logicalTable = paramTable;
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
	 * @return the nameSpace
	 */
	public String getNameSpace() {
		return ontologyNameSpace;
	}

	/**
	 * @param nameSpace the nameSpace to set
	 */
	public void setNameSpace(String nameSpace) {
		this.ontologyNameSpace = nameSpace;
	}

	/**
	 * @return the r2rmlMapping
	 */
	public R2RMLMapping getR2RmlMapping() {
		return r2rmlMapping;
	}

}
