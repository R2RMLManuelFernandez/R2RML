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

package view.menu.database;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import model.database.Database;
import model.menu.databaseConnection.DatabasesTable;
import model.menu.databaseConnection.StaXDatabaseParser;
import model.menu.databaseConnection.StaXDatabaseWriter;
import net.miginfocom.swing.MigLayout;
import control.database.connection.DatabaseConnection;

/**
 * Creates a dialog to load a database
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class OpenDatabaseDialog extends JDialog {

	private static final long serialVersionUID = -4449949069749790495L;
	
	private DatabasesTable databasesModelTable;
	
	private JPanel contentPane;
	
	private JLabel labelTitle;
	private JSeparator titleSeparator;
	private JLabel labelDatabases;
	private JTable tableDatabases;
	private JScrollPane scrollPane;
	private JPanel panelDatabaseConnection;
	private JButton buttonNewDatabase;
	private JButton buttonEditDatabase;
	private JButton buttonDeleteDatabase;
	private JButton buttonTestDatabase;
	private JPanel panelDatabasesList;
	private JButton buttonLoadDatabaseList;
	private JFileChooser fileChooserLoadDatabaseList;
	private JButton buttonSaveDatabaseList;
	private JFileChooser fileChooserSaveDatabaseList;
	private JButton buttonCancel;
	private JButton buttonFinish;
	
	private DialogDatabaseData dialogNewDB = null;
	
	private boolean cancel = false; 

	/**
	 * Create the dialog.
	 */
	public OpenDatabaseDialog(JFrame frame) {
		super(frame,true);
		initialize();
		
	}

	/**
	 * Initialize the contents of the dialog
	 */
	private void initialize() {
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 650, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[15.00][30.00][200.00,grow][150.00][150.00]", "[40.00][][][25.00][][73.00][29.00,grow]"));
		
		labelTitle = new JLabel("Enter or Select a Database Connection Data");
		labelTitle.setForeground(Color.BLACK);
		labelTitle.setBackground(Color.WHITE);
		contentPane.add(labelTitle, "cell 1 0 4 1,alignx center");
		
		titleSeparator = new JSeparator();
		contentPane.add(titleSeparator, "cell 0 1 5 1,grow");
		
		labelDatabases = new JLabel("Databases");
		contentPane.add(labelDatabases, "cell 1 3");
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, "cell 0 4 4 2,grow");
		
		databasesModelTable = new DatabasesTable();
		tableDatabases = new JTable(databasesModelTable);
		tableDatabases.setRowHeight(20);
		scrollPane.setViewportView(tableDatabases);
		tableDatabases.setShowVerticalLines(true);
		tableDatabases.setShowHorizontalLines(true);
		
		panelDatabaseConnection = new JPanel();
		panelDatabaseConnection.setBorder(new TitledBorder(null, "Database Connection", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, new Color(59, 59, 59)));
		contentPane.add(panelDatabaseConnection, "cell 4 4,grow");
		panelDatabaseConnection.setLayout(new MigLayout("", "[317.00px,fill]", "[23px][][][]"));
		
		buttonNewDatabase = new JButton("New Database");
		buttonNewDatabase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonNewDatabaseActionPerformed(e);
			}
		});
		panelDatabaseConnection.add(buttonNewDatabase, "cell 0 0,growx,aligny top");
		
		buttonEditDatabase = new JButton("Edit Database");
		buttonEditDatabase.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonEditDatabaseActionPerformed(e);				
			}
		});
		panelDatabaseConnection.add(buttonEditDatabase, "cell 0 1,alignx left,aligny top");
		
		buttonDeleteDatabase = new JButton("Delete Database");
		buttonDeleteDatabase.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonDeleteDatabaseActionPerformed(e);
			}
		});
		panelDatabaseConnection.add(buttonDeleteDatabase, "cell 0 2,alignx left,aligny top");
		
		buttonTestDatabase = new JButton("Test Database");
		buttonTestDatabase.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				buttonTestDatabaseActionPerformed(arg0);
			}
		});
		panelDatabaseConnection.add(buttonTestDatabase, "cell 0 3,alignx left,aligny top");
		
		panelDatabasesList = new JPanel();
		panelDatabasesList.setBorder(new TitledBorder(null, "Database List", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, new Color(59, 59, 59)));
		contentPane.add(panelDatabasesList, "cell 4 5,grow");
		panelDatabasesList.setLayout(new MigLayout("", "[89px,grow,fill]", "[23px][][]"));
		
		fileChooserLoadDatabaseList = new JFileChooser();
		
		buttonLoadDatabaseList = new JButton("Load Database List");
		buttonLoadDatabaseList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonLoadDatabaseListActionPerformed(arg0);
			}
		});
		panelDatabasesList.add(buttonLoadDatabaseList, "cell 0 0,aligny baseline");
		
		fileChooserSaveDatabaseList = new JFileChooser("C:/Users/Manuel/Desktop/Ontologias");
		
		buttonSaveDatabaseList = new JButton("Save Datbase List");
		buttonSaveDatabaseList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonSaveDatabaseListActionPerformed(e);
			}
		});
		panelDatabasesList.add(buttonSaveDatabaseList, "cell 0 1,alignx center,aligny center");
		
		buttonCancel = new JButton("Cancel");
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonCancelActionPerformed(e);
			}
		});
		
		buttonFinish = new JButton("Finish");
		buttonFinish.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonFinishActionPerformed(e);				
			}
		});
		contentPane.add(buttonFinish, "cell 3 6,growx,aligny center");
		contentPane.add(buttonCancel, "cell 4 6,growx,aligny center");
		
	}

	/**
	 * Creates a dialog to create a new database.
	 * If the database is succesfully created loads it into list of databases
	 * 
	 * @param e
	 */
	private void buttonNewDatabaseActionPerformed(ActionEvent e) {
		
		if (dialogNewDB == null) {		
			dialogNewDB = new DialogDatabaseData(this);
			dialogNewDB.pack();
			dialogNewDB.setLocationRelativeTo(this);
		}
		else {
			dialogNewDB.resetDatabaseData();
		}
		dialogNewDB.setVisible(true);
		
		if (dialogNewDB.checkCorrectDataAndNotCancel()) {
			 
			Database newDatabase = dialogNewDB.getValidatedDatabaseData();
			
			databasesModelTable.addRow(newDatabase);
			
		}

	}
	
	/**
	 * Creates a dialog to edit the data of the database selected from the list of databases
	 * 
	 * @param e
	 */
	private void buttonEditDatabaseActionPerformed(ActionEvent e) {
		
		int row = tableDatabases.getSelectedRow();
		
		if (row ==-1) {
			JOptionPane.showMessageDialog(this, "You havent selected a database", "Select a database", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
			
		Database database = databasesModelTable.getDatabaseAtIndex(row);
		
		if (dialogNewDB == null) {		
			dialogNewDB = new DialogDatabaseData(this);
			dialogNewDB.resetDatabaseData(database);
			dialogNewDB.pack();
			dialogNewDB.setLocationRelativeTo(this);
		}
		else {
			dialogNewDB.resetDatabaseData(database);
			dialogNewDB.pack();
			dialogNewDB.setLocationRelativeTo(this);
		}
		dialogNewDB.setVisible(true);
		
		if (dialogNewDB.checkCorrectDataAndNotCancel()) {
			 
			Database newDatabase = dialogNewDB.getValidatedDatabaseData();
			
			databasesModelTable.addRow(newDatabase, row);
		}
		
	}

	/**
	 * Deletes the database selected from the list of databases
	 * 
	 * @param e
	 */
	private void buttonDeleteDatabaseActionPerformed(ActionEvent e) {
		
		int row = tableDatabases.getSelectedRow();
		if (row != -1) {
			databasesModelTable.deleteRow(row);
		}
		else {
			JOptionPane.showMessageDialog(this, "You havent selected a database", "Select a database", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	
	/**
	 * test the connection to the database selected from the list of databases
	 * 
	 * @param arg0
	 */
	private void buttonTestDatabaseActionPerformed(ActionEvent arg0) {
		
		int row = tableDatabases.getSelectedRow();
		if (row != -1) {
			Database db = databasesModelTable.getDatabaseAtIndex(row);
			DatabaseConnection databaseConnection;
			Connection connection = null;
			try {
				databaseConnection = DatabaseConnection.getConnection(db);
				connection = databaseConnection.connect();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(this, "Cant connect to database", "Connection Error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} finally {
				if (connection != null) {
					try {
						JOptionPane.showMessageDialog(this, "Connected to Database", "Connection Succesfull", JOptionPane.INFORMATION_MESSAGE);
						connection.close();
					} catch (SQLException e) {
						System.out.println("DatabaseDataCollector: buttonTestDatabaseActionPerformed --> Couldnt close the connection");
						e.printStackTrace();
					}
				}
			}

		}
		else {
			JOptionPane.showMessageDialog(this, "You havent selected a database", "Select a database", JOptionPane.INFORMATION_MESSAGE);

		}

	}

	/**
	 * Loads the databases in a XML file into the list of databases
	 * 
	 * @param arg0
	 */
	private void buttonLoadDatabaseListActionPerformed(ActionEvent arg0) {
		
		int result = fileChooserLoadDatabaseList.showOpenDialog(null);
		
		if (result == JFileChooser.APPROVE_OPTION) {
			
			StaXDatabaseParser loadDatabasesParser = new StaXDatabaseParser(fileChooserLoadDatabaseList.getSelectedFile().getPath());
			
		    List<Database> loadedDatabases = new Vector<Database>(0);
			try {
				loadedDatabases = loadDatabasesParser.read();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    if (!loadedDatabases.isEmpty()) {
		    	int rowCount = databasesModelTable.getRowCount();
	    		if ( rowCount > 0) {
	    			for (int i = 0; i < rowCount; i++)
	    			databasesModelTable.deleteRow(i);
	    		}
		    	for (Database db: loadedDatabases) {
		    		databasesModelTable.addRow(db);
		    	}
		    	tableDatabases.setModel(databasesModelTable);
		    }
		    else {
		    	JOptionPane.showMessageDialog(this, "The selected file doesnt content any database description", "Select a file", JOptionPane.WARNING_MESSAGE);
		    }
		}
	}
	
	
	/**
	 * Writes the list of databases into a XML file
	 * 
	 * @param e
	 */
	private void buttonSaveDatabaseListActionPerformed(ActionEvent e) {
		
		int result = fileChooserSaveDatabaseList.showSaveDialog(null);
		
		if (result == JFileChooser.APPROVE_OPTION) {
			
			StaXDatabaseWriter savedDatabasesWriter = new StaXDatabaseWriter(fileChooserSaveDatabaseList.getSelectedFile().getPath());
			
			int databasesSize = databasesModelTable.getRowCount();
		    	    
//		    System.out.println("writeBookmarks-> numero de bookmarks " + bookmarksSize);
		    
		    for (int i = 0; i < databasesSize; i++) {
		    	
//		    	System.out.println("writeBookmarks-> bookmarks para escribir " + listModel.get(i));

		    	Database db = databasesModelTable.getDatabaseAtIndex(i);
		    	
		    	ArrayList<String> databaseData = new ArrayList<String>();
		    	
			    databaseData.add(db.getDBMS());
			    databaseData.add(db.getDatabaseName());
			    databaseData.add(db.getHost().getHostAddress());
			    databaseData.add(String.valueOf(db.getPort()));
			    databaseData.add(db.getUsername());
			    databaseData.add(db.getPassword());
			    
				try {
					savedDatabasesWriter.save(databaseData);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    	
		    }
		}				
	}

	/**
	 * Cancels the action in progres and returns the control to the parent component
	 * 
	 * @param e
	 */
	private void buttonCancelActionPerformed(ActionEvent e) {
		
		cancel = true;
		this.dispose();
		
	}
	

	/**
	 * Returns the control to the parent component. A valid database is selected
	 * 
	 * @param e
	 */
	private void buttonFinishActionPerformed(ActionEvent e) {
		
		if (tableDatabases.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(this, "Please, select a Database", "Select a Database", JOptionPane.WARNING_MESSAGE);
		}
		else {
			this.dispose();
		}
		
	}
	
	/**
	 * @return the data to establish a connection to a database
	 */
	public Object[] getDatabaseDescription() {
	
		Object[] dbDesc = null;
		
		int row = tableDatabases.getSelectedRow();
		
		if (row != -1) {
			
			dbDesc = new Object[6];
			
			dbDesc[0] = databasesModelTable.getValueAt(row, 0);
			dbDesc[1] = databasesModelTable.getValueAt(row, 1);
			dbDesc[2] = databasesModelTable.getValueAt(row, 2);
			dbDesc[3] = databasesModelTable.getValueAt(row, 3);
			dbDesc[4] = databasesModelTable.getValueAt(row, 4);
			dbDesc[5] = databasesModelTable.getValueAt(row, 5);
		}
		
		return dbDesc;
		
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
	 * Reset the cancel flag
	 */
	public void resetCancel() {
		
		cancel = false;
		
	}

}
