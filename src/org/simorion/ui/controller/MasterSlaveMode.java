package org.simorion.ui.controller;
import java.awt.event.MouseEvent;

import org.simorion.common.stream.StreamFailureException;
import org.simorion.engine.MasterSlaveClient;
import org.simorion.ui.model.ImmutableModel;
import org.simorion.ui.view.DefaultView;
import org.simorion.ui.view.View;
 
/**
 * 
 * @author George Young
 *
 */
public class MasterSlaveMode extends DeviceMode {
 
    public MasterSlaveMode(ModeMaster m) {
		super(m);
		// TODO Auto-generated constructor stub
	}

	private MasterSlaveView instance = new MasterSlaveView();
     
    /**
     * Implementation of the View interface for the MasterSlaveView
     * @author Karl Brown
     *
     */
    private class MasterSlaveView extends DefaultView {
    	// No implementation.         
    }
    
    void onChangedTo() {
    	
    	new MasterSlaveClient(model.getSong(), model.getInstanceID()).start();
    	changeMode(ModeMaster.PERFORMANCE_MODE);
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