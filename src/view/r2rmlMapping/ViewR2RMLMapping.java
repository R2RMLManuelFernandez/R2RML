package view.r2rmlMapping;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.database.Column;
import model.r2rmlmapping.R2RMLMapping;
import model.r2rmlmapping.triplesMap.ColumnValueObjectMap;
import model.r2rmlmapping.triplesMap.JoinCondition;
import model.r2rmlmapping.triplesMap.ObjectMap;
import model.r2rmlmapping.triplesMap.PredicateMap;
import model.r2rmlmapping.triplesMap.PredicateObjectMap;
import model.r2rmlmapping.triplesMap.ReferenceObjectMap;
import model.r2rmlmapping.triplesMap.SubjectMap;
import model.r2rmlmapping.triplesMap.TriplesMap;
import net.miginfocom.swing.MigLayout;

public class ViewR2RMLMapping extends JPanel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6428162559031790675L;

	private JTextArea textArea;
	private JScrollPane textAreaScrollPane;
	private R2RMLMapping r2rmlMapping;
	private TriplesMap triplesMapAux;
	private SubjectMap subjectMapAux;
	
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
		
		textArea.setText(null);
		textArea.append("Numero de TriplesMap: ");
		int triplesMapCounter = r2rmlMapping.getIdentifierCounter();
		textArea.append(String.valueOf(triplesMapCounter) + "\n");
		
		if (r2rmlMapping.hasTriplesMap()) {
			System.out.println("ViewR2RMLMapping --> Tiene triplesMap " + r2rmlMapping.hasTriplesMap().toString());
			for (int i = 0; i < triplesMapCounter; i++) {
				triplesMapAux = r2rmlMapping.getTriplesMap(i);
				subjectMapAux = triplesMapAux.getSubjectMap();
				textArea.append("Triples Map\n");
				textArea.append(subjectMapAux.getSubject());
				textArea.append("\n");
				textArea.append(subjectMapAux.getRdfClass());
				textArea.append("\n");
		        System.out.println("ViewR2RMLMapping --> Imprimido sujeto en text area");
		        int sizePredicateObjectMaps = triplesMapAux.getPredicateObjectMaps().size();
		        if (sizePredicateObjectMaps > 0) {
		        	ArrayList<PredicateObjectMap> predicateObjects = triplesMapAux.getPredicateObjectMaps();
		        	for (PredicateObjectMap predicateObject : predicateObjects) {
						textArea.append("\n");
		        		int predicatesSize = predicateObject.getPredicateMaps().size();
		        		if (predicatesSize > 0)  {
							textArea.append("Predicate    ");
		        			ArrayList<PredicateMap> predicates = predicateObject.getPredicateMaps();
			        		for (PredicateMap predicate : predicates) {
								textArea.append(predicate.getPredicateIRI() + "\n");
			        		}
		        		}

		        		int ObjectsSize = predicateObject.getObjectMaps().size();
		        		if (ObjectsSize > 0)  {
		        			ArrayList<ObjectMap> objects = predicateObject.getObjectMaps();
			        		for (ObjectMap object : objects) {
								textArea.append("Object    ");
			        			if (object.getType().equals("Column-Valued")) {
			        				ColumnValueObjectMap columnVal = (ColumnValueObjectMap) object;
			        				ArrayList<Column> cols =  columnVal.getObjectColumns();
			        				for (Column col : cols) {
										textArea.append(col.getColumnName() + "\n");
			        				}
			        			}
			        			else {
			        				ReferenceObjectMap ref = (ReferenceObjectMap) object;
									textArea.append("Parent Triples Map " + ref.getParentTriplesMap().getIdentifier() + "\n");
									ArrayList<JoinCondition> jConds = ref.getJoinConditions();
									for (JoinCondition jCond : jConds) {
										textArea.append("JoinCondition/n");	
										textArea.append("Parent " + jCond.getParent().getColumnName() + "/n");
										textArea.append("Child " + jCond.getChild().getColumnName() + "/n");	
									}
			        			}
			        			
			        		}
		        		}
		        	}
		        }
				textArea.append("\n");
			}
		}
		else {
	        System.out.println("ViewR2RMLMapping --> " + r2rmlMapping.hasTriplesMap().toString());
	        System.out.println("ViewR2RMLMapping --> " + String.valueOf(r2rmlMapping.getIdentifierCounter()));
		}
	}
	
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		
        System.out.println("ViewR2RMLMapping --> Disparado update de R2RMLMAPPING");
		textArea.setText(null);
        textArea.append("Numero de TriplesMap: ");
		int triplesMapCounter = r2rmlMapping.getIdentifierCounter();
		textArea.append(String.valueOf(triplesMapCounter) + "\n");
		
		if (r2rmlMapping.hasTriplesMap()) {
			System.out.println("ViewR2RMLMapping --> Tiene triplesMap " + r2rmlMapping.hasTriplesMap().toString());
			for (int i = 0; i < triplesMapCounter; i++) {
				triplesMapAux = r2rmlMapping.getTriplesMap(i);
				subjectMapAux = triplesMapAux.getSubjectMap();
				textArea.append("Triples Map\n");
				textArea.append(subjectMapAux.getSubject());
				textArea.append("\n");
				textArea.append(subjectMapAux.getRdfClass());
				textArea.append("\n");
		        System.out.println("ViewR2RMLMapping --> Imprimido sujeto en text area");
		        int sizePredicateObjectMaps = triplesMapAux.getPredicateObjectMaps().size();
		        if (sizePredicateObjectMaps > 0) {
		        	ArrayList<PredicateObjectMap> predicateObjects = triplesMapAux.getPredicateObjectMaps();
		        	for (PredicateObjectMap predicateObject : predicateObjects) {
						textArea.append("\n");
		        		int predicatesSize = predicateObject.getPredicateMaps().size();
		        		if (predicatesSize > 0)  {
							textArea.append("Predicate    ");
		        			ArrayList<PredicateMap> predicates = predicateObject.getPredicateMaps();
			        		for (PredicateMap predicate : predicates) {
								textArea.append(predicate.getPredicateIRI() + "\n");
			        		}
		        		}

		        		int ObjectsSize = predicateObject.getObjectMaps().size();
		        		if (ObjectsSize > 0)  {
		        			ArrayList<ObjectMap> objects = predicateObject.getObjectMaps();
			        		for (ObjectMap object : objects) {
								textArea.append("Object    ");
			        			if (object.getType().equals("Column-Valued")) {
			        				ColumnValueObjectMap columnVal = (ColumnValueObjectMap) object;
			        				ArrayList<Column> cols =  columnVal.getObjectColumns();
			        				for (Column col : cols) {
										textArea.append(col.getColumnName() + "\n");
			        				}
			        			}
			        			else {
			        				ReferenceObjectMap ref = (ReferenceObjectMap) object;
									textArea.append("Parent Triples Map " + ref.getParentTriplesMap().getIdentifier() + "\n");
									ArrayList<JoinCondition> jConds = ref.getJoinConditions();
									for (JoinCondition jCond : jConds) {
										textArea.append("JoinCondition/n");	
										textArea.append("Parent " + jCond.getParent().getColumnName() + "/n");
										textArea.append("Child " + jCond.getChild().getColumnName() + "/n");	
									}
			        			}
			        			
			        		}
		        		}
		        	}
		        }
				textArea.append("\n");
			}
		}
		else {
	        System.out.println("ViewR2RMLMapping --> " + r2rmlMapping.hasTriplesMap().toString());
	        System.out.println("ViewR2RMLMapping --> " + String.valueOf(r2rmlMapping.getIdentifierCounter()));
		}
	}

}
