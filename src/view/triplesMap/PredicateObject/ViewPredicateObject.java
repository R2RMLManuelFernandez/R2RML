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

package view.triplesMap.PredicateObject;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import view.R2RMLMain;
import control.r2rmlmapping.triplesMap.ControllerColumnValueObjectMap;
import control.r2rmlmapping.triplesMap.ControllerPredicateMap;
import control.r2rmlmapping.triplesMap.ControllerPredicateObjectMap;
import control.r2rmlmapping.triplesMap.ControllerReferencedObjectMap;
import model.r2rmlmapping.triplesMap.ColumnValueObjectMap;
import model.r2rmlmapping.triplesMap.ObjectMap;
import model.r2rmlmapping.triplesMap.PredicateMap;
import model.r2rmlmapping.triplesMap.PredicateObjectMap;
import model.r2rmlmapping.triplesMap.ReferencingObjectMap;
import net.miginfocom.swing.MigLayout;

/**
 * View to represent the predicate object
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class ViewPredicateObject extends JPanel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8029676945693179675L;

	private JPanel panelPredicates;
	private JPanel panelObjects;
	private JScrollPane scrollPanePredicate;
	private JScrollPane scrollPaneObject;
	private JButton buttonAddPredicate;
	private JButton buttonAddColumnValuedObject;
	private JButton buttonAddReferenceObj;
	private JButton buttonDelete;
	
	private PredicateObjectMap model;

	private static Logger logger = LoggerFactory.getLogger(R2RMLMain.class);
	
	/**
	 * Create the panel.
	 */
	public ViewPredicateObject() {
		setBorder(new TitledBorder(null, "Predicate-Object", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new MigLayout("", "[150.00,grow][150.00,grow]", "[grow][][]"));

		panelPredicates = new JPanel();
		panelPredicates.setLayout(new BoxLayout(panelPredicates, BoxLayout.Y_AXIS));
		
		scrollPanePredicate = new JScrollPane();
		add(scrollPanePredicate, "cell 0 0,grow");
		scrollPanePredicate.setViewportView(panelPredicates);

		panelObjects = new JPanel();
		panelObjects.setLayout(new BoxLayout(panelObjects, BoxLayout.Y_AXIS));
		
		scrollPaneObject = new JScrollPane();
		add(scrollPaneObject, "cell 1 0,grow");
		scrollPaneObject.setViewportView(panelObjects);
		
		buttonAddPredicate = new JButton("Add Predicate");
		buttonAddPredicate.setName("Add Predicate");
		add(buttonAddPredicate, "flowx,cell 0 1,alignx center");
		
		buttonAddColumnValuedObject = new JButton("Add Col Obj");
		buttonAddColumnValuedObject.setName("Add Col Obj");
		add(buttonAddColumnValuedObject, "flowx,cell 1 1,alignx center");
		
		buttonAddReferenceObj = new JButton("Add Ref Obj");
		buttonAddReferenceObj.setName("Add Ref Obj");
		add(buttonAddReferenceObj, "cell 1 1");
		
		buttonDelete = new JButton("Delete");
		buttonDelete.setName("Delete");
		add(buttonDelete, "cell 0 2 2 1,alignx center");
		
	}

	@Override
	public void update(Observable o, Object arg) {

/*		ArrayList<PredicateObjectMap> predicateObjectMaps = triplesMapModel.getPredicateObjectMaps();
		panelPredicateObject.removeAll();
		for (PredicateObjectMap predicateObjectMap: predicateObjectMaps) {
			ViewPredicateObject viewPredicateObject = new ViewPredicateObject();
			viewPredicateObject.setModel(predicateObjectMap);
			ControllerPredicateObject controllerPredicateObject = new ControllerPredicateObject(viewPredicateObject, predicateObjectMap);
			viewPredicateObject.setController(controllerPredicateObject);
			panelPredicateObject.add(viewPredicateObject);
	        System.out.println("ViewTriplesMap --> Deberia haberse actualizado la vista P-O");
		}
		panelPredicateObject.updateUI();*/
		
		ArrayList<PredicateMap> predicateMaps = model.getPredicateMaps();
		logger.trace("ViewPredicateObject update --> numer de predMaps " + predicateMaps.size());
		panelPredicates.removeAll();
		for (PredicateMap predicate: predicateMaps) {
			ViewPredicate viewPredicate = new ViewPredicate();
			viewPredicate.setModel(predicate);
			ControllerPredicateMap controllerPredicateMap = new ControllerPredicateMap(viewPredicate, predicate);
			viewPredicate.setController(controllerPredicateMap);
			panelPredicates.add(viewPredicate);
			logger.trace("ViewPredicateObject update --> Deberia haberse añadido una viewPredicate");
		}

		
		ArrayList<ObjectMap> objects = model.getObjectMaps();
		logger.trace("ViewPredicateObject update --> numer de predObjMaps " + objects.size());
		panelObjects.removeAll();
		for (ObjectMap objectMap : objects) {
			//TODO decidir como qiiero que sea la vista de los objetos
			// a lo mejor no necesito que sea una subvista dentro de una general sino una vista para cada tipo
			//y cambiar la vista cuando se vambie el tipo de map

			String objectType = objectMap.getType();
			
			logger.trace("ViewPredicateObject update --> tipo del object map " + objectType);
			
			if (objectType.equals("Column-Valued")) {
				logger.trace("ViewPredicateObject update --> Deberia haberse añadido una viewColumnValued object");
				ViewObjectColumnValue viewObjectCV = new ViewObjectColumnValue();
				viewObjectCV.setModel((ColumnValueObjectMap) objectMap);
				ControllerColumnValueObjectMap controllerObjectCV = new ControllerColumnValueObjectMap(viewObjectCV, objectMap);
				viewObjectCV.setController(controllerObjectCV);
				panelObjects.add(viewObjectCV);
			}
			else if(objectType.equals("Referencing")) {
				logger.trace("ViewPredicateObject update --> Deberia haberse añadido una viewRefernced Object");
				ViewObjectReferenced viewObjectRef = new ViewObjectReferenced();
				viewObjectRef.setModel((ReferencingObjectMap) objectMap);
				ControllerReferencedObjectMap controllerObjectRef = new ControllerReferencedObjectMap(viewObjectRef, objectMap);
				viewObjectRef.setController(controllerObjectRef);
				panelObjects.add(viewObjectRef);
			}
		}
		panelObjects.repaint();
		panelObjects.revalidate();
		panelObjects.updateUI();
		
		panelPredicates.repaint();
		panelPredicates.revalidate();
		panelPredicates.updateUI();

	}

	/**
	 * @return the model
	 */
	public PredicateObjectMap getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(PredicateObjectMap predicateObjectMap) {
		this.model = predicateObjectMap;
		ArrayList<PredicateMap> predicateMaps = model.getPredicateMaps();
		panelPredicates.removeAll();
		for (PredicateMap predicate: predicateMaps) {
			ViewPredicate viewPredicate = new ViewPredicate();
			viewPredicate.setModel(predicate);
			ControllerPredicateMap controllerPredicateMap = new ControllerPredicateMap(viewPredicate, predicate);
			viewPredicate.setController(controllerPredicateMap);
			panelPredicates.add(viewPredicate);
			logger.trace("ViewPredicateObject setModel --> Deberia haberse añadido una viewPredicate");
		}
		panelPredicates.repaint();
		panelPredicates.revalidate();
		panelPredicates.updateUI();
		
		ArrayList<ObjectMap> objects = model.getObjectMaps();
		logger.trace("ViewPredicateObject setModel --> objects " + objects.size());
		panelObjects.removeAll();
		for (ObjectMap objectMap : objects) {
			//TODO decidir como qiiero que sea la vista de los objetos
			// a lo mejor no necesito que sea una subvista dentro de una general sino una vista para cada tipo
			//y cambiar la vista cuando se vambie el tipo de map
			String objectType = objectMap.getType();
			if (objectType.equals("Column-Valued")) {
				logger.trace("ViewPredicateObject setModel --> Deberia haberse añadido una viewColumnValued object");
				ViewObjectColumnValue viewObjectCV = new ViewObjectColumnValue();
				viewObjectCV.setModel((ColumnValueObjectMap) objectMap);
				ControllerColumnValueObjectMap controllerObjectCV = new ControllerColumnValueObjectMap(viewObjectCV, objectMap);
				viewObjectCV.setController(controllerObjectCV);
				panelObjects.add(viewObjectCV);
			}
			else if(objectType.equals("Referenced")) {
				logger.trace("ViewPredicateObject setModel --> Deberia haberse añadido una viewRefernced Object");
				ViewObjectReferenced viewObjectRef = new ViewObjectReferenced();
				viewObjectRef.setModel((ReferencingObjectMap) objectMap);
				ControllerReferencedObjectMap controllerObjectRef = new ControllerReferencedObjectMap(viewObjectRef, objectMap);
				viewObjectRef.setController(controllerObjectRef);
				panelObjects.add(viewObjectRef);
			}
		}
		panelObjects.repaint();
		panelObjects.revalidate();
		panelObjects.updateUI();
		model.addObserver(this);
		model.notifyObservers();

	}
	
	/**
	 * @param controller the controller to set
	 */
	public void setController(ControllerPredicateObjectMap controller) {
		buttonAddPredicate.addActionListener(controller);
		buttonAddColumnValuedObject.addActionListener(controller);
		buttonAddReferenceObj.addActionListener(controller);
		buttonDelete.addActionListener(controller);
	}

}
