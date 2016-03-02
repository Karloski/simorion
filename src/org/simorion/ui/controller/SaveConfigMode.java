package org.simorion.ui.controller;
import java.awt.event.MouseEvent;

import org.simorion.ui.view.DefaultView;
import org.simorion.ui.view.View;
 
public class SaveConfigMode extends DeviceMode {
	
	String filename;
	int button;
 
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
		//filename += getLetter(x, y);
		button = y * 16 + x;
		model.setLCDDisplay(filename);
	}
     
	@Override
	void onChangedTo() {
		model.setLCDDisplay("Save Configuration Mode");
	}
}