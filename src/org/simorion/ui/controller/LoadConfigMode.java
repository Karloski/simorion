package org.simorion.ui.controller;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.AbstractButton;

import org.simorion.common.SongBuilder;
import org.simorion.common.Voice;
import org.simorion.common.stream.FileSongReader;
import org.simorion.common.stream.SongFormats;
import org.simorion.ui.view.ButtonFactory;
import org.simorion.ui.view.ButtonFactory.MidiButton;
import org.simorion.ui.view.DefaultView;
import org.simorion.ui.view.GUI;
import org.simorion.ui.view.View;
 
/**
 * Device Mode implementation for the Load Config Mode.
 * @author Karl Brown
 *
 */
public class LoadConfigMode extends DeviceMode {
 
	String filename = "";
	int button = -1;
	boolean shift = false;
	
    public LoadConfigMode(ModeMaster m) {
		super(m);
	}

	private LoadConfigView instance = new LoadConfigView();
     
    /**
     * Implementation of the View interface for the LoadConfigView
     * @author Karl Brown
     *
     */
    private class LoadConfigView extends DefaultView {

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
    			
    			if (isCharacter(k)) {
    				b.setText("" + getCharacter(k % 16, j, shift));
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
     * Reads the contents of the song from the filename into the Simori-ON.
     * If the song does not exist or the data could not be loaded, the error is represented on the screen.
     */
	@Override
	public void onOKButtonPress(MouseEvent e) {
		// Remove the pipe.
		filename = filename.substring(0, filename.length()-1);
		
		// Create a new FileSongReader from the filename.
		FileSongReader fsr = new FileSongReader(new File(filename + ".song"));
		
		// The song to read to.
		SongBuilder song = new SongBuilder();
		
		// Attempt to read the song. Report any errors to the user.
		try {
			// Reads the songs contents into the SongBuilder.
			fsr.readTo(SongFormats.PREFERRED_FORMAT, song);
			
			// Loads the song from the SongBuilder into the song.
			model.getSong().loadFrom(song);
			
			// Change back to performance mode and reset the view.
			changeMode(ModeMaster.PERFORMANCE_MODE);
			model.setLCDDisplay("Song " + filename + " loaded.");
			reset();			
			
		} catch (Exception ex) {
			model.setLCDDisplay(ex.getMessage());
			filename += "|";
		}
	}

	/**
	 * When a matrix button is pressed in load config mode, update the LCD display with the related character.
	 */
	@Override
	public void onMatrixButtonPress(MouseEvent e, int x, int y) {
		button = y * 16 + x;
		
		// If this is a character representing button.
		if (isCharacter(button))
			// Remove the pipe, add the character and then re-add the pipe.
			filename = filename.substring(0, filename.length()-1) + getCharacter(x, y, shift) + "|";
		
		assert(filename.endsWith("|"));
		
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
	 * Updates the LCD display to display the current mode and sets the filename string to blank.
	 */
	@Override
	void onChangedTo() {
		reset();
		model.setLCDDisplay("Load Config Mode");
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