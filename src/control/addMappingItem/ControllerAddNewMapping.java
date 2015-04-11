/**
 * 
 */
package control.addMappingItem;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.mapping.Mapping;
import model.mapping.MappingElement;
import view.tableMapping.ViewTableMapping;

/**
 * Controller for the view of the table mapping
 *
 * @author Manuel Fernandez Perez
 * 
 */
public class ControllerAddNewMapping implements ActionListener {

	private MappingElement mappingItem;
	private Mapping model;
	private ViewTableMapping view;
	
	public ControllerAddNewMapping(Mapping mappingModel, MappingElement mappingElement) {
		mappingItem = mappingElement;
		model = mappingModel;
	}
	
	/**
	 * Adds the new mapping element to mapping
	 * 
	 * @param source
	 */
	private void changeModel(Component source) {
		if (source.getName().equals("buttonAddMapping")) {
			MappingElement item = copy(mappingItem);
			model.addMapping(item);
		}
		// TODO controlar que pasa si el mappingItem es null
	}
	
	public void setView(ViewTableMapping v) {
		view = v;
	}
	

    /**
     * Defensive copy used in changeModel.
     * 
     * @param item
     * @return
     */
    private MappingElement copy(MappingElement item) {
        MappingElement newItem = new MappingElement(item.getDatabaseColumn(), item.getOntologyElement());
        return newItem;
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Component source = (Component) e.getSource();
		changeModel(source);
		view.repaint();
	}

}
