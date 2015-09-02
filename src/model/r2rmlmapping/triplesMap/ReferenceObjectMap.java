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
 * Represents a Referenced object map of a R2RML mapping triplesMap
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class ReferenceObjectMap extends Observable implements ObjectMap {

	public static final String ReferenceType = "Referenced";
	
	private TriplesMap parentTriplesMap;
	private ArrayList<JoinCondition> joinConditions;
	private PredicateObjectMap predicateObjectMap;
	private String type;

	public ReferenceObjectMap(PredicateObjectMap paramPredicateObjectMap) {
		this.predicateObjectMap = paramPredicateObjectMap;
		this.type = ReferenceType;
		this.joinConditions = new ArrayList<JoinCondition>();
	}

	/* (non-Javadoc)
	 * @see model.r2rmlmapping.ObjectMap#getPredicateObjectMap()
	 */
	@Override
	public PredicateObjectMap getPredicateObjectMap() {
		return predicateObjectMap;
	}

	/* (non-Javadoc)
	 * @see model.r2rmlmapping.ObjectMap#getType()
	 */
	@Override
	public String getType() {
		return type;
	}
	
	/**
	 * @return the parent
	 */
	public TriplesMap getParentTriplesMap() {
		return parentTriplesMap;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParentTriplesMap(TriplesMap parent) {
		this.parentTriplesMap = parent;
		setChanged();
		notifyObservers();
	}

	/**
	 * @return the joinConditions
	 */
	public JoinCondition getJoinConditionAt(int index) {
		return joinConditions.get(index);
	}
	
	/**
	 * @return the joinConditions
	 */
	public ArrayList<JoinCondition> getJoinConditions() {
		return joinConditions;
	}
	
	/**
	 * @param join
	 */
	public void addJoinCondition (JoinCondition join) {
		this.joinConditions.add(join);
		setChanged();
		notifyObservers();
	}
	
	/**
	 * @param join
	 */
	public void removeJoinCondition (JoinCondition join) {
		this.joinConditions.remove(join);
		setChanged();
		notifyObservers();
	}
	
}
