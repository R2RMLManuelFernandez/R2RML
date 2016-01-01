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

package model.database;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents a database table
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class Table {

	private Database database;
	
	private String tableName;
	
	private ArrayList<Column> columns = null;
	
	public Table(Database database, String name) {

		this.tableName = name;
		this.columns = new ArrayList<Column>(0);
		assert database != null;
		if (database == null) {
			//log.error("Database is null");
		}
		this.database = database;
		this.database.addTable(this);
	}

	/**
	 * @return
	 */
	public Database getDatabase() {
		return database;
	}

	/**
	 * @return
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return
	 */
	public ArrayList<Column> getColumns() {
		return columns;
	}

	/**
	 * @param column
	 */
	public void addColumn(Column column) {
		this.columns.add(column);
	}
	
	/**
	 * @return
	 */
	public Boolean hasColumns() {
		return this.columns.isEmpty()? false : true;
	}
	
	public Column getColumn(String columnName) {

		if (!(columns.isEmpty())) {
			
			Iterator<Column> iterCol = columns.iterator();
			while (iterCol.hasNext()) {
				Column col = iterCol.next();
				if (columnName.equalsIgnoreCase(col.getColumnName())) {
					return col;
				}
			}
		}
		
		return null;
		
	}
	
}
