package org.simorion.ui.controller;
import java.awt.event.MouseEvent;

import org.simorion.ui.model.ImmutableModel;
import org.simorion.ui.view.DefaultView;
import org.simorion.ui.view.View;
 
/**
 * 
 * @author George Young
 *
 */
public class LoadConfigMode extends DeviceMode {
 
    public LoadConfigMode(ModeMaster m) {
		super(m);
		// TODO Auto-generated constructor stub
	}

	private LoadConfigView instance = new LoadConfigView();
     
    /**
     * Implementation of the View interface for the LoadConfigView
     * @author Karl Brown
     *
     */
    private class LoadConfigView extends DefaultView {

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