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

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.database.Column;
import model.r2rmlmapping.R2RMLMapping;
import model.r2rmlmapping.triplesMap.ColumnValueObjectMap;
import model.r2rmlmapping.triplesMap.JoinCondition;
import model.r2rmlmapping.triplesMap.ObjectMap;
import model.r2rmlmapping.triplesMap.PredicateMap;
import model.r2rmlmapping.triplesMap.PredicateObjectMap;
import model.r2rmlmapping.triplesMap.ReferencingObjectMap;
import model.r2rmlmapping.triplesMap.SubjectMap;
import model.r2rmlmapping.triplesMap.TriplesMap;
import net.miginfocom.swing.MigLayout;

public class ViewR2RMLMapping extends JPanel implements Observer {

	/**
	 * View of the R2RML mapping
	 * 
	 * @author Manuel Fernandez Perez
	 * 
	 */
	private static final long serialVersionUID = 6428162559031790675L;

	private JTextArea textArea;
	private JScrollPane textAreaScrollPane;
	private R2RMLMapping r2rmlMapping;
	private TriplesMap triplesMapAux;
	private SubjectMap subjectMapAux;
	
	private static Logger logger = LoggerFactory.getLogger(ViewR2RMLMapping.class);
	
	/**
	 * Create the panel.
	 */
	public ViewR2RMLMapping() {
		setLayout(new MigLayout("", "[111px,grow]", "[][48px,grow]"));
		
//		JButton buttonDeleteTriplesMap = new JButton("Delete TripleMap");
//		buttonDeleteTriplesMap.setName("Delete TripleMap");
//		add(buttonDeleteTriplesMap, "cell 0 0,alignx trailing");

		textAreaScrollPane = new JScrollPane();
		add(textAreaScrollPane, "cell 0 1,grow");
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textAreaScrollPane.setViewportView(textArea);
		
	}

	/**
	 * @param paramR2rmlMapping
	 */
	public void setModel (R2RMLMapping paramR2rmlMapping) {
		
		r2rmlMapping = paramR2rmlMapping;
		r2rmlMapping.addObserver(this);
		
		setTextAreaData();

	}
	
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		
		logger.trace("Disparado update de R2RMLMAPPING");
		setTextAreaData();

	}
	
	private void setTextAreaData() {
		
		textArea.setText(null);
        textArea.append("Numero de TriplesMap: ");
		int triplesMapCounter = r2rmlMapping.getIdentifierCounter();
		logger.trace("El r2rml map tiene triples maps " + r2rmlMapping.hasTriplesMap().toString());
		logger.trace("Numero de triples maps el r2rml map " + r2rmlMapping.getIdentifierCounter());
		textArea.append(String.valueOf(triplesMapCounter) + "\n");
		textArea.append("\n");
		
		if (r2rmlMapping.hasTriplesMap()) {
			
			for (int i = 0; i < triplesMapCounter; i++) {
				
				triplesMapAux = r2rmlMapping.getTriplesMap(i);
				textArea.append("Triples Map " + i + "\n");
				textArea.append("    logicalTable    " + triplesMapAux.getLogicalTable().getTableName());
				textArea.append("\n");
				subjectMapAux = triplesMapAux.getSubjectMap();
				textArea.append("    subjectMap [\n");
				textArea.append("        template    " + subjectMapAux.getSubject());
				if (subjectMapAux.getRdfClass().getIRIClassIRI() != "") {
					
					textArea.append("\n");
					textArea.append("        class    " + subjectMapAux.getRdfClass().getIRIClassIRI());
					
				}
				textArea.append("\n");
				textArea.append("    ];\n");
				logger.trace("ViewR2RMLMapping --> Imprimido sujeto en text area");
		        int sizePredicateObjectMaps = triplesMapAux.getPredicateObjectMaps().size();
		        logger.trace("ViewR2RMLMapping --> numero de pred obj maps " + sizePredicateObjectMaps);
		        
		        if (sizePredicateObjectMaps > 0) {
		        	
		        	ArrayList<PredicateObjectMap> predicateObjects = triplesMapAux.getPredicateObjectMaps();
		        	
		        	for (PredicateObjectMap predicateObject : predicateObjects) {
		        		
						textArea.append("    predicateObjectMap [\n");

		        		int predicatesSize = predicateObject.getPredicateMaps().size();
		        		
		        		if (predicatesSize > 0)  {
		        			
		        			ArrayList<PredicateMap> predicates = predicateObject.getPredicateMaps();
		        			
			        		for (PredicateMap predicate : predicates) {
			        			
								textArea.append("        predicate    " + predicate.getPredicateIRI().getIRIClassIRI() + "\n");
								
			        		}
			        		
		        		}

		        		int ObjectsSize = predicateObject.getObjectMaps().size();
		        		logger.trace("ViewR2RMLMapping --> numero de obj maps " + ObjectsSize);
		        		
		        		if (ObjectsSize > 0)  {
		        			
		        			ArrayList<ObjectMap> objects = predicateObject.getObjectMaps();
		        			
			        		for (ObjectMap object : objects) {
								
			        			if (object.getType().equals("Column-Valued")) {
			        				
									textArea.append("        objectMap    ");
			        				
			        				ColumnValueObjectMap columnVal = (ColumnValueObjectMap) object;
			        				ArrayList<Column> cols =  columnVal.getObjectColumns();
			        				
									textArea.append("    column    ");
			        				
			        				for (Column col : cols) {
			        					
										textArea.append(" " + "\"" + col.getColumnName() + "\"");
										
			        				}
			        				
			        				textArea.append("\n");
			        				
			        			}
			        			else {
			        				
									textArea.append("        objectMap [\n");
			        				logger.trace("ViewR2RMLMapping --> tipo de obj map " + object.getType());
			        				ReferencingObjectMap ref = (ReferencingObjectMap) object;
									textArea.append("            parentTriplesMap  TriplesMap" + ref.getParentTriplesMap().getIdentifier() + "\n");
									//textArea.append("Logical table Triples Map " + ref.toString());//getParentTriplesMap().getLogicalTable().getTableName() + "\n");
									ArrayList<JoinCondition> jConds = ref.getJoinConditions();
									
									for (JoinCondition jCond : jConds) {
										
										textArea.append("            joinCondition [\n");	
										textArea.append("                parent    " + jCond.getParent().getColumnName() + "\n");
										textArea.append("                child    " + jCond.getChild().getColumnName() + "\n");
										textArea.append("            ]\n");
										
									}
									
									textArea.append("        ]\n");
									
			        			}
			        			
			        		}
			        		
		        		}
		        		
						textArea.append("    ];\n");
		        		
		        	}
		        	
		        }
		        
				textArea.append("\n");
				
			}
		}
		else {
			
	        logger.trace(r2rmlMapping.hasTriplesMap().toString());
	        logger.trace(String.valueOf(r2rmlMapping.getIdentifierCounter()));
	        
		}
		
	}

}
