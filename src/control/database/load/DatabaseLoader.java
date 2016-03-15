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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

import model.database.Column;
import model.database.Database;
import model.database.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import control.database.connection.DatabaseConnection;

/**
 * Querys the database to obtain the necesary metadata to constructs the model
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class DatabaseLoader {
	
	private static Logger logger = LoggerFactory.getLogger(DatabaseLoader.class);

	private static DatabaseLoader instance;
	
	private DatabaseLoader() {
		
	}
	
	/**
	 * singleton: compites for the loader
	 * 
	 * @return
	 */
	public static DatabaseLoader getInstace() {
		
		if (instance == null) {
			
			instance = new DatabaseLoader();
			
		}
		
		return instance;
	}
	
	// TODO sacar los tipos de columna a una clase de constantes
	
	/**
	 * Querys the database and construct the metadata lists
	 * @param database
	 * @throws Exception
	 */
	public void queryDatabase(Database database) throws Exception {
		
		Connection connection = null;
		
		try {
			//Connects to the database
			DatabaseConnection databaseConnection = DatabaseConnection.getConnection(database);
			logger.debug("get connection");
			connection = databaseConnection.connect();
			
			//Obtains the database schema (lists of tables schema)
			DatabaseSchemaExtractor databaseSchemaExtractor = new DatabaseSchemaExtractor(connection);
			logger.debug("get schema");
			ArrayList<TableSchemaExtractor> databaseSchema = databaseSchemaExtractor.getDatabaseSchema();
			logger.debug("schema " + databaseSchema.size());
			
			if (databaseSchema != null) {
				
				//Creates a schema for each table in the database
				for (TableSchemaExtractor tableSchema : databaseSchema) {
					
					String tableName = tableSchema.getTableName();
					Table table = new Table(database, tableName);
					
					Set<String> primaryKeyColumns = tableSchema.getPrimaryKeyColumns();
					Set<String> foreignKeyColumns = tableSchema.getForeignKeyColumns();
					Set<String> primaryForeignColumns = tableSchema.getPrimaryForeignColumns();
					Set<String> simpleColumns = tableSchema.getSimpleColumns();
					
					if (primaryKeyColumns != null ) {
						
						for (String primaryKeyColumn : primaryKeyColumns) {
							String type = "PRIMARY";
							@SuppressWarnings("unused")
							Column column = new Column(table, primaryKeyColumn, type);
						}
						
					}
					
					if (foreignKeyColumns != null ) {
						
						for (String foreignKeyColumn : foreignKeyColumns) {
							String type = "FOREIGN";
							@SuppressWarnings("unused")
							Column column = new Column(table, foreignKeyColumn, type);
						}
												
					}
					
					if (primaryForeignColumns != null ) {
						
						for (String primaryForeignColumn : primaryForeignColumns) {
							String type = "PRIMARYFOREIGN";
							@SuppressWarnings("unused")
							Column column = new Column(table, primaryForeignColumn, type);;
						}
												
					}
					
					if (simpleColumns != null ) {
						
						for (String simpleColumn : simpleColumns) {
							String type = "SIMPLE";
							@SuppressWarnings("unused")
							Column column = new Column(table, simpleColumn, type);;
						}
												
					}
					
				}
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
			throw e; 
			
		} finally {
			
			if (connection != null) {
				
				connection.close();
				
			}
			
		}
		
	}

}
