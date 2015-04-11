package control.database.connection;

import java.sql.SQLException;

import model.database.Database;

/**
 * Represents the connection to an Oracle database
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class OracleDatabaseConnection extends DatabaseConnection {

	/**
	 * oracle driver
	 */
	public static final String oracle_driver = "oracle.jdbc.driver.OracleDriver";
	
	public OracleDatabaseConnection(Database database) throws SQLException {
		super(database);
	}
	
	/* (non-Javadoc)
	 * @see control.database.connection.DatabaseConnection#setDatabaseDriver()
	 */
	@Override
	public void setDatabaseDriver() throws SQLException {
		super.driver = oracle_driver;

	}

	/* (non-Javadoc)
	 * @see control.database.conection.DatabaseConector#setDatabaseDriver()
	 */
	@Override
	public void setDatabaseURL() throws SQLException {
		super.databaseURL = "jdbc:oracle:thin:@" + database.getHost().getHostAddress() + ":" + database.getPort() + "/" + database.getDatabaseName();

	}

}
