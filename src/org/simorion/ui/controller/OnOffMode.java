package org.simorion.ui.controller;
import java.awt.event.MouseEvent;

import org.simorion.ui.view.DefaultView;
import org.simorion.ui.view.View;
 
public class OnOffMode extends DeviceMode {
 
    public OnOffMode(ModeMaster m) {
		super(m);
		// TODO Auto-generated constructor stub
	}

	private OnOffView instance = new OnOffView();
     
    /**
     * Implementation of the View interface for the OnOffView.
     * This defines how the Simori-ON should look for this view.
     * @author Karl Brown
     *
     */
    private class OnOffView extends DefaultView {    	
    	// No methods. The view, it does nothing!        
    }
     
    public View getView() {
        return instance;
    }
     
    @Override
    public void onOnOffButtonPress(MouseEvent e){
        changeMode(ModeMaster.PERFORMANCE_MODE);
    }

	@Override
	public void onOKButtonPress(MouseEvent e) {
		// No methods. The view, it does nothing!
	}

	@Override
	public void onMatrixButtonPress(MouseEvent e, int buttonColumn, int buttonRow) {
		// No methods. The view, it does nothing!
	}
	
	@Override
	public void onMatrixButtonPress(MouseEvent e, int x, int y, boolean lit) {
		// No methods. The view, it does nothing!
	}
     
}