/*
 * Copyright 2015 Manuel Fern�ndez P�rez
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
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.menu.bookmarks.OntologySource;
import model.r2rmlmapping.R2RMLMapping;
import model.r2rmlmapping.triplesMap.TriplesMap;
import net.miginfocom.swing.MigLayout;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import view.database.ViewDatabaseTree;
import view.menu.database.OpenDatabaseDialog;
import view.menu.ontology.OpenOntologyIRI;
import view.ontology.ViewOntology;
import view.r2rmlMapping.R2RMLMapBaseIRISelector;
import view.r2rmlMapping.TriplesMapSelector;
import view.r2rmlMapping.ViewR2RMLMapping;
import view.triplesMap.TriplesMapTableSelector;
import view.triplesMap.ViewTriplesMap;
import view.util.PreferencesMediator;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import control.r2rmlmapping.JenaModelToR2RMLModelTransformer;
import control.r2rmlmapping.R2RMLModelToJenaModelTransformer;
import control.r2rmlmapping.triplesMap.ControllerTriplesMap;

/**
 * Main view
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class R2RMLMain {

	private JFrame frame;
	private JMenuBar menuBar;
	private PreferencesMediator prefMediator;
	private OpenDatabaseDialog openDatabaseDialog;
	private OpenOntologyIRI openOntologyIRI;
	private TriplesMapTableSelector openTriplesMapTableSelector;
	private R2RMLMapBaseIRISelector openR2RMLMapBaseIRISelector;
	private TriplesMapSelector triplesMapSelector; 
	private JFileChooser fileChooserOntology;
	private List<OntologySource> recentOntologies;
	private JMenu menuRecent;
	private ViewTriplesMap viewTriplesMap;
	private ViewOntology viewOntology;
	private ViewDatabaseTree viewDatabase;
	private R2RMLMapping r2rmlMappingModel;
	private TriplesMap triplesMapModel;
	private ControllerTriplesMap triplesMapController;
	private ViewR2RMLMapping viewR2RMLMapping;
	
	private Boolean ontologyLoaded = false;
	private Boolean databaseLoaded = false;
	private Boolean r2rmlMapingLoaded = false;
	
	public static Boolean showLabels = false;   
	
	private static Logger logger = LoggerFactory.getLogger(R2RMLMain.class);
	
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
		frame.getContentPane().setLayout(new MigLayout("", "[300.00,grow][656.00,grow][300.00,grow]", "[83.00,grow,center][grow][345.00][550.00,grow][]"));

		prefMediator = new PreferencesMediator();
		
		//creates the menu
		createMenuBar();
		
		//view for the ontologyv (the model is declared in the view)
		viewOntology = new ViewOntology(frame);
		frame.getContentPane().add(viewOntology, "cell 0 0 1 4,grow");

		//view for the database (the model is declared in the view)
        viewDatabase = new ViewDatabaseTree(frame);
        frame.getContentPane().add(viewDatabase, "cell 2 0 1 4,grow");
 
		//the view to add a triples map to the r2rml mapping
        viewTriplesMap = new ViewTriplesMap(frame);

        viewTriplesMap.setBorder(new LineBorder(Color.DARK_GRAY));
        viewTriplesMap.setBackground(UIManager.getColor("EditorPane.background"));
		frame.getContentPane().add(viewTriplesMap, "cell 1 0 1 3,grow");
        
		//the view for the r2rml mapping
		viewR2RMLMapping = new ViewR2RMLMapping();
		frame.getContentPane().add(viewR2RMLMapping, "cell 1 3 ,grow");
		
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
		
		JSeparator separatorFragmentsLabels = new JSeparator();
		menuOntology.add(separatorFragmentsLabels);
		
		//creates the menu item to open an ontology from a file and actualize the list of ontologies recently opened 
		JMenuItem menuItemFragmentsLabels = new JMenuItem("Show Fragments/Labels");
		
		menuItemFragmentsLabels.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				menuItemFragmentsLabelsActionPerformed(e);
			}
		});
		
		menuOntology.add(menuItemFragmentsLabels);
		
	}

	/**
	 * creates a menu containig the list of ontologies recently opened
	 * 
	 * @param paramMenu
	 */
	private void createRecent(JMenu paramMenu) {
		
		menuRecent = new JMenu("Open Recent ...");
		paramMenu.add(menuRecent);
		
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
	    recentOntologies = prefMediator.getRecents();
	    
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
		
		JMenuItem menuItemNewR2RMLMapping = new JMenuItem("New R2RML Mapping");
		menuItemNewR2RMLMapping.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				menuItemNewR2RMLMappingActionPerformed(e);
				
			}
		});

		menuMapping.add(menuItemNewR2RMLMapping);
		
		JMenuItem menuItemOpenR2RMLMapping = new JMenuItem("Open R2RML Mapping ...");
		menuItemOpenR2RMLMapping.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				menuItemLoadR2RMLMappingActionPerformed(e);
				
			}
		});
		
		menuMapping.add(menuItemOpenR2RMLMapping);
		
		JMenuItem menuItemSaveR2RMLMapping = new JMenuItem("Save R2RML Mapping ...");
		menuItemSaveR2RMLMapping.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				menuItemSaveR2RMLMappingActionPerformed(e);
				
			}
		});
		
		menuMapping.add(menuItemSaveR2RMLMapping);
		
		JSeparator separatorR2RMLFromTriples = new JSeparator();
		menuMapping.add(separatorR2RMLFromTriples);
		
		JMenuItem menuItemNewTriplesMap = new JMenuItem("New Triples Map");
		menuItemNewTriplesMap.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				menuItemNewTriplesMapActionPerformed(e);
				
			}
		});
		
		menuMapping.add(menuItemNewTriplesMap);
		
		JMenuItem menuItemEditTriplesMap = new JMenuItem("Edit Triples Map ...");
		menuItemEditTriplesMap.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				menuItemEditTriplesMapActionPerformed(e);
				
			}
		});
		
		menuMapping.add(menuItemEditTriplesMap);
		
		JMenuItem menuItemDeleteTriplesMap = new JMenuItem("Delete Triples Map ...");
		menuItemDeleteTriplesMap.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				menuItemDeleteTriplesMapActionPerformed(e);
				
			}
		});
		
		menuMapping.add(menuItemDeleteTriplesMap);
		
	}

	/**
	 * 
	 */
	private void createMenuHelp() {
		
		JMenu menuHelp = new JMenu("Help");
		menuBar.add(menuHelp);
		
		JMenuItem menuItemManual = new JMenuItem("How to use ...");
		menuItemManual.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				menuItemManualActionPerformed(e);
				
			}
		});
		menuHelp.add(menuItemManual);
		
		JSeparator separatorMenuHelp = new JSeparator();
		menuHelp.add(separatorMenuHelp);
		
		JMenuItem menuItemAbout = new JMenuItem("About ...");
		menuItemAbout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				menuItemAboutActionPerformed(e);
				
			}
		});
		menuHelp.add(menuItemAbout);
		
	}

	/**
	 * opens the ontology from the iri, ceates the model and loads the tree view
	 * @param e
	 */
	protected void menuItemOpenOntologyIRIActionPerformed(ActionEvent e) {
		
		openOntologyIRI = new OpenOntologyIRI(frame, prefMediator);;
		openOntologyIRI.pack();
		openOntologyIRI.setLocationRelativeTo(frame);
		openOntologyIRI.setVisible(true);

		String iri = openOntologyIRI.getOntologyIRI();
		
		if (!iri.isEmpty()) {

			OntologySource source = new OntologySource();
			source.setSource(iri);
			addToRecents(source);
				
			try {
				
				viewOntology.setOntologyModel(iri);
				ontologyLoaded = true;
				
				if (databaseLoaded) {

					createR2RMLMap();	
				
				}
				
			} catch (OWLOntologyCreationException e1) {
				
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
	
		fileChooserOntology = new JFileChooser(prefMediator.getMostRecentInputOntologyFilePathVal());
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Owl and RDF", "owl", "rdf", "owx");
		fileChooserOntology.setFileFilter(filter);
		int result = fileChooserOntology.showOpenDialog(frame);
		
		if (result == JFileChooser.APPROVE_OPTION) {
			
			String file = fileChooserOntology.getSelectedFile().getPath();

			prefMediator.setMostRecentInputOntologyFilePathVal(file);
			
			OntologySource source = new OntologySource();
			source.setSource(file);
			addToRecents(source);
			
			try {
				
				viewOntology.setOntologyModel(file);
				ontologyLoaded = true;
				
				if (databaseLoaded) {

					createR2RMLMap();	
				
				}

			} catch (OWLOntologyCreationException e) {
				
		    	JOptionPane.showMessageDialog(frame, "Unable to open the ontology", "Error loading ontology", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			
			}
			
		}
		
	}
	
	/**
	 * changes the method to show the ontology elements in the views
	 * @param ae
	 */
	private void menuItemFragmentsLabelsActionPerformed(ActionEvent ae) {

		showLabels = !showLabels;
		logger.trace("show labels: " + showLabels);
		
		viewOntology.changeLabelsFragments(showLabels);
		
		if (triplesMapModel != null) {
			
			triplesMapModel.changeLabelsFragments(showLabels);
			
	        triplesMapController = new ControllerTriplesMap(viewTriplesMap, triplesMapModel);

	        viewTriplesMap.setTriplesMapModel(triplesMapModel);
	        viewTriplesMap.setController(triplesMapController);
	        viewTriplesMap.repaint();
	        viewTriplesMap.revalidate();
	        viewTriplesMap.updateUI();

			//model mappingElement for the ontology tree action listener
			viewOntology.setMappingItem(triplesMapModel);
			
			//model mappingElement for the database tree action listener
			viewDatabase.setMappingItem(triplesMapModel);
			
		}

		
	}
	
	/**
	 * connects to the database, creates the model and loadas the tree view
	 * @param e
	 */
	protected void menuItemOpenDatabaseActionPerformed(ActionEvent e) {
		
		if (openDatabaseDialog == null) {
			
			openDatabaseDialog = new OpenDatabaseDialog(frame, prefMediator);
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

	    	logger.trace("DBdesc " + dbDesc[0] + ", " + dbDesc[1] +
					", " + dbDesc[2] +", " + dbDesc[3] +", " + dbDesc[4] +", " + dbDesc[5]);
	    	
			String dbms = (String) dbDesc[0];
			String name = (String) dbDesc[1];
			InetAddress adress = (InetAddress) dbDesc[2];
			Integer port = (Integer) dbDesc[3];
			String userName = (String) dbDesc[4];
			String password = (String) dbDesc[5];
			
			try {
				
				viewDatabase.setDatabaseModel(dbms, name, adress, port, userName, password);
				databaseLoaded = true;
				
				if (ontologyLoaded) {

					createR2RMLMap();
					
				}
				
			} catch (Exception e1) {
				
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
		logger.trace("clearRecent {}", recentOntologies);
		int posSep = menuRecent.getMenuComponentCount() - 2;

		for (int i = 0 ; i < posSep ; i++) {
			
			menuRecent.remove(0);
		}
		
		menuRecent.repaint();
		writeRecents();

	}


	/**
	 * reloads the list of recent ontologies 
	 */
	private void loadAllRecents() {
		
		int posSep = menuRecent.getMenuComponentCount() - 2;
		
		for (int i = 0 ; i < posSep ; i++) {
			
			menuRecent.remove(0);

		}

    	for (OntologySource recentSource: recentOntologies) {
    		
    		JMenuItem menuItemRecent = new JMenuItem(recentSource.getSource());
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

		if (recentOntologies.contains(paramSource)) {
			
			recentOntologies.remove(paramSource);
			recentOntologies.add(paramSource);
			loadAllRecents();

		} else {
			
			recentOntologies.add(paramSource);
			JMenuItem menuItemRecent = new JMenuItem(paramSource.getSource());
			//TODO establecer una clase action que abra la ontologia que obtenga del evento para todos lo botones
			// de la barra de menus recent
			menuItemRecent.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					openRecentOntologySource(e);			
				}				
			});
			
			menuRecent.add(menuItemRecent, 0);
			writeRecents();

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
			
			viewOntology.setOntologyModel(ontologySource);
			ontologyLoaded = true;
			
			if (databaseLoaded) {

				createR2RMLMap();	
			
			}		
		} catch (OWLOntologyCreationException oe) {
			
	    	JOptionPane.showMessageDialog(frame, "Unable to open the ontology", "Error loading ontology", JOptionPane.ERROR_MESSAGE);	
			oe.printStackTrace();

		}

	}
	
	/**
	 * writes the list of ontologies recently opened into an auxiliar XML file
	 */
	private void writeRecents() {
		
	    try {
	    	
			prefMediator.setRecents(recentOntologies);
			logger.debug(recentOntologies.toString());
	
		} catch (Exception e) {
			
			e.printStackTrace();

		}
	
	}

	/**
	 * @param e
	 */
	protected void menuItemNewR2RMLMappingActionPerformed(ActionEvent e) {

		createR2RMLMap();
        
	}

	/**
	 * @param e
	 */
	protected void menuItemLoadR2RMLMappingActionPerformed(ActionEvent e) {

		JFileChooser fileChooserLoadMapping = new JFileChooser(prefMediator.getMostRecentInputR2RMLMappingFilePathVal());
		
		int result = fileChooserLoadMapping.showOpenDialog(frame);
		
		String sourceFile = "";
		
		if (result == JFileChooser.APPROVE_OPTION) {
			
			sourceFile = fileChooserLoadMapping.getSelectedFile().getPath();
			
			prefMediator.setMostRecentInputR2RMLMappingFilePathVal(sourceFile);
			
	        logger.trace("Archivo rdf con el r2rmlmap es: " + sourceFile);
			
		}
		
		//creates an empty jena model and reads the rdf file
		Path input = Paths.get(sourceFile);
		Model jenaModel = ModelFactory.createDefaultModel() ; 
		jenaModel.read(input.toUri().toString(), null, "TURTLE") ;

		logger.trace("Aqui se escribe el modelo leido del archivo");
		jenaModel.write(System.out, "Turtle");
		logger.trace("Fin del modelo");
		
        logger.trace("Archivo rdf con el r2rmlmap leido");

		if (ontologyLoaded && databaseLoaded) {
			
			JenaModelToR2RMLModelTransformer jenaTransformer = new JenaModelToR2RMLModelTransformer(jenaModel, viewDatabase.getDatabase(), viewOntology.getOntologyModel());
			
	        try {
	        	
				r2rmlMappingModel = jenaTransformer.getR2RMLMapping();
				r2rmlMapingLoaded = true;
				
				logger.trace("Leido el modelo del mapping R2RML");
				
				//update the view model for the r2rml mapping
				viewR2RMLMapping.setModel(r2rmlMappingModel);
				logger.trace("Aqui se deberia haber cargado el modelo del map leido");
				
			} catch (Exception e1) {
				
				e1.printStackTrace();
				
			}
				
		}
		else {
			
	    	JOptionPane.showMessageDialog(frame, "There is no database open", "Error loading mapping", JOptionPane.ERROR_MESSAGE);
		
		}

	}
	
	/**
	 * @param e
	 */
	protected void menuItemSaveR2RMLMappingActionPerformed(ActionEvent e) {

		JFileChooser fileChooserSaveMapping = new JFileChooser(prefMediator.getMostRecentOutputR2RMLMappingFilePathVal());
		int result = fileChooserSaveMapping.showOpenDialog(frame);
		String file = "";
		
		if (result == JFileChooser.APPROVE_OPTION) {
			
			file = fileChooserSaveMapping.getSelectedFile().getPath();
			prefMediator.setMostRecentInputR2RMLMappingFilePathVal(file);

		}
		
		R2RMLModelToJenaModelTransformer r2rmlTransformer = new R2RMLModelToJenaModelTransformer(r2rmlMappingModel);
		
/*		try {
			
			r2rmlTransformer.writeR2RMLMappingToFile(file);
			
		} catch (FileNotFoundException e1) {

	    	JOptionPane.showMessageDialog(frame, "Unable to create the file", "Error saving mapping", JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
			
		}*/
		
		try {
			
			r2rmlTransformer.otherWrite(file);
			
		} catch (IOException e1) {

	    	JOptionPane.showMessageDialog(frame, "Unable to create the file", "Error saving mapping", JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
			
		}
		
	}

	/**
	 * @param e
	 */
	protected void menuItemNewTriplesMapActionPerformed(ActionEvent e) {
		
		createTriplesMap();
		
	}

	/**
	 * @param e
	 */
	protected void menuItemEditTriplesMapActionPerformed(ActionEvent e) {
		
		if (ontologyLoaded && databaseLoaded && r2rmlMapingLoaded) {
			
			if (r2rmlMappingModel.getAllTriplesMap().size() > 0) {
				
				triplesMapSelector = new TriplesMapSelector(frame, r2rmlMappingModel.getAllTriplesMap().size());
				triplesMapSelector.pack();
				triplesMapSelector.setLocationRelativeTo(frame);
				triplesMapSelector.setVisible(true);
				
				if (!triplesMapSelector.checkCancel()) {
					
					int triplesMapIndex = triplesMapSelector.getTriplesMapSelected();
					triplesMapModel = r2rmlMappingModel.getTriplesMap(triplesMapIndex);
			        triplesMapController = new ControllerTriplesMap(viewTriplesMap, triplesMapModel);

			        viewTriplesMap.setTriplesMapModel(triplesMapModel);
			        viewTriplesMap.setController(triplesMapController);
			        viewTriplesMap.repaint();
			        viewTriplesMap.revalidate();
			        viewTriplesMap.updateUI();
			        
					//model mappingElement for the ontology tree action listener
					viewOntology.setMappingItem(triplesMapModel);
					
					//model mappingElement for the database tree action listener
					viewDatabase.setMappingItem(triplesMapModel);
					
				}
				
			}
			
			else {
				
				JOptionPane.showMessageDialog(frame, "The R2RML Map has not triples map to edit", "Warning editing triples map", JOptionPane.WARNING_MESSAGE);
			
			}
			
		}
		
	}

	/**
	 * @param e
	 */
	protected void menuItemDeleteTriplesMapActionPerformed(ActionEvent e) {
		
		if (ontologyLoaded && databaseLoaded && r2rmlMapingLoaded) {
			
			if (r2rmlMappingModel.getAllTriplesMap().size() > 0) {
				
				triplesMapSelector = new TriplesMapSelector(frame, r2rmlMappingModel.getAllTriplesMap().size());
				triplesMapSelector.pack();
				triplesMapSelector.setLocationRelativeTo(frame);
				triplesMapSelector.setVisible(true);
				
				if (!triplesMapSelector.checkCancel()) {
					int triplesMapIndex = triplesMapSelector.getTriplesMapSelected();
					r2rmlMappingModel.removeTriplesMapAt(triplesMapIndex, frame);
				}

			}
			
			else {
				
				JOptionPane.showMessageDialog(frame, "The R2RML Map has not triples map to delete", "Warning deleting triples map", JOptionPane.WARNING_MESSAGE);
			
			}

		}
		
	}

	/**
	 * 
	 */
	private void createTriplesMap() {
	
		if (ontologyLoaded && databaseLoaded && r2rmlMapingLoaded) {
			
			openTriplesMapTableSelector = new TriplesMapTableSelector(frame, viewDatabase.getDatabase().getTables());
			openTriplesMapTableSelector.pack();
			openTriplesMapTableSelector.setLocationRelativeTo(frame);
			openTriplesMapTableSelector.setVisible(true);

			if (!openTriplesMapTableSelector.checkCancel()) {

		        triplesMapModel = new TriplesMap(r2rmlMappingModel, openTriplesMapTableSelector.getTable());
		        
		        logger.trace("Creado el modelo del triples map");

		        triplesMapController = new ControllerTriplesMap(viewTriplesMap, triplesMapModel);

		        viewTriplesMap.setTriplesMapModel(triplesMapModel);
		        viewTriplesMap.setController(triplesMapController);
		        viewTriplesMap.repaint();
		        viewTriplesMap.revalidate();
		        viewTriplesMap.updateUI();

				//model mappingElement for the ontology tree action listener
				viewOntology.setMappingItem(triplesMapModel);
				
				//model mappingElement for the database tree action listener
				viewDatabase.setMappingItem(triplesMapModel);
				
			}
			
		}
		else {
			
			JOptionPane.showMessageDialog(frame, "Cannot create a triples map outside a R2RML map", "Warning creating triples map", JOptionPane.WARNING_MESSAGE);
			logger.trace("Aun no se puede crear triples Map");
			
		}
		
	}
	
	/**
	 * 
	 */
	private void  createR2RMLMap() {
		
		if (ontologyLoaded && databaseLoaded) {
			
	        //model for the R2RML mapping
	        r2rmlMappingModel = new R2RMLMapping();
			
			openR2RMLMapBaseIRISelector = new R2RMLMapBaseIRISelector(frame);
			openR2RMLMapBaseIRISelector.pack();
			openR2RMLMapBaseIRISelector.setLocationRelativeTo(frame);
			openR2RMLMapBaseIRISelector.setVisible(true);
			
			if (!openR2RMLMapBaseIRISelector.checkCancel()) {
				
				r2rmlMappingModel.setBaseIRI(openR2RMLMapBaseIRISelector.getSelectedBaseIRI());
				logger.trace("El nameSpace es " + r2rmlMappingModel.getBaseIRI());
				
			}
			
	        r2rmlMapingLoaded = true;
	        logger.trace("Creado el modelo del mapping R2RML");
			viewR2RMLMapping.setModel(r2rmlMappingModel);
			createTriplesMap();
			
		}
		else {
			
			JOptionPane.showMessageDialog(frame, "Cannot create a R2RML map whitout ontology and/or database", "Warning creating R2RML map", JOptionPane.WARNING_MESSAGE);
			logger.trace("Aun no se puede crea R2RML Map");
			
		}
		
	}

	/**
	 * @param e
	 */
	protected void menuItemAboutActionPerformed(ActionEvent e) {

		JOptionPane.showMessageDialog(frame, "R2RML Visor v1.00 /n"
				+ "R2RML Visor is an aplication developed as Proyecto Fin de Carrera"
				+ "Autor Manuel Fernandez Perez"
				+ "Tutor Oscar Corcho"
				+ "At Escuela Superior de Ingenieros Informaticos"
				+ "UPM Universidad Politecnica de Madrid", "About", JOptionPane.INFORMATION_MESSAGE);
		
	}

	/**
	 * @param e
	 */
	protected void menuItemManualActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
