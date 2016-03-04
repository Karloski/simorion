package org.simorion.ui.controller;
import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.simorion.engine.MIDIVoices;
import org.simorion.ui.view.DefaultView;
import org.simorion.ui.view.GUI;
import org.simorion.ui.view.View;
 
/**
 * 
 * @author George Young, Edmund Smith, Petar Krstic
 *
 */
public class ChangeVoiceMode extends DeviceMode {
 
	private int voice = -1;
	
    public ChangeVoiceMode(ModeMaster m) {
		super(m);
		voice = -1;		// Ok button will not let you continue if a voice has not been chosen
	}

	private ChangeVoiceView instance = new ChangeVoiceView();
     
    /**
     * Implementation of the View interface for the ChangeVoiceView
     * @author Karl Brown
     *
     */
    private class ChangeVoiceView extends DefaultView {

    	/** {@inheritDoc} */
    	@Override
    	public boolean isLit(int x, int y) {    		
    		return isRowLit(x) || isColumnLit(y);
    	}
    	
    	/** {@inheritDoc} */
    	@Override
    	public boolean isRowLit(int x) {
    		
    		// If voice is -1, then no button has been selected.
    		if (voice == -1) return false;
    		
    		// Voice will be a number between 0-255 (i.e., the button pressed).
    		return x - ((voice - 1) % 16) == 0;
    	}
    	
    	/** {@inheritDoc} */
    	@Override
    	public boolean isColumnLit(int y) {
    		
    		// If voice is -1, then no button has been selected.
    		if (voice == -1) return false;
    		
    		// Voice will be a number between 0-255 (i.e., the button pressed).
    		return (voice - 1) / 16 == y;
    	}
    	
    	/** {@inheritDoc} */
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
		if(voice != -1) {
			model.setVoice(model.getCurrentLayer(),
				MIDIVoices.getVoice(voice));
		}
		changeMode(ModeMaster.PERFORMANCE_MODE);
		voice = -1;																		// @author Edmund, Petar
	}

	@Override
	public void onMatrixButtonPress(MouseEvent e, int x, int y) {
		voice = y * 16 + x + 1;		
		model.setLCDDisplay(MIDIVoices.getVoice(voice).getName());						// @author Edmund, Petar
	}
	
	@Override
	void onChangedTo() {
		voice = model.getCurrentLayer().getVoice().getMidiVoice();
		model.setLCDDisplay("Change Voice Mode"); 				// @author Edmund, Petar
	}
     
}