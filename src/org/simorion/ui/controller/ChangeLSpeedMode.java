package org.simorion.ui.controller;
import java.awt.event.MouseEvent;

import org.simorion.ui.view.DefaultView;
import org.simorion.ui.view.View;
 
/**
 * Device Mode implementation for the Change Loop Speed Mode.
 * @author Karl Brown
 */
public class ChangeLSpeedMode extends DeviceMode {
	
	int button;
 
    public ChangeLSpeedMode(ModeMaster m) {
		super(m);
		// TODO Auto-generated constructor stub
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
		if(button != -1) {
			model.setTempo(button <= 160 ? button : 160);
		}
		changeMode(ModeMaster.PERFORMANCE_MODE);
		reset();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onMatrixButtonPress(MouseEvent e, int x, int y) {
		button = y * 16 + x;
		model.setLCDDisplay(Integer.toString(button <= 160 ? button : 160));
	}
    
	/**
	 * {@inheritDoc}
	 */
	@Override
	void onChangedTo() {
		button = (int) model.getTempo();
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