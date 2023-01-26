package de.frag99.gui;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class RightPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1838089018461373975L;
	private JScrollPane scroll;
	private JTextArea text;
	
	public RightPanel() {
		
			
			this.setBackground(Color.cyan);
			
			text = new JTextArea(30,15);
			
			scroll = new JScrollPane(text);
			scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			
			this.add(scroll);
		}
	
	public void printText(ArrayList<String> results) {
		StringBuilder sb = new StringBuilder();
		for(String s : results) {
			sb.append(s + System.lineSeparator());
		}
		text.setText(sb.toString());
	}
	
}
