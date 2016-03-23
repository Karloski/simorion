package org.simorion.ui.controller;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.AbstractButton;

import org.simorion.common.stream.FileSongWriter;
import org.simorion.common.stream.SongFormats;
import org.simorion.common.stream.StreamFailureException;
import org.simorion.sound.BankOfSounds;
import org.simorion.ui.view.ButtonFactory;
import org.simorion.ui.view.ButtonFactory.MidiButton;
import org.simorion.ui.view.DefaultView;
import org.simorion.ui.view.GUI;
import org.simorion.ui.view.View;
 
/**
 * 
 * @author Karl Brown
 *
 */
public class SaveConfigMode extends DeviceMode {
	
	String filename = "";
	int button;
	boolean shift = false;
 
    public SaveConfigMode(ModeMaster m) {
		super(m);
	}

	private SaveConfigView instance = new SaveConfigView();
     
    /**
     * Implementation of the View interface for the SaveConfigView
     * @author Karl Brown
     *
     */
    private class SaveConfigView extends DefaultView {
    	
    	/** {@inheritDoc} */
    	@Override
    	public Iterable<AbstractButton> getModeButtons() {			
    		
    		// A list of buttons to return as an iterable.
    		modeButtons = new ArrayList<AbstractButton>();
    		
    		AbstractButton b = ButtonFactory.createButton("L1", ButtonFactory.Mode.L1);
    		modeButtons.add(b);
    		
    		b = ButtonFactory.createButton("L2", ButtonFactory.Mode.L2);
    		modeButtons.add(b);
    		
    		b = ButtonFactory.createButton("L3", ButtonFactory.Mode.L3);
    		modeButtons.add(b);
    		
    		b = ButtonFactory.createButton("L4", ButtonFactory.Mode.L4);
    		modeButtons.add(b);
    		
    		b = ButtonFactory.createButton("DEL", ButtonFactory.Mode.R1);
    		modeButtons.add(b);
    		
    		b = ButtonFactory.createButton("^", ButtonFactory.Mode.R2);
    		modeButtons.add(b);
    		
    		b = ButtonFactory.createButton("_", ButtonFactory.Mode.R3);
    		modeButtons.add(b);
    		
    		b = ButtonFactory.createButton("X", ButtonFactory.Mode.R4);
    		modeButtons.add(b);
    		
    		return modeButtons;
    		
    	}
    	
    	/** {@inheritDoc} */
    	@Override
    	public AbstractButton[] getMidiButtons() {
    		
    		if(midiButtons != null) return midiButtons;
    		
    		int noButtons = NO_BUTTONS;
    		midiButtons = new MidiButton[noButtons];
    		
    		// j is a variable which is incremented when the end of the grid is reached (every 16 buttons)
    		int j = -1;
    		
    		for (int k = 0; k < noButtons; k++) {
    			
    			// Create new MidiButton with it's location parameters
    			if (k % MATRIX_SIZE.left == 0){
    				// Increment j every 16 buttons
    				j++;
    			}
    			
    			MidiButton b = ButtonFactory.createButton(k % MATRIX_SIZE.left, j);
    			
    			if (isCharacter(k)) {
    				b.setText("" + getCharacter(k % MATRIX_SIZE.left, j, shift));
    			}
    			
    			midiButtons[k] = b;
    		}
    		
    		return midiButtons;
    	}
    	
    	/** {@inheritDoc} */
    	@Override
    	public boolean isLit(int x, int y) {    		
    		return isRowLit(x) || isColumnLit(y);
    	}
    	
    	/** {@inheritDoc} */
    	@Override
    	public boolean isRowLit(int x) {
    		
    		// If voice is -1, then no button has been selected.
    		if (button == -1) return false;
    		
    		// Voice will be a number between 0-255 (i.e., the button pressed).
    		return x - (button % 16) == 0;
    	}
    	
    	/** {@inheritDoc} */
    	@Override
    	public boolean isColumnLit(int y) {
    		
    		// If voice is -1, then no button has been selected.
    		if (button == -1) return false;
    		
    		// Voice will be a number between 0-255 (i.e., the button pressed).
    		return button / 16 == y;
    	}
    	
    	/** {@inheritDoc} */
    	@Override
    	public String getLCDMessage() {
    		return model.getLCDDisplay();
    	}
    	
    }
     
    public View getView() {
        return instance;
    }

    /**
     * Save the song configuration with the specified filename.
     * Will display any errors to the LCD screen.
     */
	@Override
	public void onOKButtonPress(MouseEvent e) {
		// Create a new song writer for the given filename.
		// Remove the pipe.
		filename = filename.substring(0, filename.length()-1);
		FileSongWriter fsw = new FileSongWriter(new File(filename + ".song"));
		
		try {
			// Attempt to serialize the song data.
			fsw.write(SongFormats.PREFERRED_FORMAT, model.getSong());
			
			// Successfully wrote the song, play the happy sound (yey).
			model.enqueueSound(BankOfSounds.GOOD_SOUND);
			
			// If successfully, change back to the performance mode and update the user via the LCD display.
			changeMode(ModeMaster.PERFORMANCE_MODE);
			
			// Reset this mode.
			model.setLCDDisplay("Song '" + filename + "' saved.");
			reset();
		} catch (StreamFailureException ex) {
			// On error, display the error and add the pipe back to the string.
			model.setLCDDisplay(ex.getMessage());
			filename += "|";
			model.enqueueSound(BankOfSounds.BAD_SOUND);
		}
	}

	/**
	 * When a matrix button is pressed in load config mode, update the LCD display with the related character.
	 */
	@Override
	public void onMatrixButtonPress(MouseEvent e, int x, int y) {
		// The array index location of this button press.
		button = y * 16 + x;
		
		// If the button represents a character, get the character it represents.
		if (isCharacter(button))
			filename = filename.substring(0, filename.length()-1) + getCharacter(x, y, shift) + "|";
		
		// Update the display.
		model.setLCDDisplay(filename);
		shift = false;
	}
	
	/**
	 * Modifies the way text is entered onto the LCD screen for matrix buttons pressed based on the mode button pressed.
	 */
	@Override
	public void onRButtonPress(MouseEvent e, int buttonNum) {
		// Backspace. Creates a substring excluding the last two characters, then adds a cursor (pipe) character.
		if (buttonNum == 1) filename = (filename.length() > 1 ? filename.substring(0, filename.length()-2) : "") + "|";
		
		// Shift. Modifies whether or not the letters should be capitalised or not.
		if (buttonNum == 2) shift = shift ? false : true;
		
		// Space. Adds a space by first removing the pipe, then adding a space and the pipe.
		if (buttonNum == 3) filename = filename.substring(0, filename.length()-1) + " " + "|";
		
		// Cancel. Goes back to performance mode and updates nothing.
		if (buttonNum == 4) {
			filename = "";
			changeMode(ModeMaster.PERFORMANCE_MODE);
		}
		
		model.setLCDDisplay(filename);
		GUI.getInstance().update();
	}
    
	/**
	 * {@inheritDoc}
	 */
	@Override
	void onChangedTo() {
		reset();
		model.setLCDDisplay("Save Config Mode");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	void reset() {
		filename = "|";
		shift = false;
		button = -1;
	}
}