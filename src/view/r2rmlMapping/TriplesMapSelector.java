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

package view.r2rmlMapping;

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
 * Select a triples map to edit
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class TriplesMapSelector extends JDialog {

	private static final long serialVersionUID = -8680729602499258947L;
	private int sizeTriplesMap;
	private String[] triplesMapNames;
	private JButton buttonOk;
	private JButton buttonCancel;
	private JComboBox<String> comboBoxTriplesMap;
	private DefaultComboBoxModel<String> comboBoxTriplesMapModel;
	private boolean cancel = false;
	private int triplesMapSelected;

	/**
	 * Create the dialog.
	 * @param frame 
	 * @param paramTrilesMap 
	 */

	public TriplesMapSelector(JFrame frame, int sizeTrilesMap) {
		
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
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new MigLayout("", "[grow]", "[grow][][][grow][]"));
		
		JLabel labelSelectTiplesMap = new JLabel("Select the triples map");
		getContentPane().add(labelSelectTiplesMap, "cell 0 1,alignx center");
		
		comboBoxTriplesMap = new JComboBox<String>();
		comboBoxTriplesMap.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				comboBoxActionPerformed(e);
				
			}
		});
		getContentPane().add(comboBoxTriplesMap, "cell 0 2,growx");
		
		comboBoxTriplesMapModel = new DefaultComboBoxModel<String>(triplesMapNames);
		comboBoxTriplesMap.setModel(comboBoxTriplesMapModel);
		
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
		
		triplesMapSelected = comboBoxTriplesMap.getSelectedIndex();

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
	 * @return the triple map selected
	 */
	public int getTriplesMapSelected() {
		return triplesMapSelected;
	}

}
