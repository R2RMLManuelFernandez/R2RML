/**
 * 
 */
package control.mappingTable;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;

import model.mapping.MappingTable;
import view.tableMapping.ViewTableMapping;

/**
 * Controller class for the view and model of the mapping
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class ControllerMappingTable implements ActionListener {

	private MappingTable model;
	private ViewTableMapping view;	
	
	public ControllerMappingTable(MappingTable m) {
		this.model = m;
	}

	/**
	 * @param source
	 */
	private void changeModel(Component source) {
		if (source.getName().equals("btnDeleteMapping")) {
			JTable table = view.getTable();
			int row = table.getSelectedRow();
			if (row != -1)
				model.removeMappingElement(row);
		}
		else if (source.getName().equals("btnDeleteAll")) {
			int rows = model.getRowCount();
			for (int i = 0; i < rows; i++) {
				model.removeMappingElement(0);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Component source = (Component)e.getSource();
		changeModel(source);
	}

	/**
	 * @return
	 */
	public MappingTable getModel() {
		return model;
	}

	/**
	 * @param view
	 */
	public void setView(ViewTableMapping view) {
		this.view = view;
	}

}
