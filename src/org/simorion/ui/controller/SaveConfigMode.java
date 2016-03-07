package org.simorion.ui.controller;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractButton;

import org.simorion.ui.view.ButtonFactory;
import org.simorion.ui.view.ButtonFactory.MidiButton;
import org.simorion.ui.view.DefaultView;
import org.simorion.ui.view.GUI;
import org.simorion.ui.view.View;
 
public class SaveConfigMode extends DeviceMode {
	
	String filename = "";
	int button;
	int shift = 0;
 
    public SaveConfigMode(ModeMaster m) {
		super(m);
		// TODO Auto-generated constructor stub
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
    		
    		b = ButtonFactory.createButton("<", ButtonFactory.Mode.R1);
    		modeButtons.add(b);
    		
    		b = ButtonFactory.createButton("^", ButtonFactory.Mode.R2);
    		modeButtons.add(b);
    		
    		b = ButtonFactory.createButton("—", ButtonFactory.Mode.R3);
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

	@Override
	public void onOKButtonPress(MouseEvent e) {
		// Serialise.
		changeMode(ModeMaster.PERFORMANCE_MODE);
		button = -1;
	}

	@Override
	public void onMatrixButtonPress(MouseEvent e, int x, int y) {		
		filename = filename.substring(0, filename.length()-1) + getCharacter(x, y, shift) + "|";
		button = y * 16 + x;
		model.setLCDDisplay(filename);
		shift = 0;
	}
	
	@Override
	public void onRButtonPress(MouseEvent e, int buttonNum) {
		if (buttonNum == 1) filename = (filename.length() > 1 ? filename.substring(0, filename.length()-2) : "") + "|";
		if (buttonNum == 2) shift = shift < 0 ? 0 : -32;
		if (buttonNum == 3) filename = filename.substring(0, filename.length()-1) + " " + "|";
		if (buttonNum == 4) {
			filename = "";
			changeMode(ModeMaster.PERFORMANCE_MODE);
		}
		
		model.setLCDDisplay(filename);
		GUI.getInstance().update();
	}
     
	@Override
	void onChangedTo() {
		filename = "|";
		model.setLCDDisplay(filename);
	}
}