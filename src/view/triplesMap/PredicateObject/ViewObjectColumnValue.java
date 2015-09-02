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

package view.triplesMap.PredicateObject;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import model.database.Column;
import model.r2rmlmapping.triplesMap.ColumnValueObjectMap;
import net.miginfocom.swing.MigLayout;
import control.r2rmlmapping.triplesMap.ControllerColumnValueObjectMap;

/**
 * View to represent the object
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class ViewObjectColumnValue extends JPanel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7636003247188178952L;
//	private JTextField textFieldObject;
	private JButton buttonDelete;
	private JList<String> list;
	private DefaultListModel<String> listModel;
	private JButton buttonDeleteColumns;

	private ColumnValueObjectMap model;

	/**
	 * Create the panel.
	 */
	public ViewObjectColumnValue() {
		setBorder(new TitledBorder(null, "ColumnValue ObjectMap", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new MigLayout("", "[grow]", "[20px:n,grow][][][]"));
		
		list = new JList<String>();
		listModel = new DefaultListModel<String>();
		add(list, "cell 0 0,grow");
		list.setModel(listModel);
/*		
		textFieldObject = new JTextField();
		add(textFieldObject, "cell 0 1,growx");
		textFieldObject.setColumns(10);
*/		
		
		buttonDeleteColumns = new JButton("Delete Columns");
		buttonDeleteColumns.setName("Delete Columns");
		add(buttonDeleteColumns, "cell 0 1,alignx center");
		buttonDelete = new JButton("Delete");
		buttonDelete.setName("Delete");
		add(buttonDelete, "cell 0 3,alignx center");

	}

	public void setModel(ColumnValueObjectMap objectMap) {
		this.model = objectMap;
		this.model.addObserver(this);
//		textFieldObject.setText(model.getObject());
		ArrayList<Column> columns = model.getObjectColumns();
		listModel.clear();
		for (Column col: columns) {
			listModel.addElement(col.getColumnName());			
		}

	}

	@Override
	public void update(Observable o, Object arg) {
//		textFieldObject.setText(model.getObject());
		ArrayList<Column> columns = model.getObjectColumns();
		listModel.clear();
		for (Column col: columns) {
			listModel.addElement(col.getColumnName());			
		}
		this.repaint();
		this.updateUI();

        System.out.println("ViewObjectColumnValue --> datos actualizados");
		
	}

	public void setController(ControllerColumnValueObjectMap controllerObjectCV) {
		buttonDelete.addActionListener(controllerObjectCV);
		buttonDeleteColumns.addActionListener(controllerObjectCV);
	}

	/**
	 * @return 
	 * 
	 */
	public ArrayList<String> getSelectedColumns() {
		int[] index = list.getSelectedIndices();
		ArrayList<String> selectedColumns = new ArrayList<String>();
		for (int i: index) {
			selectedColumns.add(listModel.get(i));
		}
		return selectedColumns;
		
	}

	
}
