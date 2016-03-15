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

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import model.r2rmlmapping.triplesMap.PredicateMap;
import net.miginfocom.swing.MigLayout;

import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.JButton;

import control.r2rmlmapping.triplesMap.ControllerPredicateMap;

/**
 * View to represent the predicate
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class ViewPredicate extends JPanel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5869163176998258817L;
	
	private JTextField textFieldPredicate;
	private JButton buttonDelete;

	private PredicateMap model;

	/**
	 * Create the panel.
	 */
	public ViewPredicate() {
		setBorder(new TitledBorder(null, "Predicate", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new MigLayout("", "[90.00,grow]", "[][grow]"));
		
		textFieldPredicate = new JTextField();
		add(textFieldPredicate, "cell 0 0,growx");
		textFieldPredicate.setColumns(10);
		
		buttonDelete = new JButton("Delete");
		buttonDelete.setName("Delete");
		add(buttonDelete, "cell 0 1,alignx center,aligny baseline");

	}
	
	/**
	 * @return
	 */
	public PredicateMap getModel() {
		
		return model;
		
	}
	
	/**
	 * @param predicateMap
	 */
	public void setModel (PredicateMap predicateMap) {
		
		this.model = predicateMap;
		textFieldPredicate.setText(model.getPredicateIRI().getIRIShow());
		model.addObserver(this);
		model.notifyObservers();
		
	}
	
	public void setController(ControllerPredicateMap controller) {
		buttonDelete.addActionListener(controller);
	}

	@Override
	public void update(Observable o, Object arg) {
		textFieldPredicate.setText(model.getPredicateIRI().getIRIShow());
		
	}

}
