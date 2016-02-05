package org.simorion.ui.controller;

import org.simorion.ui.view.View;

//Example
public class ExampleMode extends DeviceMode {

	private ExampleView instance = new ExampleView();
	
	//Remember that inner classes can access outer classes with ExampleMode.this
	private class ExampleView implements View {
		
	}
	
	public View getView() {
		return instance;
	}
	
}
