package org.simorion.ui.controller;
import java.awt.event.MouseEvent;

import org.simorion.ui.model.ImmutableModel;
import org.simorion.ui.view.DefaultView;
import org.simorion.ui.view.View;
 
/**
 * 
 * @author George Young
 *
 */
public class LoadConfigMode extends DeviceMode {
 
	int load;
	
    public LoadConfigMode(ModeMaster m) {
		super(m);
		// TODO Auto-generated constructor stub
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
    	public boolean isLit(int x, int y) {    		
    		return isRowLit(x) || isColumnLit(y);
    	}
    	
    	/** {@inheritDoc} */
    	@Override
    	public boolean isRowLit(int x) {
    		
    		// If voice is -1, then no button has been selected.
    		if (load == -1) return false;
    		
    		// Voice will be a number between 0-255 (i.e., the button pressed).
    		return (load - 1) / 16 == x;
    	}
    	
    	/** {@inheritDoc} */
    	@Override
    	public boolean isColumnLit(int y) {
    		
    		// If voice is -1, then no button has been selected.
    		if (load == -1) return false;
    		
    		// Voice will be a number between 0-255 (i.e., the button pressed).
    		return y - ((load - 1) % 16) == 0;
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
		// Load shit.		
	}

	@Override
	public void onMatrixButtonPress(MouseEvent e, int x, int y) {
		load = y * 16 + x + 1;
		// model.setLCDDisplay(FileNames.getFileName(x, y));
	}
     
}