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
	private JTextArea textArea;
	private JTextArea notifications;
	
	public RightPanel() {
		
			
			this.setBackground(Color.cyan);
			
			textArea = new JTextArea(35,22);
			textArea.setEditable(false);
			
			notifications = new JTextArea(4,16);
			notifications.setEditable(false);
			
			scroll = new JScrollPane(textArea);
			scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			
			this.add(notifications);
			
			this.add(scroll);
		}
	
	public void printText(ArrayList<String> results) {
		StringBuilder sb = new StringBuilder();
		for(String s : results) {
			sb.append(s + System.lineSeparator());
		}
		this.textArea.setText(sb.toString());
	}
	
	public void printNotification(String text) {
		this.notifications.setText(text);
	}
	
	public void addNotification(String text) {
		if(notifications.getText().isBlank()) {
			printNotification(text);
		}else {
			this.notifications.setText(notifications.getText() + System.lineSeparator() + text);
		}		
	}
	
	public void resetNotification() {
		notifications.setText("");
	}
	
	public void resetTextField() {
		this.textArea.setText("");
	}
	
}
