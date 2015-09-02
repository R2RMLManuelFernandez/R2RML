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

package view.triplesMap;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import model.r2rmlmapping.triplesMap.PredicateObjectMap;
import model.r2rmlmapping.triplesMap.TriplesMap;
import net.miginfocom.swing.MigLayout;
import view.triplesMap.PredicateObject.ViewPredicateObject;
import view.triplesMap.subject.ViewSubject;
import control.r2rmlmapping.triplesMap.ControllerPredicateObjectMap;
import control.r2rmlmapping.triplesMap.ControllerSubjectMap;
import control.r2rmlmapping.triplesMap.ControllerTriplesMap;

/**
 * View to represent the triples map
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class ViewTriplesMap extends JPanel implements Observer {

	private static final long serialVersionUID = -4905463017554762422L;
	private JScrollPane scrollPanePredicateObject;
	private JPanel panelPredicateObject;
	private ViewSubject viewSubject; 
	private JButton buttonAddPredicateobject;
	private JSeparator separator;
	private JButton buttonUpdateTriplesMapInR2RMLmaping;
	private JFrame frame;
	
	private TriplesMap triplesMapModel;

	/**
	 * Create the panel.
	 */
	public ViewTriplesMap(JFrame frame) {
		
		this.frame = frame;
		
		setLayout(new MigLayout("", "[250.00][233.00,grow]", "[105.00,grow][25.00][][25.00]"));
		
		viewSubject = new ViewSubject();
		add(viewSubject, "cell 0 0 1 2,grow");
		
		scrollPanePredicateObject = new JScrollPane();
		scrollPanePredicateObject.setViewportBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Predicate-Objects", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		add(scrollPanePredicateObject, "cell 1 0,grow");
		
		panelPredicateObject = new JPanel();
		panelPredicateObject.setLayout(new BoxLayout(panelPredicateObject, BoxLayout.Y_AXIS));
		
		scrollPanePredicateObject.setViewportView(panelPredicateObject);
		
		buttonAddPredicateobject = new JButton("Add Predicate-Object");
		buttonAddPredicateobject.setName("Add Predicate-Object");
		add(buttonAddPredicateobject, "flowx,cell 1 1,alignx center");

		separator = new JSeparator();
		add(separator, "cell 0 2 2 1,growx");
		
		buttonUpdateTriplesMapInR2RMLmaping = new JButton("Update TriplesMap");
		buttonUpdateTriplesMapInR2RMLmaping.setName("Update TriplesMap");
		add(buttonUpdateTriplesMapInR2RMLmaping, "flowx,cell 1 3,alignx trailing");
		
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		
		panelPredicateObject.removeAll();
		ArrayList<PredicateObjectMap> predicateObjectMaps = triplesMapModel.getPredicateObjectMaps();
		if (predicateObjectMaps.size() > 0) {
			for (int i = 0; i < predicateObjectMaps.size(); i++) {
				System.out.println("ViewTriplesMap update --> " + predicateObjectMaps.get(i).getIdentifier());
			}
			for (PredicateObjectMap predicateObjectMap: predicateObjectMaps) {
				ViewPredicateObject viewPredicateObject = new ViewPredicateObject();
				viewPredicateObject.setModel(predicateObjectMap);
				ControllerPredicateObjectMap controllerPredicateObject = new ControllerPredicateObjectMap(frame, viewPredicateObject, predicateObjectMap);
				viewPredicateObject.setController(controllerPredicateObject);
				panelPredicateObject.add(viewPredicateObject);
			}
		}
		panelPredicateObject.repaint();
		panelPredicateObject.revalidate();
		panelPredicateObject.updateUI();
	}

	/**
	 * @param paramTriplesMapModel
	 */
	public void setTriplesMapModel(TriplesMap paramTriplesMapModel) {
		
		triplesMapModel = paramTriplesMapModel;
		triplesMapModel.addObserver(this);
		viewSubject.setModel(triplesMapModel.getSubjectMap());
		ControllerSubjectMap controllerViewSubject = new ControllerSubjectMap(viewSubject, triplesMapModel.getSubjectMap());
		viewSubject.setController(controllerViewSubject);
		System.out.println("ViewTriplesMap --> Establecido modelo y controlador de la vista del subject");
		
		panelPredicateObject.removeAll();
		System.out.println("ViewTriplesMap --> Se deberian haber eliminado todas las vistas de predicate-object");
		ArrayList<PredicateObjectMap> predicateObjectMaps = paramTriplesMapModel.getPredicateObjectMaps();
		if (predicateObjectMaps.size() > 0) {
			for (int i = 0; i < predicateObjectMaps.size(); i++) {
				System.out.println("ViewTriplesMap setTriplesMapModel --> " + predicateObjectMaps.get(i).getIdentifier());
			}
			for (PredicateObjectMap predicateObjectMap: predicateObjectMaps) {
				ViewPredicateObject viewPredicateObject = new ViewPredicateObject();
				viewPredicateObject.setModel(predicateObjectMap);
				ControllerPredicateObjectMap controllerPredicateObject = new ControllerPredicateObjectMap(frame, viewPredicateObject, predicateObjectMap);
				viewPredicateObject.setController(controllerPredicateObject);
				panelPredicateObject.add(viewPredicateObject);
			}
		}
		panelPredicateObject.repaint();
		panelPredicateObject.revalidate();
		panelPredicateObject.updateUI();

	}

	/**
	 * @param controller the controller to set
	 */
	public void setController(ControllerTriplesMap controller) {

		for(ActionListener al : buttonAddPredicateobject.getActionListeners() ) {
			buttonAddPredicateobject.removeActionListener( al );
		}
		buttonAddPredicateobject.addActionListener(controller);
		
		for(ActionListener al : buttonUpdateTriplesMapInR2RMLmaping.getActionListeners() ) {
			buttonUpdateTriplesMapInR2RMLmaping.removeActionListener( al );
		}
		buttonUpdateTriplesMapInR2RMLmaping.addActionListener(controller);
	}

}
