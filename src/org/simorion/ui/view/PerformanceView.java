package org.simorion.ui.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.simorion.ui.controller.ModeMaster;

/**
 * GUI for the SimoriOn
 * all buttons are created and added - all containing action listeners
 * 
 * @author Petar Krstic
 * @version 2.0
 */

public class PerformanceView extends JFrame { 
	// All the grid buttons are MidiButtons(extended JButton)
	// Initialise the array to hold them all
	MidiButton[] allMidiButtons = new MidiButton[256];
	
	private JPanel 		outerPanel	= new JPanel();
	private JPanel 		buttonPanel = new JPanel();
	private JTextField	dispLCD		= new JTextField("LCD");
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
					 
		public CircularButton() {
			
			setBackground(Color.white);
			setContentAreaFilled(false);
		}
			 
		protected void paintComponent(Graphics g) {
			if (getModel().isArmed()) {
				g.setColor(Color.orange);
			} else {
			    g.setColor(getBackground());
			}
			g.fillOval(0, 0, 29, 29);
			 
			super.paintComponent(g);
		}
			 
		protected void paintBorder(Graphics g) {
			g.setColor(Color.black);
			g.drawOval(0, 0, 29, 29);
		}
			 
	}
		
	/*
	 * Class to make a button circular (with text)
	 */
	private class CircularTextButton extends JButton {
		// Mode buttons and the ON/OK contain text whereas MidiButtons do not
		CircularTextButton(String s) {
			super(s);
			setBackground(Color.white);
			setContentAreaFilled(false);
			setFont(new Font("Cambria", Font.PLAIN, 21));
		}
			 
		protected void paintComponent(Graphics g) {
			if (getModel().isArmed()) {
				g.setColor(Color.orange);
			} else {
			    g.setColor(getBackground());
			}
			g.fillOval(0, 0, 49, 49);
			 
			super.paintComponent(g);
		}
			 
		protected void paintBorder(Graphics g) {
			g.setColor(Color.black);
			g.drawOval(0, 0, 49, 49);
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
		
		MidiButton(final int xLoc, final int yLoc) {
			// When instantiating each button in the grid they are all given 
			// their x and y co-ordinates
			x = xLoc;
			y = yLoc;
			addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent me) {
					// Code here for what to do when the button is pressed
					
					// Code for changing button colour
					//if(getBackground() == buttonPanel.getBackground()) {
						//setBackground(Color.orange);
					//}
					//else {
						//setBackground(null);
					//}
					
					//
					ModeMaster.getInstance().getMode().onMatrixButtonPress(me, xLoc, yLoc);
				}
			});
		}
	}
	
	/*
	 * ModeButton extends CircularTextButton
	 * Distinguishes what happens when a mode button is pressed
	 */
	private class ModeButton extends CircularTextButton {
		ModeButton(final String s) {
			super(s);
			addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent me){
					// Code here for what to do when the mode button is pressed
					switch(s.charAt(0)) {
					case 'L':
						ModeMaster.getInstance().getMode().onLButtonPress(me, s.charAt(1)-48);
						break;
					case 'R':
						ModeMaster.getInstance().getMode().onRButtonPress(me, s.charAt(1)-48);
						break;
					default:
						throw new RuntimeException("Mode button doesn't know what to do!");
					}
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
					//if(getBackground() == buttonPanel.getBackground()) {
						//setBackground(Color.orange);
					//}
					ModeMaster.getInstance().getMode().onOKButtonPress(me);
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
					ModeMaster.getInstance().getMode().onOnOffButtonPress(me);
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
		setSize(607, 632);
		
		setLayout(null);
		
		// Panel to hold everything
		outerPanel.setBounds(1, 1, 598, 600); add(outerPanel);
		outerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		outerPanel.setLayout(null);
		outerPanel.setBackground(Color.white);
		// Panel to hold all the MidiButtons
		buttonPanel.setBounds(58, 58, 484, 484); outerPanel.add(buttonPanel);
		buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		buttonPanel.setLayout(null);
		buttonPanel.setBackground(Color.white);
		// Setting bounds for each button
		btnON.setBounds(275, 5, 50, 50);	 outerPanel.add(btnON);
		btnON.setBorder(BorderFactory.createLineBorder(Color.black));		
			
		btnL1.setBounds(5, 84, 50, 50); 	 outerPanel.add(btnL1);
		btnL1.setBorder(BorderFactory.createLineBorder(Color.black));
		btnR1.setBounds(545, 84, 50, 50);	 outerPanel.add(btnR1);
		btnR1.setBorder(BorderFactory.createLineBorder(Color.black));
		
		btnL2.setBounds(5, 174, 50, 50);	 outerPanel.add(btnL2);
		btnL2.setBorder(BorderFactory.createLineBorder(Color.black));
		btnR2.setBounds(545, 174, 50, 50);	 outerPanel.add(btnR2);
		btnR2.setBorder(BorderFactory.createLineBorder(Color.black));
		
		btnL3.setBounds(5, 264, 50, 50);	 outerPanel.add(btnL3);
		btnL3.setBorder(BorderFactory.createLineBorder(Color.black));
		btnR3.setBounds(545, 264, 50, 50);	 outerPanel.add(btnR3);
		btnR3.setBorder(BorderFactory.createLineBorder(Color.black));
		
		btnL4.setBounds(5, 354, 50, 50);	 outerPanel.add(btnL4);
		btnL4.setBorder(BorderFactory.createLineBorder(Color.black));
		btnR4.setBounds(545, 354, 50, 50);	 outerPanel.add(btnR4);
		btnR4.setBorder(BorderFactory.createLineBorder(Color.black));
		
		dispLCD.setBounds(120, 545, 240, 50); outerPanel.add(dispLCD);
		dispLCD.setEditable(false);
		// Read only but with white background
		dispLCD.setBackground(Color.WHITE);
		dispLCD.setBorder(BorderFactory.createLineBorder(Color.black));
		dispLCD.setFont(new Font("Cambria", Font.PLAIN, 21));
		
		btnOK.setBounds(432, 545, 50, 50);	 outerPanel.add(btnOK);
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
			
			allMidiButtons[k].setBounds(2+(30*xLocationOfButton), 452-(30*yLocationOfButton), 30, 30); buttonPanel.add(allMidiButtons[k]);
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
