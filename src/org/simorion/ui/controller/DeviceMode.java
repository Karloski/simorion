package org.simorion.ui.controller;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.simorion.common.ImmutableRow;
import org.simorion.common.util.Util;
import org.simorion.ui.model.ImmutableModel;
import org.simorion.ui.view.View;

/**
 * Base class for all device modes
 * @author Edmund Smith
 */

public abstract class DeviceMode implements Controller {
	
	private final ModeMaster modeMaster;
	protected ImmutableModel model;
	
	public DeviceMode(ModeMaster m) {
		modeMaster = m;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public View getView() {
		throw new RuntimeException("Not yet implemented!");
	}

	public void changeMode(DeviceMode newMode) {
		modeMaster.changeMode(newMode);
	}
	
	@Override
	public void onLButtonPress(MouseEvent e, int buttonNum) {
		System.out.println("L button "+buttonNum+" pressed");
		//TODO: Change mode logic
	}

	@Override
	public void onRButtonPress(MouseEvent e, int buttonNum) {
		System.out.println("R button "+buttonNum+" pressed");
		//TODO: Change mode logic
	}


	@Override
	public void onOnOffButtonPress(MouseEvent e) {
		System.out.println("OnOff button pressed");
		//TODO: Change mode logic
	}
	
	@Override
	public void register(ImmutableModel m) {
		model = m;
	}
	
	// Methods which retrieve information from the model.
	// Conceivably, these could be in view implementation for each mode.
	// However, as the behaviour is shared across each mode, it made sense to me to place them in the abstract base class.
	// @author (for all the below): Karl Brown. 
	/**
	 * Retrieves and returns whether or not the button at coordinate {@code x}, {@code y} is lit.
	 * @param x The x coordinate of the button.
	 * @param y The y coordinate of the button.
	 * @return True if lit, false otherwise.
	 */
	public boolean isLit(int x, int y) {
		return model.getCurrentLayer().getRow(y).isLit(x);
	}

	/**
	 * Determines if the row of buttons {@code row} is on are lit. 
	 * @param y The row to check.
	 * @return True if all buttons are lit, false otherwise.
	 */
	public boolean isRowLit(int x) {
		// Each row should contain the same number of buttons.
		int columns = model.getCurrentLayer().getRow(0).cellCount();
		
		// Keeping the row constant, check the state of each button.
		for (int i = 0; i < columns; i++) {
			if (!isLit(x, i)) return false;
		}
		
		// Not all the buttons on this row are lit.
		return true;
	}

	/**
	 * Determines if the column of buttons {@code column} is on are lit. 
	 * @param x The column to check.
	 * @return True if all buttons are lit, false otherwise.
	 */
	public boolean isColumnLit(int y) {			
		// Each row should contain the same number of buttons.
		int rows = Util.count(model.getCurrentLayer().getRows());
		
		// Keeping the row constant, check the state of each button.
		for (int i = 0; i < rows; i++) {
			if (!isLit(i, y)) return false;
		}
		
		// Not all the buttons on this row are lit.
		return true;
	}
	
	/**
	 * Retrieves and returns a collection of iterable booleans representing all the currently lit matrix buttons.
	 * @return A collection of iterable booleans representing the currently lit buttons.
	 */
	public Collection<Iterable<Boolean>> getLitButtons() {
		
		List<Iterable<Boolean>> lit = new ArrayList<Iterable<Boolean>>();
		
		for (ImmutableRow r : model.getCurrentLayer().getRows()) {				
			lit.add(Util.bitstring(r.getLit()));
		}
		
		return lit;
		
	}

	/**
	 * Retrieves and returns the current textual output of the LCD.
	 * @return The textual output of the LCD.
	 */
	public String getLCDMessage() {
		return model.getCurrentLayer().getLCDMessage();
	}

	/**
	 * Retrieves and returns the MIDI ID of the currently applied instrument.
	 * @return The MIDI ID of the currently applied instrument for this view.
	 */
	public int getVoiceId() {
		return model.getCurrentLayer().getVoice().getMidiVoice();
	}

	/**
	 * Retrieves and returns the name of the currently applied instrument.
	 * @return The name of the currently applied instrument for this view
	 */
	public String getVoiceName() {
		return model.getCurrentLayer().getVoice().getName();
	}

	/**
	 * Retrieves and returns the ID of the currently applied layer.
	 * @return The ID of the currently applied layer for this view.
	 */
	public int getCurrentLayerId() {
		return model.getCurrentLayer().getLayerNumber();
	}

	/**
	 * Retrieves and returns the current loop point.
	 * @return The current loop point for this view.
	 */
	public int getLoopPoint() {
		return model.getCurrentLayer().getLoopPoint();
	}

	/**
	 * Retrieves and returns the current velocity for notes played.
	 * @return The current velocity for notes played on this view.
	 */
	public int getVelocity() {
		return model.getCurrentLayer().getVelocity();
	}
	
	/**
	 * Retrieves and returns the current note for row {@code y}.
	 * @param y The row to check.
	 * @return The current note for row {@code y}
	 */
	public byte getNote(int y) {
		return model.getCurrentLayer().getRow(y).getNote();
	} 
	
}
