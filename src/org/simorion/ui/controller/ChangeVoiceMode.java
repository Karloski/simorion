package org.simorion.ui.controller;
import java.awt.event.MouseEvent;

import org.simorion.engine.MIDIVoices;
import org.simorion.ui.view.DefaultView;
import org.simorion.ui.view.View;
 
/**
 * 
 * @author George Young
 *
 */
public class ChangeVoiceMode extends DeviceMode {
 
	private int voice;
	
    public ChangeVoiceMode(ModeMaster m) {
		super(m);
		voice = -1;
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
    
	@Override
	public void onOKButtonPress(MouseEvent e) {
		if(voice != -1) {
			model.setVoice(model.getCurrentLayer(),
				MIDIVoices.getVoice(voice));
		}
		changeMode(ModeMaster.PERFORMANCE_MODE);
		voice = -1;
	}

	@Override
	public void onMatrixButtonPress(MouseEvent e, int buttonColumn, int buttonRow) {
		voice = 16*buttonRow + buttonColumn + 1;
		model.setLit(model.getCurrentLayerId(), buttonColumn, buttonRow);
		model.setLCDDisplay(MIDIVoices.getVoice(voice).getName());
	}
	
	@Override
	void onChangedTo() {
		System.out.println(model);
		voice = model.getCurrentLayer().getVoice().getMidiVoice();
		model.setLCDDisplay(model.getCurrentLayer().getVoice().getName());
	}
     
}