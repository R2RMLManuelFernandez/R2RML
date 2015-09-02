package view.triplesMap.PredicateObject;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

/**
 * Select a predicate map from the predicate-object Map
 * 
 * @author Manuel Fernandez Perez
 *
 */
public class PredicateObjectMapPredicateSelector extends JDialog {

	private static final long serialVersionUID = -6111210302864799952L;
	
	private final JPanel contentPanel = new JPanel();
	private int sizePred;
	private String[] predNames;
	private JComboBox<String> comboBox;
	private boolean cancel = false;
	private int predSelected;

	/**
	 * Create the dialog.
	 * @param predicateObjectSelector 
	 */
	public PredicateObjectMapPredicateSelector(JFrame frame, int paramSizeP) {
		
		super(frame, true);
		this.sizePred = paramSizeP;
		predNames = new String[sizePred];
		for (int i = 0; i < sizePred; i++) {
			predNames[i] = "Predicate " + (i + 1);
		}
		initialize();
	}

	/**
	 * 
	 */
	private void initialize() {
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 250);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[grow]", "[grow][30px:n][30px:n][grow]"));

		JLabel labelSelectPredicate = new JLabel("Select the Predicate");
		contentPanel.add(labelSelectPredicate, "cell 0 1,alignx center");

		comboBox = new JComboBox<String>();
		contentPanel.add(comboBox, "cell 0 2,growx");
		
		DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel<String>(predNames);
		comboBox.setModel(comboModel);
		comboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				comboBoxActionPerformed(e);
				
			}
		});

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				okButtonActionPerformed(e);
				
			}
		});
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonCancelActionPerformed(e);
				
			}
		});
		buttonPane.add(cancelButton);

	}
	
	/**
	 * @param e
	 */
	protected void okButtonActionPerformed(ActionEvent e) {
		
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
		
		predSelected = comboBox.getSelectedIndex();

	}

	/**
	 * Checks if the cancel flag in the dialog is raised
	 * 
	 * @return
	 */
	public boolean checkCancel() {
		
		return cancel;
				
	}
	
	/**
	 * @return the predObjSelected
	 */
	public int getPredSelected() {
		return predSelected;
	}

}
