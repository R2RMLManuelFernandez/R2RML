package model.menu.databaseConnection;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import model.database.Database;

/**
 * A table. Each row contains the data to create a connection to a table
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class DatabasesTable extends AbstractTableModel {

	private static final long serialVersionUID = -8671334420818148664L;
	
	private static final String[] columnNames = {"DBMS", "DB Name", "Host", "Port", "User"};
	
	private Vector<Database> data = new Vector<>(0);

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		Database db = data.elementAt(rowIndex);
		Object value = null;
		
		switch (columnIndex) {
		case 0:
			value = db.getDBMS();
			break;
		case 1:
			value = db.getDatabaseName();
			break;
		case 2:
			value = db.getHost();
			break;
		case 3:
			value = db.getPort();
			break;
		case 4:
			value = db.getUsername();
			break;
		case 5:
			value = db.getPassword();
			break;
		}
		
		return value;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Class getColumnClass(int index) {
		return getValueAt(0, index).getClass();
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
	 */
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	public String getColumnName(int index) {
		return columnNames[index];	
	}
	
	/**
	 * @param newDatabase
	 */
	public void addRow(Database newDatabase) {
		
		data.add(newDatabase);
		fireTableDataChanged();
	}
	
	/**
	 * @param newDatabase
	 * @param index
	 */
	public void addRow(Database newDatabase, int index) {
		
		data.add(index, newDatabase);
		data.remove(index + 1);
		fireTableDataChanged();
	}
	
	/**
	 * @param row
	 */
	public void deleteRow(int row) {
		data.remove(row);
		fireTableDataChanged();
	}

	/**
	 * @param index
	 * @return
	 */
	public Database getDatabaseAtIndex(int index) {
		return data.get(index);
	}

}
