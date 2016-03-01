package org.simorion.ui.controller;
import java.awt.event.MouseEvent;

import org.simorion.ui.view.DefaultView;
import org.simorion.ui.view.View;
 
/**
 * ChangeLPointMode ready for full impl
 * @author George Young
 */
public class ChangeLPointMode extends DeviceMode {
 
	private byte point;
	
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
    		if (point == -1) return false;
    		
    		// Point will be a number between 0-255 (i.e., the button pressed).
    		// Convert the x and y into a single int which represents its position on the matrix.
    		// If this value is the same as the voice, or if it's on the same column, then the button is lit.
    		return isColumnLit(y);
    	}
    	
    	/** {@inheritDoc} */
    	@Override
    	public boolean isColumnLit(int y) {
    		
    		// If voice is -1, then no button has been selected.
    		if (point == -1) return false;
    		
    		// Voice will be a number between 0-255 (i.e., the button pressed).
    		return y - (point % 16) == 0;
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
		if(point != -1) {
			model.setLoopPoint(model.getCurrentLayer(), point);
		}
		changeMode(ModeMaster.PERFORMANCE_MODE);
		point = -1;
	}

	@Override
	public void onMatrixButtonPress(MouseEvent e, int x, int y) {
		point = (byte) y;
		model.setLCDDisplay(Byte.toString(point));
	}
     
}