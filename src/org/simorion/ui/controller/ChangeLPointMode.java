package org.simorion.ui.controller;
import java.awt.event.MouseEvent;

import org.simorion.ui.view.DefaultView;
import org.simorion.ui.view.View;
 
/**
 * Device Mode implementation for the Change Loop Point Mode.
 * @author Karl Brown
 */
public class ChangeLPointMode extends DeviceMode {
 
	private int button = -1;
	
    public ChangeLPointMode(ModeMaster m) {
		super(m);
	}

	private ChangeLPointView instance = new ChangeLPointView();
     
    /**
     * Implementation of the View interface for the ChangeLPointView
     * @author Karl Brown
     *
     */
    private class ChangeLPointView extends DefaultView {

    	/** {@inheritDoc} */
    	@Override
    	public boolean isLit(int x, int y) {
    		
    		// If voice is -1, then no button has been selected.
    		if (button == -1) return false;
    		
    		// Point will be a number between 0-255 (i.e., the button pressed).
    		// Convert the x and y into a single int which represents its position on the matrix.
    		// If this value is the same as the voice, or if it's on the same column, then the button is lit.
    		return isColumnLit(y);
    	}
    	
    	/** {@inheritDoc} */
    	@Override
    	public boolean isColumnLit(int y) {
    		
    		// If voice is -1, then no button has been selected.
    		if (button == -1) return false;
    		
    		// Voice will be a number between 0-255 (i.e., the button pressed).
    		return y - (button % 16) == 0;
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
			model.setLoopPoint(model.getCurrentLayer(), (byte) (button <= 127 ? button : 127));
		}
		changeMode(ModeMaster.PERFORMANCE_MODE);
		model.setLCDDisplay("Loop point set to " + model.getCurrentLayer().getLoopPoint());
		reset();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onMatrixButtonPress(MouseEvent e, int x, int y) {
		button = y;
		model.setLCDDisplay(Integer.toString(button <= 127 ? button : 127));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	void onChangedTo() {
		button = model.getCurrentLayer().getLoopPoint();
		model.setLCDDisplay("Change Loop Point Mode");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	void reset() {
		button = -1;
	}
     
}