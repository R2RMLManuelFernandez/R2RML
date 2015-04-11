/**
 * 
 */
package control.database.connection;

import java.sql.SQLException;

import model.database.Database;

/**
 * Represents the connection to a MySql database
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class MySqlDatabaseConnection extends DatabaseConnection {

	/**
	 * mysql driver
	 */
	public static final String mysql_driver = "com.mysql.jdbc.Driver";
	
	public MySqlDatabaseConnection(Database database) throws SQLException {
		super(database);
	}
	
	/* (non-Javadoc)
	 * @see control.database.conection.DatabaseConector#setDatabaseURL()
	 */
	@Override
	public void setDatabaseDriver() throws SQLException {
		super.driver = mysql_driver;

	}

	/* (non-Javadoc)
	 * @see control.database.conection.DatabaseConector#setDatabaseDriver()
	 */
	@Override
	public void setDatabaseURL() throws SQLException {
		super.databaseURL = "jdbc:mysql://" + database.getHost().getHostAddress() + ":" + database.getPort() + "/" + database.getDatabaseName();
	}

}
