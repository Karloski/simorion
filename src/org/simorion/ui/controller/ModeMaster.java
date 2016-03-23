package org.simorion.ui.controller;

import java.awt.event.MouseEvent;

import org.simorion.engine.EngineImpl;
import org.simorion.ui.model.MutableModel;
import org.simorion.ui.view.GUI;
import org.simorion.ui.view.View;

/**
 * Class containing static instances of every mode, since every mode should be
 * able to be left and re-entered repeatedly. This class's instance acts to
 * dispatch the Controller interface to the currently selected Mode, which is
 * itself a Controller.
 * 
 * @see Controller 
 * 
 * @author Edmund Smith
 * @author Karl Brown
 * @author George Young
 *
 */
public class ModeMaster implements Controller {
	
	private static ModeMaster instance;
	
	private MutableModel model;
	
	private DeviceMode deviceMode;
	
	/**
	 * Get the current mode.
	 * @return The current mode.
	 */
	public DeviceMode getMode() {
		return deviceMode;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public View getView() {
		return deviceMode.getView();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void register(MutableModel model) {
		this.model = model;
	}

	/**
	 * Sets the current mode to the passed mode, registers the model with it and redraws the GUI.
	 * @param newMode The mode to change to.
	 */
	public void changeMode(DeviceMode newMode) {
		if (newMode == ON_OFF_MODE) {
	    	model.stopPlaying();
	    	model.reset();
		}		
		deviceMode = newMode;
		deviceMode.register(model);
		deviceMode.onChangedTo();
		GUI.getInstance().redraw();
		//System.out.println("Mode changed to "+newMode.getClass().getName());
	}
	
	/**
	 * Returns the current instance of ModeMaster.
	 * @return The current instance.
	 */
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
	
	/**
	 * Class initialiser. Caches each mode and sets the On/Off mode as the current mode.
	 */
	public static void init() {
		instance = new ModeMaster();
		instance.register(new EngineImpl());
		ON_OFF_MODE = new OnOffMode(instance);
		EXAMPLE = new ExampleMode(instance);
		PERFORMANCE_MODE = new PerformanceMode(instance);
		CHANGE_LAYER_MODE = new ChangeLayerMode(instance);
		SAVE_CONFIG_MODE = new SaveConfigMode(instance);
		LOAD_CONFIG_MODE = new LoadConfigMode(instance);
		MASTER_SLAVE_MODE = new MasterSlaveMode(instance);
		CHANGE_LOOP_POINT_MODE = new ChangeLPointMode(instance);
		CHANGE_LOOP_SPEED_MODE = new ChangeLSpeedMode(instance);
		CHANGE_VELOCITY_MODE = new ChangeNVMode(instance);
		CHANGE_VOICE_MODE = new ChangeVoiceMode(instance);
		SHOP_BOY_MODE = new ShopMode(instance);
		
		instance.changeMode(ON_OFF_MODE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onLButtonPress(MouseEvent e, int buttonNum) {
		deviceMode.onLButtonPress(e, buttonNum);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onRButtonPress(MouseEvent e, int buttonNum) {
		deviceMode.onRButtonPress(e, buttonNum);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onOKButtonPress(MouseEvent e) {
		deviceMode.onOKButtonPress(e);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onOnOffButtonPress(MouseEvent e) {
		deviceMode.onOnOffButtonPress(e);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onMatrixButtonPress(MouseEvent e, int buttonColumn, int buttonRow) {
		deviceMode.onMatrixButtonPress(e, buttonColumn, buttonRow);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onMatrixButtonPress(MouseEvent e, int x, int y, boolean lit) {
		deviceMode.onMatrixButtonPress(e, x, y, lit);
	}
	
}
