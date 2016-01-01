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

package model.r2rmlmapping.triplesMap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Observable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import view.R2RMLMain;
import model.database.Column;

/**
 * Represents a subject map of a R2RML mapping
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class SubjectMap extends Observable {

	private String subject;
	private String rdfClass;
	private HashSet<Column> subjectColumns;
	
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(R2RMLMain.class);

	public SubjectMap() {

		this.subject = "";
		this.rdfClass = "";
		this.subjectColumns = new HashSet<Column>();
		setChanged();
		notifyObservers();

	}
	
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	
	/**
	 * @param subject the subject to set
	 */
	
	public void setSubject(String subject) {
		this.subject = subject;
		setChanged();
		notifyObservers();
        System.out.println("SubjectMap --> Notificada la vista de que ha cambiado el subject");
	}
	
	/**
	 * @return the rdfClass
	 */
	public String getRdfClass() {
		return rdfClass;
	}
	
	/**
	 * @param rdfClass the rdfClass to set
	 */
	public void setRdfClass(String rdfClass) {
		this.rdfClass = rdfClass;
		setChanged();
		notifyObservers();
        System.out.println("SubjectMap --> Notificada la vista de que ha cambiado la rdfClass");
	}

	/**
	 * @param column the column to add
	 */
	public void addColumn(Column column) {
		this.subjectColumns.add(column);
		generateSubject();
		setChanged();
		notifyObservers();
		System.out.println("SubjectMap --> Notificada la vista de que se ha a�adido una columna");
	}
	
	/**
	 * @param column the column to remove
	 */
	public void removeColumn(Column column) {
		this.subjectColumns.remove(column);
		generateSubject();
		setChanged();
		notifyObservers();

	}

	/**
	 * @param cols
	 */
	public void removeColumns(ArrayList<Column> cols) {
		for (Column col: cols) {
			this.subjectColumns.remove(col);
		}
		generateSubject();
		setChanged();
		notifyObservers();
	}
	
	/**
	 * @param cols
	 */
	public void removeColumnsByName(ArrayList<String> colNames) {
		
		System.out.println("SubjectMap --> Borrando las columnas " + colNames.toString());
		
		for (String colName: colNames) {
			
			Iterator<Column> itColumn = subjectColumns.iterator();
			
			while (itColumn.hasNext()) {
				Column col = itColumn.next();
				if (colName.equals(col.getColumnName())) {
					itColumn.remove();
					System.out.println("SubjectMap --> Borrando la columna " + col.getColumnName());
				}
			}
		}
		generateSubject();
		setChanged();
		notifyObservers();
	}
	
	/**
	 * @param columns the new columns of the subject
	 */
	public void setColumns(ArrayList<Column> columns) {
		this.subjectColumns.clear();
		for (Column col: columns) {
			this.subjectColumns.add(col);
			this.subject = this.subject + " {" + col.getColumnName() + "}";
		}
		setChanged();
		notifyObservers();
	}
	
	/**
	 * @return the subjectColumns
	 */
	public ArrayList<Column> getSubjectColumns() {
		
		ArrayList<Column> columns = new ArrayList<Column>();
		for (Column col:this.subjectColumns) {
			columns.add(col);
		}
		return columns;
	}
	

	/**
	 * 
	 */
	private void generateSubject() {
		System.out.println("SubjectMap --> Regenerando subject");
		String subjectBefore = this.subject;
		String tempCols[] = subjectBefore.split("\\{");
		String subjectAfter = "";
		
		for (String x : tempCols) {
			x = x.trim();
			if (x.endsWith("}")) {
				x = x.substring(0, x.length() - 1);
			}
			else {
				System.out.println("SubjectMap --> Subject part sin tratar " + x);
				subjectAfter = subjectAfter.concat(x);
			}
		}
		
		for (Column colInSubject : this.subjectColumns) {
			subjectAfter = subjectAfter.concat(" {" + colInSubject.getColumnName() + "}");	
		}
		
		this.subject = subjectAfter;
		System.out.println("SubjectMap --> Subject after " + subjectAfter);
	}


}
