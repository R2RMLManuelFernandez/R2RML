package view.util;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import model.database.Column;
import model.mapping.MappingElement;

public class DatabaseTreePopupHandler extends TreePopupHandler {

	public DatabaseTreePopupHandler(JTree tree, MappingElement mappingItem) {
		super(tree, mappingItem);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		TreePath[] paths = tree.getSelectionPaths();
		ArrayList<Column> columns;

		if (paths == null) {
//			System.out.println("No hay nodo seleccionado");
			JOptionPane.showMessageDialog(popup, "You have not selected an item from the tree", "Warning no item selected", JOptionPane.WARNING_MESSAGE);

		}
		else {

			columns = new ArrayList<Column>(10);
			
			for (TreePath path : paths) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
				Column column = (Column) node.getUserObject();
				columns.add(column);
				System.out.println("Se añade el elemento de la BBDD: " + column.getColumnName());
			}
			
			mappingElement.setDatabaseColumn(columns.get(0));
/*
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
	        OntologyElement ontologyElement = (OntologyElement) node.getUserObject();
//			System.out.println("Se añade el elemento de la ontologia");
	        mappingElement.setOntologyElement(ontologyElement);
*/
		}  
		
	}

}
