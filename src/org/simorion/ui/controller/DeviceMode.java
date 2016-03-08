package org.simorion.ui.controller;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.simorion.common.ImmutableRow;
import org.simorion.common.util.Util;
import org.simorion.ui.model.MutableModel;
import org.simorion.ui.view.GUI;
import org.simorion.ui.view.View;

/**
 * Base class for all device modes
 * @author Edmund Smith
 */

public abstract class DeviceMode implements Controller {
	
	private final ModeMaster modeMaster;
	protected MutableModel model;
	protected boolean isDirty;
	
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
	public void onMatrixButtonPress(MouseEvent e, int x, int y) {
		GUI.getInstance().update();
	}
	
	@Override
	public void onMatrixButtonPress(MouseEvent e, int x, int y, boolean lit) {
		GUI.getInstance().update();
	}
	
	@Override
	public void onLButtonPress(MouseEvent e, int buttonNum) {
		//TODO: Change mode logic
	}

	@Override
	public void onRButtonPress(MouseEvent e, int buttonNum) {
		//TODO: Change mode logic
	}


	@Override
	public void onOnOffButtonPress(MouseEvent e) {
		//TODO: Change mode logic
	}
	
	void onChangedTo() {}
	
	protected char getCharacter(int x, int y, int shift) {
		int loc = y * 16 + x;
		
		// Returns an alphabetic character.
		if (isLetter(loc)) {
			return (char) (loc + 97 + shift);
		}
		// Returns a numeric character.
		else if (isNumber(loc)) {
			return (char) (loc + 16);
		}
		
		return '\0';
	}
	
	protected boolean isCharacter(int loc) {
		return isLetter(loc) || isNumber(loc);
	}
	
	protected boolean isLetter(int loc) {
		return loc >= 0 && loc < 26;
	}
	
	protected boolean isNumber(int loc) {
		return loc >= 32 && loc < 42;
	}
	
	@Override
	public void register(MutableModel m) {
		model = m;
	}
}
