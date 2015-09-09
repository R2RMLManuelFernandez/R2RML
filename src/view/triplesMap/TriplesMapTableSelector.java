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

package view.triplesMap;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.database.Table;
import net.miginfocom.swing.MigLayout;

/**
 * Establish the datbase table for the Triples Map
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class TriplesMapTableSelector extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9131842232944898463L;
	private final JPanel contentPanel = new JPanel();

	private JComboBox<String> comboBox;
	private String[] tableNames;
	private ArrayList<Table> databaseTables;
	private Table selectedTable;
	private boolean cancel = false;
	
	/**
	 * Create the dialog.
	 * @param frame 
	 * @param databaseTables 
	 */
	public TriplesMapTableSelector(JFrame frame, ArrayList<Table> paramDatabaseTables) {
		super(frame, true);
		this.databaseTables = paramDatabaseTables;
		tableNames = new String[databaseTables.size()];
		int tblNameSize = databaseTables.size();
		for (int i = 0; i < tblNameSize; i++) {
			tableNames[i] = databaseTables.get(i).getTableName();
		}
		selectedTable = databaseTables.get(0);
		initialize();
	}

	/**
	 * 
	 */
	private void initialize() {
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 250);
		getContentPane().setLayout(new MigLayout("", "[420px]", "[211px][33px]"));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, "cell 0 0,grow");
		contentPanel.setLayout(new MigLayout("", "[grow]", "[grow,center][20.00][10px:n][][grow]"));
		
		JLabel labelSelectTable = new JLabel("Select a table from the database to match the triples map");
		contentPanel.add(labelSelectTable, "cell 0 1,alignx center");
		
		comboBox = new JComboBox<String>();
		contentPanel.add(comboBox, "cell 0 3,growx");
		
		DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel<String>(tableNames);
		comboBox.setModel(comboModel);
		comboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				comboBoxActionPerformed(e);
				
			}
		});
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, "cell 0 1,growx,aligny top");

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				okButtonActionPerformed(e);
				
			}
		});
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonCancelActionPerformed(e);
				
			}
		});
		buttonPane.add(cancelButton);
		
	}

	/**
	 * @param e
	 */
	protected void okButtonActionPerformed(ActionEvent e) {
		this.setVisible(false);
		
	}

	/**
	 * @param e
	 */
	protected void buttonCancelActionPerformed(ActionEvent e) {
		
		cancel = true;
		this.dispose();
		
	}

	/**
	 * Checks if the cancel flag in the dialog is raised
	 * 
	 * @return
	 */
	public boolean checkCancel() {
		
		return cancel;
				
	}
	
	/**
	 * @param e
	 */
	protected void comboBoxActionPerformed(ActionEvent e) {

		int tableSelected = comboBox.getSelectedIndex();	
		selectedTable = databaseTables.get(tableSelected);
		System.out.println("La tabla que se ha elegido es: " + selectedTable.getTableName());
	}

	/**
	 * @return the selevted table
	 */
	public Table getTable() {

		return selectedTable;
	}
	
}
