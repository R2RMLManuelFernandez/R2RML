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

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

/**
 * Select a parent triples map for the reference object map
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class ParentTriplesMapSelector extends JDialog {

	private static final long serialVersionUID = -8680729602499258947L;
	private int sizeTriplesMap;
	private String[] triplesMapNames;
	private JButton buttonOk;
	private JButton buttonCancel;
	private JComboBox<String> comboBox;
	private DefaultComboBoxModel<String> comboBoxModel;
	private boolean cancel = false;
	private int parentTriplesMapSelected;

	/**
	 * Create the dialog.
	 * @param frame 
	 * @param paramTrilesMap 
	 */
	public ParentTriplesMapSelector(JFrame frame, int sizeTrilesMap) {
		
		super(frame, true);
		this.sizeTriplesMap = sizeTrilesMap;

		triplesMapNames = new String[sizeTriplesMap];
		for (int i = 0; i < sizeTriplesMap; i++) {
			triplesMapNames[i] = "Triples Map " + (i + 1);
		}
		initialize();

	}

	/**
	 * 
	 */
	private void initialize() {
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(new MigLayout("", "[grow]", "[grow][][][grow][]"));
		
		JLabel labelSelectTheParent = new JLabel("Select the parent triples map");
		getContentPane().add(labelSelectTheParent, "cell 0 1,alignx center");
		
		comboBox = new JComboBox<String>();
		comboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				comboBoxActionPerformed(e);
				
			}
		});
		getContentPane().add(comboBox, "cell 0 2,growx");
		
		comboBoxModel = new DefaultComboBoxModel<String>(triplesMapNames);
		comboBox.setModel(comboBoxModel);
		
		buttonOk = new JButton("OK");
		buttonOk.setName("OK");
		buttonOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonOkActionPerformed(e);
				
			}
		});
		getContentPane().add(buttonOk, "flowx,cell 0 4,alignx trailing");
		
		buttonCancel = new JButton("Cancel");
		buttonCancel.setName("Cancel");
		buttonCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonCancelActionPerformed(e);
				
			}
		});
		getContentPane().add(buttonCancel, "cell 0 4,alignx trailing");
		
	}
	
	/**
	 * @param e
	 */
	protected void buttonOkActionPerformed(ActionEvent e) {
		
		this.setVisible(false);
		
	}
	
	/**
	 * @param e
	 */
	protected void buttonCancelActionPerformed(ActionEvent e) {
		
		cancel  = true;
		this.dispose();
		
	}

	/**
	 * @param e
	 */
	protected void comboBoxActionPerformed(ActionEvent e) {	
		
		parentTriplesMapSelected = comboBox.getSelectedIndex();

	}

	/**
	 * Checks if the cancel flag in the dialog is raised
	 * 
	 * @return
	 */
	public boolean checkCancel() {
		
		return cancel ;
				
	}
	
	/**
	 * @return the predObjSelected
	 */
	public int getParentTriplesMapSelected() {
		return parentTriplesMapSelected;
	}

}
