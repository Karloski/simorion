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

		@Override
		public void setLit(int x, int y) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setLCDMessage() {
			// TODO Auto-generated method stub
			
		}
        
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
		System.out.println("OK button pressed");
	}

	@Override
	public void onMatrixButtonPress(MouseEvent e, int buttonColumn, int buttonRow) {
		// Set button to lit (or unlit) in the model.
		// Model then updates the view.
	}
     
}