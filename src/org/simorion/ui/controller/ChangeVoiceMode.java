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
public class ChangeVoiceMode extends DeviceMode {
 
    public ChangeVoiceMode(ModeMaster m) {
		super(m);
	}

	private ChangeVoiceView instance = new ChangeVoiceView();
     
    /**
     * Implementation of the View interface for the ChangeVoiceView
     * @author Karl Brown
     *
     */
    private class ChangeVoiceView extends DefaultView {

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
     
    public void onMatrixButtonPressed(MouseEvent e, int x, int y){
    	// FIXME: This should be handled by the controller and not deferred to the view.
        /* instance.clearButtons();
        instance.lightRow(y, Color.ORANGE);
        instance.lightColumn(x, Color.ORANGE); */
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