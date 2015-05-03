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

package control.ontology;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import model.ontology.OntologyClass;
import model.ontology.OntologyDataProperty;
import model.ontology.OntologyObjectProperty;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.SimpleRootClassChecker;

/**
 * @author Manuel Fernandez Perez
 *
 */
/**
 * @author Manuel Fernandez Perez
 *
 */
public class OntologyModelConstructor {
    
	private OWLOntologyManager manager;
    
	private OWLOntology owlOntology;
    
	//the set of OwlClass to allocate in the model
    private Set<OWLClass> owlClasses;
    
	//the set of OWLDataProperty in the ontology
    private Set<OWLDataProperty> owlDataProperties;
    
	//the set of OwlObjectProperty in the ontology
    private Set<OWLObjectProperty> owlObjectProperties;
    
    private HashMap<String, OntologyObjectProperty> mapEquivalentObjectProperties = new HashMap<>();
    
    private HashMap<String, OntologyDataProperty> mapEquivalentDataProperties = new HashMap<>();
    
    private OntologyClass thing;
    
    private OntologyObjectProperty topObjectProperty;
    
    private OntologyDataProperty topDataProperty;
    
    public OntologyModelConstructor(String paramOntologySource) throws OWLOntologyCreationException {
    	
    	loadOntology(paramOntologySource);
    	  	
    	createOntologyModel();
    	
	}

	/**
	 * read the ontology from an internet IRI or a local file
	 * 
	 * @param paramIRI
	 * @throws OWLOntologyCreationException
	 */
	private void loadOntology(String paramOntologySource) throws OWLOntologyCreationException {
		
		//Creates the ontology manager
		this.manager = OWLManager.createOWLOntologyManager();
		
		//Identifies the the ontology source 
		if (paramOntologySource.startsWith("http://", 0)) {
			IRI owlOntologyIRI = IRI.create(paramOntologySource);
			this.owlOntology = manager.loadOntologyFromOntologyDocument(owlOntologyIRI);
		}
		else {
			File file = new File(paramOntologySource);
			this.owlOntology = manager.loadOntologyFromOntologyDocument(file);
		}

	}
	
    
	/**
	 * Creates the ontology model previously loaded from the document
	 */
	private void createOntologyModel() {
		
    	this.owlClasses = this.owlOntology.getClassesInSignature();
    	this.owlObjectProperties = this.owlOntology.getObjectPropertiesInSignature();
    	this.owlDataProperties = this.owlOntology.getDataPropertiesInSignature();
//    	System.out.println("createOntologyModel: N� de clases: " + this.owlClasses.size());
    	
    	createObjectPropertiesHierarchy();
    	
    	createDataPropertiesHierarchy();
    	
    	Set<OWLClass> owlRootClasses = findRootClasses();
    	
/*    	System.out.println("createOntologyModel: N� de clases root: " + owlRootClasses.size());
    	
		for (OWLClass owlRootClass: owlRootClasses){
			System.out.println("createOntologyModel: root: " + owlRootClass.getIRI());
		}*/
		
		OWLClass owlThing = findOwlThing(owlRootClasses);
		
/*		for (OWLClass owlRootClass: owlRootClasses){
			System.out.println("createOntologyModel: root after create Thing: " + owlRootClass.getIRI());
		}*/
		
		this.createThing(owlThing);
		
		for (OWLClass owlRootClass: owlRootClasses) {
			createClass(this.thing, owlRootClass);
		}

		if (this.owlClasses.size() > 0) {
			this.reallocaRemainClasses();
		}
		
		if (this.owlClasses.size() > 0) {
			for (OWLClass remainClass: this.owlClasses) {
				createClass(this.thing, remainClass);
			}
		}
		
	}

	
	/**
	 * Obtains the classes in the ontology that are root
	 * 
	 * @return the set of classes in the ontology that are root
	 */
	private Set<OWLClass> findRootClasses() {
		
		Set<OWLClass> owlRootClasses = new HashSet<OWLClass>();
		
		Set<OWLOntology> owlOntologies = new HashSet<OWLOntology>();
		owlOntologies.add(owlOntology);
		
		SimpleRootClassChecker rootChecker = new SimpleRootClassChecker(owlOntologies);
		
		for (OWLClass owlClass : owlClasses) {
			if (rootChecker.isRootClass(owlClass)) {
				owlRootClasses.add(owlClass);
			}
		}
		
		return owlRootClasses;
		
	}
	
	
	/**
	 * search for Thing class into the root classes
	 * 
	 * @return the Thing class in owlOntology, null if Thing class is not present
	 */
	private OWLClass findOwlThing(Set<OWLClass> paramOwlRootClasses) {
		
		OWLClass owlthing = null;
		
		for (OWLClass clazz : paramOwlRootClasses) {
			if (clazz.isOWLThing()){
				owlthing = clazz;	
				break;
			}
		}
		
		if (owlthing != null) {
			paramOwlRootClasses.remove(owlthing);
//			System.out.println("Owl Thing: " + owlthing.getIRI());
		}
		
		return owlthing;
		
	}
	
	/**
	 * generates th OntologyClass for Thing. If OwlClass thing doesnt exits its created whit default parameters 
	 * 
	 * @param owlThing
	 */
	private void createThing(OWLClass paramOwlThing) {
		
		OntologyClass Thing = null;
		
		if (paramOwlThing != null) {
			IRI ontologyIRI = paramOwlThing.getIRI();
			String displayName = ontologyIRI.getFragment();
			String nameSpace = ontologyIRI.getNamespace();
			Thing = new OntologyClass(ontologyIRI.toString(), displayName, nameSpace);
			this.owlClasses.remove(paramOwlThing);
//			System.out.println("N� de clases: " + this.owlClasses.size());
		}
		else {
			Thing = new OntologyClass("http://www.w3.org/2002/07/owl#Thing", "Thing", "http://www.w3.org/2002/07/owl#");
		}
		
//		System.out.println("createThing: Ontology Thing: " + Thing.getIRI() + ", " + Thing.getDisplayName() + ", " + Thing.getNameSpace());
		
		this.thing = Thing;
		
	}
	
	/**
	 * Recursively creates the classes of the ontology
	 * 
	 * @param paramSuperClass
	 */
	private void createClass(OntologyClass paramSuperClass, OWLClass paramOwlClass) {
		
		IRI owlIRI = paramOwlClass.getIRI();
		String displayName = owlIRI.getFragment();
		String nameSpace = owlIRI.getNamespace();
		OntologyClass newOntologyClazz = new OntologyClass(owlIRI.toString(), displayName, nameSpace);
		paramSuperClass.addSubclass(newOntologyClazz);
		
//		Set<OntologyObjectProperty> classObjectProperties = createObjectProperties(getOwlClassObjectProperties(paramOwlClass));
		Set<OWLObjectProperty> classOwlObjectProperties = getOwlClassObjectProperties(paramOwlClass);
/*		System.out.println("createClass --> " + displayName + " OOP: " + classOwlObjectProperties.size());
		for (OWLObjectProperty oOP: classOwlObjectProperties) {
			System.out.println("createClass --> " + oOP.getIRI().toString());
		}*/
		Set<OntologyObjectProperty> classObjectProperties = getOntologyObjectProperties(classOwlObjectProperties);
//		System.out.println("createClass --> " + displayName + " OP: " + classObjectProperties.size());
		
		for (OntologyObjectProperty classObjectProperty: classObjectProperties) {
			newOntologyClazz.addObjectProperty(classObjectProperty);
		}
		
//		Set<OntologyDataProperty> classDataProperties = createClassDataProperties(getOwlClassDataProperties(paramOwlClass));
		Set<OWLDataProperty> classOwlDataProperties = getOwlClassDataProperties(paramOwlClass);
/*		System.out.println("createClass --> " + displayName + " ODP: " + classOwlDataProperties.size());
		for (OWLDataProperty oDP: classOwlDataProperties) {
			System.out.println("createClass --> " + oDP.getIRI().toString());
		}*/
		Set<OntologyDataProperty> classDataProperties = getOntologyDataProperties(classOwlDataProperties);
//		System.out.println("createClass --> " + displayName + " DP: " + classDataProperties.size());
		
		for (OntologyDataProperty classDataProperty: classDataProperties) {
			newOntologyClazz.addDataProperty(classDataProperty);
		}
		
		this.owlClasses.remove(paramOwlClass);
//		System.out.println("createClass: " + paramOwlClass.getIRI() + " removed. " + "N� de clases restantes: " + this.owlClasses.size());
		Set<OWLClass> owlSubclasses = findOwlSubclasses(paramOwlClass);
		for (OWLClass owlSubclass: owlSubclasses) {
			createClass(newOntologyClazz, owlSubclass);
		}
		
	}
	
	/**
	 * find the classes in the ontology that are subclasses of the given class
	 * 
	 * @param paramOwlClass
	 * @return the Set of OwlClasses (instance of OWLClass) that are subclass of the OWlClass
	 */
	private Set<OWLClass> findOwlSubclasses(OWLClass paramOwlClass) {
		
		Set<OWLClass> owlSubclasses = new HashSet<OWLClass>(0);
		
		Set<OWLClassExpression> owlPosibleSubClasses = paramOwlClass.getSubClasses(owlOntology);
		
		Iterator<OWLClassExpression> iterator = owlPosibleSubClasses.iterator();
		
		while(iterator.hasNext()) {
			OWLClassExpression owlPosibleSubClass = iterator.next();
			if (owlPosibleSubClass instanceof OWLClass) {
				owlSubclasses.add((OWLClass) owlPosibleSubClass);
			}
		}
		
		return owlSubclasses;
		
	}
	
	/**
	 * places in the ontology model the classes whitout superclasses, but whit equivalent classes already in the model
	 */
	private void reallocaRemainClasses() {
		//conjunto de las clases que quedan por colocar
		Set<OWLClass> remainOwlClasses = this.owlClasses;
		//conjunto de las iris de las clases que hay que buscar por que tienen clases equivalemtes que hay que colocar
		Set<String> classesToSearch = new HashSet<String>();
		//map que relaciona las iris de las clases que tienen relacion con las clases por colocar con las que tienen relacion
		HashMap<String, Set<OWLClass>> mapEquivalentClasses = new HashMap<>();
		
//		System.out.println("reallocaRemainClasses: Clases equivalentes");
		//para cada clase que queda por colocar
		for (OWLClass remainOwlClass: remainOwlClasses) {
			//conjunto para almacernar las clases equivalentes de una clase
			Set<OWLClass> eqClassesOfClass = new HashSet<OWLClass>();
			//conjunto de las expresiones equivalente a una clase que queda por colocar
			Set<OWLClassExpression> eqOwlClasses = remainOwlClass.getEquivalentClasses(owlOntology);
			//para cada expresion
			for (OWLClassExpression eqOwlClass : eqOwlClasses){
				//conjunto de las expresiones equivalentes separando las conjunciones
				Set<OWLClassExpression>conjunctExp = eqOwlClass.asConjunctSet();
				//para cada expresion se comprueba si es clase y si lo es se a�ade al conjunto de clases equivalentes
				for (OWLClassExpression conjExp: conjunctExp){
					if (conjExp instanceof OWLClass){
						eqClassesOfClass.add((OWLClass)conjExp);
					}
				}
				//conjunto de las expresiones equivalentes separando las disyunciones
				Set<OWLClassExpression>disjunctExp = eqOwlClass.asDisjunctSet();
				//para cada expresion se comprueba si es clase y si lo es se a�ade al conjunto de clases equivalentes
				for (OWLClassExpression disjExp: disjunctExp){
					if (disjExp instanceof OWLClass){
						eqClassesOfClass.add((OWLClass)disjExp);
					}
				}
//				System.out.println("reallocaRemainClasses: " + remainOwlClass.getIRI().getFragment() + " " + eqClassesOfClass);
			}
			//conjunto de clases equivalentes de una clase en el modelo para a�adir al map
			Set<OWLClass> eqCl = new HashSet<OWLClass>();
			//para cada clase equivalente que esta en el modelo de una clase se comprueba si esta ya en el map
			for (OWLClass eqClassOfClass: eqClassesOfClass) {
				//si lo esta se obtiene el valor y se actualiza
				String auxIRI = eqClassOfClass.getIRI().toString();
				if (mapEquivalentClasses.containsKey(auxIRI)) {
					eqCl = mapEquivalentClasses.get(auxIRI);
				}
				eqCl.add(remainOwlClass);

				//se a�ade al map
				mapEquivalentClasses.put(eqClassOfClass.getIRI().toString(), eqCl);
			}
		}
		
/*		System.out.println("reallocaRemainClasses: Clases equivalentes en el map");
		
		if (mapEquivalentClasses.isEmpty()){
			System.out.println("reallocaRemainClasses: No hay nada en el map");
		}*/
		
		Iterator<String> it = mapEquivalentClasses.keySet().iterator();
		
		while (it.hasNext()) {
			String iri = it.next();
//			System.out.println("reallocaRemainClasses: " + iri + " " + mapEquivalentClasses.get(iri));
			classesToSearch.add(iri);
		}
		
		Set<String> itString = mapEquivalentClasses.keySet();
		
		for (String firstIri: itString) {
			findParentClassInModel(firstIri, mapEquivalentClasses, this.thing);
		}
		
	}
	
	/**
	 * find the parnt class in the model for a class
	 * 
	 * @param IriOfClass
	 * @param mapEquivalentClasses
	 * @param ontologyClass
	 * @return true if foundd the superclass 
	 */
	private Boolean findParentClassInModel(String IriOfClass,
										HashMap<String, Set<OWLClass>> mapEquivalentClasses, OntologyClass ontologyClass) {

		ArrayList<OntologyClass> subClazzes = ontologyClass.getSubclasses();
//		System.out.println("findParentClass: Buscando en Subclases " + subClazzes);
		
		Boolean find = false;
		
		if (subClazzes.isEmpty()) {
			
			return false;
		}
		
		Iterator<OntologyClass> it = subClazzes.iterator();
		while (it.hasNext() && !find) {
			OntologyClass ontClazz = it.next();
//			System.out.println("findParentClass: Comparando " + ontClazz.getIRI() + " con " + IriOfClass);
			if (ontClazz.getIRI().toString().equals(IriOfClass)) {
//				System.out.println("ENCONTRADO findParentClass: " + ontologyClass.getDisplayName() + " superclase de " + IriOfClass);
				Set<OWLClass> classesToAdd = mapEquivalentClasses.get(IriOfClass);
//				System.out.println("clases eq de : " + IriOfClass + " --> " + classesToAdd);
				for (OWLClass classToAdd: classesToAdd) {
					createClass(ontClazz, classToAdd);
				}
				return true;
			}
			find = findParentClassInModel(IriOfClass, mapEquivalentClasses, ontClazz);
		}
		//System.out.println("findParentClass: Se ha terminado la busquedade " + IriOfClass + " y no se ha encontrado nada");
		return find;
	}
	
	
	
	/**
	 * @return the thing
	 */
	public OntologyClass getThing() {
		
		return thing;
		
	}
	
	/**
	 * @param owlClazz
	 * @return
	 */
	private Set<OWLObjectProperty> getOwlClassObjectProperties(OWLClass owlClazz) {
		 
		Set<OWLObjectProperty> clazzObjectProperties = new HashSet<OWLObjectProperty>();
		
		for (OWLObjectProperty owlObjectProperty : this.owlObjectProperties) {
			for (OWLClassExpression objectPropertyDomain : owlObjectProperty.getDomains(owlOntology)) {
				Set<OWLClassExpression> objectPropertydomainDisjunction = objectPropertyDomain.asDisjunctSet();
				for (OWLClassExpression objectPropertyDomainAux : objectPropertydomainDisjunction) {
					if (objectPropertyDomainAux instanceof OWLClass) {
						if(objectPropertyDomainAux.equals(owlClazz)) {
							clazzObjectProperties.add(owlObjectProperty);
						}
					}
				}
			}
		}
		
		return clazzObjectProperties;
	}

	/**
	 * @param owlClazz
	 * @return
	 */
	private Set<OWLDataProperty> getOwlClassDataProperties(OWLClass owlClazz) {
	    
		Set<OWLDataProperty> clazzDataProperties = new HashSet<OWLDataProperty>();
	    
		for (OWLDataProperty owlDataProperty : this.owlDataProperties) {
			for (OWLClassExpression dataPropertyDomain : owlDataProperty.getDomains(owlOntology)) {
				Set<OWLClassExpression> dataPropertydomainDisjunction = dataPropertyDomain.asDisjunctSet();
				for (OWLClassExpression dataPropertyDomainAux : dataPropertydomainDisjunction) {
					if (dataPropertyDomainAux instanceof OWLClass) {
						if(dataPropertyDomainAux.equals(owlClazz)) {
							clazzDataProperties.add(owlDataProperty);
						}
					}
				}
			}
		}
		
	    return clazzDataProperties;
	}

	/**
	 * 
	 */
	private void createObjectPropertiesHierarchy() {
		
		//creates the topObjectProperty
		this.topObjectProperty = new OntologyObjectProperty("http://www.w3.org/2002/07/owl#topObjectProperty",
				"topObjectProperty", "http://www.w3.org/2002/07/owl#");
		
		//search for the rootobjectProperties
		
		for (OWLObjectProperty owlObjectProperty: this.owlObjectProperties) {
			
			if (owlObjectProperty.getSuperProperties(owlOntology).isEmpty()) {
				
				OntologyObjectProperty newObjectProperty = createObjectProperty(owlObjectProperty);
				this.topObjectProperty.addSubObjectProperty(newObjectProperty);
				
			}
			
		}
		
	}
	
	/**
	 * @param owlObjectProperty
	 * @return
	 */
	private OntologyObjectProperty createObjectProperty(OWLObjectProperty owlObjectProperty) {
		
		IRI iriObjectProperty = owlObjectProperty.getIRI();
		String displayName = iriObjectProperty.getFragment();
		String nameSpace = iriObjectProperty.getNamespace();
		
		OntologyObjectProperty ontologyObjectProperty = new OntologyObjectProperty(iriObjectProperty.toString(),
																displayName, nameSpace);
    	//System.out.println("createObjectProperty -> ontologyObjectProperty: " + ontologyObjectProperty.getIRI());
		
		this.mapEquivalentObjectProperties.put(iriObjectProperty.toString(), ontologyObjectProperty);
		
		Set<OWLObjectPropertyExpression> owlObjectSubProperties = owlObjectProperty.getSubProperties(owlOntology);
		
		for (OWLObjectPropertyExpression owlObjectSubProperty: owlObjectSubProperties) {
			
			if (owlObjectSubProperty instanceof OWLObjectProperty) {
				
				OntologyObjectProperty OntologyObjectSubProperty = createObjectProperty((OWLObjectProperty) owlObjectSubProperty);
		    	//System.out.println("createObjectProperty -> ontologyObjectSubProperty: " + OntologyObjectSubProperty.getIRI());
				ontologyObjectProperty.addSubObjectProperty(OntologyObjectSubProperty);
				
			}
			
		}
		
		return ontologyObjectProperty;
	}
	
	/**
	 * 
	 */
	private void createDataPropertiesHierarchy() {
		
		//creates the topDataProperty
		this.topDataProperty = new OntologyDataProperty("http://www.w3.org/2002/07/owl#topDataProperty",
				"topDataProperty", "http://www.w3.org/2002/07/owl#");
		
		//search for the rootDataProperties
		
		for (OWLDataProperty owlDataProperty: this.owlDataProperties) {
			
			if (owlDataProperty.getSuperProperties(owlOntology).isEmpty()) {
				
				OntologyDataProperty newDataProperty = createDataProperty(owlDataProperty);
				this.topDataProperty.addSubDataProperty(newDataProperty);
				
			}
			
		}

	}
	
	/**
	 * @param owlDataProperty
	 * @return
	 */
	private OntologyDataProperty createDataProperty(OWLDataProperty owlDataProperty) {
		
		IRI iriDataProperty = owlDataProperty.getIRI();
		String displayName = iriDataProperty.getFragment();
		String nameSpace = iriDataProperty.getNamespace();
		
		OntologyDataProperty ontologyDataProperty = new OntologyDataProperty(iriDataProperty.toString(),
																displayName, nameSpace);
		this.mapEquivalentDataProperties.put(iriDataProperty.toString(), ontologyDataProperty);
		
		Set<OWLDataPropertyExpression> owlDataSubProperties = owlDataProperty.getSubProperties(owlOntology);
		
		for (OWLDataPropertyExpression owlDataSubProperty: owlDataSubProperties) {
			
			if (owlDataSubProperty instanceof OWLDataProperty) {
				
				OntologyDataProperty ontologyDataSubProperty = createDataProperty((OWLDataProperty) owlDataSubProperty);
				ontologyDataProperty.addSubDataProperty(ontologyDataSubProperty);
			}
			
		}
		
		return ontologyDataProperty;
	}
	

	private Set<OntologyObjectProperty> getOntologyObjectProperties(Set<OWLObjectProperty> owlObjectProperties) {
		
		Set<OntologyObjectProperty> classObjectProperties = new HashSet<OntologyObjectProperty>();
		
		for (OWLObjectProperty owlObjectProperty : owlObjectProperties) {
			
//			System.out.println("getOntologyObjectProperties --> Is owlOP: " + owlObjectProperty.getIRI().toString() + " in Hierarchy: " + mapEquivalentObjectProperties.containsKey(owlObjectProperty.getIRI().toString()));
			if (mapEquivalentObjectProperties.containsKey(owlObjectProperty.getIRI().toString())) {
				classObjectProperties.add(mapEquivalentObjectProperties.get(owlObjectProperty.getIRI().toString()));
			}

			
		}
		
		return classObjectProperties;
		
	}
	

	private Set<OntologyDataProperty> getOntologyDataProperties(Set<OWLDataProperty> owlDataProperties) {
		
		Set<OntologyDataProperty> classDataProperties = new HashSet<OntologyDataProperty>();
		
		for (OWLDataProperty owlDataProperty : owlDataProperties) {
			
			if (mapEquivalentDataProperties.containsKey(owlDataProperty.getIRI().toString())) {
				classDataProperties.add(mapEquivalentDataProperties.get(owlDataProperty.getIRI().toString()));
			}
			
		}
		
		return classDataProperties;
		
	}
	
	/**
	 * @return the topObjectProperty
	 */
	public OntologyObjectProperty getTopObjectProperty() {
		return topObjectProperty;
	}

	/**
	 * @return the topDataProperty
	 */
	public OntologyDataProperty getTopDataProperty() {
		return topDataProperty;
	}
	
	
	
//--------------------------------------------------------------------------------------------------------------------------------	


	public void printProp() {
		
    	System.out.println("printProp -> ObjectProperties -> Printing Object Properties");
    	
    	printObjectProp(this.topObjectProperty);
    	
    	System.out.println("printProp -> DataProperties -> Printing Data Properties");
    	
    	printDataProp(this.topDataProperty);
    	
	}
	
	private void printObjectProp(OntologyObjectProperty top) {
		
    	System.out.println("printObjectProp -> ObjectProperties -> op: " + top.getIRI());
    	
    	ArrayList<OntologyObjectProperty> subOProps = top.getSubObjectProperties();
    	
    	for(OntologyObjectProperty subOP: subOProps) {
    		printObjectProp(subOP);
    	}
		
	}
	
	private void printDataProp(OntologyDataProperty tdp) {
		
    	System.out.println("printDataProp -> DataProperties -> dp: " + tdp.getIRI());
    	
    	ArrayList<OntologyDataProperty> subDProps = tdp.getSubDataProperties();
    	
    	for(OntologyDataProperty subDP: subDProps) {
    		printDataProp(subDP);
    	}
		
	}
	
	private void printClass (OntologyClass oClass) {
		
		System.out.println();
		System.out.println("printClass --> Class Name: " + oClass.getDisplayName());

		System.out.println("printClass --> Has Object Properties : " + oClass.hasObjectProperties());
		if (oClass.hasObjectProperties()) {
			System.out.println("printClass --> Printing Object Properties for : " + oClass.getDisplayName());
			ArrayList<OntologyObjectProperty> objectProperties = oClass.getObjectProperties();
			System.out.println("Number of object properties: " + objectProperties.size());
			for (OntologyObjectProperty oProperty: objectProperties) {
				System.out.println(oProperty.getDisplayName());
			}
		}
		
		System.out.println("printClass --> Has Data Properties : " + oClass.hasDataProperties());
		if (oClass.hasDataProperties()) {
			System.out.println("printClass --> Printing Data Properties for : " + oClass.getDisplayName());
			ArrayList<OntologyDataProperty> dataProperties = oClass.getDataProperties();
			System.out.println("Number of data properties: " + dataProperties.size());
			for (OntologyDataProperty dProperty: dataProperties) {
				System.out.println(dProperty.getDisplayName());
			}
		}
		
		if (oClass.hasSubclasses()) {
			System.out.println("printClass --> Printing SubClasses for : " + oClass.getDisplayName());
			ArrayList<OntologyClass> subClasses = oClass.getSubclasses();
//			System.out.println("Number of subclasses: " + subClasses.size());
			for (OntologyClass oSubClass: subClasses) {
				printClass(oSubClass);				
			}

		}
		
	}
	

	public static void main(String[] args) {
		
		try {
//			OntologyModelConstructor ontmConstructor = new OntologyModelConstructor("C:/Users/Manuel/Desktop/Ontologias/pizza.owl");
			OntologyModelConstructor ontmConstructor = new OntologyModelConstructor("C:/Users/Manuel/Desktop/Ontologias/myOnto.owl");
//			OntMConstructor ontmConstructor = new OntMConstructor("C:/Users/Manuel/Desktop/Ontologias/onto2.owl");
//			OntMConstructor ontmConstructor = new OntMConstructor("C:/Users/Manuel/Desktop/Ontologias/onto.rdf");
			
			ontmConstructor.printClass(ontmConstructor.getThing());
			
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
