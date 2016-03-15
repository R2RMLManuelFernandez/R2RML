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

package view.triplesMap.subject;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import model.database.Column;
import model.r2rmlmapping.triplesMap.SubjectMap;
import net.miginfocom.swing.MigLayout;
import control.r2rmlmapping.triplesMap.ControllerSubjectMap;

/**
 * View to represent the subject
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class ViewSubject extends JPanel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7507373384133193262L;
	private JTextField textFieldSubject;
	private JTextField textFieldRDFClass;
	private SubjectMap subjectModel;
	private JList<String> columnList;
	private DefaultListModel<String> listModel;
	private JScrollPane scrollPane;
	private JButton buttonDeleteColumns;

	/*	private JButton buttonModify;
	private JFrame frame;
	private ModifySubjectDialog modifySubjectDialog;
	 */
	
	/**
	 * Create the panel.
	 */
	public ViewSubject() {
		setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Subject", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		setLayout(new MigLayout("", "[grow]", "[50.00][161.00][][50.00]"));
		
		textFieldSubject = new JTextField();
		add(textFieldSubject, "cell 0 0,growx");
		textFieldSubject.setColumns(10);
		
		listModel = new DefaultListModel<String>();
		
		scrollPane = new JScrollPane();
		add(scrollPane, "cell 0 1,grow");
		columnList = new JList<String>();
		scrollPane.setViewportView(columnList);
		columnList.setModel(listModel);
		
		buttonDeleteColumns = new JButton("Delete Columns");
		buttonDeleteColumns.setName("buttonDeleteColumns");
		add(buttonDeleteColumns, "cell 0 2,alignx trailing");
		
		textFieldRDFClass = new JTextField();
		add(textFieldRDFClass, "cell 0 3,growx");
		textFieldRDFClass.setColumns(10);
		textFieldRDFClass.setEditable(false);
/*		
		this.frame = frame;
		
		buttonModify = new JButton("Modify");
		add(buttonModify, "cell 0 3,alignx right");
		buttonModify.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					buttonModifySubjectActionPerformed(e);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		*/
	}
/*
	protected void buttonModifySubjectActionPerformed(ActionEvent e) {
		
		modifySubjectDialog = new ModifySubjectDialog(frame, subjectModel.getSubject(), subjectModel.getRdfClass());
		modifySubjectDialog.pack();
		modifySubjectDialog.setLocationRelativeTo(frame);
		modifySubjectDialog.setVisible(true);
		modifySubjectDialog.setAlwaysOnTop(true);
		
		//No se para aqui a esperar por el JDialog, consume los datos (que no han podido cambiar) y sigue
		//cuando se cambia algo en el Jdialog ya se ha pasado de este punto
		//Voy a cambiar esto, en vez de hacer JDialogs voy a interntar cambiarlo directamente en la interfaz
		//No creo que haya problema con los DnD y los problemas de los popuphandler creo que los puedo areglar con jdialogs
		//Tampoco tengo que actualizar la vista del r2rmlmapping cuando inicia el programa por que estara vacio ni 
		//AÑADIRLE EL PRIMER TRIPLESMAP NADA MAS EMPEZAR
		//eso ya lo hare con el boton añadir de la interfaz del triples map
		//HAY QUE DIVIDIR LA INTERFAZ ENTRE VISTA MODELO Y CONTROLADOR
		//EN LOS JDIALOG NO LO HAGO, JUNTO EL CONTROLADOR CON LA VISTA
		//EN EL CONTROLADOR VAN LOS BUTTONACTIONPERFORMED
		//Realmente en los jdialog creo que daria igual por que no hay modelo y no hay que hacer diagrama de modelo de ello
		  
		 En el modelo de subject tengo la tabla dde la BBDD y el namespace de la ontologia
		 Creo que no tengo que guardar la BBDD en el r2rmlmapping, ¿pero el namespace de la ontologia? creo que no, ya se 
		 guarda dentro de cada triples map, pero la verdad me ahorraria bastante espacio de almacenamiento
		 Creo que tengo que cambiar la forma de iniciar el modelo del subject Map, tengo que pedir la tabla de la BBDD 
		 a la que corresponde
		
		
		if (!(modifySubjectDialog.checkCancel())) {

			//get data from JDialog
			
			String subject = modifySubjectDialog.getSubject();
	        System.out.println("ViewSubject --> Nuevo subject " + subject);
			String rdfClass = modifySubjectDialog.getRDFClass();
	        System.out.println("ViewSubject --> Nueva rdfClass " + rdfClass);
			ArrayList<String> columns = modifySubjectDialog.getColumns();
			
			//pass data to subjectMap
			subjectModel.setSubject(subject);
			subjectModel.setRdfClass(rdfClass);
			subjectModel.setColumns(columns);
	        System.out.println("ViewSubject --> Aqui tendrian que cambiar los datos del subject");
		}

	}

*/
	/**
	 * @param subjectModel
	 */
	public void setModel(SubjectMap subjectModel) {
		this.subjectModel = subjectModel;
		subjectModel.addObserver(this);
		textFieldSubject.setText(subjectModel.getSubject());
		textFieldRDFClass.setText(subjectModel.getRdfClass().getIRIShow());
		ArrayList<Column> columns = subjectModel.getSubjectColumns();
		listModel.clear();
		for (Column col: columns) {
			listModel.addElement(col.getColumnName());			
		}
        System.out.println("ViewSubject --> establecido modelo para el sujeto");
	}

	/**
	 * @param controller the controller to set
	 */
	public void setController(ControllerSubjectMap controller) {
		buttonDeleteColumns.addActionListener(controller);
        System.out.println("ViewSubject --> establecido controlador para el sujeto");
	}
	
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		textFieldSubject.setText(subjectModel.getSubject());
		textFieldRDFClass.setText(subjectModel.getRdfClass().getIRIShow());
		ArrayList<Column> columns = subjectModel.getSubjectColumns();
		listModel.clear();
		for (Column col: columns) {
			listModel.addElement(col.getColumnName());			
		}

        System.out.println("ViewSubject -->datos actualizados");
	}
	
	/**
	 * @return 
	 * 
	 */
	public ArrayList<String> getSelectedColumns() {
		int[] index = columnList.getSelectedIndices();
		ArrayList<String> selectedColumns = new ArrayList<String>();
		for (int i: index) {
			selectedColumns.add(listModel.get(i));
		}
		return selectedColumns;
		
	}

}
