package org.simorion.ui.controller;
import java.awt.event.MouseEvent;

import org.simorion.sound.BankOfSounds;
import org.simorion.ui.view.DefaultView;
import org.simorion.ui.view.View;
 
/**
 * Device Mode implementation for the Change Loop Speed Mode.
 * @author Karl Brown
 */
public class ChangeLSpeedMode extends DeviceMode {
	
	int button = -1;
 
    public ChangeLSpeedMode(ModeMaster m) {
		super(m);
	}

	private ChangeLSpeedView instance = new ChangeLSpeedView();
     
    /**
     * Implementation of the View interface for the ChangeLSpeedView
     * @author Karl Brown
     *
     */
    private class ChangeLSpeedView extends DefaultView {

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
     
	/**
	 * {@inheritDoc}
	 */
    public View getView() {
        return instance;
    }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onOKButtonPress(MouseEvent e) {
		if (button > 160) {
			model.enqueueSound(BankOfSounds.BAD_SOUND);
			model.setLCDDisplay("Tempo cannot exceed 160.");
		}
		else {
			model.setBPM((byte) button);
			model.enqueueSound(BankOfSounds.GOOD_SOUND);
			changeMode(ModeMaster.PERFORMANCE_MODE);
			model.setLCDDisplay("Loop speed set to " + (model.getBPM() & 0xff));
			reset();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onMatrixButtonPress(MouseEvent e, int x, int y) {
		button = y * 16 + x;
		model.setLCDDisplay(Integer.toString(button));
	}
    
	/**
	 * {@inheritDoc}
	 */
	@Override
	void onChangedTo() {
		//Avoid the signed-ness issue
		button = 0xff & model.getBPM();
		model.setLCDDisplay("Change Loop Speed Mode");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	void reset() {
		button = -1;
	}
}