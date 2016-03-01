package org.simorion.ui.controller;
import java.awt.event.MouseEvent;

import org.simorion.ui.view.DefaultView;
import org.simorion.ui.view.View;
 
public class SaveConfigMode extends DeviceMode {
 
    public SaveConfigMode(ModeMaster m) {
		super(m);
		// TODO Auto-generated constructor stub
	}

	private SaveConfigView instance = new SaveConfigView();
     
    /**
     * Implementation of the View interface for the SaveConfigView
     * @author Karl Brown
     *
     */
    private class SaveConfigView extends DefaultView {
		
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