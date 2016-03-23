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
	private MasterSlaveClient client;
     
    /**
     * Implementation of the View interface for the MasterSlaveView
     * @author Karl Brown
     *
     */
    private class MasterSlaveView extends DefaultView {
    	// No implementation.
    	long startTime = 0;
    	long messageAge = 5000;
    	@Override
    	public boolean isLit(int x, int y) {
    		return (client.alreadySearched) % 256 > (x*16+y); 
    	}
    	
    	@Override
    	public String getLCDMessage() {
    		messageAge++;
    		return messageAge < 500 ? model.getLCDDisplay() : super.getLCDMessage();
    	}
    	
    	@Override
    	public void setLCDMessage(String message) {
    		messageAge = 0;
    		super.setLCDMessage(message);
    	}
    }
    
    void onChangedTo() {
    	instance.startTime = System.currentTimeMillis();
    	instance.setLCDMessage("Searching...");
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