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
	
	public static final DeviceMode PERFORMANCE_MODE;
	public static final DeviceMode ON_OFF_MODE;
	public static final DeviceMode EXAMPLE;

	public static final DeviceMode CHANGE_LAYER_MODE = null;
	public static final DeviceMode SAVE_CONFIG_MODE = null;
	public static final DeviceMode LOAD_CONFIG_MODE = null;
	public static final DeviceMode MASTER_SLAVE_MODE = null;
	public static final DeviceMode CHANGE_LOOP_POINT_MODE = null;
	public static final DeviceMode CHANGE_LOOP_SPEED_MODE = null;
	public static final DeviceMode CHANGE_VELOCITY_MODE = null;
	public static final DeviceMode CHANGE_VOICE_MODE = null;
	
	static {
		instance = new ModeMaster();
		ON_OFF_MODE = new OnOffMode(instance);
		EXAMPLE = new ExampleMode(instance);
		PERFORMANCE_MODE = null; //TODO
		
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
