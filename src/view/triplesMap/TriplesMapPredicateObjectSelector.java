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

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

/**
 * Select a predicate-object map from the Triples Map
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class TriplesMapPredicateObjectSelector extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6859256147372827490L;
	private final JPanel contentPanel = new JPanel();

	private JComboBox<String> comboBox;
	private int sizePredObj;
	private String[] predObjNames;
	private int predObjSelected;
	private boolean cancel = false;
	
	/**
	 * Create the dialog.
	 * @param frame 
	 */
	public TriplesMapPredicateObjectSelector(JFrame frame, int paramSizePO) {
		super(frame, true);
		this.sizePredObj = paramSizePO;
		predObjNames = new String[sizePredObj];
		for (int i = 0; i < sizePredObj; i++) {
			predObjNames[i] = "Predicate-Object " + (i + 1);
		}
		initialize();
	}

	/**
	 * Initializes the JDialog
	 */
	private void initialize() {
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 250);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[grow]", "[grow][30px:n][30px:n][grow]"));

		JLabel labelSelecPredicateObject = new JLabel("Select the Predicate-Object");
		contentPanel.add(labelSelecPredicateObject, "cell 0 1,alignx center");

		comboBox = new JComboBox<String>();
		contentPanel.add(comboBox, "cell 0 2,growx");
		
		DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel<String>(predObjNames);
		comboBox.setModel(comboModel);
		comboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				comboBoxActionPerformed(e);
				
			}
		});

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				okButtonActionPerformed(e);
				
			}
		});
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonCancelActionPerformed(e);
				
			}
		});
		buttonPane.add(cancelButton);
		
	}
	
	/**
	 * @param e
	 */
	protected void okButtonActionPerformed(ActionEvent e) {
		
		this.setVisible(false);
		
	}
	
	/**
	 * @param e
	 */
	protected void buttonCancelActionPerformed(ActionEvent e) {
		
		cancel = true;
		this.dispose();
		
	}

	/**
	 * @param e
	 */
	protected void comboBoxActionPerformed(ActionEvent e) {	
		
		predObjSelected = comboBox.getSelectedIndex();

	}

	/**
	 * Checks if the cancel flag in the dialog is raised
	 * 
	 * @return
	 */
	public boolean checkCancel() {
		
		return cancel;
				
	}
	
	/**
	 * @return the predObjSelected
	 */
	public int getPredObjSelected() {
		return predObjSelected;
	}

}
