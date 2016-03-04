package org.simorion.ui.view;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.management.RuntimeErrorException;
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
import org.simorion.ui.view.ButtonFactory.ModeButton;
import org.simorion.ui.view.ButtonFactory.OKButton;
import org.simorion.ui.view.ButtonFactory.ONButton;

/**
 * The default view provides default view behaviour for the Simori-ON.
 * @author Karl Brown, Petar Krstic
 *
 */
public class DefaultView implements View {
	
	protected JPanel outerPanel;
	protected JPanel buttonPanel;
	
	protected ONButton buttonOn;
	protected OKButton buttonOK;
	protected JTextField dispLCD;
	
	protected MidiButton[] midiButtons;
	protected List<AbstractButton> modeButtons;

	/** {@inheritDoc} */
	@Override
	public String getTitle() {
		return "Simori-ON";
	}
	
	/** {@inheritDoc} */
	@Override
	public Pair<Integer, Integer> getSize() {
		return new Pair<Integer, Integer>(605, 630);
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
		outerPanel = new JPanel();
		
		outerPanel.setBounds(1, 1, 600, 625);
		outerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		outerPanel.setLayout(null);
		outerPanel.setBackground(Color.white);
		
		return outerPanel;
		
	}
	
	/** {@inheritDoc} */
	@Override
	public JComponent getButtonPanel() {
		
		// Create and define the inner JPanel.
		buttonPanel = new JPanel();
		
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
		buttonOn = (ONButton) ButtonFactory.createButton("ON", ButtonFactory.Button.ONOFF);
		
		// Define it.
		buttonOn.setBounds(275, 5, 50, 50);
		buttonOn.setBorder(BorderFactory.createLineBorder(Color.black));
		
		// Return it.
		return buttonOn;
		
	}
	
	/** {@inheritDoc} */
	@Override
	public AbstractButton getOKButton() {
		
		// Use the ButtonFactory to create the OK button.
		buttonOK = (OKButton) ButtonFactory.createButton("OK", ButtonFactory.Button.OK);
		
		// Define it.
		buttonOK.setBounds(432, 545, 50, 50);
		buttonOK.setBorder(BorderFactory.createLineBorder(Color.black));
		
		// Return it.
		return buttonOK;
	}
	
	/** {@inheritDoc} */
	@Override
	public Iterable<AbstractButton> getModeButtons() {			
		
		// A list of buttons to return as an iterable.
		modeButtons = new ArrayList<AbstractButton>();
		
		AbstractButton b = ButtonFactory.createButton("L1", ButtonFactory.Mode.L1);
		b.setBounds(5, 84, 50, 50);
		b.setBorder(BorderFactory.createLineBorder(Color.black));
		modeButtons.add(b);
		
		b = ButtonFactory.createButton("L2", ButtonFactory.Mode.L2);
		b.setBounds(5, 174, 50, 50);
		b.setBorder(BorderFactory.createLineBorder(Color.black));
		modeButtons.add(b);
		
		b = ButtonFactory.createButton("L3", ButtonFactory.Mode.L3);
		b.setBounds(5, 264, 50, 50);
		b.setBorder(BorderFactory.createLineBorder(Color.black));
		modeButtons.add(b);
		
		b = ButtonFactory.createButton("L4", ButtonFactory.Mode.L4);
		b.setBounds(5, 354, 50, 50);
		b.setBorder(BorderFactory.createLineBorder(Color.black));
		modeButtons.add(b);
		
		b = ButtonFactory.createButton("R1", ButtonFactory.Mode.R1);
		b.setBounds(545, 84, 50, 50);
		b.setBorder(BorderFactory.createLineBorder(Color.black));
		modeButtons.add(b);
		
		b = ButtonFactory.createButton("R2", ButtonFactory.Mode.R2);
		b.setBounds(545, 174, 50, 50);
		b.setBorder(BorderFactory.createLineBorder(Color.black));
		modeButtons.add(b);
		
		b = ButtonFactory.createButton("R3", ButtonFactory.Mode.R3);
		b.setBounds(545, 264, 50, 50);
		b.setBorder(BorderFactory.createLineBorder(Color.black));
		modeButtons.add(b);
		
		b = ButtonFactory.createButton("R4", ButtonFactory.Mode.R4);
		b.setBounds(545, 354, 50, 50);
		b.setBorder(BorderFactory.createLineBorder(Color.black));
		modeButtons.add(b);
		
		return modeButtons;
		
	}
	
	/** {@inheritDoc} */
	@Override
	public AbstractButton[] getMidiButtons() {
		
		midiButtons = new MidiButton[256];
		
		// FIXME: Matrix size takes from model, however there is as of yet no model implementations.
		//int noButtons = matrixSize().left * matrixSize().right;
		int noButtons = 256; // 16 * 16
		
		// j is a variable which is incremented when the end of the grid is reached (every 16 buttons)
		int j = -1;
		
		for (int k = 0; k < noButtons; k++) {
			
			// Create new MidiButton with it's location parameters
			if (k % 16 == 0){
				// Increment j every 16 buttons
				j++;
			}	
			
			MidiButton b = ButtonFactory.createButton(k % 16, j);
			
			b.setBounds(2 + (30 * (k % 16) - 1), 452 - (30 * j), 30, 30);
			b.setBackground(Color.white);
			
			midiButtons[k] = b;
		}
		
		return midiButtons;
	}
	
	/** {@inheritDoc} */
	@Override
	public JComponent getLCDScreen() {
		
		// Create and define the LCD screen.
		dispLCD = new JTextField();
		
		dispLCD.setBounds(120, 545, 240, 50);
		dispLCD.setEditable(false);
		dispLCD.setBackground(Color.WHITE);
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
		return false;
	}

	/**
	 * Determines if the row of buttons {@code row} is on are lit. 
	 * @param y The row to check.
	 * @return True if all buttons are lit, false otherwise.
	 */
	@Override
	public boolean isRowLit(int x) {
		return false;
	}

	/**
	 * Determines if the column of buttons {@code column} is on are lit. 
	 * @param x The column to check.
	 * @return True if all buttons are lit, false otherwise.
	 */
	@Override
	public boolean isColumnLit(int y) {			
		return false;
	}

	/**
	 * Retrieves and returns the current textual output of the LCD.
	 * @return The textual output of the LCD.
	 */
	@Override
	public String getLCDMessage() {
		return "";
	}

	/**
	 * Retrieves and returns the MIDI ID of the currently applied instrument.
	 * @return The MIDI ID of the currently applied instrument for this view.
	 */
	@Override
	public int getVoiceId() {
		return 0;
	}

	/**
	 * Retrieves and returns the name of the currently applied instrument.
	 * @return The name of the currently applied instrument for this view
	 */
	@Override
	public String getVoiceName() {
		return null;
	}

	/**
	 * Retrieves and returns the ID of the currently applied layer.
	 * @return The ID of the currently applied layer for this view.
	 */
	@Override
	public int getCurrentLayerId() {
		return 0;
	}

	/**
	 * Retrieves and returns the current loop point.
	 * @return The current loop point for this view.
	 */
	@Override
	public int getLoopPoint() {
		return 0;
	}

	/**
	 * Retrieves and returns the current velocity for notes played.
	 * @return The current velocity for notes played on this view.
	 */
	@Override
	public int getVelocity() {
		return 0;
	}
	
	/**
	 * Retrieves and returns the current note for row {@code y}.
	 * @param y The row to check.
	 * @return The current note for row {@code y}
	 */
	@Override
	public byte getNote(int y) {
		return 0;
	} 

}
