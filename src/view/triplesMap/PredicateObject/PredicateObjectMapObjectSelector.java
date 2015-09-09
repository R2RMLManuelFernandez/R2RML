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

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

/**
 * Select an object map from the predicate-object Map
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class PredicateObjectMapObjectSelector extends JDialog {

	private static final long serialVersionUID = -8476428493870623329L;
	
	private final JPanel contentPanel = new JPanel();
	private int sizeObject;
	private String[] objectNames;
	private JComboBox<String> comboBox;
	private boolean cancel = false;
	private int objectSelected;

	/**
	 * Create the dialog.
	 * @param frame 
	 */
	public PredicateObjectMapObjectSelector(Frame frame, int paramSizeObj) {
		
		super(frame, true);
		this.sizeObject = paramSizeObj;
		objectNames = new String[sizeObject];
		for (int i = 0; i < paramSizeObj; i++) {
			objectNames[i] = "Object " + (i + 1);
		}
		initialize();

	}
	/**
	 * 
	 */
	private void initialize() {
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 250);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[grow]", "[grow][30px:n][30px:n][grow]"));

		JLabel labelSelectObject = new JLabel("Select the Object");
		contentPanel.add(labelSelectObject, "cell 0 1,alignx center");

		comboBox = new JComboBox<String>();
		contentPanel.add(comboBox, "cell 0 2,growx");
		
		DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel<String>(objectNames);
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
		
		cancel  = true;
		this.dispose();
		
	}

	/**
	 * @param e
	 */
	protected void comboBoxActionPerformed(ActionEvent e) {	
		
		objectSelected = comboBox.getSelectedIndex();

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
	public int getObjectSelected() {
		return objectSelected;
	}

}
