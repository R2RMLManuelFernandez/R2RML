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

package control.r2rmlmapping;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import model.database.Column;
import model.r2rmlmapping.triplesMap.TriplesMap;

/**
 * @author Manuel
 *
 * Controller for a popup menu in a Jtree
 */
public class ControllerDatabaseTreePopupSubject implements ActionListener {

	private JTree tree;
	private JPopupMenu popup;
	private TriplesMap triplesMap;

	public ControllerDatabaseTreePopupSubject(JTree paramTree, JPopupMenu paramPopup, TriplesMap paramTriplesMap) {
		this.tree = paramTree;
		this.popup = paramPopup;
		this.triplesMap = paramTriplesMap;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
			
		TreePath[] paths = tree.getSelectionPaths();
//		ArrayList<Column> columns;

		if (paths == null) {
//				System.out.println("No hay nodo seleccionado");
			JOptionPane.showMessageDialog(popup, "You have not selected an item from the tree", "Warning no item selected", JOptionPane.WARNING_MESSAGE);

		}
		else {

//			columns = new ArrayList<Column>(10);
				
			for (TreePath path : paths) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
				Column column = (Column) node.getUserObject();
//				columns.add(column);
				triplesMap.getSubjectMap().addColumn(column);
				System.out.println("ControllerDatabaseTreePopupSubject --> Se añade el elemento de la BBDD: " + column.getColumnName());
			}
			
/*			
			for (String col: columns) {
				triplesMap.getSubjectMap().addColumn(col);
			}
			
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
		        OntologyElement ontologyElement = (OntologyElement) node.getUserObject();
//				System.out.println("Se añade el elemento de la ontologia");
		        mappingElement.setOntologyElement(ontologyElement);
	*/
		}  

	}

}
