/**
 * 
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
