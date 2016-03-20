package org.simorion.ui.controller;

import java.awt.event.MouseEvent;

import org.simorion.engine.EngineImpl;
import org.simorion.ui.model.MutableModel;
import org.simorion.ui.view.GUI;
import org.simorion.ui.view.View;

public class ModeMaster implements Controller {
	
	private static ModeMaster instance;
	
	private MutableModel model;
	
	private DeviceMode deviceMode;
	
	public DeviceMode getMode() {
		return deviceMode;
	}
	
	@Override
	public View getView() {
		return deviceMode.getView();
	}

	@Override
	public void register(MutableModel model) {
		this.model = model;
	}

	public void changeMode(DeviceMode newMode) {
		if (newMode == PERFORMANCE_MODE) {
			deviceMode = TRANSITION_MODE;
			deviceMode.register(model);
			deviceMode.onChangedTo();
		}
		deviceMode = newMode;
		deviceMode.register(model);
		deviceMode.onChangedTo();
		GUI.getInstance().redraw();
		System.out.println("Mode changed to "+newMode.getClass().getName());
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
	
	public static DeviceMode PERFORMANCE_MODE;
	public static DeviceMode ON_OFF_MODE;
	public static DeviceMode EXAMPLE;

	public static DeviceMode CHANGE_LAYER_MODE;
	public static DeviceMode SAVE_CONFIG_MODE;
	public static DeviceMode LOAD_CONFIG_MODE;
	public static DeviceMode MASTER_SLAVE_MODE;
	public static DeviceMode CHANGE_LOOP_POINT_MODE;
	public static DeviceMode CHANGE_LOOP_SPEED_MODE;
	public static DeviceMode CHANGE_VELOCITY_MODE;
	public static DeviceMode CHANGE_VOICE_MODE;
	public static DeviceMode SHOP_BOY_MODE;
	public static DeviceMode TRANSITION_MODE;
	
	public static void init() {
		instance = new ModeMaster();
		instance.register(new EngineImpl());
		ON_OFF_MODE = new OnOffMode(instance);
		EXAMPLE = new ExampleMode(instance);
		PERFORMANCE_MODE = new PerformanceMode(instance); //TODO
		CHANGE_LAYER_MODE = new ChangeLayerMode(instance);
		SAVE_CONFIG_MODE = new SaveConfigMode(instance);
		LOAD_CONFIG_MODE = new LoadConfigMode(instance);
		MASTER_SLAVE_MODE = new MasterSlaveMode(instance);
		CHANGE_LOOP_POINT_MODE = new ChangeLPointMode(instance);
		CHANGE_LOOP_SPEED_MODE = new ChangeLSpeedMode(instance);
		CHANGE_VELOCITY_MODE = new ChangeNVMode(instance);// TODO confirm
		CHANGE_VOICE_MODE = new ChangeVoiceMode(instance);
		SHOP_BOY_MODE = new ShopMode(instance);
		TRANSITION_MODE = new TransitionMode(instance);
		
		instance.changeMode(ON_OFF_MODE);
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

	@Override
	public void onMatrixButtonPress(MouseEvent e, int x, int y, boolean lit) {
		deviceMode.onMatrixButtonPress(e, x, y, lit);
	}
	
}
