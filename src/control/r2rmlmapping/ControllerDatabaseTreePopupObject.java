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
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import model.database.Column;
import model.r2rmlmapping.triplesMap.ColumnValueObjectMap;
import model.r2rmlmapping.triplesMap.JoinCondition;
import model.r2rmlmapping.triplesMap.ObjectMap;
import model.r2rmlmapping.triplesMap.PredicateObjectMap;
import model.r2rmlmapping.triplesMap.ReferencingObjectMap;
import model.r2rmlmapping.triplesMap.TriplesMap;
import view.triplesMap.TriplesMapPredicateObjectSelector;
import view.triplesMap.PredicateObject.JoinConditionSelector;
import view.triplesMap.PredicateObject.PredicateObjectMapObjectSelector;

/**
 * @author Manuel
 *
 * Controller for a popup menu in a Jtree
 */
public class ControllerDatabaseTreePopupObject implements ActionListener {
	
	private JFrame frame;
	private JTree tree;
	private TriplesMap triplesMap;
	private JPopupMenu popup;
	private TriplesMapPredicateObjectSelector predicateObjectSelector;
	private PredicateObjectMapObjectSelector objectSelector;
	private JoinConditionSelector joinCondSelector;
	
	public ControllerDatabaseTreePopupObject(JFrame frame, JTree paramTree, JPopupMenu paramPopup, TriplesMap paramTriplesMap) {
		
		this.frame = frame;
		this.tree = paramTree;
		this.popup = paramPopup;
		this.triplesMap = paramTriplesMap;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		TreePath[] paths = tree.getSelectionPaths();
//		ArrayList<Column> columns;

		if (paths == null) {
			JOptionPane.showMessageDialog(popup, "You have not selected an item from the tree", "Warning no item selected", JOptionPane.WARNING_MESSAGE);
		}
		else {
	        ArrayList<PredicateObjectMap> predicateObjectMaps =  triplesMap.getPredicateObjectMaps();

	        if (predicateObjectMaps.size() == 0) {
	        	JOptionPane.showMessageDialog(popup, "There are no Predicate-Object maps in the triples map", "Warning no Predicate-Objects in the triples map", JOptionPane.WARNING_MESSAGE);
	        }
	        else {
	        	predicateObjectSelector = new TriplesMapPredicateObjectSelector(frame, predicateObjectMaps.size());
	        	predicateObjectSelector.pack();
	        	predicateObjectSelector.setLocationRelativeTo(frame);
	    		predicateObjectSelector.setVisible(true);
	    		if (!predicateObjectSelector.checkCancel()) {
		    		int predObjIndex = predicateObjectSelector.getPredObjSelected();
			        System.out.println("ControllerOntologyTreePopupPredicate --> Elegido el predicate-object " + predObjIndex);
			        ArrayList<ObjectMap> objectMaps = predicateObjectMaps.get(predObjIndex).getObjectMaps();
			        if (objectMaps.size() == 0) {
			        	JOptionPane.showMessageDialog(popup, "There are no Object maps in the predicate-object map", "Warning no Predicate-Objects in the triples map", JOptionPane.WARNING_MESSAGE);
			        }
			        else {
			        	objectSelector = new PredicateObjectMapObjectSelector(frame, objectMaps.size());
			        	objectSelector.pack();
			        	objectSelector.setLocationRelativeTo(frame);
			        	objectSelector.setVisible(true);
			    		if (!objectSelector.checkCancel()) {
				    		int objectIndex = objectSelector.getObjectSelected();
					        System.out.println("ControllerOntologyTreePopupPredicate --> Elegido el predicate " + objectIndex);
					        if (objectMaps.get(objectIndex).getType().equals("Column-Valued")) {
								
								for (TreePath path : paths) {
									DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
									Column column = (Column) node.getUserObject();
									ColumnValueObjectMap columnValueObjectMap = (ColumnValueObjectMap) objectMaps.get(objectIndex);
									if (column.getTable().equals(triplesMap.getLogicalTable())) {
										columnValueObjectMap.addObjectColumn(column);
										System.out.println("ControllerDatabaseTreePopupObject --> Se añade el elemento de la BBDD: " + column.getColumnName());
									}
									else {
										JOptionPane.showMessageDialog(popup, "You have to select a column from the table corresponding to the triples map", "Wrong column selection", JOptionPane.ERROR_MESSAGE);
									}

								}
							
					        }
					        else if (objectMaps.get(objectIndex).getType().equals("Referencing")) {

								ReferencingObjectMap referenceObjectMap = (ReferencingObjectMap) objectMaps.get(objectIndex);
								ArrayList<JoinCondition> joinCond = referenceObjectMap.getJoinConditions();
						       	joinCondSelector = new JoinConditionSelector(frame, joinCond.size());
						       	joinCondSelector.pack();
						       	joinCondSelector.setLocationRelativeTo(frame);
						       	joinCondSelector.setVisible(true);
						   		if (!joinCondSelector.checkCancel()) {
						    		int joinCondIndex = joinCondSelector.getJoinConditionSelected();
						    		JoinCondition joinC = joinCond.get(joinCondIndex);
						    		int componentSelected = JOptionPane.showOptionDialog(frame, "Select the join condition component", 
					    				"JoinCondition Component Selector", JOptionPane.YES_NO_CANCEL_OPTION,
						    				JOptionPane.QUESTION_MESSAGE, null, 
						    				new Object[] {"Parent", "Child", "Cancel"}, "Parent");
						        	TreePath path2 = tree.getSelectionPath();
									DefaultMutableTreeNode node2 = (DefaultMutableTreeNode)path2.getLastPathComponent();
									Column column2 = (Column) node2.getUserObject();
							    	if (componentSelected == 0) {
										if (column2.getTable().equals(referenceObjectMap.getParentTriplesMap().getLogicalTable())) {
											joinC.setParent(column2);
										}
										else {
											JOptionPane.showMessageDialog(popup, 
											"You have to select a column from the table corresponding to the parent triples map", 
											"Wrong column selection", 
											JOptionPane.ERROR_MESSAGE);
										}
						    		}
						    		else if (componentSelected == 1) {
						    			if (column2.getTable().equals(triplesMap.getLogicalTable())) {
							    			joinC.setChild(column2);
										}
										else {
											JOptionPane.showMessageDialog(popup, 
											"You have to select a column from the table corresponding to the triples map", 												"Wrong column selection", 
												JOptionPane.ERROR_MESSAGE);
										}
							    	}
						    	}
					        }
					    }
			    	}
	    	    }
	        }

		}
		
	}

}
