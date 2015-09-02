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

package view.database;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.util.Enumeration;

import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Position;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import model.database.Database;
import model.r2rmlmapping.triplesMap.TriplesMap;
import net.miginfocom.swing.MigLayout;
import view.util.DatabaseTreePopupHandler;
import view.util.TreeUtil;
import control.database.load.DatabaseLoader;
import control.database.load.DatabaseTreeModelConstructor;

/**
 * Displays the database model in a tree
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class ViewDatabaseTree extends JPanel {

	private static final long serialVersionUID = 7987745922993061337L;
	
	private JTextField textFieldDatabaseSearch;
	private DefaultTreeModel model;
	private Database database = null;
	private DatabaseLoader DBLoader = null;
	private JTree treeDatabase;
	private MyDatabaseRender databaseRender;
	private DatabaseTreePopupHandler databasePopupHandler;
	@SuppressWarnings("unused")
	private TriplesMap mappingItem;

	private JFrame frame;
	
	/**
	 * Create the panel.
	 */
	public ViewDatabaseTree(JFrame frame) {
		
		this.frame = frame;
		
		setLayout(new MigLayout("", "[grow]", "[][2.00][55.00][pref!][][grow]"));
		
		JLabel labelDatabaseName = new JLabel("Database:");
		add(labelDatabaseName, "cell 0 0");
		
		JSeparator separator_1 = new JSeparator();
		add(separator_1, "cell 0 1,grow");
		
		JPanel panelFind = new JPanel();
		add(panelFind, "cell 0 2,grow");
		panelFind.setLayout(new MigLayout("", "[30.00px][grow][]", "[18.00px,grow][18.00]"));
		
		JLabel labelDatabaseSearch = new JLabel("Find:");
		panelFind.add(labelDatabaseSearch, "cell 0 0 1 2,alignx trailing,aligny center");
		
		textFieldDatabaseSearch = new JTextField();
		panelFind.add(textFieldDatabaseSearch, "cell 1 0 1 2,growx,aligny center");
		textFieldDatabaseSearch.setColumns(10);
		textFieldDatabaseSearch.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				findDatabaseNodes();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				findDatabaseNodes();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				findDatabaseNodes();
			}
		});
		
		JButton buttonDatabaseFindPrevious = new JButton("Previous");
		buttonDatabaseFindPrevious.setMinimumSize(new Dimension(30, 15));
		buttonDatabaseFindPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonDatabaseFindPreviousActionPerformed();
			}
		});
		panelFind.add(buttonDatabaseFindPrevious, "cell 2 0,grow");
		
		JButton buttonDatabaseFindNext = new JButton("Next");
		buttonDatabaseFindNext.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonDatabaseFindNextActionPerformed();		
			}
		});
		buttonDatabaseFindNext.setMinimumSize(new Dimension(30, 15));
		panelFind.add(buttonDatabaseFindNext, "cell 2 1,grow");
		
		JSeparator separator = new JSeparator();
		add(separator, "cell 0 3,grow");
		
		JPanel panelTreeButton = new JPanel();
		add(panelTreeButton, "cell 0 4,grow");
		panelTreeButton.setLayout(new MigLayout("", "[grow][89px][15][][grow]", "[23px]"));
		
		JButton buttonExpandDatabaseTree = new JButton("Expand");
		buttonExpandDatabaseTree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TreeUtil.expandAll(treeDatabase);
			}
		});
		panelTreeButton.add(buttonExpandDatabaseTree, "cell 1 0,alignx left");
		
		JButton buttonCollapseDatabaseTree = new JButton("Collapse");
		buttonCollapseDatabaseTree.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TreeUtil.collapseAll(treeDatabase);
			}
		});
		panelTreeButton.add(buttonCollapseDatabaseTree, "cell 3 0,alignx right");
		
		JScrollPane scrollPaneDatabaseTree = new JScrollPane();
		add(scrollPaneDatabaseTree, "cell 0 5,grow");
		
		treeDatabase = new JTree(new DefaultTreeModel(null));
		databaseRender = new MyDatabaseRender();
		treeDatabase.setCellRenderer(databaseRender);
		treeDatabase.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);		
		treeDatabase.setDragEnabled(true);
		treeDatabase.setDropMode(DropMode.ON_OR_INSERT);
		treeDatabase.setRootVisible(false);
		scrollPaneDatabaseTree.setViewportView(treeDatabase);

	}

	/**
	 * Sets the transferhandler for the tree
	 * 
	 * @param paramTransferHandler
	 */
	public void setDatabaseTransferHandler(TransferHandler paramTransferHandler) {
		
		treeDatabase.setTransferHandler(paramTransferHandler);
		
	}
	
	/**
	 * Creates a new database, constructs its model and loads it into the tree
	 * 
	 * @param paramDBMS
	 * @param paramName
	 * @param paramAddress
	 * @param paramPort
	 * @param paramUserName
	 * @param paramPassword
	 * @throws Exception
	 */
	public void setDatabaseModel(String paramDBMS, String paramName, InetAddress paramAddress,
								 int paramPort, String paramUserName, String paramPassword) throws Exception {

		this.database = new Database(paramDBMS, paramName, paramAddress, paramPort, paramUserName, paramPassword);

		this.DBLoader = DatabaseLoader.getInstace();

		try {
			DBLoader.queryDatabase(database);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error Opening Database");
			e.printStackTrace();
		}

		DatabaseTreeModelConstructor DBModelT = new DatabaseTreeModelConstructor(database);

		this.model = DBModelT.getDatabaseTreeModel();

		this.treeDatabase.setModel(model);

	}
	
	public void setMappingItem (TriplesMap mappingItem) {
		
		this.mappingItem = mappingItem;
		databasePopupHandler = new DatabaseTreePopupHandler(frame, treeDatabase, mappingItem);
		treeDatabase.add(databasePopupHandler.getPopup());
		
	}
	
	/**
	 * Find the nodes in the database tree which label begins whith the pattern
	 */
	@SuppressWarnings("rawtypes")
	private void findDatabaseNodes() {
		String text = textFieldDatabaseSearch.getText();
		
		databaseRender.setStart(text);
		
		if (text != null && !text.isEmpty()) {
			TreeModel dbModel = treeDatabase.getModel();
			DefaultMutableTreeNode rootDatabase = (DefaultMutableTreeNode) dbModel.getRoot();
			Enumeration tables = rootDatabase.children();
			TreePath rootPath = new TreePath(rootDatabase);
			while (tables.hasMoreElements()) {
				TreePath tablePath = rootPath.pathByAddingChild(tables.nextElement());
				TreeUtil.searchDatabaseTree(treeDatabase, tablePath, text);
			}
		}
		treeDatabase.repaint();
	}

	/**
	 * Finds the position of the next node in the database tree which label begins whith the pattern
	 */
	protected void buttonDatabaseFindNextActionPerformed() {
		
		String text = textFieldDatabaseSearch.getText();
		
		if (text != null && !text.isEmpty()) {
			findDatabaseNodes();
			TreePath selPath = treeDatabase.getSelectionPath();

			int row = treeDatabase.getRowForPath(selPath) + 1;
			if (row < 0 ) {
				row = 1;
			}
			if (row >= treeDatabase.getRowCount()) {
				row = 1;
			}

			TreePath path = null;
			try {
				path = treeDatabase.getNextMatch(text, row, Position.Bias.Forward);
			} catch (IllegalArgumentException  ia) {
				System.out.print(row);
				ia.printStackTrace();
			}
			
			if (path != null) {
				treeDatabase.setSelectionPath(path);
				treeDatabase.scrollPathToVisible(path);
			}
		}
	}

	/**
	 * Finds the position of the previous node in the database tree which label begins whith the pattern
	 */
	protected void buttonDatabaseFindPreviousActionPerformed() {
		
		String text = textFieldDatabaseSearch.getText();
		
		if (text != null && !text.isEmpty()) {
			findDatabaseNodes();
			TreePath selPath = treeDatabase.getSelectionPath();

			int row = treeDatabase.getRowForPath(selPath) - 1;
			if (row < 0 ) {
				row = treeDatabase.getRowCount() - 1;
			}
			if (row > treeDatabase.getRowCount()) {
				row = 1;
			}
	
			TreePath path = null;
			try {
				path = treeDatabase.getNextMatch(text, row, Position.Bias.Backward);
			} catch (IllegalArgumentException  ia) {
				System.out.print(row);
				ia.printStackTrace();
			}

			if (path != null) {
				treeDatabase.setSelectionPath(path);
				treeDatabase.scrollPathToVisible(path);
			}
		}		
	}

	/**
	 * @return the database
	 */
	public Database getDatabase() {
		return database;
	}

}
