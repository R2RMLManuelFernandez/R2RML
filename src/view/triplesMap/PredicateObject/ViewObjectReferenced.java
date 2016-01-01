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

package view.triplesMap.PredicateObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import model.r2rmlmapping.triplesMap.JoinCondition;
import model.r2rmlmapping.triplesMap.ReferencingObjectMap;
import model.r2rmlmapping.triplesMap.TriplesMap;
import net.miginfocom.swing.MigLayout;
import control.r2rmlmapping.triplesMap.ControllerReferencedObjectMap;

/**
 * View to represent the object
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class ViewObjectReferenced extends JPanel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton buttonDelete;
	private JButton buttonDeleteJoinCondition;
	private JLabel labelTriplesMap;
	private JButton buttonAddJoinCondition;
	private JComboBox<String> comboBoxJoinConditions;
	private DefaultComboBoxModel<String> comboBoxJoinCondModel;
	private String[] joinCondNames;
	private ViewJoinCondition viewJoinCondition;
/*	private JComboBox<String> comboBoxTriplesMap;
	private DefaultComboBoxModel<String> comboBoxTriMapModel;
	private String[] triplesMapNames;
*/	
	private ReferencingObjectMap model;


	/**
	 * Create the panel.
	 */
	public ViewObjectReferenced() {
		setBorder(new TitledBorder(null, "Referenced ObjectMap", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new MigLayout("", "[grow]", "[grow][grow][25px:n][][]"));
		
		labelTriplesMap = new JLabel("");
		add(labelTriplesMap, "cell 0 0");
		
		comboBoxJoinConditions = new JComboBox<String>();
		comboBoxJoinConditions.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				comboBoxJoinConditionsActionPerformed(e);
				
			}
		});
		add(comboBoxJoinConditions, "cell 0 1,growx");
		comboBoxJoinConditions.setVisible(false);
		
/*		comboBoxTriplesMap = new JComboBox<String>();
		comboBoxTriplesMap.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				comboBoxTriplesMapActionPerformed(e);
				
			}
		});
		add(comboBoxTriplesMap, "cell 0 0,growx");
*/
		viewJoinCondition = new ViewJoinCondition();
		add(viewJoinCondition, "cell 0 2,growx");
		viewJoinCondition.setVisible(false);

		buttonAddJoinCondition = new JButton("Add Join");
		buttonAddJoinCondition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonAddJoinCondition.setName("Add Join Condition");
		add(buttonAddJoinCondition, "flowx,cell 0 3,alignx center");
		
		buttonDeleteJoinCondition = new JButton("Delete Join");
		buttonDeleteJoinCondition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonDeleteJoinCondition.setName("Delete Join Condition");
		add(buttonDeleteJoinCondition, "cell 0 3,alignx center");
		buttonDeleteJoinCondition.setVisible(false);
		
		buttonDelete = new JButton("Delete");
		buttonDelete.setName("Delete");
		add(buttonDelete, "cell 0 4,alignx center");

	}

	/**
	 * @param objectMap
	 */
	public void setModel(ReferencingObjectMap objectMap) {
		this.model = objectMap;
		this.model.addObserver(this);
		TriplesMap triplesMap = this.model.getParentTriplesMap();
		int triplesMapIndex = this.model.getPredicateObjectMap().getTriplesMap().getR2RmlMapping().getTriplesMapIdent(triplesMap);
		labelTriplesMap.setText("Triples Map " + (triplesMapIndex + 1));
		ArrayList<JoinCondition> joinConditions = this.model.getJoinConditions();
		
		if (joinConditions.size() > 0) {
			joinCondNames = new String[joinConditions.size()];

			for (int i = 0; i < joinConditions.size(); i++) {
				joinCondNames[i] = "Join Condition " + (i + 1);
			}
			comboBoxJoinCondModel = new DefaultComboBoxModel<>(joinCondNames);
			comboBoxJoinConditions.setModel(comboBoxJoinCondModel);
			comboBoxJoinConditions.setSelectedIndex(0);
			comboBoxJoinConditions.setVisible(true);

			viewJoinCondition.setModel(getJoinConditionSelected());
			viewJoinCondition.setVisible(true);
			
			buttonDeleteJoinCondition.setVisible(true);
		}

		
 /*		ArrayList<TriplesMap> triplesMap = model.getPredicateObjectMap().getTriplesMap().getR2RmlMapping().getAllTriplesMap();
		triplesMapNames = new String[triplesMap.size()];
		System.out.println("ViewObjectReferenced --> " + triplesMap.size());
		for (int j = 0; j < triplesMap.size(); j++) {
			triplesMapNames[j] = "TriplesMap " + (j + 1);
		}
		comboBoxTriMapModel = new DefaultComboBoxModel<>(triplesMapNames);
		comboBoxTriplesMap.setModel(comboBoxTriMapModel);
*/
//		System.out.println("ViewObjectReferenced --> " + model.getPredicateObjectMap().getTriplesMap().getR2rmlMapping().getIdentifierCounter());
	}

	@Override
	public void update(Observable o, Object arg) {
		
		TriplesMap triplesMap = this.model.getParentTriplesMap();
		int triplesMapIndex = this.model.getPredicateObjectMap().getTriplesMap().getR2RmlMapping().getTriplesMapIdent(triplesMap);
		labelTriplesMap.setText("Triples Map " + (triplesMapIndex + 1));
		ArrayList<JoinCondition> joinConditions = this.model.getJoinConditions();
		
		if (joinConditions.size() > 0) {
			joinCondNames = new String[joinConditions.size()];

			for (int i = 0; i < joinConditions.size(); i++) {
				joinCondNames[i] = "Join Condition " + (i + 1);
			}
			comboBoxJoinCondModel = new DefaultComboBoxModel<>(joinCondNames);
			comboBoxJoinConditions.setModel(comboBoxJoinCondModel);
			comboBoxJoinConditions.setSelectedIndex(joinConditions.size() - 1);
			comboBoxJoinConditions.setVisible(true);

			viewJoinCondition.setModel(getJoinConditionSelected());
			viewJoinCondition.setVisible(true);
			
			buttonDeleteJoinCondition.setVisible(true);
		}
		
	}

	/**
	 * @param controllerObjectRef
	 */
	public void setController(ControllerReferencedObjectMap controllerObjectRef) {
		buttonDelete.addActionListener(controllerObjectRef);
		buttonDeleteJoinCondition.addActionListener(controllerObjectRef);
		buttonAddJoinCondition.addActionListener(controllerObjectRef);
	}
	
	protected void comboBoxJoinConditionsActionPerformed(ActionEvent e) {

		viewJoinCondition.setModel(getJoinConditionSelected());
		
	}


	public JoinCondition getJoinConditionSelected() {
		return model.getJoinConditionAt(comboBoxJoinConditions.getSelectedIndex());
		
	}
	
}
