package org.simorion.ui.controller;
import java.awt.event.MouseEvent;

import org.simorion.ui.view.DefaultView;
import org.simorion.ui.view.View;
 
public class ChangeNVMode extends DeviceMode {
	
	byte velocity = -1;
 
    public ChangeNVMode(ModeMaster m) {
		super(m);
	}

	private ChangeNVView instance = new ChangeNVView();
     
    /**
     * Implementation of the View interface for the ChangeNVView
     * @author Karl Brown
     *
     */
    private class ChangeNVView extends DefaultView {

    	/** {@inheritDoc} */
    	@Override
    	public boolean isLit(int x, int y) {    		
    		return isRowLit(x) || isColumnLit(y);
    	}
    	
    	/** {@inheritDoc} */
    	@Override
    	public boolean isRowLit(int x) {
    		
    		// If voice is -1, then no button has been selected.
    		if (velocity == -1) return false;
    		
    		// Voice will be a number between 0-255 (i.e., the button pressed).
    		return velocity / 16 == x;
    	}
    	
    	/** {@inheritDoc} */
    	@Override
    	public boolean isColumnLit(int y) {
    		
    		// If voice is -1, then no button has been selected.
    		if (velocity == -1) return false;
    		
    		// Voice will be a number between 0-255 (i.e., the button pressed).
    		return y - (velocity % 16) == 0;
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
		if(velocity != -1) {
			model.setVelocity(model.getCurrentLayer(), velocity);
		}
		changeMode(ModeMaster.PERFORMANCE_MODE);
		velocity = -1;
	}

	@Override
	public void onMatrixButtonPress(MouseEvent e, int x, int y) {
		byte tmp = (byte) (y * 16 + x);
		
		if (Math.abs(tmp) == tmp) {
			velocity = tmp;
			model.setLCDDisplay(Byte.toString(velocity));
		}
	}
     
}