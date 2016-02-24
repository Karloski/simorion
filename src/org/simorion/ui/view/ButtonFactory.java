package org.simorion.ui.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import org.simorion.ui.controller.ModeMaster;

public class ButtonFactory {

	public enum Button {
		CIRCULAR, CIRCULARTEXT, MIDI, MODE, ONOFF, OK
	}
	
	public static JButton createButton(int x, int y) {		
		return new MidiButton(x, y);
	}
	
	public static JButton createButton(String text, Button buttonType) {
		
		switch (buttonType) {
			case CIRCULAR:
				return new CircularButton();
			case CIRCULARTEXT:
				return new CircularTextButton(text);
			case MODE:
				return new ModeButton(text);
			case OK:
				return new OKButton(text);
			case ONOFF:
				return new ONButton(text);
			default:
				return null;		
		}
		
	}
	
	/*
	 * Class to make a button circular (no text)
	 */
	private static class CircularButton extends JButton {
					 
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
	private static class CircularTextButton extends JButton {
		// Mode buttons and the ON/OK contain text whereas MidiButtons do not
		CircularTextButton(String s) {
			setBackground(Color.white);
			setContentAreaFilled(false);
			setFont(new Font("Cambria", Font.PLAIN, 21));
			setText(s);
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
	private static class MidiButton extends CircularButton { // Doesn't extend CircularTextButton 
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
	private static class ModeButton extends CircularTextButton {
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
	private static class OKButton extends CircularTextButton {
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
	private static class ONButton extends CircularTextButton {
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
	
}
