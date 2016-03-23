package org.simorion.ui.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

import org.simorion.ui.controller.ModeMaster;

/**
 * Factory class for button creation.
 * @author Karl Brown
 *
 */
public class ButtonFactory {

	public enum Button {
		CIRCULAR, CIRCULARTEXT, MIDI, MODE, ONOFF, OK
	}
	
	public enum Mode {
		L1, L2, L3, L4, R1, R2, R3, R4
	}
	
	/**
	 * Creates a MIDI button with coordinate x, y.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @return A new instance of a MIDI button with cooridnate x, y.
	 */
	public static MidiButton createButton(int x, int y) {
		
		MidiButton b = new MidiButton(x, y);
		
		b.setBounds(2 + (30 * (x % 16) - 1), 452 - (30 * y), 30, 30);
		b.setBackground(Color.white);
		b.setBorder(DefaultView.UNLIT_BORDER);
		
		return b;
		
	}
	
	/**
	 * Creates a MODE button for mode {@code mode}.
	 * @param text The text for the button to display.
	 * @param mode The mode which it relates to.
	 * @return A new instance of a MODE button for mode {@code mode}.
	 */
	public static ModeButton createButton(String text, Mode mode) {
		
		ModeButton b = new ModeButton(text, mode);
		
		switch (mode) {
		case L1:
			b.setBounds(5, 84, 50, 50);
			break;
		case L2:
			b.setBounds(5, 174, 50, 50);
			break;
		case L3:
			b.setBounds(5, 264, 50, 50);
			break;
		case L4:
			b.setBounds(5, 354, 50, 50);
			break;
		case R1:
			b.setBounds(545, 84, 50, 50);
			break;
		case R2:
			b.setBounds(545, 174, 50, 50);
			break;
		case R3:
			b.setBounds(545, 264, 50, 50);
			break;
		case R4:
			b.setBounds(545, 354, 50, 50);
			break;
		default:
			break;
		}
		
		b.setBorder(DefaultView.UNLIT_BORDER);
		
		return b;
	}
	
	/**
	 * Creates a new button of type {@code buttonType}.
	 * Midi and Mode button creation have been deferred to overloaded methods.
	 * @param text The text for the button to display.
	 * @param buttonType The type of button to create.
	 * @return A new instance of a button of type {@code buttonType}
	 */
	public static JButton createButton(String text, Button buttonType) {
		
		switch (buttonType) {
			case CIRCULAR:
				return new CircularButton();
			case CIRCULARTEXT:
				return new CircularTextButton(text);
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
	public static class CircularButton extends JButton {
		
		public static final int radius = 15;
					 
		public CircularButton() {
			setContentAreaFilled(false);
		}
			 
		protected void paintBorder(Graphics g) {
			g.setColor(((LineBorder) getBorder()).getLineColor());
			g.drawOval(0, 0, radius*2-1, radius*2-1);
		}
			 
	}
	
	/*
	 * Class to make a button circular (with text)
	 */
	public static class CircularTextButton extends JButton {
		// Mode buttons and the ON/OK contain text whereas MidiButtons do not
		CircularTextButton(String s) {
			setContentAreaFilled(false);
			setFont(new Font("Cambria", Font.PLAIN, 21));
			setText(s);
		}
			 
		protected void paintBorder(Graphics g) {
			g.setColor(((LineBorder) getBorder()).getLineColor());
			g.drawOval(0, 0, 49, 49);
		}
	}
	
	/*
	 * A MidiButton extends CircularButton
	 * Takes two parameters which distinguish it's co-ordinates
	 * All the buttons in the grid
	 */
	public static class MidiButton extends CircularButton { // Doesn't extend CircularTextButton 
		// Variables to return where in the grid the button is.
		private int x;
		private int y;
		
		public static Color LIT_COLOUR = Color.ORANGE;
		
		/**
		 * @return x - the x location in the grid of the button
		 */
		public int getXLoc() { return x; }
		
		/**
		 * @return y - the y location in the grid of the button
		 */
		public int getYLoc() {return y; }
		
		static int buttonPressed = 0;
		static long timePressed;
		static boolean isLit = false;
		static byte rapidPaint = 0; // 0 = Unknown, 1 = true, 2 = false.
		
		MidiButton(final int xLoc, final int yLoc) {
			// When instantiating each button in the grid they are all given 
			// their x and y co-ordinates
			x = xLoc;
			y = yLoc;
			
			addMouseListener(new MouseAdapter(){				
				public void mousePressed(MouseEvent me) {
					int dx = me.getX() - radius;
					int dy = me.getY() - radius;
					if(dx * dx + dy * dy > (radius * radius + radius)) return;
					if (ModeMaster.getInstance().getMode() != ModeMaster.ON_OFF_MODE) {
						ModeMaster.getInstance().getMode().onMatrixButtonPress(me, x, y);
						GUI.getInstance().update();
					}
					
					buttonPressed = me.getButton();
					timePressed = System.currentTimeMillis();
					isLit = ModeMaster.getInstance().getView().isLit(x, y);
				}
				
				public void mouseReleased(MouseEvent me) {
					buttonPressed = 0;
					rapidPaint = 0;
					timePressed = 0;
					isLit = false;
				}
				
				public void mouseEntered(MouseEvent me) {
					if (buttonPressed > 0 && rapidPaint == 0) {
						if (System.currentTimeMillis() - timePressed > 60 * 3) rapidPaint = 1;
						else rapidPaint = 2;
					}
					if (ModeMaster.getInstance().getMode() != ModeMaster.ON_OFF_MODE && 
							buttonPressed > 0 && 
							rapidPaint == 1) {
						ModeMaster.getInstance().getMode().onMatrixButtonPress(me, x, y, isLit);
						GUI.getInstance().update();
					}
				}
			});
		}
		
		/**
		 * PaintComponent override, making the button flash orange, should only be present on the MIDI Buttons.
		 */
		protected void paintComponent(Graphics g) {			
			g.setColor(getBackground());
			g.fillOval(0, 0, radius*2-1, radius*2-1);
			 
			super.paintComponent(g);
		}
	}
	
	/*
	 * ModeButton extends CircularTextButton
	 * Distinguishes what happens when a mode button is pressed
	 */
	public static class ModeButton extends CircularTextButton {
		
		private Mode mode;
		public Mode getMode() { return mode; }
		
		ModeButton(final String s, Mode mode) {
			super(s);
			addMouseListener(new MouseAdapter(){
				public void mousePressed(MouseEvent me){
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
			
			this.mode = mode;
		}
	}
	
	/*
	 * Class for an OKButton - extends CircularTextButton
	 */
	public static class OKButton extends CircularTextButton {
		OKButton(String s) {
			super(s);
			addMouseListener(new MouseAdapter(){
				public void mousePressed(MouseEvent me){
					ModeMaster.getInstance().getMode().onOKButtonPress(me);
				}
			});
		}
	}
	
	/*
	 * Class for an ONButton - extends CircularTextButton
	 */
	public static class ONButton extends CircularTextButton {
		ONButton(String s) {
			super(s);
			addMouseListener(new MouseAdapter(){
				public void mousePressed(MouseEvent me){
					// What to do when the ON button is pressed
					ModeMaster.getInstance().getMode().onOnOffButtonPress(me);
				}
			});
		}
	}
	
}
