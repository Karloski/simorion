package org.simorion.ui.controller;
import java.awt.event.MouseEvent;

import org.simorion.engine.MasterSlaveClient;
import org.simorion.ui.view.DefaultView;
import org.simorion.ui.view.View;
 
/**
 * Device Mode implementation for the Master/Slave Mode.
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
     * @author Karl Brown, Edmund Smith
     *
     */
    private class MasterSlaveView extends DefaultView {
    	long startTime = 0;
    	
    	/**
    	 * Continually sets matrix buttons as the mode searches for other Simori-ONs.
    	 */
    	@Override
    	public boolean isLit(int x, int y) {
    		return ((System.currentTimeMillis() - startTime) / 50) % 256 > (x*16+y); 
    	}
    }
    
	/**
	 * Begins a search for local Simori-ONs to send song data to.
	 * Updates in the LCD on failure or success.
	 */
    void onChangedTo() {
    	instance.startTime = System.currentTimeMillis();
    	model.setLCDDisplay("Searching...");
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