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
import javax.swing.JTextField;

import model.r2rmlmapping.triplesMap.JoinCondition;
import net.miginfocom.swing.MigLayout;

/**
 * View to represent the join condition
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class ViewJoinCondition extends JPanel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7553816901888991198L;
	
	private JTextField textFieldChild;
	private JTextField textFieldParent;

	private JoinCondition model;
	
	/**
	 * Create the panel.
	 */
	public ViewJoinCondition() {
		setLayout(new MigLayout("", "[86px,grow]", "[grow][20px,grow]"));

		textFieldChild = new JTextField();
		add(textFieldChild, "cell 0 1,growx,aligny center");
		textFieldChild.setColumns(10);
		
		textFieldParent = new JTextField();
		add(textFieldParent, "cell 0 0,growx,aligny center");
		textFieldParent.setColumns(10);
		
	}

	/**
	 * @param joinCondition
	 */
	public void setModel(JoinCondition joinCondition) {
		
		this.model = joinCondition;
		this.model.addObserver(this);
		
		if (model.getChild() != null) {
			textFieldChild.setText(model.getChild().getColumnName());
		}
		else {
			textFieldChild.setText("");
		}
			
		if (model.getParent() != null) {
			textFieldParent.setText(model.getParent().getColumnName());
		}
		else {
			textFieldParent.setText("");
		}

		
	}


	@Override
	public void update(Observable o, Object arg) {
		
		if (model.getChild() != null) {
			textFieldChild.setText(model.getChild().getColumnName());
		}
		else {
			textFieldChild.setText("");
		}
			
		if (model.getParent() != null) {
			textFieldParent.setText(model.getParent().getColumnName());
		}
		else {
			textFieldParent.setText("");
		}

	}

}
