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

package view.ontology;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Position;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import model.r2rmlmapping.triplesMap.TriplesMap;
import net.miginfocom.swing.MigLayout;
import view.util.OntologyTreePopupHandler;
import view.util.TreeUtil;

/**
 * @author Manuel
 *
 * Displays a tree component of the model
 */
public class ViewOntologyTree extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6320786910764700467L;

	private MyOntologyRender ontologyRender;
	private JTextField textFieldOntologySearch;
	private JTree treeOntology;
	@SuppressWarnings("unused")
	private TriplesMap mappingItem;
	private OntologyTreePopupHandler popupHandler;
	private JFrame frame;
	
	/**
	 * Create the panel.
	 */
	public ViewOntologyTree(JFrame paramFrame) {
		
		this.frame = paramFrame;
		
        ontologyRender = new MyOntologyRender();
		setLayout(new MigLayout("", "[316px,grow]", "[62px][2.00][38.00px][grow]"));
		
		JPanel panelFind = new JPanel();
		add(panelFind, "cell 0 0,growx,aligny top");
		panelFind.setLayout(new MigLayout("", "[40.00px][150.00,grow][100.00px]", "[18.00px][4px][18.00px]"));
		
		JLabel labelFind = new JLabel("Find:");
		panelFind.add(labelFind, "cell 0 0 1 3,alignx center,aligny center");
		
		textFieldOntologySearch = new JTextField();
		panelFind.add(textFieldOntologySearch, "cell 1 0 1 3,growx,aligny center");
		textFieldOntologySearch.setColumns(10);
		textFieldOntologySearch.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				findOntologyNodes(treeOntology);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				findOntologyNodes(treeOntology);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				findOntologyNodes(treeOntology);
			}
		});
		
		JButton buttonOntologyFindPrevious = new JButton("Previous");
		panelFind.add(buttonOntologyFindPrevious, "cell 2 0,grow");
		buttonOntologyFindPrevious.setMinimumSize(new Dimension(30, 15));
		buttonOntologyFindPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonOntologyFindPreviousActionPerformed(treeOntology);
			}
		});
		
		JButton buttonOntologyFindNext = new JButton("Next");
		panelFind.add(buttonOntologyFindNext, "cell 2 2,grow");
		buttonOntologyFindNext.setMinimumSize(new Dimension(30, 15));
		buttonOntologyFindNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonOntologyFindNextActionPerformed(treeOntology);
			}
		});
		
		JSeparator separator = new JSeparator();
		add(separator, "cell 0 1,growx");
		
		JPanel panelTreeButton = new JPanel();
		add(panelTreeButton, "cell 0 2,grow");
		panelTreeButton.setLayout(new MigLayout("", "[-92.00,grow][85.00][10.00][85.00][grow]", "[]"));
		
		JButton buttonExpandOntologyTree = new JButton("Expand");
		buttonExpandOntologyTree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TreeUtil.expandAll(treeOntology);
			}
		});
		panelTreeButton.add(buttonExpandOntologyTree, "cell 1 0,growx");
		
		JButton buttonCollapseOntologyTree = new JButton("Collapse");
		buttonCollapseOntologyTree.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TreeUtil.collapseAll(treeOntology);
			}
		});
		panelTreeButton.add(buttonCollapseOntologyTree, "cell 3 0,growx");
        
        JScrollPane scrollPaneOntologyTree = new JScrollPane();		
        add(scrollPaneOntologyTree, "cell 0 3,grow");
        
        treeOntology = new JTree(new DefaultTreeModel(null));
        scrollPaneOntologyTree.setViewportView(treeOntology);
        treeOntology.setCellRenderer(ontologyRender);
        treeOntology.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);		
        treeOntology.setDragEnabled(true);
        treeOntology.setDropMode(DropMode.ON_OR_INSERT);
        
	}
	
	/**
	 * sets ontology model and loads it into the tree
	 * 
	 * @param treeModel
	 */
	public void setTreeModel(DefaultTreeModel treeModel) {
		
		this.treeOntology.setModel(treeModel);

	}
	
	/**
	 * @param mappingItem
	 */
	public void setMappingItem (TriplesMap mappingItem) {
		
		this.mappingItem = mappingItem;
		
        //creo el mappingItem vacio y lo asigno vacio al popup-habdler
        //tengo que establecer el mappingItem antes de crear el popuphandler
        popupHandler = new OntologyTreePopupHandler(treeOntology, mappingItem, frame);
        treeOntology.add(popupHandler.getPopup());		
	}
	
	/**
	 * Sets the transferhandler for the tree
	 * 
	 * @param paramTransferHandler
	 */
	public void setTreeTransferHandler(TransferHandler paramTransferHandler) {
		
		treeOntology.setTransferHandler(paramTransferHandler);
		
	}
	
	/**
	 * Find the nodes in the ontology tree which label begins whith the pattern
	 */
	protected void findOntologyNodes(JTree paramTree) {
		String textToFind = textFieldOntologySearch.getText();
		ontologyRender.setStart(textToFind);
		if (textToFind != null && !textToFind.isEmpty()) {
			TreeUtil.searchNameInOntologyTree(paramTree, paramTree.getPathForRow(0), textToFind.toLowerCase());
		}
		paramTree.repaint();
	}

	/**
	 * Finds the position of the next node in the ontology tree which label begins whith the pattern
	 */
	protected void buttonOntologyFindNextActionPerformed(JTree paramTree) {
		
		String text = textFieldOntologySearch.getText();
		
		if (text != null && !text.isEmpty()) {
			findOntologyNodes(paramTree);
			
			TreePath selPath = paramTree.getSelectionPath();
			
			int row = paramTree.getRowForPath(selPath) + 1;
			
			if (row < 0 ) {
				row = 0;
			}
			if (row >= paramTree.getRowCount()) {
				row = 0;
			}

			TreePath path = null;
			try {
				path = paramTree.getNextMatch(text, row, Position.Bias.Forward);
			} catch (IllegalArgumentException  ia) {
				System.out.print(row);
				ia.printStackTrace();
			}
			
			if (path != null) {
				paramTree.setSelectionPath(path);
				paramTree.scrollPathToVisible(path);
			}

		}
	}

	/**
	 * Finds the position of the previous node in the ontology tree which label begins whith the pattern
	 */
	protected void buttonOntologyFindPreviousActionPerformed(JTree paramTree) {
		
		String text = textFieldOntologySearch.getText();
		
		if (text != null && !text.isEmpty()) {
			findOntologyNodes(paramTree);
			TreePath selPath = paramTree.getSelectionPath();
			int row = paramTree.getRowForPath(selPath) - 1;

			if (row < 0 ) {
				row = paramTree.getRowCount() - 1;
			}
			if (row > paramTree.getRowCount()) {
				row = 0;
			}
	
			TreePath path = null;
			try {
				path = paramTree.getNextMatch(text, row, Position.Bias.Backward);
			} catch (IllegalArgumentException  ia) {
				System.out.print(row);
				ia.printStackTrace();
			}
			
			if (path != null) {
				paramTree.setSelectionPath(path);
				paramTree.scrollPathToVisible(path);
			}
			else {
				if (row != 0) {
					path = paramTree.getNextMatch(text, paramTree.getRowCount(), Position.Bias.Backward);
					if (path != null) {
						paramTree.setSelectionPath(path);
					}
				}
			}
		}
	}
	

	/**
	 * Find the nodes in the ontology tree which label begins whith the pattern
	 */
	public void findInMappingNodes(String iri, Boolean dirt) {
		
		//System.out.println("findInMappingNodes: " + iri);
		TreeUtil.searchIriInOntologyTree(treeOntology, treeOntology.getPathForRow(0), iri, dirt);

	}
	
	/**
	 * Repaints the tree
	 */
	public void repaintTree() {
		
		treeOntology.repaint();
	
	}
	
}
