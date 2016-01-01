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

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import model.database.Column;
import model.r2rmlmapping.R2RMLMapping;
import model.r2rmlmapping.triplesMap.ColumnValueObjectMap;
import model.r2rmlmapping.triplesMap.JoinCondition;
import model.r2rmlmapping.triplesMap.ObjectMap;
import model.r2rmlmapping.triplesMap.PredicateMap;
import model.r2rmlmapping.triplesMap.PredicateObjectMap;
import model.r2rmlmapping.triplesMap.ReferencingObjectMap;
import model.r2rmlmapping.triplesMap.TriplesMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Transform the r2rml model into a jena model
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class R2RMLModelToJenaModelTransformer {
	
	private static Logger logger = LoggerFactory.getLogger(R2RMLModelToJenaModelTransformer.class);
	
	private final static String rr = "http://www.w3.org/ns/r2rml#";
	@SuppressWarnings("unused")
	private final static String rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
	@SuppressWarnings("unused")
	private final static String rdfs = "http://www.w3.org/2000/01/rdf-schema#";
	@SuppressWarnings("unused")
	private final static String xsd = "http://www.w3.org/2001/XMLSchema#";
	
	private R2RMLMapping r2rmlModel;
	private String nameSpace;
	private Model jenaModel;

	/**
	 * @param R2RML model
	 */
	public R2RMLModelToJenaModelTransformer(R2RMLMapping model) {
		
		this.r2rmlModel = model;
		transformIntoJenaModel();
		
	}

	/**
	 * Creates a Jena model from the R2RML Mapping
	 */
	private void transformIntoJenaModel() {
		
		nameSpace = "http://example.com/test";
		//TODO: Obtener el namespace del mapping, para ello antes tiene que tenerlo el mapping (Obtener del ususario)
		//TODO No olvidar escribir el namespace en el modelo jena
		
		// create an empty Model
		jenaModel = ModelFactory.createDefaultModel();
		
		ArrayList<TriplesMap> r2rmlTriplesMaps = r2rmlModel.getAllTriplesMap();
		
		if (r2rmlTriplesMaps.isEmpty()) {
			logger.debug("R2RML Map is empty");
		}
		else {
			logger.trace("R2RML Map is not empty");
			for (TriplesMap r2rmlTriplesMap: r2rmlTriplesMaps) {				
				transformIntoJenaTriplesMap(r2rmlTriplesMap, jenaModel);		
			}
		}

	}

	/**
	 * @param r2rmlTriplesMap
	 * @param jenaModel 
	 */
	private void transformIntoJenaTriplesMap(TriplesMap r2rmlTriplesMap, Model jenaModel) {

		Resource jenaTriplesMap = jenaModel.createResource(nameSpace + "TriplesMap" + r2rmlTriplesMap.getIdentifier());
		
		//Creacion de la logical table
		
		Resource blankNodeLogicalTable = jenaModel.createResource();
		
		final Property logicalTable = jenaModel.createProperty(rr + "logicalTable");
		
		jenaTriplesMap.addProperty(logicalTable, blankNodeLogicalTable);
		
		final Property tableName = jenaModel.createProperty(rr + "tableName");
		
		blankNodeLogicalTable.addProperty(tableName, r2rmlTriplesMap.getLogicalTable().getTableName());
		
		//Creacion del subjectMap
		
		Resource blankNodeSubjectMap = jenaModel.createResource();
		
		final Property subjectMap = jenaModel.createProperty(rr + "subjectMap"); 
		
		jenaTriplesMap.addProperty(subjectMap, blankNodeSubjectMap);
		
		final Property template = jenaModel.createProperty(rr + "template");

		blankNodeSubjectMap.addProperty(template, r2rmlTriplesMap.getSubjectMap().getSubject());
		
		if (!r2rmlTriplesMap.getSubjectMap().getRdfClass().isEmpty()) {
			
			final Property rdfClass = jenaModel.createProperty(rr + "class");
			blankNodeSubjectMap.addProperty(rdfClass, r2rmlTriplesMap.getSubjectMap().getRdfClass());
			
		}
		
		//Creacion de los predicate-object maps 
		if (!r2rmlTriplesMap.getPredicateObjectMaps().isEmpty()) {
			
			final Property predicateObjectMap = jenaModel.createProperty(rr + "predicateObjectMap");
			
			final Property predicate = jenaModel.createProperty(rr + "predicate");
			
			final Property objectMap = jenaModel.createProperty(rr + "objectMap");
			
			final Property column = jenaModel.createProperty(rr + "column");
			
			final Property parentTriplesMap = jenaModel.createProperty(rr + "parentTriplesMap");
			
			final Property joinCondition = jenaModel.createProperty(rr + "joinCondition");
			
			final Property child = jenaModel.createProperty(rr + "child");
			
			final Property parent = jenaModel.createProperty(rr + "parent");
			
			ArrayList<PredicateObjectMap> predicateObjects = r2rmlTriplesMap.getPredicateObjectMaps();
			
			for (PredicateObjectMap predicateObject : predicateObjects) {

				Resource blankNodePredicateObjectMap = jenaModel.createResource();
				
				jenaTriplesMap.addProperty(predicateObjectMap, blankNodePredicateObjectMap);
				
				//Creacion de los predicate maps 
				ArrayList<PredicateMap> predicateMaps = predicateObject.getPredicateMaps();
				
				for (PredicateMap predicateMap : predicateMaps) {
					
					blankNodePredicateObjectMap.addProperty(predicate, predicateMap.getPredicateIRI());
					
				}
				
				//Creacion de los object maps 
				ArrayList<ObjectMap> objectMaps = predicateObject.getObjectMaps();
				
				for (ObjectMap object : objectMaps) {
					
					//Si el object map es un Column Valued Object Map
					if (object.getType().equalsIgnoreCase("Column-Valued")) {
						
						Resource blankNodeColumn = jenaModel.createResource();
						
						ArrayList<Column> columns = ((ColumnValueObjectMap) object).getObjectColumns();
						
						String cols = "";
						
						for (Column col : columns) {
							
							cols = cols + " {" + col.getColumnName() +"}";
							
						}
						
						blankNodeColumn.addProperty(column, cols);
						
						blankNodePredicateObjectMap.addProperty(objectMap, blankNodeColumn);
						
					}
					//Si el object map es un Referenced Object Map
					else if (object.getType().equalsIgnoreCase("Referencing")) {
						
						Resource blankNodeReferencing = jenaModel.createResource();
						
						blankNodeReferencing.addProperty(parentTriplesMap, "http://example.com/testTriplesMap" + (((ReferencingObjectMap) object).getParentTriplesMap()).getIdentifier());
						
						ArrayList<JoinCondition> joinConditions = ((ReferencingObjectMap) object).getJoinConditions();
						
						
						for (JoinCondition join : joinConditions) {
							
							Resource blankNodeJoinCondition = jenaModel.createResource(); 
							
							blankNodeReferencing.addProperty(joinCondition, blankNodeJoinCondition);
							
							blankNodeJoinCondition.addProperty(child, join.getChild().getColumnName());
							
							blankNodeJoinCondition.addProperty(parent, join.getParent().getColumnName());
							
						}
						
						blankNodePredicateObjectMap.addProperty(objectMap, blankNodeReferencing);
						
					}
					
				}
				
			}
			
		}
		
	}
	
	public void writeR2RMLMappingToFile(String file) throws FileNotFoundException {
		
		PrintWriter modelWriter = new PrintWriter(file);
		
		jenaModel.write(modelWriter, "Turtle");
		
	}
	
}
