package org.simorion.ui.controller;
import java.awt.event.MouseEvent;

import org.simorion.ui.view.DefaultView;
import org.simorion.ui.view.View;
 
public class ChangeNVMode extends DeviceMode {
	
	int button = -1;
 
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
		if (button != -1) {
			model.setVelocity(model.getCurrentLayer(), (byte) (button <= 127 ? button : 127));
		}		
		changeMode(ModeMaster.PERFORMANCE_MODE);
		button = -1;
	}

	@Override
	public void onMatrixButtonPress(MouseEvent e, int x, int y) {
		button = (y * 16 + x);
		byte display = (byte) (button <= 127 ? button : 127);		
		model.setLCDDisplay(Byte.toString(display));
	}
	
	@Override
	void onChangedTo() {
		button = model.getCurrentLayer().getVelocity();
		model.setLCDDisplay("Change Velocity Mode");
	}
     
}