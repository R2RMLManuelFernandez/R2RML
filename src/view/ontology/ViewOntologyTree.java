package view.ontology;

import javax.swing.DropMode;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Position;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import view.util.TreeUtil;
import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JScrollPane;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import control.ontology.OntologyModelConstructor;
import control.ontology.OntologyTreeModelConstructor;
import javax.swing.JTabbedPane;

/**
 * Displays the ontology model in a tree
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class ViewOntologyTree extends JPanel {

	private static final long serialVersionUID = 2335706050345687194L;

	private JTree treeOntology;
	private DefaultTreeModel model;
	private OntologyModelConstructor ontologyModel = null;
	private OntologyTreeModelConstructor ontologyTreeModel = null;
	private JTextField textFieldOntologySearch;
	private MyOntologyRender ontologyRender;
	
	/**
	 * Create the panel.
	 */
	public ViewOntologyTree() {
		setLayout(new MigLayout("", "[325px,grow]", "[55.00px][2px][37px][2px][14px][563px,grow]"));
		
		JPanel panelFind = new JPanel();
		add(panelFind, "cell 0 0,grow");
		panelFind.setLayout(new MigLayout("", "[35.00px][86px,grow][]", "[18.00px][18.00px]"));
		
		JLabel lblFnd = new JLabel("Find:");
		panelFind.add(lblFnd, "cell 0 0 1 2,alignx center,aligny center");
		
		textFieldOntologySearch = new JTextField();
		panelFind.add(textFieldOntologySearch, "flowx,cell 1 0 1 2,growx,aligny center");
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
		panelFind.add(buttonOntologyFindNext, "cell 2 1,grow");
		buttonOntologyFindNext.setMinimumSize(new Dimension(30, 15));
		buttonOntologyFindNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buttonOntologyFindNextActionPerformed(treeOntology);
			}
		});
		
		JSeparator separator = new JSeparator();
		add(separator, "cell 0 1,grow");
		
		JPanel panelTreeButton = new JPanel();
		add(panelTreeButton, "cell 0 2,grow");
		panelTreeButton.setLayout(new MigLayout("", "[grow][][15.00][][grow]", "[]"));
		
		JButton buttonExpandOntologyTree = new JButton("Expand");
		buttonExpandOntologyTree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TreeUtil.expandAll(treeOntology);
			}
		});
		panelTreeButton.add(buttonExpandOntologyTree, "cell 1 0,alignx left");
		
		JButton buttonCollapseOntologyTree = new JButton("Collapse");
		buttonCollapseOntologyTree.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TreeUtil.collapseAll(treeOntology);
			}
		});
		panelTreeButton.add(buttonCollapseOntologyTree, "cell 3 0,alignx right");
		
		JSeparator separator_1 = new JSeparator();
		add(separator_1, "cell 0 3,grow");
		
		JLabel labelOntologyIRI = new JLabel("Ontology:");
		add(labelOntologyIRI, "cell 0 4,alignx left,aligny top");
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, "cell 0 5,grow");
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		JScrollPane scrollPaneOntologyTree = new JScrollPane();
		tabbedPane.addTab("All", null, scrollPaneOntologyTree, null);
		
		tabbedPane.addTab("Classes", null, new JTree(), null);
		
		tabbedPane.addTab("Data Properties", null, new JTree(), null);
		
		tabbedPane.addTab("Object Properties", null, new JTree(), null);
		
		treeOntology = new JTree(new DefaultTreeModel(null));
		treeOntology.setCellRenderer(ontologyRender);
		treeOntology.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);		
		treeOntology.setDragEnabled(true);
		treeOntology.setDropMode(DropMode.ON_OR_INSERT);
		scrollPaneOntologyTree.setViewportView(treeOntology);
        ontologyRender = new MyOntologyRender();

		
	}
	
	/**
	 * Sets the transferhandler for the tree
	 * 
	 * @param paramTransferHandler
	 */
	public void setOntologyTransferHandler(TransferHandler paramTransferHandler) {
		
		treeOntology.setTransferHandler(paramTransferHandler);
		
	}
		
	/**
	 * Creates a new ontology model and loads it into the tree
	 * 
	 * @param paramOntologySource
	 * @throws OWLOntologyCreationException
	 */
	public void setOntologyModel(String paramOntologySource) throws OWLOntologyCreationException {
		
		try {
			ontologyModel = new OntologyModelConstructor(paramOntologySource);
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			throw(e);
		}
		ontologyTreeModel = new OntologyTreeModelConstructor(ontologyModel.getThing());
		
		this.model = ontologyTreeModel.getOntologyTreeModel();
		this.treeOntology.setModel(model);
		
	}

	/**
	 * Find the nodes in the ontology tree which label begins whith the pattern
	 */
	protected void findOntologyNodes(JTree paramTree) {
		String textToFind = textFieldOntologySearch.getText();
		ontologyRender.setStart(textToFind);
		if (textToFind != null && !textToFind.isEmpty()) {
			TreeUtil.searchOntologyTree(paramTree, paramTree.getPathForRow(0), textToFind.toLowerCase());
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
			if (row > treeOntology.getRowCount()) {
				row = 0;
			}
	
			TreePath path = null;
			try {
				path = treeOntology.getNextMatch(text, row, Position.Bias.Backward);
			} catch (IllegalArgumentException  ia) {
				System.out.print(row);
				ia.printStackTrace();
			}
			
			if (path != null) {
				treeOntology.setSelectionPath(path);
				treeOntology.scrollPathToVisible(path);
			}
			else {
				if (row != 0) {
					path = treeOntology.getNextMatch(text, treeOntology.getRowCount(), Position.Bias.Backward);
					if (path != null) {
						treeOntology.setSelectionPath(path);
					}
				}
			}
		}
	}

}
