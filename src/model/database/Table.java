/**
 * 
 */
package model.database;

import java.util.ArrayList;

/**
 * Represents a database table
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class Table {

	private Database database;
	
	private String tableName;
	
	private ArrayList<Column> columns = null;
	
	public Table(Database database, String name) {

		this.tableName = name;
		this.columns = new ArrayList<Column>(0);
		assert database != null;
		if (database == null) {
			//log.error("Database is null");
		}
		this.database = database;
		this.database.addTable(this);
	}

	/**
	 * @return
	 */
	public Database getDatabase() {
		return database;
	}

	/**
	 * @return
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return
	 */
	public ArrayList<Column> getColumns() {
		return columns;
	}

	/**
	 * @param column
	 */
	public void addColumn(Column column) {
		this.columns.add(column);
	}
	
	/**
	 * @return
	 */
	public Boolean hasColumns() {
		return this.columns.isEmpty()? false : true;
	}
	
}
