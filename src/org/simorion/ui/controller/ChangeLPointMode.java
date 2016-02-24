package org.simorion.ui.controller;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.simorion.common.util.Util.Pair;
import org.simorion.ui.model.ImmutableModel;
import org.simorion.ui.view.ButtonFactory;
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
    private class ChangeLPointView implements View {

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
    	public Pair<Integer, Integer> getMatrixSize() {    		
			// Return a new pair representing the size of this view's button matrix.
    		return new Pair<Integer, Integer>(16, 16);
    	}
		
		/** {@inheritDoc} */
		@Override
    	public JComponent getOuterPanel() {
    		
			// Create and define the outer JPanel.
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
			
			// Create and define the inner JPanel.
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
			
			// Use the ButtonFactory to create the ON/OFF button.
			AbstractButton btnON = ButtonFactory.createButton("ON", ButtonFactory.Button.ONOFF);
			
			// Define it.
			btnON.setBounds(275, 5, 50, 50);
			btnON.setBorder(BorderFactory.createLineBorder(Color.black));
			
			// Return it.
			return btnON;
			
		}
		
		/** {@inheritDoc} */
		@Override
    	public AbstractButton getOKButton() {
			
			// Use the ButtonFactory to create the OK button.
			AbstractButton btnOK = ButtonFactory.createButton("OK", ButtonFactory.Button.OK);
			
			// Define it.
			btnOK.setBounds(432, 545, 50, 50);
			btnOK.setBorder(BorderFactory.createLineBorder(Color.black));
			
			// Return it.
			return btnOK;
		}
		
		/** {@inheritDoc} */
		@Override
    	public Iterable<AbstractButton> getModeButtons() {			
			
			// A list of buttons to return as an iterable.
			List<AbstractButton> buttons = new ArrayList<AbstractButton>();
			
			// Four buttons for the left side.
			for (int i = 1; i < 5; i++) {
				
				// Create the button from the ButtonFactory.
				AbstractButton b = ButtonFactory.createButton("L" + i, ButtonFactory.Button.MODE);
				
				// Each button has its own bounds (position).
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
				
				// Each button has a black border.
				b.setBorder(BorderFactory.createLineBorder(Color.black));
				buttons.add(b);
			}
			
			// Four buttons for the right side.
			for (int i = 1; i < 5; i++) {
				
				// Create the button from the ButtonFactory.
				AbstractButton b = ButtonFactory.createButton("R" + i, ButtonFactory.Button.MODE);
				
				// Each button has its own bounds (position).
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
				
				// Each button has a black border.
				b.setBorder(BorderFactory.createLineBorder(Color.black));
				buttons.add(b);
			}
			
			return buttons;
			
		}
		
		/** {@inheritDoc} */
		@Override
		public Iterable<AbstractButton> getMidiButtons() {
			
			List<AbstractButton> buttons = new ArrayList<AbstractButton>();
			
			// FIXME: Matrix size takes from model, however there is as of yet no model implementations.
			//int noButtons = matrixSize().left * matrixSize().right;
			int noButtons = 256; // 16 * 16
			
			int xLocationOfButton = 0;
			int yLocationOfButton = 0;
			// j is a variable which is incremented when the end of the grid is reached (every 16 buttons)
			int j = 0;
			
			for (int k = 0; k < noButtons; k++) {
				
				AbstractButton b = ButtonFactory.createButton(k % 16, j);
				
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
			
			// Create and define the LCD screen.
			JTextField dispLCD = new JTextField();
			
			dispLCD.setBounds(120, 545, 240, 50);
			dispLCD.setEditable(false);
			dispLCD.setBackground(Color.WHITE);
			dispLCD.setBorder(BorderFactory.createLineBorder(Color.black));
			dispLCD.setFont(new Font("Cambria", Font.PLAIN, 21));
			
			return dispLCD;
			
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