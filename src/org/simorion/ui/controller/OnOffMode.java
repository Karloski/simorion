package org.simorion.ui.controller;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTextField;

import org.simorion.ui.view.ButtonFactory;
import org.simorion.ui.view.DefaultView;
import org.simorion.ui.view.View;
import org.simorion.ui.view.ButtonFactory.MidiButton;
 
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
    	
    	/** {@inheritDoc} */
    	@Override
    	public AbstractButton[] getMidiButtons() {
    		
    		midiButtons = new MidiButton[256];
    		
    		// FIXME: Matrix size takes from model, however there is as of yet no model implementations.
    		//int noButtons = matrixSize().left * matrixSize().right;
    		int noButtons = 256; // 16 * 16
    		
    		// j is a variable which is incremented when the end of the grid is reached (every 16 buttons)
    		int j = -1;
    		
    		for (int k = 0; k < noButtons; k++) {
    			
    			// Create new MidiButton with it's location parameters
    			if (k % 16 == 0){
    				// Increment j every 16 buttons
    				j++;
    			}	
    			
    			MidiButton b = ButtonFactory.createButton(k % 16, j);
    			
    			b.setBackground(null);
    			
    			midiButtons[k] = b;
    		}
    		
    		return midiButtons;
    	}
    	
    	/** {@inheritDoc} */
    	@Override
    	public JComponent getLCDScreen() {
    		
    		// Create and define the LCD screen.
    		dispLCD = new JTextField();
    		
    		dispLCD.setBounds(120, 545, 240, 50);
    		dispLCD.setEditable(false);
    		dispLCD.setBackground(Color.white);
    		dispLCD.setBorder(BorderFactory.createLineBorder(Color.black));
    		dispLCD.setFont(new Font("Cambria", Font.PLAIN, 21));
    		
    		return dispLCD;
    		
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