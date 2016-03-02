package org.simorion.ui.view;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.simorion.ui.controller.ModeMaster;
import org.simorion.ui.view.ButtonFactory.MidiButton;
import org.simorion.ui.view.ButtonFactory.ModeButton;
import org.simorion.ui.view.ButtonFactory.OKButton;
import org.simorion.ui.view.ButtonFactory.ONButton;

/**
 * GUI for the SimoriOn
 * all buttons are created and added - all containing action listeners
 * 
 * @author Karl Brown, Petar Krstic
 * @version 2.0
 */
public class GUI extends JFrame { 
	// All the grid buttons are MidiButtons(extended JButton)
	// Initialise the array to hold them all
	private JPanel outerPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	
	private ONButton buttonOn;
	private OKButton buttonOK;
	private JTextField dispLCD;
	
	MidiButton[] midiButtons = new MidiButton[256];
	List<ModeButton> modeButtons = new ArrayList<ModeButton>();
	
	/**
	 * Where all the buttons are added to the UI 
	 * There is no set layout and all the buttons have their bounds set
	 * Also where the array of MidiButtons is made
	 * @author Karl Brown, Petar Krstic
	 */
	public GUI() {
	
		// Get the current view.
		// For the constructor, this will be the ONOFF view.
		View view = ModeMaster.getInstance().getView();
		
		// Draw the Simori-ON based on the current view.
		setTitle(view.getTitle()); // Set the title.
		setSize(view.getSize().left, view.getSize().right); // Set the size in pixels.
		
		outerPanel = (JPanel) add(view.getOuterPanel()); // Add the outer panel.
		buttonPanel = (JPanel) outerPanel.add(view.getButtonPanel()); // Add the inner panel.
		
		// Add the ON and OK buttons.
		buttonOn = (ONButton) outerPanel.add(view.getOnButton());
		buttonOK = (OKButton) outerPanel.add(view.getOKButton());
		
		// Add the LCD Screen.
		dispLCD = (JTextField) outerPanel.add(view.getLCDScreen());
		
		// Add each MODE button.
		for (AbstractButton button : view.getModeButtons()) {
			outerPanel.add(button);
			modeButtons.add((ModeButton) button);
		}
		
		// Add each MIDI (matrix) button.
		midiButtons = (MidiButton[]) view.getMidiButtons();
		
		for (AbstractButton button : midiButtons) {
			buttonPanel.add(button);
		}
		
	}
	
	/**
	 * Redraws the current GUI. Should be called when the mode is changed.
	 * @author Karl Brown
	 */	
	public void redraw() {
		
		// Get the current view.
		View view = ModeMaster.getInstance().getView();
		
		setTitle(view.getTitle()); // Set the title.
		setSize(view.getSize().left, view.getSize().right); // Set the size in pixels.
		
		// Retrieve and apply the new outer panel data.
		JPanel newPanel = (JPanel) view.getOuterPanel();
		outerPanel.setBounds(newPanel.getBounds());
		outerPanel.setBorder(newPanel.getBorder());
		outerPanel.setLayout(newPanel.getLayout());
		outerPanel.setBackground(newPanel.getBackground());
		
		// Retrieve and apply the new button panel data.
		JPanel buttonPanel = (JPanel) view.getButtonPanel();
		buttonPanel.setBounds(buttonPanel.getBounds());
		buttonPanel.setBorder(buttonPanel.getBorder());
		buttonPanel.setLayout(buttonPanel.getLayout());
		buttonPanel.setBackground(buttonPanel.getBackground());
		
		// Retrieve and apply the new ON button data.
		AbstractButton newONButton = view.getOnButton();
		buttonOn.setBounds(newONButton.getBounds());
		buttonOn.setBorder(newONButton.getBorder());
		
		// Retrieve and apply the new OK button data.
		AbstractButton newOKButton = view.getOKButton();
		buttonOK.setBounds(newOKButton.getBounds());
		buttonOK.setBorder(newOKButton.getBorder());
		
		// Retrieve and apply the new LCD button.
		JTextField newLCD = (JTextField) view.getLCDScreen();
		dispLCD.setBounds(newLCD.getBounds());
		dispLCD.setEditable(newLCD.isEditable());
		dispLCD.setBackground(newLCD.getBackground());
		dispLCD.setBorder(newLCD.getBorder());
		dispLCD.setFont(newLCD.getFont());
		
		// Retrieve and apply the new mode button data.
		List<AbstractButton> newModeButtons = (List<AbstractButton>) view.getModeButtons();
		for (ModeButton button : modeButtons) {			
			for (AbstractButton b : newModeButtons) {				
				if (button.getMode() == ((ModeButton) b).getMode()) {
					button.setBounds(b.getBounds());
					button.setBorder(b.getBorder());
					break;					
				}				
			}			
		}
		
		// Retrieve and apply the new midi button data.
		MidiButton[] newMidiButtons = (MidiButton[]) view.getMidiButtons();
		for (int r = 0; r < 16; r++) {			
			for (int c = 0; c < 16; c++) {
				midiButtons[r * 16 + c].setBounds(newMidiButtons[r * 16 + c].getBounds());
				if (view.isLit(r, c)) {
					midiButtons[r * 16 + c].setBackground(Color.ORANGE);
				}
				else {
					midiButtons[r * 16 + c].setBackground(null);
				}
			}			
		}
		
		dispLCD.setText(view.getLCDMessage());
	}
	
	/**
	 * Should be called when the current mode is altered.
	 */
	public void update() {
		
		View view = ModeMaster.getInstance().getView();
		
		for (int r = 0; r < 16; r++) {			
			for (int c = 0; c < 16; c++) {
				if (view.isLit(r, c)) {
					midiButtons[r * 16 + c].setBackground(Color.ORANGE);
				}
				else {
					midiButtons[r * 16 + c].setBackground(Color.WHITE);
				}
			}			
		}
		
		dispLCD.setText(view.getLCDMessage());
		
	}

	public static void main(String[] argv) {
		GUI frame = getInstance();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private static GUI instance;
	
	public static GUI getInstance() {
		
		if (instance == null) instance = new GUI();
		
		return instance;
	}
}
