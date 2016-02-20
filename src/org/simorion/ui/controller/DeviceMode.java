package org.simorion.ui.controller;

import java.awt.event.MouseEvent;

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
	
}
