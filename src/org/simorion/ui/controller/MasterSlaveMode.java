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
	private MasterSlaveClient client;
     
    /**
     * Implementation of the View interface for the MasterSlaveView
     * @author Karl Brown, Edmund Smith
     *
     */
    private class MasterSlaveView extends DefaultView {
    	long startTime = 0;
    	long messageAge = 5000;
    	String standardMessage;
    	/**
    	 * Continually sets matrix buttons as the mode searches for other Simori-ONs.
    	 */
    	@Override
    	public boolean isLit(int x, int y) {
    		return (client.alreadySearched) % 256 > (x*16+y); 
    	}
    	
    	@Override
    	public String getLCDMessage() {
    		messageAge++;
    		return messageAge < 500 ? model.getLCDDisplay() : standardMessage;
    	}
    }
    
	/**
	 * Begins a search for local Simori-ONs to send song data to.
	 * Updates in the LCD on failure or success.
	 */
    void onChangedTo() {
    	instance.startTime = System.currentTimeMillis();
    	instance.standardMessage = "Searching...";
    	instance.messageAge = 5000;
    	client = new MasterSlaveClient(model, model.getSong(), model.getInstanceID(), new Runnable() {
    		public void run() {
    			changeMode(ModeMaster.PERFORMANCE_MODE);
    		}
    	});
    	client.start();
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