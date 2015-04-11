package view.tableMapping;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import model.mapping.MappingTable;
import net.miginfocom.swing.MigLayout;

public class ViewTableMapping extends JPanel implements Observer {

	private static final long serialVersionUID = -7473179144746801714L;
	
	private JTable table;
	private JButton btnDeleteall;
	private JButton btnDeleteMapping;
	
	/**
	 * Create the panel.
	 */
	public ViewTableMapping() {
		setLayout(new MigLayout("", "[grow]", "[grow][]"));
		table = new JTable();
		table.setShowVerticalLines(true);
		table.setShowHorizontalLines(true);
		table.setBorder(new LineBorder(Color.DARK_GRAY));
		table.setBackground(UIManager.getColor("Table.alternateRowColor"));
		add(table, "cell 0 0,grow");
		
		btnDeleteall = new JButton("DeleteAll");
		btnDeleteall.setName("btnDeleteAll");
		add(btnDeleteall, "flowx,cell 0 1,alignx trailing");
		
		btnDeleteMapping = new JButton("Delete Mapping");
		btnDeleteMapping.setName("btnDeleteMapping");
		add(btnDeleteMapping, "cell 0 1,alignx trailing");
	}
	
	/**
	 * @param tableModel
	 */
	public void setModel(MappingTable tableModel) {
		table.setModel(tableModel);
		tableModel.getModel().addObserver(this);
	}

	/**
	 * @param controller
	 */
	public void setController(ActionListener controller) {
		btnDeleteMapping.addActionListener(controller);
		btnDeleteall.addActionListener(controller);
	}
	
	/**
	 * @return the table
	 */
	public JTable getTable() {
		return table;
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
	}

}
