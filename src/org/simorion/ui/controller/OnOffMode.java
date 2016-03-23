package org.simorion.ui.controller;
import java.awt.event.MouseEvent;

import org.simorion.sound.BankOfSounds;
import org.simorion.ui.view.DefaultView;
import org.simorion.ui.view.View;
 
public class OnOffMode extends DeviceMode {
 
    public OnOffMode(ModeMaster m) {
		super(m);
	}

	private OnOffView instance = new OnOffView();
     
    /**
     * Implementation of the View interface for the OnOffView.
     * This defines how the Simori-ON should look for this view.
     * @author Karl Brown
     *
     */
    private class OnOffView extends DefaultView {    	
    	
    }
     
    public View getView() {
        return instance;
    }
    
    /**
     * Functionality for the On/Off button.
     * Two quick clicks will turn the Simori-ON off.
     */
    @Override
    public void onOnOffButtonPress(MouseEvent e){
    	if(e.getClickCount() == 2) System.exit(0);
    	((PerformanceMode)ModeMaster.PERFORMANCE_MODE).isFresh = true;
    	model.enqueueSound(BankOfSounds.ON_SOUND);
        changeMode(ModeMaster.PERFORMANCE_MODE);
    }

	@Override
	public void onOKButtonPress(MouseEvent e) {
		// No methods. The view, it does nothing!
	}

	@Override
	public void onMatrixButtonPress(MouseEvent e, int x, int y) {
		// No methods. The view, it does nothing!
	}
	
	@Override
	public void onMatrixButtonPress(MouseEvent e, int x, int y, boolean lit) {
		// No methods. The view, it does nothing!
	}
     
}