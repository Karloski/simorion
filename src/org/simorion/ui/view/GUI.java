package org.simorion.ui.view;

import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
		draw();
	}
	
	/**
	 * Should be called when a mode is changed.
	 */
	public void draw() {
		
		// Invalidate the view for re-drawing.
		invalidate();
		
		// Get the current view.
		View view = ModeMaster.getInstance().getView();
		
		// Draw the Simori-ON based on the current view.
		setTitle(view.getTitle()); // Set the title.
		setSize(view.getSize().left, view.getSize().right); // Set the size in pixels.
		
		outerPanel = (JPanel) add(view.getOuterPanel()); // Add the outer panel.
		buttonPanel = (JPanel) outerPanel.add(view.getButtonPanel()); // Add the inner panel.
		
		// Add the ON and OK buttons.
		outerPanel.add(view.getOnButton());
		outerPanel.add(view.getOKButton());
		
		// Add the LCD Screen.
		outerPanel.add(view.getLCDScreen());
		
		// Add each MODE button.
		for (AbstractButton button : view.getModeButtons()) {
			outerPanel.add(button);
		}
		
		// Add each MIDI (matrix) button.
		for (AbstractButton button : view.getMidiButtons()) {
			buttonPanel.add(button);
		}
		
	}
	
	/**
	 * Should be called when the current mode is altered.
	 */
	public void update() {
		
	}

	public static void main(String[] argv) {
		JFrame frame = new GUI();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
