package de.frag99.gui;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class RadioButtonPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2590874648736166974L;
	private JRadioButton doubleRh;
	private JRadioButton vowelRh;
	private JRadioButton classicRh; 
	private ButtonGroup rhType;
	
	public RadioButtonPanel() {
		
		this.setBackground(Color.LIGHT_GRAY);
		this.setLayout(new GridLayout(1, 3));
		
		doubleRh = new JRadioButton("double");
		vowelRh = new JRadioButton("vowel");
		classicRh = new JRadioButton("classic");
		rhType = new ButtonGroup();
		rhType.add(doubleRh);
		rhType.add(vowelRh);
		rhType.add(classicRh);
		rhType.setSelected(vowelRh.getModel(), true);
		
		this.add(doubleRh);
		this.add(vowelRh);
		this.add(classicRh);
	}
	
	public void setButtonsEnabled(boolean value) {
		doubleRh.setEnabled(value);
		vowelRh.setEnabled(value);
		classicRh.setEnabled(value);
	}
	
	public String getSelectedButton() {
		if(doubleRh.isSelected()) {
			return "double";
		}
		if(vowelRh.isSelected()) {
			return "vowel";
		}
		return "classic";
	}
}
