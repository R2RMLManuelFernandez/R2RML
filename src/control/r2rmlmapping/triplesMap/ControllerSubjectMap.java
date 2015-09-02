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

package control.r2rmlmapping.triplesMap;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import view.triplesMap.subject.ViewSubject;
import model.r2rmlmapping.triplesMap.SubjectMap;

/**
 * @author Manuel Fernandez Perez
 * 
 * Controller for the SubjectMap view *
 */
public class ControllerSubjectMap implements ActionListener{

	private ViewSubject view;
	private SubjectMap model;
	
	/**
	 * @param paramView
	 * @param paramModel
	 */
	public ControllerSubjectMap(ViewSubject paramView, SubjectMap paramModel) {
		view = paramView;
		model = paramModel;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("ControllerSubjectMap --> action ");
		Component source = (Component) e.getSource();
		changeModel(source);
	}

	private void changeModel(Component source) {

		if (source.getName().equals("buttonDeleteColumns")) {
			ArrayList<String> cols = view.getSelectedColumns();
			model.removeColumnsByName(cols);
		}

	}

}
