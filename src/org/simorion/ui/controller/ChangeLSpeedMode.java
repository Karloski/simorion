package org.simorion.ui.controller;
import java.awt.event.MouseEvent;

import org.simorion.ui.model.ImmutableModel;
import org.simorion.ui.view.DefaultView;
import org.simorion.ui.view.View;
 
/**
 * ChangeLPointMode ready for full impl
 * @author George Young
 */
public class ChangeLSpeedMode extends DeviceMode {
	
	float speed;
 
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
    		if (speed == -1) return false;
    		
    		// Voice will be a number between 0-255 (i.e., the button pressed).
    		return speed / 16 == x;
    	}
    	
    	/** {@inheritDoc} */
    	@Override
    	public boolean isColumnLit(int y) {
    		
    		// If voice is -1, then no button has been selected.
    		if (speed == -1) return false;
    		
    		// Voice will be a number between 0-255 (i.e., the button pressed).
    		return y - (speed % 16) == 0;
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

    /** @author Karl Brown} */
	@Override
	public void onOKButtonPress(MouseEvent e) {
		if(speed != -1) {
			model.setTempo(speed);
		}
		changeMode(ModeMaster.PERFORMANCE_MODE);
		speed = -1;
	}

	/** @author Karl Brown} */
	@Override
	public void onMatrixButtonPress(MouseEvent e, int x, int y) {
		float tmp = y * 16 + x;
		
		if (tmp <= 160) {
			speed = tmp;
			model.setLCDDisplay(Float.toString(speed));
		}
	}
     
}