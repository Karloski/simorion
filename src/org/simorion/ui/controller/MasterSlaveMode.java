package org.simorion.ui.controller;
import java.awt.event.MouseEvent;

import org.simorion.engine.MasterSlaveClient;
import org.simorion.ui.view.DefaultView;
import org.simorion.ui.view.View;
 
/**
 * 
 * @author Karl Brown
 *
 */
public class MasterSlaveMode extends DeviceMode {
 
    public MasterSlaveMode(ModeMaster m) {
		super(m);
	}

	private MasterSlaveView instance = new MasterSlaveView();
     
    /**
     * Implementation of the View interface for the MasterSlaveView
     * @author Karl Brown
     *
     */
    private class MasterSlaveView extends DefaultView {
    	// No implementation.
    	long startTime = 0;
    	@Override
    	public boolean isLit(int x, int y) {
    		return ((System.currentTimeMillis() - startTime) / 50) % 256 > (x*16+y); 
    	}
    }
    
    void onChangedTo() {    	
    	new MasterSlaveClient(model.getSong(), model.getInstanceID()).start();
    	instance.startTime = System.currentTimeMillis();
    	instance.setLCDMessage("Searching...");
    	new MasterSlaveClient(model.getSong(), model.getInstanceID(), new Runnable() {
    		public void run() {
    			changeMode(ModeMaster.PERFORMANCE_MODE);
    			model.setLCDDisplay("Song sent.");
    		}
    	}).start();
    }
     
    public View getView() {
        return instance;
    }

	@Override
	public void onOKButtonPress(MouseEvent e) {
		// No implementation.		
	}

	@Override
	public void onMatrixButtonPress(MouseEvent e, int buttonColumn, int buttonRow) {
		// No implementation.
	}
     
}