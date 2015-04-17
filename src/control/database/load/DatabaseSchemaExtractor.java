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
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Extracts the schma from the connected database
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class DatabaseSchemaExtractor {

	private DatabaseMetaData metadata;
	private ArrayList<TableSchemaExtractor> tablesSchemas;
	
	public DatabaseSchemaExtractor(Connection conn) throws SQLException {
		
		this.metadata = conn.getMetaData();
		this.tablesSchemas = null;
		this.buildDatabaseSchema();
		
	}

	/**
	 * Generates the database schema
	 * 
	 * @throws SQLException
	 */
	private void buildDatabaseSchema() throws SQLException {
		
		Set<String> userTables = this.getUserTables();
		this.tablesSchemas = new ArrayList<TableSchemaExtractor>();
		for (String tableName : userTables) {
			
			TableSchemaExtractor tableExtractor = new TableSchemaExtractor(this.metadata, tableName);
			this.tablesSchemas.add(tableExtractor);
			
		}
		
	}

	/**
	 * Gets the metadata of the tables in the database
	 * @return
	 * @throws SQLException
	 */
	private Set<String> getUserTables() throws SQLException {
		
		String[] types = {"TABLE", "VIEW"};
		ResultSet res = metadata.getTables(null, metadata.getUserName(), "%", types);
		HashSet<String> userTables = new HashSet<String>();
		while(res.next()) {
			
			userTables.add(res.getString(3));
			
		}
		res.close();
		
		return userTables;
		
	}
	
	/**
	 * @return
	 */
	public ArrayList<TableSchemaExtractor> getDatabaseSchema() {
		
		return this.tablesSchemas;
		
	}
	
}
