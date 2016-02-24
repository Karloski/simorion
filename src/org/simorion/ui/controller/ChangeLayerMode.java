package org.simorion.ui.controller;

import java.awt.event.MouseEvent;

import org.simorion.ui.model.ImmutableModel;
import org.simorion.ui.view.DefaultView;
import org.simorion.ui.view.View;
 
/**
 * ChangeLayerMode ready for full impl
 * @author George Young
 */
public class ChangeLayerMode extends DeviceMode {
 
    public ChangeLayerMode(ModeMaster m) {
		super(m);
	}

	private ChangeLayerView instance = new ChangeLayerView();
     
    /**
     * Implementation of the View interface for the ChangeLayerView
     * @author Karl Brown
     *
     */
    private class ChangeLayerView extends DefaultView {

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
	public void register(ImmutableModel model) {
		// TODO Auto-generated method stub
		
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