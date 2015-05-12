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

package view.util;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import model.mapping.MappingElement;
import model.ontology.OntologyElement;

/**
 * @author Manuel Fernandez Perez
 * 
 * Controller for the popup menu in an Ontology Jtree *
 */
public class OntologyTreePopupHandler extends TreePopupHandler {

	public OntologyTreePopupHandler(JTree tree, MappingElement mappingItem) {
		super(tree, mappingItem);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		TreePath path = tree.getSelectionPath();

		if (path == null) {
//			System.out.println("No hay nodo seleccionado");
			JOptionPane.showMessageDialog(popup, "You have not selected an item from the tree", "Warning no item selected", JOptionPane.WARNING_MESSAGE);

		}
		else if (path.getLastPathComponent() == tree.getModel().getRoot()) {
//			System.out.println("Esta seleccionada la raiz");
			JOptionPane.showMessageDialog(popup, "You can not add the root to the mapping", "Warning selection not allowed", JOptionPane.WARNING_MESSAGE);			
		}
		else {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
	        OntologyElement ontologyElement = (OntologyElement) node.getUserObject();
//			System.out.println("Se añade el elemento de la ontologia");
	        mappingElement.setOntologyElement(ontologyElement);
		}  
		
	}

}
