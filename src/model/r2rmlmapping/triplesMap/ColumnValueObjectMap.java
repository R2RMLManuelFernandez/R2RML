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
import java.util.Iterator;
import java.util.Observable;

import model.database.Column;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a Column-Valued object map of a R2RML mapping triplesMap
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class ColumnValueObjectMap extends Observable implements ObjectMap {

	public static final String ColumnValuedType = "Column-Valued";
	
	private String object;
	
	private ArrayList<Column> objectColumns;
	
	private PredicateObjectMap predicateObjectMap;
	
	private String type;
		
	private static Logger logger = LoggerFactory.getLogger(ColumnValueObjectMap.class);
	
	public ColumnValueObjectMap(PredicateObjectMap paramPredicateObjectMap) {
		this.predicateObjectMap = paramPredicateObjectMap;
		this.type = ColumnValuedType;
		this.objectColumns = new ArrayList<Column>();
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
	 * @return the object
	 */
	public String getObject() {
		return object;
	}

	/**
	 * @param object the object to set
	 */
	public void setObject(String object) {
		this.object = object;
		setChanged();
		notifyObservers();
	}

	/**
	 * @return the objectColumns
	 */
	public ArrayList<Column> getObjectColumns() {
		return objectColumns;
	}
	
	/**
	 * @param column
	 */
	public void addObjectColumn(Column column) {
		this.objectColumns.add(column);
		setChanged();
		notifyObservers();
		
		logger.trace("ColumnValueObjectMap --> se ha añadido uuna columna a un object map");
		
	}

	/**
	 * @param cols
	 */
	public void removeColumnsByName(ArrayList<String> colNames) {
		
		logger.trace("ColumnValueObjectMap --> Borrando las columnas " + colNames.toString());
		
		for (String colName: colNames) {
			
			Iterator<Column> itColumn = objectColumns.iterator();
			
			while (itColumn.hasNext()) {
				Column col = itColumn.next();
				if (colName.equals(col.getColumnName())) {
					itColumn.remove();
					logger.trace("ColumnValueObjectMap --> Borrando la columna " + col.getColumnName());
				}
			}
		}
		setChanged();
		notifyObservers();
	}
	
}
