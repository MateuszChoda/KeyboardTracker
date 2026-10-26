package main;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class MainPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private TrackerManager dbmanager;
	
	private JLabel recPresLbl = new JLabel("Last pressed key: ");
	private JLabel recAmoLbl = new JLabel("Total amount pressed: ");
	private JLabel mostPresLbl = new JLabel("Most pressed key: ");
	private JLabel mostAmoLbl = new JLabel("Total amount pressed: ");
	
	private JTextField recPresFld = new JTextField(10);
	private JTextField recAmoFld = new JTextField(10);
	private JTextField mostPresFld = new JTextField(10);
	private JTextField mostAmoFld = new JTextField(10);
	
	private GridBagLayout l = new GridBagLayout();
	private GridBagConstraints gbc = new GridBagConstraints();

	public MainPanel(TrackerManager tracker) throws IOException, SQLException {
		this.dbmanager = tracker;
		setLayout(l);
		TitledBorder tb = new TitledBorder("Panel powitalny");
		this.setBorder(tb);
		this.setFocusable(true);
		
		addObjects(recPresLbl, this, 0, 0, 1, 1);
		addObjects(recAmoLbl, this, 0, 1, 1, 1);
		addObjects(mostPresLbl, this, 0, 2, 1, 1);
		addObjects(mostAmoLbl, this, 0, 3, 1, 1);
		
		addObjects(recPresFld , this, 1, 0, 1, 1);
		addObjects(recAmoFld, this, 1, 1, 1, 1);
		addObjects(mostPresFld, this, 1, 2, 1, 1);
		addObjects(mostAmoFld, this, 1, 3, 1, 1);
		
		recPresFld.setEditable(false);
		recAmoFld.setEditable(false);
		mostPresFld.setEditable(false);
		mostAmoFld.setEditable(false);
	}
	
	private void addObjects(Component comp, Container cont, int gridX, int gridY, int gridWidth, int gridHeight) {
		gbc.gridx = gridX;
        gbc.gridy = gridY;

        gbc.gridwidth = gridWidth;
        gbc.gridheight = gridHeight;
        gbc.insets = new Insets(10,10,10,10);
        
        gbc.weightx = 1;
        gbc.weighty = 1;
        
        gbc.anchor = GridBagConstraints.WEST;

        l.setConstraints(comp, gbc);
        cont.add(comp);
	}
	
	public void updateRecent(String key) throws NumberFormatException, SQLException {
		dbmanager.addKey(key);
		dbmanager.incrementKey(key);
		ResultSet amountResult = dbmanager.getAmount(key);
		double amount = 0;
		if(amountResult != null) {
			while (amountResult.next()) {
				double increase = Double.parseDouble(amountResult.getString(1));
				amount=increase+1;
			}
		}
		recPresFld.setText(key);
		recAmoFld.setText(Double.toString(amount));
	}
	
	public void updateMost() throws SQLException {
		ResultSet keyResult = dbmanager.getHighest();
		String key = "";
		if(keyResult != null) {
			while (keyResult.next()) {
				String name = keyResult.getString(1);
				key = name;
			}
		}

		ResultSet amountResult = dbmanager.getAmount(key);
		double amount = 0;
		if(amountResult != null) {
			while (amountResult.next()) {
				double increase = Double.parseDouble(amountResult.getString(1));
				amount=increase+1;
			}
		}
		mostPresFld.setText(key);
		mostAmoFld.setText(Double.toString(amount));
	}

}
