package org.simorion.ui.controller;

import java.awt.event.MouseEvent;

import org.simorion.ui.model.MutableModel;
import org.simorion.ui.view.CharacterMap;
import org.simorion.ui.view.GUI;
import org.simorion.ui.view.View;

/**
 * Base class for all device modes
 * @author Karl Brown
 */

public abstract class DeviceMode implements Controller {
	
	private final ModeMaster modeMaster;
	protected MutableModel model;
	
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
	
	/**
	 * View specific handling for MIDI Button presses.
	 * @param e The mouse event associated with the press
	 * @param x The x coordinate of the button.
	 * @param y The y coordinate of the button.
	 */
	@Override
	public void onMatrixButtonPress(MouseEvent e, int x, int y) {
		GUI.getInstance().update();
	}
	
	/**
	 * View specific handling for MIDI Button drag operations.
	 * @param e The mouse event associated with the press
	 * @param x The x coordinate of the button.
	 * @param y The y coordinate of the button.
	 * @param lit Whether the original button dragged from was lit.
	 */
	@Override
	public void onMatrixButtonPress(MouseEvent e, int x, int y, boolean lit) {
		GUI.getInstance().update();
	}
	
	/**
	 * View specific handling for L button presses.
	 * @param e The mouse event associated with the press
	 * @param buttonNum The button that was pressed.
	 */
	@Override
	public void onLButtonPress(MouseEvent e, int buttonNum) {
		//Do nothing; only performance mode routinely handles L buttons
	}

	/**
	 * View specific handling for R button presses.
	 * @param e The mouse event associated with the press
	 * @param buttonNum The button that was pressed.
	 */
	@Override
	public void onRButtonPress(MouseEvent e, int buttonNum) {
		//Do nothing; only performance mode routinely handles R buttons
	}


	/**
	 * Resets this view and the entire Simori-ON before turning it off.
	 */
	@Override
	public void onOnOffButtonPress(MouseEvent e) {
		reset();
		changeMode(ModeMaster.ON_OFF_MODE);
	}
	
	/**
	 * Resets the state of this view.
	 */
	void reset() {
		model.reset();
	}
	
	/**
	 * Loads current parameters for this view.
	 */
	void onChangedTo() {}
	
	/**
	 * Returns the character at position {@code x}, {@code y}.
	 * @param x The x coordinate of the matrix button.
	 * @param y The y coordinate of the matrix button.
	 * @param shift 
	 * @return The character from the character set.
	 */
	protected char getCharacter(int x, int y, boolean shift) {
		int loc = y * 16 + x;		
		return CharacterMap.getCharacter(loc, shift);
	}
	
	/**
	 * Checks if the passed position on the matrix grid represents a character.
	 * @param loc The index position on the matrix grid.
	 * @return True if the location represents a character, false otherwise.
	 */
	protected boolean isCharacter(int loc) {
		return isLetter(loc) || isNumber(loc) || isSymbol(loc);
	}
	
	/**
	 * Checks if the passed position on the matrix grid represents a letter.
	 * @param loc The index position on the matrix grid.
	 * @return True if the location represents a letter, false otherwise.
	 */
	protected boolean isLetter(int loc) {
		return (loc >= 3 && loc < 10) ||
			(loc >= 18 && loc < 27) ||
			(loc >= 34 && loc < 44);
	}
	
	/**
	 * Checks if the passed position on the matrix grid represents a number.
	 * @param loc The index position on the matrix grid.
	 * @return True if the location represents a number, false otherwise.
	 */
	protected boolean isNumber(int loc) {
		return loc >= 50 && loc < 60;
	}
	
	/**
	 * Checks if the passed position on the matrix grid represents a symbol.
	 * @param loc The index position on the matrix grid.
	 * @return True if the location represents a symbol, false otherwise.
	 */
	protected boolean isSymbol(int loc) {
		return (loc == 2) || 
			(loc > 9 && loc <= 12) ||
			(loc > 26 && loc <= 29) ||
			(loc > 43 && loc <= 45) ||
			(loc > 49 && loc <= 61);			
	}
	
	@Override
	public void register(MutableModel m) {
		model = m;
	}
}
