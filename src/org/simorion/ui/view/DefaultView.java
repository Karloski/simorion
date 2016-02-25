package org.simorion.ui.view;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.simorion.common.ImmutableRow;
import org.simorion.common.MutableLayer;
import org.simorion.common.util.Util;
import org.simorion.common.util.Util.Pair;
import org.simorion.ui.view.ButtonFactory.MidiButton;

/**
 * The default view provides default view behaviour for the Simori-ON.
 * @author Karl Brown, Petar Krstic
 *
 */
public abstract class DefaultView implements View {
	
	protected MutableLayer layer;	

	/** {@inheritDoc} */
	@Override
	public String getTitle() {
		return "Simori-ON";
	}
	
	/** {@inheritDoc} */
	@Override
	public Pair<Integer, Integer> getSize() {
		return new Pair<Integer, Integer>(607, 632);
	}
	
	/** {@inheritDoc} */
	@Override
	public Pair<Integer, Integer> getMatrixSize() {    		
		// Return a new pair representing the size of this view's button matrix.
		return new Pair<Integer, Integer>(16, 16);
	}
	
	/** {@inheritDoc} */
	@Override
	public JComponent getOuterPanel() {
		
		// Create and define the outer JPanel.
		JPanel outerPanel = new JPanel();
		
		outerPanel.setBounds(1, 1, 598, 600);
		outerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		outerPanel.setLayout(null);
		outerPanel.setBackground(Color.white);
		
		return outerPanel;
		
	}
	
	/** {@inheritDoc} */
	@Override
	public JComponent getButtonPanel() {
		
		// Create and define the inner JPanel.
		JPanel buttonPanel = new JPanel();
		
		buttonPanel.setBounds(58, 58, 484, 484);
		buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		buttonPanel.setLayout(null);
		buttonPanel.setBackground(Color.white);
		
		return buttonPanel;
		
	}
	
	/** {@inheritDoc} */
	@Override
	public AbstractButton getOnButton() {
		
		// Use the ButtonFactory to create the ON/OFF button.
		AbstractButton btnON = ButtonFactory.createButton("ON", ButtonFactory.Button.ONOFF);
		
		// Define it.
		btnON.setBounds(275, 5, 50, 50);
		btnON.setBorder(BorderFactory.createLineBorder(Color.black));
		
		// Return it.
		return btnON;
		
	}
	
	/** {@inheritDoc} */
	@Override
	public AbstractButton getOKButton() {
		
		// Use the ButtonFactory to create the OK button.
		AbstractButton btnOK = ButtonFactory.createButton("OK", ButtonFactory.Button.OK);
		
		// Define it.
		btnOK.setBounds(432, 545, 50, 50);
		btnOK.setBorder(BorderFactory.createLineBorder(Color.black));
		
		// Return it.
		return btnOK;
	}
	
	/** {@inheritDoc} */
	@Override
	public Iterable<AbstractButton> getModeButtons() {			
		
		// A list of buttons to return as an iterable.
		List<AbstractButton> buttons = new ArrayList<AbstractButton>();
		
		AbstractButton b = ButtonFactory.createButton("L1", ButtonFactory.Mode.L1);
		b.setBounds(5, 84, 50, 50);
		b.setBorder(BorderFactory.createLineBorder(Color.black));
		buttons.add(b);
		
		b = ButtonFactory.createButton("L2", ButtonFactory.Mode.L2);
		b.setBounds(5, 174, 50, 50);
		b.setBorder(BorderFactory.createLineBorder(Color.black));
		buttons.add(b);
		
		b = ButtonFactory.createButton("L3", ButtonFactory.Mode.L3);
		b.setBounds(5, 264, 50, 50);
		b.setBorder(BorderFactory.createLineBorder(Color.black));
		buttons.add(b);
		
		b = ButtonFactory.createButton("L4", ButtonFactory.Mode.L4);
		b.setBounds(5, 354, 50, 50);
		b.setBorder(BorderFactory.createLineBorder(Color.black));
		buttons.add(b);
		
		b = ButtonFactory.createButton("R1", ButtonFactory.Mode.R1);
		b.setBounds(545, 84, 50, 50);
		b.setBorder(BorderFactory.createLineBorder(Color.black));
		buttons.add(b);
		
		b = ButtonFactory.createButton("R2", ButtonFactory.Mode.R2);
		b.setBounds(545, 174, 50, 50);
		b.setBorder(BorderFactory.createLineBorder(Color.black));
		buttons.add(b);
		
		b = ButtonFactory.createButton("R3", ButtonFactory.Mode.R3);
		b.setBounds(545, 264, 50, 50);
		b.setBorder(BorderFactory.createLineBorder(Color.black));
		buttons.add(b);
		
		b = ButtonFactory.createButton("R4", ButtonFactory.Mode.R4);
		b.setBounds(545, 354, 50, 50);
		b.setBorder(BorderFactory.createLineBorder(Color.black));
		buttons.add(b);
		
		return buttons;
		
	}
	
	/** {@inheritDoc} */
	@Override
	public AbstractButton[] getMidiButtons() {
		
		MidiButton[] buttons = new MidiButton[256];
		
		// FIXME: Matrix size takes from model, however there is as of yet no model implementations.
		//int noButtons = matrixSize().left * matrixSize().right;
		int noButtons = 256; // 16 * 16
		
		int xLocationOfButton = 0;
		int yLocationOfButton = 0;
		// j is a variable which is incremented when the end of the grid is reached (every 16 buttons)
		int j = 0;
		
		for (int k = 0; k < noButtons; k++) {
			
			MidiButton b = ButtonFactory.createButton(k % 16, j);
			
			// Create new MidiButton with it's location parameters
			if ((k + 1) % 16 == 0){
				// Increment j every 16 buttons
				j++;
			}	
			
			b.setBounds(2 + (30 * xLocationOfButton), 452 - (30 * yLocationOfButton), 30, 30);
			
			// Setting bounds of the button depending on it's co-ords
			if((k + 1) % 16 == 0) {
				// When reaching 16 buttons - go back to left hand side of grid and increment y
				xLocationOfButton = 0;
				yLocationOfButton++;
			}
			else {
				// If not at the end of the grid - just increment x
				xLocationOfButton++;
			}
			
			buttons[k] = b;
		}
		
		return buttons;
	}
	
	/** {@inheritDoc} */
	@Override
	public JComponent getLCDScreen() {
		
		// Create and define the LCD screen.
		JTextField dispLCD = new JTextField();
		
		dispLCD.setBounds(120, 545, 240, 50);
		dispLCD.setEditable(false);
		dispLCD.setBackground(Color.BLACK);
		dispLCD.setBorder(BorderFactory.createLineBorder(Color.black));
		dispLCD.setFont(new Font("Cambria", Font.PLAIN, 21));
		
		return dispLCD;
		
	}
	
	/**
	 * Retrieves and returns whether or not the button at coordinate {@code x}, {@code y} is lit.
	 * @param x The x coordinate of the button.
	 * @param y The y coordinate of the button.
	 * @return True if lit, false otherwise.
	 */
	@Override
	public boolean isLit(int x, int y) {
		if(layer.getVoice().getMidiVoice() == -1) return false;
		return (16*y+x+1) == layer.getVoice().getMidiVoice() ||
				layer.getVoice().getMidiVoice() / 16 == y ||
				x - (layer.getVoice().getMidiVoice() % 16) == 0;
	}

	/**
	 * Determines if the row of buttons {@code row} is on are lit. 
	 * @param y The row to check.
	 * @return True if all buttons are lit, false otherwise.
	 */
	@Override
	public boolean isRowLit(int x) {
		// Each row should contain the same number of buttons.
		int columns = layer.getRow(x).cellCount();
		
		// Keeping the row constant, check the state of each button.
		for (int i = 0; i < columns; i++) {
			if (!isLit(x, i)) return false;
		}
		
		// Not all the buttons on this row are lit.
		return true;
	}

	/**
	 * Determines if the column of buttons {@code column} is on are lit. 
	 * @param x The column to check.
	 * @return True if all buttons are lit, false otherwise.
	 */
	@Override
	public boolean isColumnLit(int y) {			
		// Each row should contain the same number of buttons.
		int rows = Util.count(layer.getRows());
		
		// Keeping the row constant, check the state of each button.
		for (int i = 0; i < rows; i++) {
			if (!isLit(i, y)) return false;
		}
		
		// Not all the buttons on this row are lit.
		return true;
	}
	
	/**
	 * Retrieves and returns a collection of iterable booleans representing all the currently lit matrix buttons.
	 * @return A collection of iterable booleans representing the currently lit buttons.
	 */
	@Override
	public Collection<Iterable<Boolean>> getLitButtons() {
		
		List<Iterable<Boolean>> lit = new ArrayList<Iterable<Boolean>>();
		
		for (ImmutableRow r : layer.getRows()) {				
			lit.add(Util.bitstring(r.getLit()));
		}
		
		return lit;
		
	}

	/**
	 * Retrieves and returns the current textual output of the LCD.
	 * @return The textual output of the LCD.
	 */
	@Override
	public String getLCDMessage() {
		return layer.getLCDMessage();
	}

	/**
	 * Retrieves and returns the MIDI ID of the currently applied instrument.
	 * @return The MIDI ID of the currently applied instrument for this view.
	 */
	@Override
	public int getVoiceId() {
		return layer.getVoice().getMidiVoice();
	}

	/**
	 * Retrieves and returns the name of the currently applied instrument.
	 * @return The name of the currently applied instrument for this view
	 */
	@Override
	public String getVoiceName() {
		return layer.getVoice().getName();
	}

	/**
	 * Retrieves and returns the ID of the currently applied layer.
	 * @return The ID of the currently applied layer for this view.
	 */
	@Override
	public int getCurrentLayerId() {
		return layer.getLayerNumber();
	}

	/**
	 * Retrieves and returns the current loop point.
	 * @return The current loop point for this view.
	 */
	@Override
	public int getLoopPoint() {
		return layer.getLoopPoint();
	}

	/**
	 * Retrieves and returns the current velocity for notes played.
	 * @return The current velocity for notes played on this view.
	 */
	@Override
	public int getVelocity() {
		return layer.getVelocity();
	}
	
	/**
	 * Retrieves and returns the current note for row {@code y}.
	 * @param y The row to check.
	 * @return The current note for row {@code y}
	 */
	@Override
	public byte getNote(int y) {
		return layer.getRow(y).getNote();
	} 

}
