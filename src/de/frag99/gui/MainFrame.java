package de.frag99.gui;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;

public class MainFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3350883501420379251L;
	private LeftPanel leftP;
	private RightPanel rightP;
	
	public MainFrame() {
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 700);
		this.setLayout(new GridLayout(1, 2, 5, 5));
		this.setLocationRelativeTo(null);
		this.setTitle("Rhyme Generator");
		
		leftP = new LeftPanel();
		this.add(leftP);
		
		rightP = new RightPanel();
		this.add(rightP);
		
		
		this.setVisible(true);
	}
	
	public void reenable() {
		leftP.reenable();
	}
	
	public void resetNotification() {
		rightP.resetNotification();
	}
	
	public void printText(ArrayList<String> results) {
		rightP.printText(results);
	}
	
	public void printNotification(String text) {
		rightP.printNotification(text);
	}
	
	public void addNotification(String text) {
		rightP.addNotification(text);
	}
	
	public void resetTextField() {
		this.rightP.resetTextField();
	}
}
