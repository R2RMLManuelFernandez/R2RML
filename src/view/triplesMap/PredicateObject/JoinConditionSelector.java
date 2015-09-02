package view.triplesMap.PredicateObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

public class JoinConditionSelector extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5749344593679400219L;
	
	private int sizeJoinConditions;
	private String[] joinConditionsNames;
	private JButton buttonOk;
	private JButton buttonCancel;
	private JComboBox<String> comboBox;
	private DefaultComboBoxModel<String> comboBoxModel;
	private boolean cancel = false;
	private int joinConditionSelected;
	/**
	 * Create the dialog.
	 * @param frame 
	 * @param paramTrilesMap 
	 */
	public JoinConditionSelector(JFrame frame, int paramJoinConditions) {
		
		super(frame, true);
		this.sizeJoinConditions = paramJoinConditions;

		joinConditionsNames = new String[sizeJoinConditions];
		for (int i = 0; i < sizeJoinConditions; i++) {
			joinConditionsNames[i] = "Join Condition " + (i + 1);
		}
		initialize();

	}

	/**
	 * 
	 */
	private void initialize() {
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new MigLayout("", "[grow]", "[grow][][][grow][]"));
		
		JLabel labelSelectTheJoinCondition = new JLabel("Select the join condition");
		getContentPane().add(labelSelectTheJoinCondition, "cell 0 1,alignx center");
		
		comboBox = new JComboBox<String>();
		comboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				comboBoxActionPerformed(e);
				
			}
		});
		getContentPane().add(comboBox, "cell 0 2,growx");
		
		comboBoxModel = new DefaultComboBoxModel<>(joinConditionsNames);
		comboBox.setModel(comboBoxModel);
		
		buttonOk = new JButton("OK");
		buttonOk.setName("OK");
		buttonOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonOkActionPerformed(e);
				
			}
		});
		getContentPane().add(buttonOk, "flowx,cell 0 4,alignx trailing");
		
		buttonCancel = new JButton("Cancel");
		buttonCancel.setName("Cancel");
		buttonCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonCancelActionPerformed(e);
				
			}
		});
		getContentPane().add(buttonCancel, "cell 0 4,alignx trailing");
		
	}
	
	/**
	 * @param e
	 */
	protected void buttonOkActionPerformed(ActionEvent e) {
		
		this.setVisible(false);
		
	}
	
	/**
	 * @param e
	 */
	protected void buttonCancelActionPerformed(ActionEvent e) {
		
		cancel  = true;
		this.dispose();
		
	}

	/**
	 * @param e
	 */
	protected void comboBoxActionPerformed(ActionEvent e) {	
		
		joinConditionSelected = comboBox.getSelectedIndex();

	}

	/**
	 * Checks if the cancel flag in the dialog is raised
	 * 
	 * @return
	 */
	public boolean checkCancel() {
		
		return cancel ;
				
	}
	
	/**
	 * @return the predObjSelected
	 */
	public int getJoinConditionSelected() {
		return joinConditionSelected;
	}

}
