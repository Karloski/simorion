package org.simorion.ui.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.simorion.common.Row;
import org.simorion.common.util.Util;
import org.simorion.common.util.Util.Pair;
import org.simorion.ui.model.Model;
import org.simorion.ui.view.ButtonFactory;
import org.simorion.ui.view.View;
 
public class PerformanceMode extends DeviceMode {
 
    public PerformanceMode(ModeMaster m) {
		super(m);
	}
    
	private PerformanceView instance = new PerformanceView();
     
    /**
     * Implementation of the View interface for the PerformanceView
     * @author Karl Brown
     *
     */
    private class PerformanceView implements View {

		/** {@inheritDoc} */
		@Override
    	public String getTitle() {
    		return "Simori-ON";
    	}
		
		/** {@inheritDoc} */
		@Override
    	public Pair<Integer, Integer> getSize() {
    		return new Pair<Integer, Integer>(607, 632);
    	}
    	
		/** {@inheritDoc} */
		@Override
    	public Pair<Integer, Integer> matrixSize() {    		
			// Return a new pair representing the size of this view's button matrix.
    		return new Pair<Integer, Integer>(Util.count(model.getCurrentLayer().getRows()), model.getCurrentLayer().getRow(0).cellCount());
    	}
		
		/** {@inheritDoc} */
		@Override
    	public JComponent getOuterPanel() {
    		
    		JPanel outerPanel = new JPanel();
    		
    		outerPanel.setBounds(1, 1, 598, 600);
    		outerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    		outerPanel.setLayout(null);
    		outerPanel.setBackground(Color.white);
    		
    		return outerPanel;
    		
    	}
		
		/** {@inheritDoc} */
		@Override
		public JComponent getButtonPanel() {
			
			JPanel buttonPanel = new JPanel();
			
			buttonPanel.setBounds(58, 58, 484, 484);
			buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black));
			buttonPanel.setLayout(null);
			buttonPanel.setBackground(Color.white);
			
			return buttonPanel;
			
		}
		
		/** {@inheritDoc} */
		@Override
    	public AbstractButton getOnButton() {
			AbstractButton btnON = ButtonFactory.createButton("OK", ButtonFactory.Button.OK);
			btnON.setBounds(275, 5, 50, 50);
			btnON.setBorder(BorderFactory.createLineBorder(Color.black));
			return btnON;		
		}
		
		/** {@inheritDoc} */
		@Override
    	public AbstractButton getOKButton() {			
			AbstractButton btnOK = ButtonFactory.createButton("OK", ButtonFactory.Button.OK);
			btnOK.setBounds(432, 545, 50, 50);
			btnOK.setBorder(BorderFactory.createLineBorder(Color.black));
			return btnOK;
		}
		
		/** {@inheritDoc} */
		@Override
    	public Iterable<AbstractButton> getModeButtons() {			
			
			List<AbstractButton> buttons = new ArrayList<AbstractButton>();
			
			for (int i = 1; i < 5; i++) {
				
				AbstractButton b = ButtonFactory.createButton("L" + i, ButtonFactory.Button.MODE);
				
				switch (i) {
				case 1:
					b.setBounds(5, 84, 50, 50);
					break;
				case 2:
					b.setBounds(5, 174, 50, 50);
					break;
				case 3:
					b.setBounds(5, 264, 50, 50);
					break;
				case 4:
					b.setBounds(5, 354, 50, 50);
				}
				
				b.setBorder(BorderFactory.createLineBorder(Color.black));
				buttons.add(b);
			}
			
			for (int i = 1; i < 5; i++) {
				
				AbstractButton b = ButtonFactory.createButton("L" + i, ButtonFactory.Button.MODE);
				
				switch (i) {
				case 1:
					b.setBounds(545, 84, 50, 50);
					break;
				case 2:
					b.setBounds(545, 174, 50, 50);	
					break;
				case 3:
					b.setBounds(545, 264, 50, 50);
					break;
				case 4:
					b.setBounds(545, 354, 50, 50);
				}
				
				b.setBorder(BorderFactory.createLineBorder(Color.black));
				buttons.add(b);
			}
			
			return buttons;
			
		}
		
		/** {@inheritDoc} */
		@Override
		public Iterable<AbstractButton> getMidiButtons() {
			
			List<AbstractButton> buttons = new ArrayList<AbstractButton>();
			
			int xLocationOfButton = 0;
			int yLocationOfButton = 0;
			// j is a variable which is incremented when the end of the grid is reached (every 16 buttons)
			int j = 0;
			
			for (int k = 0; k < buttons.size(); k++) {
				
				AbstractButton b = ButtonFactory.createButton(new Pair<Integer, Integer>(k % 16, j));
				
				// Create new MidiButton with it's location parameters
				if ((k + 1) % 16 == 0){
					// Increment j every 16 buttons
					j++;
				}	
				
				b.setBounds(2 + (30 * xLocationOfButton), 452 - (30 * yLocationOfButton), 30, 30);
				
				// Setting bounds of the button depending on it's co-ords
				if((k + 1) % 16 == 0) {
					// When reaching 16 buttons - go back to left hand side of grid and increment y
					xLocationOfButton = 0;
					yLocationOfButton++;
				}
				else {
					// If not at the end of the grid - just increment x
					xLocationOfButton++;
				}
				
				buttons.add(b);
			}
			
			return buttons;
		}
		
		/** {@inheritDoc} */
		@Override
		public JComponent getLCDScreen() {
			
			JTextField dispLCD = new JTextField();
			
			dispLCD.setBounds(120, 545, 240, 50);
			dispLCD.setEditable(false);
			dispLCD.setBackground(Color.WHITE);
			dispLCD.setBorder(BorderFactory.createLineBorder(Color.black));
			dispLCD.setFont(new Font("Cambria", Font.PLAIN, 21));
			
			return dispLCD;
			
		}
    	
		/** {@inheritDoc} */
		@Override
		public boolean isLit(int x, int y) {
			return model.getCurrentLayer().getRow(y).isLit(x);
		}
		
		/** {@inheritDoc} */
		@Override
		public Collection<Iterable<Boolean>> getLitButtons() {
			
			List<Iterable<Boolean>> lit = new ArrayList<Iterable<Boolean>>();
			
			for (Row r : model.getCurrentLayer().getRows()) {				
				lit.add(Util.bitstring(r.getLit()));			
			}
			
			return lit;
			
		}

		/** {@inheritDoc} */
		@Override
		public boolean isRowLit(int x) {
			// Each row should contain the same number of buttons.
			int columns = matrixSize().right;
			
			// Keeping the row constant, check the state of each button.
			for (int i = 0; i < columns; i++) {
				if (!isLit(x, i)) return false;
			}
			
			// Not all the buttons on this row are lit.
			return true;
		}

		/** {@inheritDoc} */
		@Override
		public boolean isColumnLit(int y) {			
			// Each row should contain the same number of buttons.
			int rows = matrixSize().left;
			
			// Keeping the row constant, check the state of each button.
			for (int i = 0; i < rows; i++) {
				if (!isLit(i, y)) return false;
			}
			
			// Not all the buttons on this row are lit.
			return true;
		}

		/** {@inheritDoc} */
		@Override
		public String getLCDMessage() {
			return model.getCurrentLayer().getLCDMessage();
		}
		
		/** {@inheritDoc} */
		@Override
		public int getVoiceId() {
			return model.getCurrentLayer().getVoice().getMidiVoice();
		}
		
		/** {@inheritDoc} */
		@Override
		public String getVoiceName() {
			return model.getCurrentLayer().getVoice().getName();
		}
		
		/** {@inheritDoc} */
		@Override
		public int getCurrentLayerId() {
			return model.getCurrentLayer().getLayerNumber();
		}
		
		/** {@inheritDoc} */
		@Override
		public int getLoopPoint() {
			return model.getCurrentLayer().getLoopPoint();
		}
		
		/** {@inheritDoc} */
		@Override
		public int getVelocity() {
			return model.getCurrentLayer().getVelocity();
		}
		
		/** {@inheritDoc} */
		@Override
		public byte getNote(int y) {
			return model.getCurrentLayer().getRow(y).getNote();
		} 
    }
     
    public View getView() {
        return instance;
    }
     
    @Override
    public void onOnOffButtonPress(MouseEvent e) {
    	// FIXME: This should be handled by the controller and not deferred to the view.
        //instance.clearButtons();
        changeMode(ModeMaster.ON_OFF_MODE);
    }
     
    public void onLButtonPressed(MouseEvent e, int ButtonNum){
          
        switch (ButtonNum){
            case 1 : 
                changeMode(ModeMaster.CHANGE_VOICE_MODE);
                break;
            case 2 : 
                changeMode(ModeMaster.CHANGE_VELOCITY_MODE);
                break;
            case 3 : 
                changeMode(ModeMaster.CHANGE_LOOP_SPEED_MODE);
                break;
            case 4 : 
                changeMode(ModeMaster.CHANGE_LOOP_POINT_MODE);
                break;
        }
    }
     
    public void onRButtonPressed(MouseEvent e, int ButtonNum){
          
        switch (ButtonNum){
            case 1 : 
                changeMode(ModeMaster.CHANGE_LAYER_MODE);
                break;
            case 2 : 
                changeMode(ModeMaster.SAVE_CONFIG_MODE);
                break;
            case 3 : 
                changeMode(ModeMaster.LOAD_CONFIG_MODE);
                break;
            case 4 : 
                changeMode(ModeMaster.MASTER_SLAVE_MODE);
                break;
        }
    }
     
    public void onMatrixPressed(MouseEvent e, int x, int y){
        //send button pressed to model
        if (model.getCurrentLayer().getRow(y).isLit(x)){
        	// FIXME: This should be handled by the controller and not deferred to the view.
            //instance.lightButton(x, y, Color.ORANGE);
        }else{
        	// FIXME: This should be handled by the controller and not deferred to the view.
            //instance.lightButton(x, y, Color.WHITE);
        }
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