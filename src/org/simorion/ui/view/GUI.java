package org.simorion.ui.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.simorion.ui.controller.ModeMaster;

/**
 * GUI for the SimoriOn
 * all buttons are created and added - all containing action listeners
 * 
 * @author Petar Krstic
 * @version 2.0
 */

public class GUI extends JFrame { 
	// All the grid buttons are MidiButtons(extended JButton)
	// Initialise the array to hold them all
	AbstractButton[] allMidiButtons = new AbstractButton[256];
	
	private JPanel 		outerPanel	= new JPanel();
	private JPanel 		buttonPanel = new JPanel();
	
	/*
	 * Where all the buttons are added to the UI 
	 * There is no set layout and all the buttons have their bounds set
	 * Also where the array of MidiButtons is made
	 */
	public GUI() {
		
		View view = ModeMaster.getInstance().getView();
		
		setTitle(view.getTitle());
		setSize(view.getSize().left, view.getSize().right);		
		outerPanel = (JPanel) add(view.getOuterPanel());
		buttonPanel = (JPanel) outerPanel.add(view.getButtonPanel());
		outerPanel.add(view.getOnButton());
		outerPanel.add(view.getOKButton());
		outerPanel.add(view.getLCDScreen());
		for (AbstractButton button : view.getModeButtons()) {
			outerPanel.add(button);
		}
		for (AbstractButton button : view.getMidiButtons()) {
			buttonPanel.add(button);
		}
		
	}

	public static void main(String[] argv) {
		JFrame frame = new GUI();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
