
package org.simorion.ui.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
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
		View view = new DefaultView();
		
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
		outerPanel.setBorder(newPanel.getBorder());
		outerPanel.setBackground(newPanel.getBackground());
		
		// Retrieve and apply the new button panel data.
		JPanel buttonPanel = (JPanel) view.getButtonPanel();
		buttonPanel.setBorder(buttonPanel.getBorder());
		buttonPanel.setBackground(buttonPanel.getBackground());
		
		// Retrieve and apply the new ON button data.
		AbstractButton newONButton = view.getOnButton();
		buttonOn.setBorder(newONButton.getBorder());
		
		// Retrieve and apply the new OK button data.
		AbstractButton newOKButton = view.getOKButton();
		buttonOK.setBorder(newOKButton.getBorder());
		
		// Retrieve and apply the new LCD button.
		JTextField newLCD = (JTextField) view.getLCDScreen();
		dispLCD.setEditable(newLCD.isEditable());
		dispLCD.setBackground(newLCD.getBackground());
		dispLCD.setForeground(newLCD.getForeground());
		dispLCD.setBorder(newLCD.getBorder());
		dispLCD.setFont(newLCD.getFont());
		
		// Retrieve and apply the new mode button data.
		List<AbstractButton> newModeButtons = (List<AbstractButton>) view.getModeButtons();
		for (ModeButton button : modeButtons) {			
			for (AbstractButton b : newModeButtons) {				
				if (button.getMode() == ((ModeButton) b).getMode()) {
					button.setBorder(b.getBorder());
					button.setText(b.getText());
					break;					
				}				
			}			
		}
		
		// Then update the view.
		update();
	}
	
	/**
	 * Updates the GUI with information from the current view.
	 */
	public void update() {
		
		// Get the current view.
		View view = ModeMaster.getInstance().getView();
		
		// Retrieve new button data and iterate over each button on the view.
		MidiButton[] newMidiButtons = (MidiButton[]) view.getMidiButtons();
		assert(newMidiButtons.length == midiButtons.length);
		for (int r = 0; r < 16; r++) {			
			for (int c = 0; c < 16; c++) {
				// Change the content of each button on the GUI based on this view's data.
				if (view.isLit(r, c)) {
					midiButtons[c * 16 + r].setBackground(MidiButton.LIT_COLOUR);
					midiButtons[c * 16 + r].setBorder(DefaultView.LIT_BORDER);
				}
				// Unlit state for the view.
				else {
					midiButtons[c * 16 + r].setBackground(newMidiButtons[c * 16 + r].getBackground());
					midiButtons[c * 16 + r].setBorder(newMidiButtons[c * 16 + r].getBorder());
				}
				
				// Only really applies to Save/Load views.
				midiButtons[c * 16 + r].setText(newMidiButtons[c * 16 + r].getText());
			}			
		}
		
		// Update the LCD for this view.
		dispLCD.setText(view.getLCDMessage());
		
	}

	/**
	 * Temporary main method on the GUI.
	 * Initialises the ModeMaster (creates instances of each mode).
	 * Creates a (the) new GUI, and sets its parameters.
	 * @param argv Not used.
	 */
	public static void main(String[] argv) {
		ModeMaster.init();
		GUI frame = getInstance();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private static GUI instance;
	
	public static GUI newInstance() {
		instance = new GUI();
		return instance;
	}
	
	public static GUI getInstance() {		
		if (instance == null) instance = new GUI();
		
		return instance;
	}
}