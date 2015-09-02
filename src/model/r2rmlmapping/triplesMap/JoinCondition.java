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

import model.database.Column;

/**
 * Represents a join condition of a refernced object map
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class JoinCondition extends Observable {
	
	private Column child = null;
	private Column parent = null;
	
	/**
	 * @return the child
	 */
	public Column getChild() {
		return child;
	}
	/**
	 * @param child the child to set
	 */
	public void setChild(Column child) {
		this.child = child;
		setChanged();
		notifyObservers();
	}
	/**
	 * @return the parent
	 */
	public Column getParent() {
		return parent;
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParent(Column parent) {
		this.parent = parent;
		setChanged();
		notifyObservers();
	}
	
}
