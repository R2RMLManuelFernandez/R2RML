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
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import model.r2rmlmapping.triplesMap.TriplesMap;

/**
 * @author Manuel
 *
 * Controller for a popup menu in a Jtree
 */
public abstract class TreePopupHandler implements ActionListener, PopupMenuListener{

	protected JTree tree;
	protected JPopupMenu popup;
//	private JMenu menu;
//	private boolean overRoot = false;
	protected TriplesMap mappingElement;
	
	public TreePopupHandler(JTree tree, TriplesMap mappingItem) {
		
		this.tree = tree;
		this.mappingElement = mappingItem;
		popup = new JPopupMenu();
		popup.setInvoker(tree);
//		menu = new JMenu();
		JMenuItem neItem = new JMenuItem("Add to R2RML Mapping");
		neItem.addActionListener(this);
//		menu.add(neItem);
//		popup.add(menu);
		popup.add(neItem);
		tree.addMouseListener(mListener);
		popup.addPopupMenuListener(this);
		
	}

	private MouseListener mListener = new MouseAdapter() {
		
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
	public void popupMenuCanceled(PopupMenuEvent arg0) {}

	@Override
	public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {}

	@Override
	public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {	
	}

	@Override
	public abstract void actionPerformed(ActionEvent e);

	/**
	 * @return the popup
	 */
	public JPopupMenu getPopup() {
		return popup;
	}

}
