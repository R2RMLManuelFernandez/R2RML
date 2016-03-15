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

import model.ontology.OntologyElement;
import model.r2rmlmapping.triplesMap.IRIClass;
import model.r2rmlmapping.triplesMap.PredicateMap;
import model.r2rmlmapping.triplesMap.PredicateObjectMap;
import model.r2rmlmapping.triplesMap.TriplesMap;
import view.triplesMap.TriplesMapPredicateObjectSelector;
import view.triplesMap.PredicateObject.PredicateObjectMapPredicateSelector;

/**
 * @author Manuel
 *
 * Controller for a popup menu in a Jtree
 */
public class ControllerOntologyTreePopupPredicate implements ActionListener {
	
	private JFrame frame;
	private JTree tree;
	private TriplesMap triplesMap;
	private JPopupMenu popup;
	private TriplesMapPredicateObjectSelector predicateObjectSelector;
	private PredicateObjectMapPredicateSelector predicateSelector;
	
	public ControllerOntologyTreePopupPredicate(JFrame frame, JTree paramTree, JPopupMenu paramPopup, TriplesMap paramTriplesMap) {
		
		this.frame = frame;
		this.tree = paramTree;
		this.popup = paramPopup;
		this.triplesMap = paramTriplesMap;
		
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
			System.out.println("Se añade el elemento de la ontologia " + ontologyElement.getIRI());
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
			        ArrayList<PredicateMap> predicateMaps = predicateObjectMaps.get(predObjIndex).getPredicateMaps();
			        
			        if (predicateMaps.size() == 0) {
			        	
			        	JOptionPane.showMessageDialog(popup, "There are no Predicate maps in the predicate-object map", "Warning no Predicate-Objects in the triples map", JOptionPane.WARNING_MESSAGE);
			        
			        }
			        else {
			        	
			        	predicateSelector = new PredicateObjectMapPredicateSelector(frame, predicateMaps.size());
			        	predicateSelector.pack();
			        	predicateSelector.setLocationRelativeTo(frame);
			        	predicateSelector.setVisible(true);
			        	
			    		if (!predicateSelector.checkCancel()) {
			    			
				    		int predIndex = predicateSelector.getPredSelected();
					        System.out.println("ControllerOntologyTreePopupPredicate --> Elegido el predicate " + predIndex);
					        IRIClass predicateIRI = predicateMaps.get(predIndex).getPredicateIRI();
					        predicateIRI.setIRIClassIRI(ontologyElement.getIRI());
					        
					        if (ontologyElement.getLabel() != null) {
					        	
					        	predicateIRI.setIRIClassLabel(ontologyElement.getLabel());	
					        	
					        }
					        
					        predicateMaps.get(predIndex).setPredicateIRI(predicateIRI);
					        
			    		}
			    		
			        }
			        
	    	    }
	    		
	        }
	        
		}
		
    }
		
}

