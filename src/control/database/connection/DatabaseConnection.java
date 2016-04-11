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

package control.database.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import model.database.Database;

/**
 * Connection to database
 * 
 * @author Manuel Fernandez Perez
 *
 */
public abstract class DatabaseConnection {
	//the type of databases supported
	public static final String DBMS_MYSQL = "mysql";
	public static final String DBMS_ORACLE = "oracle";
	public static final String DBMS_FIREBIRD = "firebird";
	public static final String DBMS_MARIADB = "mariadb";

	//the database driver
	protected String driver;
	//the database URL
	protected String databaseURL;
	//the database
	protected Database database;
	
	protected DatabaseConnection(Database database) throws SQLException {
		
		this.database = database;
		this.setDatabaseDriver();
		this.setDatabaseURL();
		
	}

	/**
	 * singleton: compites for the connection to the database
	 * 
	 * @param database
	 * @return
	 * @throws SQLException
	 */
	public static DatabaseConnection getConnection(Database database) throws SQLException {
		
		if (database.getDBMS().equalsIgnoreCase(DBMS_MYSQL)) {
			
			return new MySqlDatabaseConnection(database);
			
		}
		else if(database.getDBMS().equalsIgnoreCase(DBMS_ORACLE)) {
			
			return new OracleDatabaseConnection(database);
			
		}
		else if(database.getDBMS().equalsIgnoreCase(DBMS_FIREBIRD)) {
			
			return new FirebirdDatabaseConnection(database);
			
		}
		else if(database.getDBMS().equalsIgnoreCase(DBMS_MARIADB)) {
			
			return new MariadbDatabaseConnection(database);
			
		}
		else {
			
			throw new SQLException("Database not yet supported");
			
		}
		
	}
	
	/**
	 * @throws SQLException
	 */
	public abstract void setDatabaseURL() throws SQLException;

	/**
	 * @throws SQLException
	 */
	public abstract void setDatabaseDriver() throws SQLException;
	
	/**
	 * creates a connection to the database
	 * @return
	 * @throws SQLException
	 */
	public Connection connect() throws SQLException {
		
		Connection conn = null;
		
		try {
			
			Class.forName(this.driver);
			conn = DriverManager.getConnection(databaseURL, database.getUsername(), database.getPassword());
			
		}
		catch (Exception e) {
			
			e.printStackTrace();
			throw new SQLException();
			
		}
		
		return conn;
		
	}

/*	*//**
	 * @return
	 *//*
	public static String getDbmsMysql() {
		
		return DBMS_MYSQL;
		
	}

	*//**
	 * @return
	 *//*
	public static String getDbmsOracle() {
		
		return DBMS_ORACLE;
		
	}
*/
	/**
	 * @return
	 */
	public String getDriver() {
		return driver;
	}

	/**
	 * @return
	 */
	public String getDatabaseURL() {
		
		return databaseURL;
		
	}
	


}
