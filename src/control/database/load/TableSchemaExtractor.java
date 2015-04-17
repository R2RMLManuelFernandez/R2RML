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

package control.database.load;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Constructs the schema from a databases table
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class TableSchemaExtractor {

	private DatabaseMetaData metadata;
	private String tableName;
	private Set<String> primaryKeyColumns;
	private Set<String> foreignKeyColumns;
	private Set<String> primaryForeignColumns;
	private Set<String> simpleColumns;
	
	public TableSchemaExtractor(DatabaseMetaData metadata, String tableName) throws SQLException {
		
		this.metadata = metadata;
		this.tableName = tableName;
		this.buildTableSchema();
		
	}

	/**
	 * @return
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @return
	 */
	public Set<String> getPrimaryKeyColumns() {
		return primaryKeyColumns;
	}

	/**
	 * @return
	 */
	public Set<String> getForeignKeyColumns() {
		return foreignKeyColumns;
	}

	/**
	 * @return
	 */
	public Set<String> getPrimaryForeignColumns() {
		return primaryForeignColumns;
	}

	/**
	 * @return
	 */
	public Set<String> getSimpleColumns() {
		return simpleColumns;
	}

	/**
	 * Creates the schema
	 * 
	 * @throws SQLException
	 */
	private void buildTableSchema() throws SQLException {

		this.calculatePrimaryKeyColumns();
		this.calculateForeignKeyColumns();
		this.calculatePrimaryForeignColumns();
		this.calculateSimpleColumns();
		this.primaryKeyColumns.removeAll(primaryForeignColumns);
		this.foreignKeyColumns.removeAll(primaryForeignColumns);
		
	}
	
	/**
	 * Gets the primary key columns from the database
	 * 
	 * @throws SQLException
	 */
	private void calculatePrimaryKeyColumns() throws SQLException {
		
		ResultSet res = metadata.getPrimaryKeys(null, null, tableName);
		this.primaryKeyColumns = new HashSet<String>();
		while (res.next()) {
			
			this.primaryKeyColumns.add(res.getString(4));
			
		}
		res.close();
		
	}

	/**
	 * Gets the foreign key columns from the database
	 * 
	 * @throws SQLException
	 */
	private void calculateForeignKeyColumns() throws SQLException {
		
		ResultSet res = metadata.getImportedKeys(null, null, tableName);
		this.foreignKeyColumns = new HashSet<String>();
		while (res.next()) {
			
			this.foreignKeyColumns.add(res.getString(8));
			
		}
		res.close();
		
	}
	
	/**
	 * Gets the columns from the database that are both primary key and foreign key
	 * 
	 * @throws SQLException
	 */
	private void calculatePrimaryForeignColumns() throws SQLException {
		
		primaryForeignColumns = new HashSet<String>();
		this.primaryForeignColumns.addAll(primaryKeyColumns);
		this.primaryForeignColumns.retainAll(foreignKeyColumns);
		
	}

	/**
	 * Gets the columns from the database that arent PK or FK
	 * 
	 * @throws SQLException
	 */
	private void calculateSimpleColumns() throws SQLException {
		
		ResultSet res = metadata.getColumns(null, null, tableName, null);
		this.simpleColumns = new HashSet<String>();
		while (res.next()) {
			
			this.simpleColumns.add(res.getString(4));
			
		}
		res.close();
		this.simpleColumns.removeAll(primaryKeyColumns);
		this.simpleColumns.removeAll(foreignKeyColumns);
		
	}
	
}
