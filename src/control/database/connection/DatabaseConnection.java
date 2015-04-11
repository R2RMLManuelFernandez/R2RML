package control.database.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import model.database.Database;

/**
 * Represents the connection to a database
 * 
 * @author Manuel Fernandez Perez
 *
 */
public abstract class DatabaseConnection {
	//the type of databases supported
	public static final String DBMS_MYSQL = "mysql";
	public static final String DBMS_ORACLE = "oracle";
	//public static final String DBMS_ODBC = "ODBC";
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

	/**
	 * @return
	 */
	public static String getDbmsMysql() {
		return DBMS_MYSQL;
	}

	/**
	 * @return
	 */
	public static String getDbmsOracle() {
		return DBMS_ORACLE;
	}

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
