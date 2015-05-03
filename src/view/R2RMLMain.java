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

package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import model.mapping.Mapping;
import model.mapping.MappingElement;
import model.mapping.MappingTable;
import model.menu.bookmarks.OntologySource;
import model.menu.bookmarks.StaXListOntologySourcesParser;
import model.menu.bookmarks.StaXListOntologySourcesWriter;
import net.miginfocom.swing.MigLayout;
import view.addMappingItem.ViewAddMappingItem;
import view.database.DatabaseTreeToTextTransferHandler;
import view.database.ViewDatabaseTree;
import view.menu.database.OpenDatabaseDialog;
import view.menu.ontology.OpenOntologyIRI;
import view.ontology.OntologyTreeToTextTransferHandler;
import view.ontology.ViewOntologyTree;
import view.tableMapping.ViewTableMapping;
import control.addMappingItem.ControllerAddNewMapping;
import control.mappingTable.ControllerMappingTable;

/**
 * Main view
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class R2RMLMain {

	private JFrame frame;
	private JMenuBar menuBar;
	private OpenDatabaseDialog openDatabaseDialog;
	private OpenOntologyIRI openOntologyIRI;
	private JFileChooser fileChooserOntology;
	private List<OntologySource> recentOntologies;
	private JMenu menuRecent;
	private StaXListOntologySourcesWriter recentsWriter;
	private ViewAddMappingItem viewAddNewMapping;
	private ViewTableMapping viewTableMapping;
	private ViewOntologyTree viewOntologyTree;
	private ViewDatabaseTree viewDatabaseTree;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					R2RMLMain window = new R2RMLMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the application.
	 */
	public R2RMLMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.GRAY);
		frame.getContentPane().setLayout(new MigLayout("", "[300.00,grow][656.00,grow][300.00,grow]", "[83.00,grow,center][grow][97.00][716.00,grow][]"));
		
		//creates the menu
		createMenuBar();
		
		//view for the ontology 
		viewOntologyTree = new ViewOntologyTree();
		frame.getContentPane().add(viewOntologyTree, "cell 0 0 1 4,grow");

		//transferhandler between ontology view and the view to add a term to the mapping
        TransferHandler ontologyTransferHandler = new OntologyTreeToTextTransferHandler();
        
        viewOntologyTree.setOntologyTransferHandler(ontologyTransferHandler);

		//view for the database 
        viewDatabaseTree = new ViewDatabaseTree();
        frame.getContentPane().add(viewDatabaseTree, "cell 2 0 1 4,grow");

		//transferhandler between database view and the view to add a term to the mapping
        TransferHandler databaseTransferHandler = new DatabaseTreeToTextTransferHandler();
        
        viewDatabaseTree.setDatabaseTransferHandler(databaseTransferHandler);
       
        //model for the mapping term view
		MappingElement viewMappingModel = new MappingElement();

		//the view to add a term to the mapping
		viewAddNewMapping = new ViewAddMappingItem();
		viewAddNewMapping.setModel(viewMappingModel);
		viewAddNewMapping.setOntologyTransferHandler(ontologyTransferHandler);
        viewAddNewMapping.setDatabaseTransferHandler(databaseTransferHandler);
		viewAddNewMapping.setBorder(new LineBorder(Color.DARK_GRAY));
		viewAddNewMapping.setBackground(UIManager.getColor("EditorPane.background"));
		frame.getContentPane().add(viewAddNewMapping, "cell 1 1 1 2,grow");
		
		//model for the mapping
		MappingTable modelMappingTable = new MappingTable(new Mapping());
		
		//controller to add a term to the mapping
		ControllerAddNewMapping controlAddNewMapping = new ControllerAddNewMapping(modelMappingTable.getModel(), viewMappingModel);
		
		viewAddNewMapping.setController(controlAddNewMapping);

		//view for the mapping
		viewTableMapping = new ViewTableMapping();
		viewTableMapping.setModel(modelMappingTable);
		frame.getContentPane().add(viewTableMapping, "cell 1 3,grow");
		
		controlAddNewMapping.setViewMapping(viewTableMapping);
		controlAddNewMapping.setViewOntology(viewOntologyTree);
		
		//controller for the mapping
		ControllerMappingTable controlTableMapping = new ControllerMappingTable(modelMappingTable);
		
		viewTableMapping.setController(controlTableMapping);
		controlTableMapping.setView(viewTableMapping);
		controlTableMapping.setViewOntology(viewOntologyTree);

		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Creates the menu and all the menu items
	 */
	private void createMenuBar() {
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		createMenuOntology();	
		createMenuDatabase();
		createMenuMapping();
		createMenuHelp();

	}
	
	/**
	 * Creates the menu for open an ontology
	 */
	private void createMenuOntology() {
		
		JMenu menuOntology = new JMenu("Ontology");
		menuBar.add(menuOntology);
		
		//creates the menu item to open an ontology from an IRI and actualize the list of ontologies recently opened 
		JMenuItem menuItemOpenIRI = new JMenuItem("Open Ontology from IRI ...");
		menuItemOpenIRI.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				menuItemOpenOntologyIRIActionPerformed(e);
			}
		});
		menuOntology.add(menuItemOpenIRI);

		//creates the menu item to open an ontology from a file and actualize the list of ontologies recently opened 
		JMenuItem menuItemOpenOntologyFile = new JMenuItem("Open Ontology from File ...");
		menuItemOpenOntologyFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				menuItemOpenOntologyFileActionPerformed(e);
			}
		});
		menuOntology.add(menuItemOpenOntologyFile);
		
		JSeparator separatorMenuOntology = new JSeparator();
		menuOntology.add(separatorMenuOntology);
		
		createRecent(menuOntology);
		
	}

	/**
	 * creates a menu containig the list of ontologies recently opened
	 * 
	 * @param paramMenu
	 */
	private void createRecent(JMenu paramMenu) {
		
		menuRecent = new JMenu("Open Recent ...");
		paramMenu.add(menuRecent);
		
		//reads the lst of recent ontologies from the auxiliar XML file
		StaXListOntologySourcesParser importRecentParser = new StaXListOntologySourcesParser("ontology", "source","xmls/RecentOntologies.xml");
		
		JSeparator separatorRecent = new JSeparator();
		menuRecent.add(separatorRecent);

		JMenuItem clearRecent = new JMenuItem("Clear recent");
		clearRecent.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clearRecentActionPerformed();
			}
		});
		menuRecent.add(clearRecent);
		menuRecent.repaint();

		recentOntologies = new ArrayList<OntologySource>(0);
	    recentOntologies = importRecentParser.read();
	    
	    if (!recentOntologies.isEmpty()) {
	    	loadAllRecents();
	    }
	}

	/**
	 * Creates the menu item to load a database
	 */
	private void createMenuDatabase() {
		JMenu menuDatabase = new JMenu("Database");
		menuBar.add(menuDatabase);
		
		JMenuItem menuItemOpenDatabase = new JMenuItem("Open Database ...");
		menuItemOpenDatabase.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				menuItemOpenDatabaseActionPerformed(e);
			}
		});
		menuDatabase.add(menuItemOpenDatabase);
		
	}

	/**
	 * 
	 */
	private void createMenuMapping() {
		
		JMenu menuMapping = new JMenu("R2RML Mapping");
		menuBar.add(menuMapping);
		
	}

	/**
	 * 
	 */
	private void createMenuHelp() {
		
		JMenu menuHelp = new JMenu("Help");
		menuBar.add(menuHelp);
		
		JMenuItem menuItemHowTo = new JMenuItem("How to use ...");
		menuHelp.add(menuItemHowTo);
		
		JSeparator separatorMenuHelp = new JSeparator();
		menuHelp.add(separatorMenuHelp);
		
		JMenuItem menuItemAbout = new JMenuItem("About ...");
		menuHelp.add(menuItemAbout);
		
	}

	/**
	 * opens the ontology from the iri, ceates the model and loads the tree view
	 * @param e
	 */
	protected void menuItemOpenOntologyIRIActionPerformed(ActionEvent e) {
		
		openOntologyIRI = new OpenOntologyIRI(frame);
		openOntologyIRI.pack();
		openOntologyIRI.setLocationRelativeTo(frame);
		openOntologyIRI.setVisible(true);

		String iri = openOntologyIRI.getOntologyIRI();
		
		if (!iri.isEmpty()) {

			OntologySource source = new OntologySource();
			source.setSource(iri);
			addToRecents(source);
				
			try {
				viewOntologyTree.setOntologyModel(iri);
			} catch (OWLOntologyCreationException e1) {
				// TODO Auto-generated catch block
		    	JOptionPane.showMessageDialog(frame, "Unable to open the ontology", "Error loading ontology", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
		
		}
	}
	
	/**
	 * opens the ontology from the file, ceates the model and loads the tree view
	 * @param ae
	 */
	private void menuItemOpenOntologyFileActionPerformed(ActionEvent ae) {
	
		fileChooserOntology = new JFileChooser();
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Owl and RDF", "owl", "rdf", "owx");
		fileChooserOntology.setFileFilter(filter);
		
		int result = fileChooserOntology.showOpenDialog(frame);
		
		if (result == JFileChooser.APPROVE_OPTION) {
			
			String file = fileChooserOntology.getSelectedFile().getPath();

			OntologySource source = new OntologySource();
			source.setSource(file);
			addToRecents(source);
			
			try {
				viewOntologyTree.setOntologyModel(file);
			} catch (OWLOntologyCreationException e) {
				// TODO Auto-generated catch block
		    	JOptionPane.showMessageDialog(frame, "Unable to open the ontology", "Error loading ontology", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
			
		}
		
	}

	/**
	 * connects to the database, creates the model and loadas the tree view
	 * @param e
	 */
	protected void menuItemOpenDatabaseActionPerformed(ActionEvent e) {
		
		if (openDatabaseDialog == null) {		
			openDatabaseDialog = new OpenDatabaseDialog(frame);
			openDatabaseDialog.pack();
			openDatabaseDialog.setLocationRelativeTo(frame);
		}
		else {
			openDatabaseDialog.resetCancel();
			openDatabaseDialog.pack();
			openDatabaseDialog.setLocationRelativeTo(frame);
		}
		openDatabaseDialog.setVisible(true);
		
		if (!(openDatabaseDialog.checkCancel())) {
			Object[] dbDesc = new Object[6];
			dbDesc = openDatabaseDialog.getDatabaseDescription();
//			System.out.println("DBdesc " + dbDesc[0] + ", " + dbDesc[1] +
//						", " + dbDesc[2] +", " + dbDesc[3] +", " + dbDesc[4] +", " + dbDesc[5]);
			
			String dbms = (String) dbDesc[0];
			String name = (String) dbDesc[1];
			InetAddress adress = (InetAddress) dbDesc[2];
			int port = (int) dbDesc[3];
			String userName = (String) dbDesc[4];
			String password = (String) dbDesc[5];
			
			try {
				viewDatabaseTree.setDatabaseModel(dbms, name, adress, port, userName, password);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
		    	JOptionPane.showMessageDialog(frame, "Unable to connect to the database", "Error connecting to database", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}

		}

	}

	/**
	 * Clear the list of recently opened ontologies
	 */
	protected void clearRecentActionPerformed() {
		
		recentOntologies.clear();
		System.out.println("clearRecent " + recentOntologies);
		int posSep = menuRecent.getMenuComponentCount() - 2;

		for (int i = 0 ; i < posSep ; i++) {
			menuRecent.remove(0);
		}
		menuRecent.repaint();
		writeRecents();

	}

	/**
	 * reloads the list recent ontologies 
	 */
	private void loadAllRecents() {
		
		int posSep = menuRecent.getMenuComponentCount() - 2;
		
		for (int i = 0 ; i < posSep ; i++) {
			menuRecent.remove(0);
		}
		//System.out.println("loadAllRecents " + recentOntologies);
    	for (OntologySource recentSource: recentOntologies) {
    		JMenuItem menuItemRecent = new JMenuItem(recentSource.getSource());
    		//int pos = menuRecent.getMenuComponentCount() - 2;
    		//menuRecent.add(menuItemRecent, pos);
    		menuItemRecent.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					openRecentOntologySource(e);				
				}
			});
    		menuRecent.add(menuItemRecent, 0);
    	}
    	writeRecents();
		menuRecent.repaint();
	}

	/**
	 * ad the ontology source to the list of sources of ontologies recently openened
	 * @param paramSource
	 */
	private void addToRecents(OntologySource paramSource) { 
	//	System.out.println("addToRecents " + recentOntologies);
		if (recentOntologies.contains(paramSource)) {
			recentOntologies.remove(paramSource);
		//	System.out.println("addToRecents " + recentOntologies);
			recentOntologies.add(paramSource);
		//	System.out.println("addToRecents " + recentOntologies);
			loadAllRecents();
		//	System.out.println("La fuente ya estaba en la lista " + paramSource.getSource());
		} else {
			recentOntologies.add(paramSource);
			JMenuItem menuItemRecent = new JMenuItem(paramSource.getSource());
			//TODO establecer una clase action que abra la ontologia que obtenga del evento para todos lo botones
			// de la barra de menus recent
			//int pos = menuRecent.getMenuComponentCount() - 2;
			menuItemRecent.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					openRecentOntologySource(e);			
				}
			});
			menuRecent.add(menuItemRecent, 0);
			writeRecents();
		//	System.out.println("Es un origen nuevo " + paramSource.getSource());
		}
		
	}

	/**
	 * opens an ontology from the menu of ontologies recently opened
	 * @param e
	 */
	protected void openRecentOntologySource(ActionEvent e) {
		JMenuItem jMenuItemSource = (JMenuItem) e.getSource();
		String ontologySource = jMenuItemSource.getText();
		
		OntologySource source = new OntologySource();
		source.setSource(ontologySource);
		addToRecents(source);
		
		try {
			viewOntologyTree.setOntologyModel(ontologySource);
		} catch (OWLOntologyCreationException oe) {
			// TODO Auto-generated catch block
	    	JOptionPane.showMessageDialog(frame, "Unable to open the ontology", "Error loading ontology", JOptionPane.ERROR_MESSAGE);	
			oe.printStackTrace();
		}

	}
	
	/**
	 * writes the list of ontologies recently opened into an auxiliar XML file
	 */
	private void writeRecents() {
		
		recentsWriter = new StaXListOntologySourcesWriter("recents", "source","xmls/RecentOntologies.xml");
		
		int recentSize = recentOntologies.size();

		ArrayList<String> recents = new ArrayList<>(recentSize);
		
	    for (int i = 0; i < recentSize; i++) {

	    	recents.add(recentOntologies.get(i).getSource());
	    	
	    }
		
	    try {
			recentsWriter.save(recents);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
