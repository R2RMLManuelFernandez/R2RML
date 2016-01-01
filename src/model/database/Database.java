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

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents a database
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class Database {
	
	private String DBMS;
	
	private String databaseName;
	
	private InetAddress host; 
	
	private int port;
	
	private String username;
	
	private String password;
	
	private ArrayList<Table> tables = null;
	
	public Database() {
		
		this.DBMS = null;
		this.databaseName = null;
		this.host = null;
		this.port = 0;
		this.username = null;
		this.password = null;
		
	}
	
	public Database(String DBMS, String name, InetAddress host, int port, String username, String password) {
		
		this.DBMS = DBMS;
		this.databaseName = name;
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
		this.tables = new ArrayList<Table>(0);
		
	}

	/**
	 * @return
	 */
	public String getDBMS() {
		return DBMS;
	}

	/**
	 * @param dBMS
	 */
	public void setDBMS(String dBMS) {
		DBMS = dBMS;
	}

	/**
	 * @return
	 */
	public String getDatabaseName() {
		return databaseName;
	}

	/**
	 * @param name
	 */
	public void setDatabaseName(String name) {
		this.databaseName = name;
	}

	/**
	 * @return
	 */
	public InetAddress getHost() {
		return host;
	}

	/**
	 * @param host
	 */
	public void setHost(InetAddress host) {
		this.host = host;
	}

	/**
	 * @return
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return
	 */
	public ArrayList<Table> getTables() {
		return tables;
	}

	/**
	 * @param table
	 */
	public void addTable(Table table) {
		this.tables.add(table);
	}

	/**
	 * @return
	 */
	public Boolean hasTables() {
		return this.tables.isEmpty()? false : true;
	}
	
	/**
	 * @param tableName
	 * @return table
	 */
	public Table getTable(String tableName) {
		
		if (!(tables.isEmpty())) {
			
			Iterator<Table> itTab = tables.iterator();
			while (itTab.hasNext()) {
				Table table = itTab.next();
				if (tableName.equalsIgnoreCase(table.getTableName())) {
					return table;
				}
			}
		}
		
		return null;
	}
	
}
