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

package view.tableMapping;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import model.mapping.MappingTable;
import net.miginfocom.swing.MigLayout;

public class ViewTableMapping extends JPanel implements Observer {

	private static final long serialVersionUID = -7473179144746801714L;
	
	private JTable table;
	private JButton btnDeleteall;
	private JButton btnDeleteMapping;
	
	/**
	 * Create the panel.
	 */
	public ViewTableMapping() {
		setLayout(new MigLayout("", "[grow]", "[grow][]"));
		table = new JTable();
		table.setShowVerticalLines(true);
		table.setShowHorizontalLines(true);
		table.setBorder(new LineBorder(Color.DARK_GRAY));
		table.setBackground(UIManager.getColor("Table.alternateRowColor"));
		add(table, "cell 0 0,grow");
		
		btnDeleteall = new JButton("DeleteAll");
		btnDeleteall.setName("btnDeleteAll");
		add(btnDeleteall, "flowx,cell 0 1,alignx trailing");
		
		btnDeleteMapping = new JButton("Delete Mapping");
		btnDeleteMapping.setName("btnDeleteMapping");
		add(btnDeleteMapping, "cell 0 1,alignx trailing");
	}
	
	/**
	 * @param tableModel
	 */
	public void setModel(MappingTable tableModel) {
		table.setModel(tableModel);
		tableModel.getModel().addObserver(this);
	}

	/**
	 * @param controller
	 */
	public void setController(ActionListener controller) {
		btnDeleteMapping.addActionListener(controller);
		btnDeleteall.addActionListener(controller);
	}
	
	/**
	 * @return the table
	 */
	public JTable getTable() {
		return table;
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
	}

}
