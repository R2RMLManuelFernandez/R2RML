package view.util;

import java.util.Enumeration;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import model.database.Column;
import model.database.Table;
import model.ontology.OntologyElement;

public final class TreeUtil {

/*	public static void expandTree(JTree tree) {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)tree.getModel().getRoot();
		@SuppressWarnings("rawtypes")
		Enumeration e = root.breadthFirstEnumeration();
		while(e.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)e.nextElement();
			if(node.isLeaf()) continue;
			int row = tree.getRowForPath(new TreePath(node.getPath()));
			tree.expandRow(row);
		}
	}
	
	  
	public static void collapseTree(JTree tree) {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)tree.getModel().getRoot();
		@SuppressWarnings("rawtypes")
		Enumeration e = root.breadthFirstEnumeration();
		while(e.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)e.nextElement();
			if(node.isLeaf()) continue;
			int row = tree.getRowForPath(new TreePath(node.getPath()));
			if (row != 0) 
				tree.collapseRow(row);
		}
	}*/
  
	/**
	 * Expands all the rows in the JTree
	 * 
	 * @param tree
	 */
	public static void expandAll(JTree tree) {
		int row = 0;
		while (row < tree.getRowCount()) {
			tree.expandRow(row);
			row++;
		}
	}
  
	/**
	 * Collapses all the nodes in the JTree
	 * 
	 * @param tree
	 */
	public static void collapseAll(JTree tree) {
		int row = tree.getRowCount() - 1;
		while (row >= 0) {
			tree.collapseRow(row);
			row--;
		}
	}
	

	@SuppressWarnings("rawtypes")
	public static void searchOntologyTree(JTree tree, TreePath path, String word) {
		
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
		
		if (node != null ) {
			OntologyElement element = (OntologyElement) node.getUserObject();
			String displayName = element.getDisplayName().toLowerCase();
			if(displayName.startsWith(word)) {
				tree.expandPath(path.getParentPath());
			}
			if (!node.isLeaf()) {
				Enumeration nodeChildren = node.children();
				
				while (nodeChildren.hasMoreElements()) {
					searchOntologyTree(tree, path.pathByAddingChild(nodeChildren.nextElement()), word);
				}
			}
		}
		else {
			return;
		}

	}

	@SuppressWarnings("rawtypes")
	public static void searchDatabaseTree(JTree tree, TreePath path, String word) {
		
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();

		if (node != null ) {
			Object nodeObject = node.getUserObject();
			String name = null;
		
			if (nodeObject instanceof Column) {
				name = ((Column) nodeObject).getColumnName().toLowerCase();
			}
			else if (nodeObject instanceof Table) {
				name = ((Table) nodeObject).getTableName().toLowerCase();
			}
			else {
				name = nodeObject.toString().toLowerCase();
			}

			if(name.startsWith(word)) {
				tree.expandPath(path.getParentPath());
			}
			if (!node.isLeaf()) {
				Enumeration nodeChildren = node.children();
				
				while (nodeChildren.hasMoreElements()) {
					searchDatabaseTree(tree, path.pathByAddingChild(nodeChildren.nextElement()), word);
				}
			}
		}
		else {
			return;
		}
	}

}
