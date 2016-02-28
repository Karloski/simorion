package org.simorion.ui.controller;
import java.awt.event.MouseEvent;

import org.simorion.ui.model.ImmutableModel;
import org.simorion.ui.view.DefaultView;
import org.simorion.ui.view.View;
 
/**
 * ChangeLPointMode ready for full impl
 * @author George Young
 */
public class ChangeLPointMode extends DeviceMode {
 
    public ChangeLPointMode(ModeMaster m) {
		super(m);
	}

	private ChangeLPointView instance = new ChangeLPointView();
     
    /**
     * Implementation of the View interface for the ChangeLPointView
     * @author Karl Brown
     *
     */
    private class ChangeLPointView extends DefaultView {

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
	public void onOKButtonPress(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMatrixButtonPress(MouseEvent e, int buttonColumn, int buttonRow) {
		// TODO Auto-generated method stub
		
	}
     
}