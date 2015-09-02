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
	
	private ImageIcon iconClass = new ImageIcon("");
	private ImageIcon iconObjectProperty = new ImageIcon("");
	private ImageIcon iconDataProperty = new ImageIcon("");
	
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
		
		String displayName = ((OntologyElement)nodeObject).getDisplayName().toLowerCase();
		
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
		
		if (((OntologyElement) nodeObject).getInMapping()) {
			setForeground(Color.GREEN);
//			System.out.println("getTreeCellRendererComponent: Poniendo verde " + ((OntologyElement) nodeObject).getDisplayName());
		}
		else {
			if (selected) {
				setForeground(textSelectionColor);
			}
			else {
				setForeground(textNonSelectionColor);
			}
		}
	
		if (start != null && !start.isEmpty() && displayName.startsWith(start)) {
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
