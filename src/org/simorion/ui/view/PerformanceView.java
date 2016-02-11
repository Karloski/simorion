package org.simorion.ui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * GUI for the SimoriOn
 * all buttons are created and added - all containing action listeners
 * 
 * @author Petar Krstic
 * @version 1.0
 */

public class PerformanceView extends JFrame { 
	// All the grid buttons are MidiButtons(extended JButton)
	// Initialise the array to hold them all
	MidiButton[] allMidiButtons = new MidiButton[256];
	
	private JPanel 		buttonPanel = new JPanel();
	private JTextField	dispLCD		= new JTextField();
	// All the mode buttons
	final ModeButton 	btnL1 		= new ModeButton("L1");
	final ModeButton 	btnL2 		= new ModeButton("L2");
	final ModeButton 	btnL3 		= new ModeButton("L3");
	final ModeButton 	btnL4 		= new ModeButton("L4");
	final ModeButton 	btnR1 		= new ModeButton("R1");
	final ModeButton 	btnR2 		= new ModeButton("R2");
	final ModeButton 	btnR3 		= new ModeButton("R3");
	final ModeButton 	btnR4 		= new ModeButton("R4");
	
	final ONButton		btnON 		= new ONButton("ON");
	
	final OKButton 		btnOK 		= new OKButton("OK");
	
	/*
	 * Class to make a button circular (no text)
	 */
	private class CircularButton extends JButton {
		// Code here to make buttons circular
		
	}
	
	/*
	 * Class to make a button circular (with text)
	 */
	private class CircularTextButton extends JButton {
		// Mode buttons and the ON/OK contain text whereas MidiButtons do not
		CircularTextButton(String s) {
			super(s);
			
			setContentAreaFilled(false);		
		}
		
		protected void paintComponent(Graphics g){
			
		}
	}
	
	/*
	 * A MidiButton extends CircularButton
	 * Takes two parameters which distinguish it's co-ordinates
	 * All the buttons in the grid
	 */
	private class MidiButton extends CircularButton { // Doesn't extend CircularTextButton 
		// Variables to return where in the grid the button is
		private int x;
		private int y;
		
		/**
		 * @return x - the x location in the grid of the button
		 */
		public int getXLoc() { return x; }
		
		/**
		 * @return y - the y location in the grid of the button
		 */
		public int getYLoc() {return y; }
		
		MidiButton(int xLoc, int yLoc) {
			// When instantiating each button in the grid they are all given 
			// their x and y co-ordinates
			x = xLoc;
			y = yLoc;
			addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent me) {
					// Code here for what to do when the button is pressed
				}
			});
		}
	}
	
	/*
	 * ModeButton extends CircularTextButton
	 * Distinguishes what happens when a mode button is pressed
	 */
	private class ModeButton extends CircularTextButton {
		ModeButton(String s) {
			super(s);
			addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent me){
					// Code here for what to do when the mode button is pressed
				}
			});
		}
	}
	
	/*
	 * Class for an OKButton - extends CircularTextButton
	 */
	private class OKButton extends CircularTextButton {
		OKButton(String s) {
			super(s);
			addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent me){
					// What to do when the OK button is pressed
				}
			});
		}
	}
	
	/*
	 * Class for an ONButton - extends CircularTextButton
	 */
	private class ONButton extends CircularTextButton {
		ONButton(String s) {
			super(s);
			addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent me){
					// What to do when the ON button is pressed
				}
			});
		}
	}
	
	/*
	 * Where all the buttons are added to the UI 
	 * There is no set layout and all the buttons have their bounds set
	 * Also where the array of MidiButtons is made
	 */
	public PerformanceView() {
		setTitle("Simori-ON");
		setSize(505, 530);
		
		setLayout(null);
		// Panel to hold all the MidiButtons
		buttonPanel.setBounds(49, 49, 402, 402); add(buttonPanel);
		buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		buttonPanel.setLayout(null);
		// Setting bounds for each button
		btnON.setBounds(230, 5, 40, 40);	 add(btnON);
		btnON.setBorder(BorderFactory.createLineBorder(Color.black));		
			
		btnL1.setBounds(5, 70, 40, 40); 	 add(btnL1);
		btnL1.setBorder(BorderFactory.createLineBorder(Color.black));
		btnR1.setBounds(455, 70, 40, 40);	 add(btnR1);
		btnR1.setBorder(BorderFactory.createLineBorder(Color.black));
		
		btnL2.setBounds(5, 145, 40, 40);	 add(btnL2);
		btnL2.setBorder(BorderFactory.createLineBorder(Color.black));
		btnR2.setBounds(455, 145, 40, 40);	 add(btnR2);
		btnR2.setBorder(BorderFactory.createLineBorder(Color.black));
		
		btnL3.setBounds(5, 220, 40, 40);	 add(btnL3);
		btnL3.setBorder(BorderFactory.createLineBorder(Color.black));
		btnR3.setBounds(455, 220, 40, 40);	 add(btnR3);
		btnR3.setBorder(BorderFactory.createLineBorder(Color.black));
		
		btnL4.setBounds(5, 295, 40, 40);	 add(btnL4);
		btnL4.setBorder(BorderFactory.createLineBorder(Color.black));
		btnR4.setBounds(455, 295, 40, 40);	 add(btnR4);
		btnR4.setBorder(BorderFactory.createLineBorder(Color.black));
		
		dispLCD.setBounds(100, 455, 200, 40);add(dispLCD);
		dispLCD.setEditable(false);
		// Read only but with white background
		dispLCD.setBackground(Color.WHITE);
		dispLCD.setBorder(BorderFactory.createLineBorder(Color.black));
		
		btnOK.setBounds(360, 455, 40, 40);	 add(btnOK);
		btnOK.setBorder(BorderFactory.createLineBorder(Color.black));
		
		int xLocationOfButton = 0;
		int yLocationOfButton = 0;
		// j is a variable which is incremented when the end of the grid is reached (every 16 buttons)
		int j = 0;
		
		for(int k=0; k < allMidiButtons.length; k++) {
			allMidiButtons[k] = new MidiButton((k%16), j);
			// Create new MidiButton with it's location parameters
			if((k+1) % 16 == 0){
				// Increment j every 16 buttons
				j++;
			}	
			
			allMidiButtons[k].setBounds(1+(25*xLocationOfButton), 376-(25*yLocationOfButton), 25, 25); buttonPanel.add(allMidiButtons[k]);
			// Setting bounds of the button depending on it's co-ords
			if((k+1)%16 == 0) {
				// When reaching 16 buttons - go back to left hand side of grid and increment y
				xLocationOfButton = 0;
				yLocationOfButton++;
			}
			else {
				// If not at the end of the grid - just increment x
				xLocationOfButton++;
			}

		}	
		
	}
	
	public static void main(String[] argv) {
		JFrame frame = new PerformanceView();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
	}
}
