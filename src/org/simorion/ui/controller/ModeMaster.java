package org.simorion.ui.controller;

import java.awt.event.MouseEvent;

import org.simorion.ui.model.Model;
import org.simorion.ui.view.View;

public class ModeMaster implements Controller {
	
	private static ModeMaster instance;
	
	private Model model;
	
	private DeviceMode deviceMode;
	
	public DeviceMode getMode() {
		return deviceMode;
	}
	
	@Override
	public View getView() {
		return deviceMode.getView();
	}

	@Override
	public void register(Model model) {
		this.model = model;
	}

	public void changeMode(DeviceMode newMode) {
		deviceMode = newMode;
	}
	
	public static ModeMaster getInstance() {
		if(instance == null) {
			synchronized(instance) {
				if(instance == null) {
					instance = new ModeMaster();
				}
			}
		}
		return instance;
	}
	
	//public static final DeviceMode PERFORMANCE_MODE;
	public static final DeviceMode ON_OFF_MODE;
	public static final DeviceMode EXAMPLE;
	
	static {
		instance = new ModeMaster();
		ON_OFF_MODE = new OnOffMode(instance);
		EXAMPLE = new ExampleMode(instance);
		
		instance.changeMode(EXAMPLE);
	}

	@Override
	public void onLButtonPress(MouseEvent e, int buttonNum) {
		deviceMode.onLButtonPress(e, buttonNum);
	}

	@Override
	public void onRButtonPress(MouseEvent e, int buttonNum) {
		deviceMode.onRButtonPress(e, buttonNum);
	}

	@Override
	public void onOKButtonPress(MouseEvent e) {
		deviceMode.onOKButtonPress(e);
	}

	@Override
	public void onOnOffButtonPress(MouseEvent e) {
		deviceMode.onOnOffButtonPress(e);
	}

	@Override
	public void onMatrixButtonPress(MouseEvent e, int buttonColumn, int buttonRow) {
		deviceMode.onMatrixButtonPress(e, buttonColumn, buttonRow);
	}
	
}
