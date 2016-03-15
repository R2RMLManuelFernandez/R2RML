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

package control.database.load;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import model.database.Column;
import model.database.Database;
import model.database.Table;

/**
 * Constructor for the data model used in the database tree
 * Constructs a tree schema from the database model
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class DatabaseTreeModelConstructor {

	private DefaultTreeModel databaseTreeModel;
	
	private Database database;
	
	public DatabaseTreeModelConstructor(Database database) throws Exception {
		
		this.database = database;
		
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(database.getDatabaseName());		
		this.databaseTreeModel = new DefaultTreeModel(rootNode);
		this.createDataBaseTreeModel(rootNode);
		
	}
	
	/**
	 * creates the model
	 * 
	 * @param rootNode
	 * @throws Exception
	 */
	private void createDataBaseTreeModel(DefaultMutableTreeNode rootNode) throws Exception {
		if (database.hasTables()) {			
			addTablesToTree(rootNode);
		}
		else {
			throw new Exception("Database is empty");
		}
			
	}
	
	/**
	 * adds the tables schemas to the model
	 * 
	 * @param rootNode
	 * @throws Exception
	 */
	private void addTablesToTree(DefaultMutableTreeNode rootNode) throws Exception {
		int tableIndex = 0;
		ArrayList<Table> tables = database.getTables();
		for (Table table: tables) {
			
			DefaultMutableTreeNode tableNode = new DefaultMutableTreeNode(table.getTableName());
			databaseTreeModel.insertNodeInto(tableNode , rootNode, tableIndex++);
			if (table.hasColumns()) {
				
				ArrayList<Column> columns = table.getColumns();
				addColumnsToTree(columns, tableNode);
				
			}
			else {
				
				throw new Exception("Table is empty");
				
			}

		}
	}
	
	/**
	 * adds the columns schemas to the model
	 * 
	 * @param columns
	 * @param tableNode
	 */
	private void addColumnsToTree(ArrayList<Column> columns, DefaultMutableTreeNode tableNode) {
		int columnIndex = 0;
		for (Column column: columns) {
			DefaultMutableTreeNode columnNode = new DefaultMutableTreeNode(column);
			databaseTreeModel.insertNodeInto(columnNode , tableNode, columnIndex++);
		}
	}

	/**
	 * @return the databaseTreeModel
	 */
	public DefaultTreeModel getDatabaseTreeModel() {
		return databaseTreeModel;
	}
	
}
