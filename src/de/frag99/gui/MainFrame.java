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
		this.setSize(450, 600);
		this.setLayout(new GridLayout(1, 2, 5, 5));
		this.setLocationRelativeTo(null);
		this.setTitle("DoubleRyhme");
		
		leftP = new LeftPanel();
		this.add(leftP);
		
		rightP = new RightPanel();
		this.add(rightP);
		
		
		this.setVisible(true);
	}
	
	public void reenable() {
		leftP.reenable();
	}
	
	public void printText(ArrayList<String> results) {
		rightP.printText(results);
	}
}
