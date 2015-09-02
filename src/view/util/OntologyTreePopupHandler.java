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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import model.r2rmlmapping.triplesMap.TriplesMap;
import control.r2rmlmapping.ControllerOntologyTreePopupPredicate;
import control.r2rmlmapping.ControllerOntologyTreePopupSubject;

/**
 * @author Manuel Fernandez Perez
 * 
 * Handler for the popup menu in an Ontology Jtree *
 */
public class OntologyTreePopupHandler implements PopupMenuListener {

	protected JTree tree;
	protected JPopupMenu popup;
	protected TriplesMap triplesMap;
	private ControllerOntologyTreePopupSubject controllerSubject;
	private ControllerOntologyTreePopupPredicate controllerPredicate;
	
	public OntologyTreePopupHandler(JTree tree, TriplesMap triplesMap, JFrame frame) {
		this.tree = tree;
		this.triplesMap = triplesMap;
		popup = new JPopupMenu();
		popup.setInvoker(tree);
//		menu = new JMenu();
		JMenuItem neItem = new JMenuItem("Set as SubjectMap RDFClass");
		controllerSubject = new ControllerOntologyTreePopupSubject(tree, popup, triplesMap);
		neItem.addActionListener(controllerSubject);
//		menu.add(neItem);
//		popup.add(menu);
		popup.add(neItem);
		JMenuItem neItem2 = new JMenuItem("Set as PredicateIRI in Predicate");
		controllerPredicate = new ControllerOntologyTreePopupPredicate(frame, tree, popup, triplesMap);
		neItem2.addActionListener(controllerPredicate);
		popup.add(neItem2);
		tree.addMouseListener(mouseListener);
		popup.addPopupMenuListener(this);
	}

	private MouseListener mouseListener = new MouseAdapter() {
		
		private void checkForPopup(MouseEvent e) {
			if (e.isPopupTrigger()) {
/*				//TreePath path = tree.getPathForLocation(e.getX(), e.getY());
				TreePath path = tree.getSelectionPath();
				DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
				overRoot = (path == null) || (path.getLastPathComponent() == root);
				if (overRoot) {
					System.out.println("checkForPopup --> No hay nodo seleccionado o es la raiz");
					e.consume();
					return;
				}
				else {
					popup.show(tree, e.getX(), e.getY());					
				}
*/
				popup.show(tree, e.getX(), e.getY());
			}
		}
		
		public void mousePressed(MouseEvent e) {
			checkForPopup(e);
		}
		
		public void mouseClicked(MouseEvent e) {
			checkForPopup(e);
		}
		
		public void mouseReleased(MouseEvent e) {
			checkForPopup(e);
		}
		
	}; 
	
	@Override
	public void popupMenuCanceled(PopupMenuEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @return the popup
	 */
	public JPopupMenu getPopup() {
		return popup;
	}

}
