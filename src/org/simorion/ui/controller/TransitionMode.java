package org.simorion.ui.controller;

import java.awt.event.MouseEvent;

import org.simorion.ui.view.DefaultView;
import org.simorion.ui.view.GUI;
import org.simorion.ui.view.View;

/**
 * Class that is called when one mode wants to transition to another.
 * Currently simply works for transitioning between On/Off to Performance Mode.
 * @author Karl
 *
 */
public class TransitionMode extends DeviceMode {
	
	boolean[] buttons = new boolean[DefaultView.MATRIX_SIZE.left * DefaultView.MATRIX_SIZE.right];
	public static final long TRANSITION_LENGTH = 3 * 1000;  // 3 seconds.

	public TransitionMode(ModeMaster m) {
		super(m);
	}
	
	private TransitionView instance = new TransitionView();
	
	private class TransitionView extends DefaultView {
		
    	/** {@inheritDoc} */
    	@Override
    	public boolean isLit(int x, int y) {
    		if (buttons[y * DefaultView.MATRIX_SIZE.right + x]) 
    			return buttons[y * DefaultView.MATRIX_SIZE.right + x];
    		else return false;
    	}
		
    	/**
    	 * Retrieves and returns the current textual output of the LCD.
    	 * @return The textual output of the LCD.
    	 */
    	@Override
    	public String getLCDMessage() {
    		return model.getLCDDisplay();
    	}
    	
	}
	
    public View getView() {
        return instance;
    }
	
	@Override
	public void onOKButtonPress(MouseEvent e) {
		// No methods. The view in non-interactable.
	}

	@Override
	public void onMatrixButtonPress(MouseEvent e, int x, int y) {
		// No methods. The view in non-interactable.
	}
	
	@Override
	public void onMatrixButtonPress(MouseEvent e, int x, int y, boolean lit) {
		// No methods. The view in non-interactable.
	}
	
	/**
	 * On changed to, play the transition effect.
	 */
	@Override
	public void onChangedTo() {		
		long startTime = System.currentTimeMillis();
		
		model.setLCDDisplay("Welcome to Simori-ON");
		GUI.getInstance().update();
		
		while (System.currentTimeMillis() < startTime + TRANSITION_LENGTH) {
			
			for (int i = 0; i < DefaultView.MATRIX_SIZE.left * DefaultView.MATRIX_SIZE.right; i++) {
				buttons[i] = buttons[i] == Math.random() > 0.5f ? false : true;
			}
			GUI.getInstance().update();
			
		}
	}

}
