package org.simorion.ui.controller;

import java.awt.Color;

import javax.swing.JButton;

import org.simorion.ui.model.Model;
import org.simorion.ui.view.View;

//Example
public class ExampleMode extends DeviceMode {

	private ExampleView instance = new ExampleView();
	private Model model;
	
	//Remember that inner classes can access outer classes with ExampleMode.this
	private class ExampleView implements View {

		@Override
		public JButton getMidiButton(int x, int y) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public JButton[] getColumn(int y) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public JButton[] getRow(int x) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void lightMidiButton(int x, int y, Color color) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void lightRow(int row, Color color) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void lightColumn(int column, Color color) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String getLCDOutput() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setLCDOutput(String text) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean isActive() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public JButton buttonClicked() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	public View getView() {
		return instance;
	}
	
	public void register(Model m) {
		model = m;
	}
}
