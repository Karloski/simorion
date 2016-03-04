package org.simorion.ui.controller;

import java.awt.event.MouseEvent;

import org.simorion.ui.model.ImmutableModel;
import org.simorion.ui.view.DefaultView;
import org.simorion.ui.view.View;

//Example
public class ExampleMode extends DeviceMode {

	public ExampleMode(ModeMaster m) {
		super(m);
	}

	private ExampleView instance = new ExampleView();
	private ImmutableModel model;
	
	/**
	 * An example implementation of a controller view.
	 * @author Karl
	 *
	 */
	private class ExampleView extends DefaultView {
		
	}
	
	public View getView() {
		return instance;
	}
	
	public void register(ImmutableModel m) {
		model = m;
	}


	@Override
	public void onOKButtonPress(MouseEvent e) {
		System.out.println("OK button pressed");
	}
	
	@Override
	public void onMatrixButtonPress(MouseEvent e, int buttonColumn, int buttonRow) {
		System.out.printf("Matrix button (%3d, %3d) pressed%n", buttonColumn, buttonRow);
	}
	
}