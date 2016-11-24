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

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Establish the namespace for Triples Map
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class R2RMLMapBaseIRISelector extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5802324026326616228L;
	private final JPanel contentPanel = new JPanel();
	
	private boolean cancel = false;
	private JTextField textFieldBaseIRI;
	private String baseIRI;

	/**
	 * Create the dialog.
	 */
	public R2RMLMapBaseIRISelector(JFrame frame) {
		super(frame, true);
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
		
		JLabel labelR2RMLMapBaseIRI = new JLabel("Enter the R2RML Map's base IRI");
		
		contentPanel.add(labelR2RMLMapBaseIRI, "cell 0 1,alignx center,aligny center");
		
		textFieldBaseIRI = new JTextField();
		textFieldBaseIRI.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {

				textFieldBaseIRIchanged();
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {

				textFieldBaseIRIchanged();
				
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {

				textFieldBaseIRIchanged();
				
			}
		});
		
		contentPanel.add(textFieldBaseIRI, "cell 0 2,grow");
		textFieldBaseIRI.setColumns(10);
		
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
		
		if (this.baseIRI == null) {
			
			JOptionPane.showMessageDialog(this, "You havent entered a IRI base namesapce", "Enter a R2RML mapping base IRI", JOptionPane.WARNING_MESSAGE);
			
		}
		
	   else {
		   
		   this.setVisible(false);
		   
	   }

	}
	
	/**
	 * @param e
	 */
	protected void buttonCancelActionPerformed(ActionEvent e) {
		
		cancel = true;
		this.dispose();
		
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
	 * Gets the value of the text from the JtestField
	 */
	protected void textFieldBaseIRIchanged() {
		this.baseIRI = textFieldBaseIRI.getText();
	}

	/**
	 * @return the predObjSelected
	 */
	public String getSelectedBaseIRI() {
		return baseIRI;
	}
	
}
