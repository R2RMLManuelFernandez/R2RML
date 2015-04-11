package view.ontology;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import model.ontology.OntologyClass;
import model.ontology.OntologyDataProperty;
import model.ontology.OntologyElement;
import model.ontology.OntologyObjectProperty;

/**
 * Render to display the ontology tree
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class MyOntologyRender extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = 5537249275462431499L;
	
	private ImageIcon iconClass = new ImageIcon();
	private ImageIcon iconObjectProperty = new ImageIcon();
	private ImageIcon iconDataProperty = new ImageIcon();
	
	private String start = null;
	
	/**
	 * @return the start
	 */
	public String getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(String start) {
		this.start = start.toLowerCase();
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultTreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree, java.lang.Object, boolean, boolean, boolean, int, boolean)
	 */
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		
		Object nodeObject = ((DefaultMutableTreeNode)value).getUserObject();
		
		String dispalyName = ((OntologyElement)nodeObject).getDisplayName().toLowerCase();
		
		if(nodeObject instanceof OntologyObjectProperty) {
			setIcon(iconObjectProperty);
			setToolTipText(((OntologyObjectProperty)nodeObject).getIRI());
		}
		else if (nodeObject instanceof OntologyDataProperty) {
			setIcon(iconDataProperty);
			setToolTipText(((OntologyDataProperty)nodeObject).getIRI());
		}
		else {
			setIcon(iconClass);
			setToolTipText(((OntologyClass)nodeObject).getIRI());
		}
	
		if (start != null && !start.isEmpty() && dispalyName.startsWith(start)) {
			setOpaque(true);
			if (selected) {
				setBackground(Color.RED);
			}
			else {
				setBackground(Color.ORANGE);
			}
		}
		else {
			setOpaque(false);
			if (selected) {
				setBackground(backgroundNonSelectionColor);
			}
			else {
				setBackground(backgroundNonSelectionColor);
			}
		}
		
		return this;
		 
	}
	
}
