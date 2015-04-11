package model.database;

/**
 * Represents a column in a database
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class Column {
	
	private Table table;
	
	private String dataType;
	
	private String columnName;
	
	public Column(Table table, String name, String dataType) {

		this.columnName = name;
		this.dataType = dataType;
		assert table != null;
		if (table != null) {
			//log.error("Database is null");

		this.table = table;
		this.table.addColumn(this);
		}
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return columnName;
	}
}
