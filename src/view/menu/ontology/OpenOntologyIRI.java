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

package view.menu.ontology;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.menu.bookmarks.OntologySource;
import model.menu.bookmarks.StaXListOntologySourcesParser;
import model.menu.bookmarks.StaXListOntologySourcesWriter;
import net.miginfocom.swing.MigLayout;

/**
 * Opens an ontology from its IRI
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class OpenOntologyIRI extends JDialog {

	private static final long serialVersionUID = -3632433535651195352L;

	private JPanel contentPane;

	private JLabel labelOntologyIRI;
	private JTextField textFieldOntologyIRI;
	private JLabel labelOr;
	private JLabel labelSelectBookmark;
	private JButton buttonAddToBookmarks;
	private JButton buttonDeleteBookmark;
	private JButton buttonImportBookmarks;
	private JFileChooser fileChooserImportBookmarks;
	private JButton buttonExportBookmarks;
	private JFileChooser fileChooserExportBookmarks;
	private JList<String> listBookMark;
	private JScrollPane scrollPaneListBookMark;
	private JButton buttonNext;
	private JButton buttonCancel;
	
	private StaXListOntologySourcesParser bookmarkParser;
	private StaXListOntologySourcesWriter bookmarkWriter;

	/**
	 * Create the dialog.
	 */
	public OpenOntologyIRI(JFrame frame) {
		super(frame, true);
		initialize();

	}

	/**
	 * Initialize the contents of the dialog.
	 */
	private void initialize() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 550, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);	
		contentPane.setLayout(new MigLayout("", "[20.00][][]", "[][][][10.00][][50px:50,grow][][][]"));
		
		labelOntologyIRI = new JLabel("Insert Ontology IRI: ");
		contentPane.add(labelOntologyIRI, "cell 1 1,alignx center");

		textFieldOntologyIRI = new JTextField();
		contentPane.add(textFieldOntologyIRI, "cell 2 1,growx");
		textFieldOntologyIRI.setColumns(10);
		
		labelOr = new JLabel("Or");
		labelOr.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPane.add(labelOr, "cell 1 2,alignx center");
		
		buttonAddToBookmarks = new JButton("Add To Bookmarks");
		buttonAddToBookmarks.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					buttonAddBookmarkActionPerformed(e);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		contentPane.add(buttonAddToBookmarks, "cell 2 2,alignx trailing,aligny top");
		
		labelSelectBookmark = new JLabel("Select Bookmark: ");
		labelSelectBookmark.setVerticalAlignment(SwingConstants.TOP);
		contentPane.add(labelSelectBookmark, "cell 1 4,alignx center,aligny top");
		
		scrollPaneListBookMark = new JScrollPane();
		contentPane.add(scrollPaneListBookMark, "cell 2 4 1 2,grow");
		
		listBookMark = new JList<String>();
		listBookMark.setModel(new DefaultListModel<String>());
		listBookMark.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				listBookmarkValueChanged(e);
			}
		});
		scrollPaneListBookMark.setViewportView(listBookMark);
		listBookMark.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		readBookmarks();
		
		buttonDeleteBookmark = new JButton("Delete Bookmark");
		buttonDeleteBookmark.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					buttonDeleteBookmarkActionPerformed(e);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		contentPane.add(buttonDeleteBookmark, "flowx,cell 2 6,alignx right");
		
		fileChooserImportBookmarks = new JFileChooser();
		
		buttonImportBookmarks = new JButton("Import Bookmarks");
		buttonImportBookmarks.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					buttonImportBookmarksActionPerformed(e);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		contentPane.add(buttonImportBookmarks, "cell 2 6");
		
		fileChooserExportBookmarks = new JFileChooser();
		
		buttonExportBookmarks = new JButton("Export Bookmarks");
		buttonExportBookmarks.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					buttonExportBookmarksActionPerformed(e);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		contentPane.add(buttonExportBookmarks, "cell 2 6");
		
		buttonNext = new JButton("Next ...");
		buttonNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonNextActionPerformed(e);
			}
		});
		contentPane.add(buttonNext, "flowx,cell 2 8,alignx trailing");
		
		buttonCancel = new JButton("Cancel");
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonCancelActionPerformed(e);
			}
		});
		contentPane.add(buttonCancel, "cell 2 8,alignx trailing");
		
	}

	/**
	 * loads the bookmark from the bookmark list onto the ontology tect field 
	 * 
	 * @param le
	 */
	protected void listBookmarkValueChanged(ListSelectionEvent le) {
		
		if (!le.getValueIsAdjusting()) {
			
			String selected = listBookMark.getSelectedValue();
			textFieldOntologyIRI.setText(selected);		
			
		}
	}
	
	/**
	 * Adds a bokkmark to the bookmark list and updates the XML bookmark file.
	 * 
	 * @param ae
	 * @throws Exception
	 */
	private void buttonAddBookmarkActionPerformed(ActionEvent ae) throws Exception {
		
		String bookmarkToAdd = textFieldOntologyIRI.getText();
		
		if (!(bookmarkToAdd.equals(""))) {
			
			DefaultListModel<String> listModel = (DefaultListModel<String>) listBookMark.getModel();
			listModel.addElement(bookmarkToAdd);
			writeBookmarks();
			
		}
		else {	
	    	JOptionPane.showMessageDialog(this, "Please enter an IRI to add to bookmarks", "Enter an IRI", JOptionPane.WARNING_MESSAGE);
		}
		
	}
	
	/**
	 * Delete a bokkmark from the bookmark list and updates the XML bookmark file.
	 * 
	 * @param ae
	 * @throws Exception
	 */
	private void buttonDeleteBookmarkActionPerformed(ActionEvent ae) throws Exception {
		
		String bookmarkToDelete = listBookMark.getSelectedValue();
		
		if (bookmarkToDelete != null) {
			DefaultListModel<String> listModel = (DefaultListModel<String>) listBookMark.getModel();
			listModel.removeElement(bookmarkToDelete);	
			writeBookmarks();
		}
		else {
	    	JOptionPane.showMessageDialog(this, "Please select a bookmark to delete", "Select a bookmark", JOptionPane.WARNING_MESSAGE);
		}
		
	}
	
	/**
	 * Loads the bookmarks from an XML file into the bookmark list and updates the XML bookmark file
	 * 
	 * @param e
	 * @throws Exception
	 */
	protected void buttonImportBookmarksActionPerformed(ActionEvent e) throws Exception {
		
		int result = fileChooserImportBookmarks.showOpenDialog(null);
		
		if (result == JFileChooser.APPROVE_OPTION) {
			
			StaXListOntologySourcesParser importBookmarkParser = new StaXListOntologySourcesParser("ontology", "iri", fileChooserImportBookmarks.getSelectedFile().getPath());
			
		    List<OntologySource> readBookmarks = importBookmarkParser.read();
		    
		    if (!readBookmarks.isEmpty()) {
		    	
		    	for (OntologySource bookmark: readBookmarks) {
		    		
		    		DefaultListModel<String> listModel = (DefaultListModel<String>) listBookMark.getModel();
		    		listModel.addElement(bookmark.toString());

		    	}
		    	
				writeBookmarks();
		    }
		    else {
		    	JOptionPane.showMessageDialog(this, "The selected file doesnt content any ontology bookmark", "Select a file", JOptionPane.WARNING_MESSAGE);
		    }
		}
		
	}

	/**
	 * Exports the bookmarks from the bookmark list to an an XML file
	 * 
	 * @param ae
	 * @throws Exception
	 */
	private void buttonExportBookmarksActionPerformed(ActionEvent ae) throws Exception {

		int result = fileChooserExportBookmarks.showSaveDialog(null);
		
		if (result == JFileChooser.APPROVE_OPTION) {
			
			StaXListOntologySourcesWriter exportBookmarkWriter = new StaXListOntologySourcesWriter("bookmarks", "iri", fileChooserExportBookmarks.getSelectedFile().getPath());
			
		    ArrayList<String> bookmarksToWrite = new ArrayList<String>(0);
			   
		    DefaultListModel<String> listModel = (DefaultListModel<String>) listBookMark.getModel();
		    
		    int bookmarksSize = listModel.size();
		    
		    for (int i = 0; i < bookmarksSize; i++) {
		    	
		    	bookmarksToWrite.add(listModel.get(i));
		    	
		    }
			
		    exportBookmarkWriter.save(bookmarksToWrite);
			
		}
		
	}
	
	/**
	 * Returns the control to the parent component. A valid ontology IRI is in the text field
	 * 
	 * @param ae
	 */
	private void buttonNextActionPerformed(ActionEvent ae) {
		if (textFieldOntologyIRI.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please, enter an ontology IRI", "Enter an IRI", JOptionPane.WARNING_MESSAGE);
		}
		else {
			this.dispose();
		}

	}
	
	/**
	 * Cancels the action in progres and returns the control to the parent component
	 * 
	 * @param ae
	 */
	private void buttonCancelActionPerformed(ActionEvent ae) {
		
		this.dispose();

	}
	
	/**
	 * Load the bookmarks from the XML bookmark file  into the bookmark list 
	 */
	private void readBookmarks() { 
		
	    bookmarkParser= new StaXListOntologySourcesParser("ontology", "iri", "resources/xmls/Bookmarks.xml");
	    List<OntologySource> readBookmarks = bookmarkParser.read();
	    
	    if (!readBookmarks.isEmpty()) {
	    	
	    	for (OntologySource bookmark: readBookmarks) {
	    		
	    		DefaultListModel<String> listModel = (DefaultListModel<String>) listBookMark.getModel();
	    		listModel.addElement(bookmark.getSource());
	    		
	    	}
	    	
	    }
	    
	}
	
	/**
	 * Writes the bookmarks from the bookmark list into the XML bookmark file
	 * 
	 * @throws Exception
	 */
	private void writeBookmarks() throws Exception { 
		
	    bookmarkWriter= new StaXListOntologySourcesWriter("bookmarks", "iri", "resources/xmls/Bookmarks.xml");
	    ArrayList<String> bookmarksToWrite = new ArrayList<String>(0);
	   
	    DefaultListModel<String> listModel = (DefaultListModel<String>) listBookMark.getModel();
	    
	    int bookmarksSize = listModel.size();
	    
	    for (int i = 0; i < bookmarksSize; i++) {

	    	bookmarksToWrite.add(listModel.get(i));
	    	
	    }
		
		bookmarkWriter.save(bookmarksToWrite);

	}

	/**
	 * Returns the IRI of the ontology
	 * 
	 * @return
	 */
	public String getOntologyIRI() {

		return textFieldOntologyIRI.getText().trim();

	}
	
}
