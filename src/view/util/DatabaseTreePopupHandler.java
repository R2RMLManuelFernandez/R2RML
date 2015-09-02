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
import control.r2rmlmapping.ControllerDatabaseTreePopupObject;
import control.r2rmlmapping.ControllerDatabaseTreePopupSubject;

/**
 * @author Manuel Fernandez Perez
 * 
 * Handler for the popup menu in a Database Jtree *
 */
public class DatabaseTreePopupHandler implements PopupMenuListener {

	protected JTree tree;
	protected JPopupMenu popup;
	protected TriplesMap triplesMap;
	private ControllerDatabaseTreePopupSubject controllerSubject;
	private ControllerDatabaseTreePopupObject controllerObject;
	
	public DatabaseTreePopupHandler(JFrame frame, JTree paramTree, TriplesMap paramTriplesMap) {

		this.tree = paramTree;
		this.triplesMap = paramTriplesMap;
		popup = new JPopupMenu();
		popup.setInvoker(tree);

		JMenuItem neItem = new JMenuItem("Add to SubjectMap Columns");
		controllerSubject = new ControllerDatabaseTreePopupSubject(tree, popup, triplesMap);
		neItem.addActionListener(controllerSubject);

		popup.add(neItem);
		JMenuItem neItem2 = new JMenuItem("Add to Object Columns");
		controllerObject = new ControllerDatabaseTreePopupObject(frame, tree, popup, triplesMap);
		neItem2.addActionListener(controllerObject);

		popup.add(neItem2);
		tree.addMouseListener(mouseListener);
		popup.addPopupMenuListener(this);
	}

	private MouseListener mouseListener = new MouseAdapter() {
		
		private void checkForPopup(MouseEvent e) {
			if (e.isPopupTrigger()) {
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
	public void popupMenuCanceled(PopupMenuEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @return the popup
	 */
	public JPopupMenu getPopup() {
		return popup;
	}

}
