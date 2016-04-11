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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.database.Database;
import net.miginfocom.swing.MigLayout;

/**
 * @author Manuel Fernandez Perez
 *
 */
public class DialogDatabaseData extends JDialog {

	private static final long serialVersionUID = -1598984771548767604L;
	
	private static final String[] dbms = {"MySQL", "Oracle", "MariaDB", "Firebird"};
	private static final String localhost = "localhost";
	
	private JLabel labelDBMS;
	private JComboBox<String> comboBoxDBMS;
	private JLabel labelDatabaseName;
	private JTextField textFieldDatabaseName;
	private JLabel labelDatabaseHost;
	private JTextField textFieldHost;
	private JTextField textFieldPort;
	private JTextField textFieldUser;
	private JPasswordField passwordField;
	private JLabel labelPort;
	private JLabel labelUser;
	private JLabel labelPassword;
	private JButton buttonFinish;
	private JButton buttonCancel;
	
	private boolean dataCorrect;
	private boolean notCancel = true;
	private Database databaseData;

	/**
	 * Create the dialog.
	 */
	public DialogDatabaseData(JDialog databaseDataCollector) {
		
		super(databaseDataCollector, true);
		setTitle("New Database");
		setBounds(100, 100, 450, 400);
		getContentPane().setLayout(new MigLayout("", "[][305px:305.00px,grow][]", "[20][20][20][][20][][20][][20][][20][][20][][grow]"));
		
		labelDBMS = new JLabel("DBMS: ");
		getContentPane().add(labelDBMS, "cell 0 1,alignx trailing");
		
		comboBoxDBMS = new JComboBox<String>();
		comboBoxDBMS.setModel(new DefaultComboBoxModel<String>(dbms));
		getContentPane().add(comboBoxDBMS, "cell 1 1,growx");
		
		labelDatabaseName = new JLabel("Database Name: ");
		getContentPane().add(labelDatabaseName, "cell 0 3,alignx trailing");
		
		textFieldDatabaseName = new JTextField();
		getContentPane().add(textFieldDatabaseName, "cell 1 3,growx");
		textFieldDatabaseName.setColumns(10);
		
		labelDatabaseHost = new JLabel("Database Host: ");
		getContentPane().add(labelDatabaseHost, "cell 0 5,alignx trailing");
		
		textFieldHost = new JTextField(localhost);
		textFieldHost.setEditable(true);
		getContentPane().add(textFieldHost, "cell 1 5,growx");
		
		labelPort = new JLabel("Port: ");
		getContentPane().add(labelPort, "cell 0 7,alignx trailing");
		
		textFieldPort = new JTextField();
		getContentPane().add(textFieldPort, "cell 1 7,growx");
		textFieldPort.setColumns(10);
		
		labelUser = new JLabel("User: ");
		getContentPane().add(labelUser, "cell 0 9,alignx trailing");
		
		textFieldUser = new JTextField();
		getContentPane().add(textFieldUser, "cell 1 9,growx");
		textFieldUser.setColumns(10);
		
		labelPassword = new JLabel("Password: ");
		getContentPane().add(labelPassword, "cell 0 11,alignx trailing");
		
		passwordField = new JPasswordField();
		getContentPane().add(passwordField, "cell 1 11,growx");
		
		buttonFinish = new JButton("Finish");
		buttonFinish.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonFinishActionPerformed(e);
			}
		});
		getContentPane().add(buttonFinish, "flowx,cell 1 13,alignx trailing");
		
		buttonCancel = new JButton("Cancel");
		buttonCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonCancelActionPerformed(e);
			}
		});
		getContentPane().add(buttonCancel, "cell 1 13,alignx trailing");

	}

	/**
	 * Checks the database data. 
	 * Creates a new database from the database data.
	 * 
	 * @param e
	 */
	protected void buttonFinishActionPerformed(ActionEvent e) {
		
		dataCorrect = true;
		
		databaseData = new Database();
		
		databaseData.setDBMS((String)comboBoxDBMS.getSelectedItem());

		String dbName = textFieldDatabaseName.getText();

		if ((dbName.equals(null)) || (dbName.isEmpty())) {
			JOptionPane.showMessageDialog(this, "Please, enter a valid Database Name", "Data Error", JOptionPane.ERROR_MESSAGE, null);
			dataCorrect = false;
		}
		else {
			databaseData.setDatabaseName(dbName);
			}
		
		try {
			InetAddress host = InetAddress.getByName(textFieldHost.getText());
			databaseData.setHost(host);
		} catch (UnknownHostException e1) {
			JOptionPane.showMessageDialog(this, "Please, enter a valid Host", "Data Error", JOptionPane.ERROR_MESSAGE, null);
			dataCorrect = false;
		}
		
		try {
			int port = Integer.parseInt(textFieldPort.getText());
			databaseData.setPort(port);
		} catch (NumberFormatException e2) {
			JOptionPane.showMessageDialog(this, "Please, enter a valid Port", "Data Error", JOptionPane.ERROR_MESSAGE, null);
			dataCorrect = false;
		}

		String user = textFieldUser.getText();
		if ((user == null) || user.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please, enter a valid user", "Data Error", JOptionPane.ERROR_MESSAGE, null);
			dataCorrect = false;
		}
		else {
			databaseData.setUsername(user);
		}
		
		char[] password = passwordField.getPassword();
		if (password.length == 0) {
			JOptionPane.showMessageDialog(this, "Please, enter a valid password", "Data Error", JOptionPane.ERROR_MESSAGE, null);
			dataCorrect = false;
		}
		else {
			databaseData.setPassword(new String(password));;
		}
		
		if (dataCorrect) {
			this.setVisible(false);
			this.dispose();
		}

	}

	/**
	 * Cancels the action in progres and returns the control to the parent component
	 * The cancel flag is raised
	 * 
	 * @param e
	 */
	protected void buttonCancelActionPerformed(ActionEvent e) {
		notCancel = false;
		this.dispose();
	}
	
	/**
	 * @returna a valid database
	 */
	protected Database getValidatedDatabaseData() {

		return databaseData;

	}

	/**
	 * @return true if the database description is correct and the user didnt cancel the action
	 */
	protected boolean checkCorrectDataAndNotCancel() {
		if (dataCorrect && notCancel) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Clear the database text fields
	 * The cancel flag is reset
	 */
	protected void resetDatabaseData() {
		
		textFieldDatabaseName.setText(null);
		textFieldHost.setText(localhost);
		textFieldPort.setText(null);
		textFieldUser.setText(null);
		passwordField.setText(null);
		
		notCancel = true;
		
	}
	
	/**
	 * Clear the database text fields
	 * The cancel flag is reset
	 * 
	 * @param paramDatabase
	 */
	protected void resetDatabaseData(Database paramDatabase) {
		
		comboBoxDBMS.setSelectedItem(paramDatabase.getDBMS());
		textFieldDatabaseName.setText(paramDatabase.getDatabaseName());
		textFieldHost.setText(paramDatabase.getHost().getHostAddress());
		textFieldPort.setText(String.valueOf(paramDatabase.getPort()));
		textFieldUser.setText(paramDatabase.getUsername());
		passwordField.setText(paramDatabase.getPassword());
		
		notCancel = true;
		
	}

}
