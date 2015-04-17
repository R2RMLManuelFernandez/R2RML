/*
 * Copyright 2015 Manuel Fern�ndez P�rez
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
