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

package model.mapping;

import javax.swing.table.AbstractTableModel;


/**
 * The table to store the mapping
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class MappingTable extends AbstractTableModel {

	private static final long serialVersionUID = 6124561308466221268L;

	private static String[] columnNames = {"Ontology Element", "Database Element"};
	
	private Mapping model;
	
	
	public MappingTable(Mapping mapping) {
		model = mapping;
	}
	
	/**
	 * @return the model
	 */
	public Mapping getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(Mapping model) {
		this.model = model;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return model.getMapping().size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int row, int colum) {
		
		MappingElement mapp = model.getMapping().get(row);
		
		switch (colum) {
		case 0:
			return mapp.getOntologyElement();
		case 1:
			return mapp.getDatabaseColumn();
		}
		
		return 0;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	public String getColumnName(int index) {
		return columnNames[index];	
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
	
	/**
	 * @param element
	 */
	public void addMappingElement(MappingElement element) {
		model.addMapping(element);
		fireTableDataChanged();
	}
	
	/**
	 * @param element
	 */
	public void removeMappingElement(MappingElement element) {
		if (model.getMapping().contains(element)) {
			model.removeMapping(element);
			fireTableDataChanged();
		}
	}
	
	/**
	 * @param row
	 */
	public void removeMappingElement(int row) {
		if (row <= getRowCount()) {
			model.getMapping().remove(row);
			fireTableDataChanged();
		}
	}

}
