package view.addMappingItem;

import javax.swing.JTextField;

import model.mapping.MappingElement;

/**
 * Auxiliar: view for the model of the mapping element 
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class MappingTextField extends JTextField {

	private static final long serialVersionUID = -2161715131382328708L;

	private MappingElement model;
	
	public MappingTextField() {
	}

	/**
	 * @return the model
	 */
	public MappingElement getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(MappingElement model) {
		this.model = model;
	}

}
