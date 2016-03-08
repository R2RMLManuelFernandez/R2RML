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

package control.r2rmlmapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

import model.database.Column;
import model.database.Database;
import model.database.Table;
import model.r2rmlmapping.R2RMLMapping;
import model.r2rmlmapping.triplesMap.ColumnValueObjectMap;
import model.r2rmlmapping.triplesMap.JoinCondition;
import model.r2rmlmapping.triplesMap.PredicateMap;
import model.r2rmlmapping.triplesMap.PredicateObjectMap;
import model.r2rmlmapping.triplesMap.ReferencingObjectMap;
import model.r2rmlmapping.triplesMap.SubjectMap;
import model.r2rmlmapping.triplesMap.TriplesMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;


/**
 * Transform the jena model into a r2rml model
 * 
 * @author Manuel Fernandez Perez
 *
 */

public class JenaModelToR2RMLModelTransformer {
	
	private static Logger logger = LoggerFactory.getLogger(JenaModelToR2RMLModelTransformer.class);

	private final static String rr = "http://www.w3.org/ns/r2rml#";
	@SuppressWarnings("unused")
	private final static String rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
	@SuppressWarnings("unused")
	private final static String rdfs = "http://www.w3.org/2000/01/rdf-schema#";
	@SuppressWarnings("unused")
	private final static String xsd = "http://www.w3.org/2001/XMLSchema#";
	
	private R2RMLMapping r2rmlMapping;
	private String baseIRI;
	private Model jenaModel;
	private Database database;
	
	/**
	 * @param Jena model
	 * @return 
	 */
	public JenaModelToR2RMLModelTransformer(Model model, Database database) {
		
		this.jenaModel = model;
		this.database = database;
		transformIntoR2RMLModel();
		
	}

	/**
	 * Creates a R2RML Mapping model from the Jena model
	 */
	private void transformIntoR2RMLModel() {

		logger.trace("Obtaining statements in Jena model");
		// list the statements in the Model
		StmtIterator iter = jenaModel.listStatements();
		
		final Property logicalTable = jenaModel.createProperty(rr + "logicalTable");
		
		final Property tableName = jenaModel.createProperty(rr + "tableName");
		
		final Property subjectMap = jenaModel.createProperty(rr + "subjectMap"); 
		
		final Property template = jenaModel.createProperty(rr + "template");
		
		final Property rdfClass = jenaModel.createProperty(rr + "class");
		
		final Property predicateObjectMap = jenaModel.createProperty(rr + "predicateObjectMap");
		
		final Property predicate = jenaModel.createProperty(rr + "predicate");
		
		final Property objectMap = jenaModel.createProperty(rr + "objectMap");
		
		final Property column = jenaModel.createProperty(rr + "column");
		
		final Property parentTriplesMap = jenaModel.createProperty(rr + "parentTriplesMap");
		
		final Property joinCondition = jenaModel.createProperty(rr + "joinCondition");
		
		final Property child = jenaModel.createProperty(rr + "child");
		
		final Property parent = jenaModel.createProperty(rr + "parent");
		
		ArrayList<Statement> listLogicalTables = new ArrayList<Statement>();
		
		ArrayList<Statement> listTableNames = new ArrayList<Statement>();

		ArrayList<Statement> listSubjectMaps = new ArrayList<Statement>();
		
		ArrayList<Statement> listTemplates = new ArrayList<Statement>();
		
		ArrayList<Statement> listRdfClasses = new ArrayList<Statement>();
		
		ArrayList<Statement> listPredicateObjectMaps = new ArrayList<Statement>();
		
		ArrayList<Statement> listPredicates = new ArrayList<Statement>();
		
		ArrayList<Statement> listObjectMaps = new ArrayList<Statement>();
		
		ArrayList<Statement> listColumns = new ArrayList<Statement>();
		
		ArrayList<Statement> listParentTriplesMap = new ArrayList<Statement>();
		
		ArrayList<Statement> listJoinConditions = new ArrayList<Statement>();
		
		ArrayList<Statement> listChilds = new ArrayList<Statement>();
		
		ArrayList<Statement> listParents = new ArrayList<Statement>();
		
		//creates an empty R2RML Mapping model
		r2rmlMapping = new R2RMLMapping();
		
		//auxiliar map taht matches triples maps whith the Resource of the subject statement which define it
		Map<String, TriplesMap> triplesMapMap = new HashMap<String, TriplesMap>();
		
		logger.trace("buscando el turtle de los triples maps");
		
		// classifies the statements attending to his prercicate
		while (iter.hasNext()) {
			
		    Statement stmt = iter.nextStatement();  // get next statement
		    
		    logger.trace("Statement " + stmt);
		    
		    // get the predicate
		    Property stmtPredicate = stmt.getPredicate();   
		    
		   if (stmtPredicate.equals(logicalTable)) {
			   
			   listLogicalTables.add(stmt);
			   
		   }
		   else if (stmtPredicate.equals(tableName)) {
			   
			   listTableNames.add(stmt);
			   
		   }
		   else if (stmtPredicate.equals(subjectMap)) {
			   
			   listSubjectMaps.add(stmt);
			   
		   }
		   else if (stmtPredicate.equals(template)) {
			   
			   listTemplates.add(stmt);
			   
		   }
		   else if (stmtPredicate.equals(rdfClass)) {
			   
			   listRdfClasses.add(stmt);
			   
		   }
		   else if (stmtPredicate.equals(predicateObjectMap)) {
			   
			   listPredicateObjectMaps.add(stmt);
			   
		   }
		   else if (stmtPredicate.equals(predicate)) {
			   
			   listPredicates.add(stmt);
			   
		   }
		   else if (stmtPredicate.equals(objectMap)) {
			   
			   listObjectMaps.add(stmt);
			   
		   }
		   else if (stmtPredicate.equals(column)) {
			   
			   listColumns.add(stmt);
			   
		   }
		   else if (stmtPredicate.equals(parentTriplesMap)) {
			   
			   listParentTriplesMap.add(stmt);
			   
		   }
		   else if (stmtPredicate.equals(joinCondition)) {
			   
			   listJoinConditions.add(stmt);
			   
		   }
		   else if (stmtPredicate.equals(child)) {
			   
			   listChilds.add(stmt);
			   
		   }
		   else if (stmtPredicate.equals(parent)) {
			   
			   listParents.add(stmt);
			   
		   }
		   else {
			   logger.error("The statement cant be processed", stmt);
		   }
		   
		}
		
		Statement logTableStatement = listLogicalTables.get(0);
		
	    Resource subjectLogTableStatement = logTableStatement.getSubject();     // get the subject
	    //get the triplesmap identifier
	    String URILogTableStatement = subjectLogTableStatement.toString();
		
	    String[] uriSplit = URILogTableStatement.split("#");
	    
	    baseIRI = uriSplit[0];
	    
	    r2rmlMapping.setBaseIRI(baseIRI);
		
		for (Statement statement: listLogicalTables) {

		    Resource subject = statement.getSubject();     // get the subject
		    //get the triplesmap identifier
		    String triplesMapURI = subject.toString();
		    
		    logger.trace("triplesMapURI " + triplesMapURI);
		    
		    //TODO separar el id en namespace + TriplesMapid
		    
//		    String[] uriSplit = triplesMapURI.split("#");
//		    
//		    nameSpace = uriSplit[0];
//		    
//		    r2rmlMapping.setRdfNameSpace(nameSpace);
		    
//		    String tiplesMapId = uriSplit[1];
		    	
		    //get the triplesmap logical table
		    RDFNode object = statement.getObject();      // get the object
		    
		    if (object instanceof Resource) {
		    	// search for the logical table
		    	ListIterator<Statement> tableNamesIter = listTableNames.listIterator();
		    	
		    	while (tableNamesIter.hasNext()) {
		    		
		    		Statement stmtTableName = tableNamesIter.next();
		    		
		    		logger.trace("table name statement " + stmtTableName);
		    		logger.trace("triples map " + object);
		    		
		    		if (stmtTableName.getSubject().equals((Resource)object)) {
		    			
		    			RDFNode tableNameObject = stmtTableName.getObject();
		    			
		    			if (tableNameObject instanceof Literal) {
		    				
		    				String nameTable = tableNameObject.toString();
		    				
		    				//get the table from the database
		    				Table table = database.getTable(nameTable);
		    				logger.trace("table for triples map " + table.getTableName());
		    				//TODO cambiar el constructor del triplesmap para que no coja int sino Strings
		    				TriplesMap triplesMap = new TriplesMap(r2rmlMapping.getIdentifierCounter(), r2rmlMapping, table);
		    				r2rmlMapping.addTriplesMap(triplesMap);
		    				logger.trace("key triples map added " + subject.toString());
		    				triplesMapMap.put(subject.toString(), triplesMap);
		    				
	    		    		logger.trace("buscando subject map");
		    				
		    		    	// search for the subject map
		    		    	ListIterator<Statement> subjectMapIter = listSubjectMaps.listIterator();
		    		    	
		    		    	while (subjectMapIter.hasNext()) {
		    		    		
		    		    		Statement stmtSubjectMap = subjectMapIter.next();
		    		    		
		    		    		logger.trace("Subject Map statement subject " + stmtSubjectMap.getSubject());
		    		    		logger.trace("triples map " + subject);
		    		    		
		    		    		if (stmtSubjectMap.getSubject().equals(subject)) {
		    		    			
			    		    		logger.trace("encontrado subject map");
		    		    			
		    		    			RDFNode subjectMapObject = stmtSubjectMap.getObject();
		    		    			
		    		    			logger.trace("objeto del subject map " + subjectMapObject.toString());
		    		    			
		    		    			if (subjectMapObject instanceof Resource) {
			    		    			
			    		    			//creates the subjectap
			    		    			SubjectMap subjMap = new SubjectMap();
			    		    			
			    		    			//searchs for the templates
			    		    			ListIterator<Statement> templatesIter = listTemplates.listIterator();
			    		    			
			    		    			while (templatesIter.hasNext()) {
			    		    				
											Statement stmtTemplate = templatesIter.next();
											
											if (stmtTemplate.getSubject().equals((Resource)subjectMapObject)) {
												
					    		    			logger.trace("template del subject map " + subjectMapObject.toString());
												
												RDFNode subjectToSplit = stmtTemplate.getObject();
												
												logger.trace("template to split " + subjectToSplit.toString());
												
								    			if (subjectToSplit instanceof Literal) {
								    				
								    				String subjectToLoad = subjectToSplit.toString();
								    				
								    				int cutPoint = subjectToLoad.indexOf('{');
								    				
								    				String tempSubject = subjectToLoad.substring(cutPoint);
								    				
								    				String tempCols[] = subjectToLoad.split("\\{");
								    				
								    				logger.trace("template columns " + subjectToLoad.toString());
								    				
								    				subjMap.setSubject(tempSubject);
								    				
								    				for (String x : tempCols) {
								    					
								    					x = x.trim();
								    					
								    					if (x.endsWith("}")) {
								    						
								    						x = x.substring(0, x.length() - 1);
								    						logger.trace("col to add " + x);
								    						Column col = triplesMap.getLogicalTable().getColumn(x);
								    						subjMap.addColumn(col);
								    						
								    					}
								    					
								    				}
				
												}
								    			else {
								    				
								    				logger.error("The statement is not a template", stmtTemplate);
								    				
								    			}
											}
											
										}
			    		    			
			    		    			//searchs for the rdfClasses
			    		    			ListIterator<Statement> rdfClassesIter = listRdfClasses.listIterator();
			    		    			
			    		    			while (rdfClassesIter.hasNext()) {
			    		    				
											Statement stmtRdfClass = rdfClassesIter.next();
											
											if (stmtRdfClass.getSubject().equals((Resource)subjectMapObject)) {
												
												if (stmtRdfClass.getObject() instanceof Literal) {
													
													subjMap.setRdfClass(stmtRdfClass.getObject().toString());
													//TODO buscar en la ontologia la clase a la que correspnde la IRI 
													
												}
												else {
													
								    				logger.error("The statement is not a RdfClass", stmtRdfClass);
								    				
								    			}
												
											}
											
										}
			    		    			
										triplesMap.setSubjectMap(subjMap);
										logger.trace("SubjectMap added " + subjMap.getSubjectColumns().toString() + " " + subjMap.getRdfClass());
		    		    			
		    		    			}
		    		    			else {
		    		    				
		    		    				logger.error("The statement is not a subject map", subjectMapObject);
		    		    			
		    		    			}
		    					
		    		    		}
		    		    		
		    		    	}

		    			}
		    			else {
		    				
		    				logger.error("The statement is not a tables name", stmtTableName);
		    			
		    			}
					
		    		}
		    		
		    	}
		    	
		    }
		    else {
		    	// object is a literal
		    	logger.error("The statement is not a triples map", statement);	
		    
		    }
			
		}

		//creates the predicate-object maps amd assign them to the corresponding triples map
		int id = 0;
		
		for (Statement predicateObjectStmt : listPredicateObjectMaps) {			
			
		    Resource subject = predicateObjectStmt.getSubject();     // get the subject
		    TriplesMap triplesMap = triplesMapMap.get(subject.toString());
		    
		    logger.trace("predicateObjectStmt subject triplewa map database" + triplesMap.getLogicalTable().getTableName());
		    
    		logger.trace("predicateObjectStmt subject " + subject);
		    
		    PredicateObjectMap predObjMap = new PredicateObjectMap(id++, triplesMap);
		    triplesMap.addPredicateObjectMap(predObjMap);
		    
		    RDFNode object = predicateObjectStmt.getObject();     // get the object
		    
    		logger.trace("predicateObjectStmt object " + object.toString());
		    
		    if (object instanceof Resource) {
		    	//get the predicate maps for the predicate-object
				for (Statement predStmt : listPredicates) {
					
					//if the subject of the statement matches, creates the predicate
					if (predStmt.getSubject().equals((Resource)object)) {
						
						PredicateMap predMap = new PredicateMap(predObjMap);
						predMap.setPredicateIRI(predStmt.getObject().toString());
						predObjMap.addPredicateMap(predMap);
						logger.trace("prodicateObject added " + predStmt.getObject().toString());

					}
				}
		    	
		    	//get the object maps for the predicate-object
				for (Statement objectStmt : listObjectMaps) {
					//if the subject of the statement matches, creates the predicate
					if (objectStmt.getSubject().equals((Resource)object)) {
						
					    RDFNode objectStmtObject = objectStmt.getObject();     // get the Object
					    
					    logger.trace("objectStmt Object " + objectStmtObject.toString());

					    if (objectStmtObject instanceof Resource) {
					    	
					    	Boolean itsColumnValued = false;
					    	ColumnValueObjectMap colValObjMap = new ColumnValueObjectMap(predObjMap);
					    	
					    	for (Statement columnObjStmt : listColumns) {
					    		
								if (columnObjStmt.getSubject().equals((Resource)objectStmtObject)) {
									
								    logger.trace("columnObjStmt subject " + columnObjStmt.getSubject().toString());
									
									itsColumnValued = true;
									
									String colsNameToSplit = columnObjStmt.getObject().toString();
									
									logger.trace("colsNameToSplit " + colsNameToSplit);
									
									String tempNameCols[] = colsNameToSplit.split("\\{");
									logger.trace("tempNameCols lenght " + tempNameCols.length);
									
									for (String colName : tempNameCols) {
										colName = colName.trim();
										if (colName.endsWith("}")) {
											colName = colName.substring(0, colName.length() - 1);
											logger.trace("colName " + colName);
											Column col = triplesMap.getLogicalTable().getColumn(colName);
											colValObjMap.addObjectColumn(col);
											logger.trace("column added to column-valued object map");
										}
										
									}

								}
					    	}
					    	
					    	if (itsColumnValued) {
					    		
					    		predObjMap.addObjectMap(colValObjMap);
					    		
					    	}
					    	else {
					    		
					    		for (Statement referencedObjStmt : listParentTriplesMap) {
					    			
									if (referencedObjStmt.getSubject().equals((Resource)objectStmtObject)) {
										//TODO Como resolver que los triples map no se lean en orden
										TriplesMap parentTripMap = triplesMapMap.get(referencedObjStmt.getObject().toString());
										logger.trace("parentTripMap " + referencedObjStmt.getObject().toString());
										logger.trace("triples map keys " + triplesMapMap.keySet());

										ReferencingObjectMap refObjMap = new ReferencingObjectMap(predObjMap, parentTripMap);
										
										for (Statement joinConditionStmt : listJoinConditions) {
											
											if (joinConditionStmt.getSubject().equals((Resource)objectStmtObject)) {
												
												RDFNode joinCondObject = joinConditionStmt.getObject();
												logger.trace("joinCondObject " + joinConditionStmt.getObject().toString());
												JoinCondition joinCond = new JoinCondition();
												
												for (Statement childStmt : listChilds) {
													
													if (childStmt.getSubject().equals((Resource) joinCondObject)) {
														
														String colChildName = childStmt.getObject().toString();
														logger.trace("colChildName  " + colChildName);
														Column colChild = triplesMap.getLogicalTable().getColumn(colChildName);
														joinCond.setChild(colChild);
													
													}
												
												}
												
												for (Statement parentStmt : listParents) {
													
													if (parentStmt.getSubject().equals((Resource) joinCondObject)) {
														
														String colParentName = parentStmt.getObject().toString();
														logger.trace("colParentName  " + colParentName);
														logger.trace("colParent log table " + parentTripMap.getLogicalTable().getTableName());
														Column colParent = parentTripMap.getLogicalTable().getColumn(colParentName);
														joinCond.setParent(colParent);
													
													}
												
												}
												
												refObjMap.addJoinCondition(joinCond);
											
											}
										
										}
										
										predObjMap.addObjectMap(refObjMap);
									
									}
									else {
										
										logger.error("The statement is not a referenced object", referencedObjStmt);
									
									}
						    	
					    		}
						    	
					    	}
					    	
					    }
					    else {
					    	
					    	logger.error("The statement is not an object", objectStmt);
					    	
					    }
					    
					}
				}
				
		    }
		    else {
		    	// object is a literal
		    	logger.error("The statement is not a predicate-object map", predicateObjectStmt);	
		    
		    }
		
		}
		
	}
	
	/**
	 * @return the R2RML mapping obtained from the jena model
	 */
	public R2RMLMapping getR2RMLMapping() {
		
		return this.r2rmlMapping;
		
	}
	
}
