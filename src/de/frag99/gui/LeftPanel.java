package de.frag99.gui;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import de.frag99.machine.RhymeGenerator;

public class LeftPanel extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7152458506548820465L;
	
	private JTextField inputField;
	private JButton searchButton;
	private RadioButtonPanel rhymeSelect;
	private JComboBox<String> languageSelect;
	private final String[] languages  = {"deutsch", "english", "français"};
	
	public LeftPanel() {
		
		
		this.setLayout(new GridLayout(4, 1, 10, 20));
		
		
		inputField = new JTextField();
		inputField.setPreferredSize(new Dimension(50, 50));
		inputField.addActionListener(this);
		inputField.requestFocus();
		inputField.setBorder(new TitledBorder("Input"));
		
		searchButton = new JButton();
		searchButton.setText("Search");
		searchButton.setFocusable(false);
		searchButton.addActionListener(this);
		
		
		
		languageSelect = new JComboBox<>(languages);
		languageSelect.setBorder(new TitledBorder("Language"));
		
		
		this.add(inputField);
		this.add(searchButton);
		
		rhymeSelect = new RadioButtonPanel();
		rhymeSelect.setBorder(new TitledBorder("Type of rhyme"));
		this.add(rhymeSelect);
		
		
		this.add(languageSelect);
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==inputField || e.getSource() == searchButton) {
			RhymeGenerator.userInput = inputField.getText();
			RhymeGenerator.rhymeType = rhymeSelect.getSelectedButton();
			
			
			RhymeGenerator.lang = String.valueOf(languageSelect.getSelectedItem());
			
			inputField.setEnabled(false);
			searchButton.setEnabled(false);
			rhymeSelect.setButtonsEnabled(false);
			languageSelect.setEnabled(false);
			
			//search rhymes
			RhymeGenerator.start();
		}

	}
	
	public void reenable() {
		inputField.setEnabled(true);
		searchButton.setEnabled(true);
		rhymeSelect.setButtonsEnabled(true);
		languageSelect.setEnabled(true);
		inputField.requestFocus();
		inputField.selectAll();
	}
	

	
}
