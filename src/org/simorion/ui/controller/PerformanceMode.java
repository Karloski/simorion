package org.simorion.ui.controller;

import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import org.simorion.ui.model.Model;
import org.simorion.ui.view.View;
 
public class PerformanceMode extends DeviceMode {
 
    public PerformanceMode(ModeMaster m) {
		super(m);
	}
    
	private PerformanceView instance = new PerformanceView();
     
    //Remember that inner classes can access outer classes with PreformanceMode.this
    private class PerformanceView implements View {

		@Override
		public JButton getButton(int x, int y) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public JButton[] getButtons() {
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
		public void lightButton(int x, int y, Color color) {
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
		public void clearButtons() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean isLit(int x, int y) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isRowLit(int row) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isColumnLit(int column) {
			// TODO Auto-generated method stub
			return false;
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
    }
     
    public View getView() {
        return instance;
    }
     
    @Override
    public void onOnOffButtonPress(MouseEvent e) {
        instance.clearButtons();
        changeMode(ModeMaster.ON_OFF_MODE);
    }
     
    public void onLButtonPressed(MouseEvent e, int ButtonNum){
          
        switch (ButtonNum){
            case 1 : 
                changeMode(ModeMaster.CHANGE_VOICE_MODE);
                break;
            case 2 : 
                changeMode(ModeMaster.CHANGE_VELOCITY_MODE);
                break;
            case 3 : 
                changeMode(ModeMaster.CHANGE_LOOP_SPEED_MODE);
                break;
            case 4 : 
                changeMode(ModeMaster.CHANGE_LOOP_POINT_MODE);
                break;
        }
    }
     
    public void onRButtonPressed(MouseEvent e, int ButtonNum){
          
        switch (ButtonNum){
            case 1 : 
                changeMode(ModeMaster.CHANGE_LAYER_MODE);
                break;
            case 2 : 
                changeMode(ModeMaster.SAVE_CONFIG_MODE);
                break;
            case 3 : 
                changeMode(ModeMaster.LOAD_CONFIG_MODE);
                break;
            case 4 : 
                changeMode(ModeMaster.MASTER_SLAVE_MODE);
                break;
        }
    }
     
    public void onMatrixPressed(MouseEvent e, int x, int y){
        //send button pressed to model
        if (model.getCurrentLayer().getRow(y).isLit(x)){
            instance.lightButton(x, y, Color.ORANGE);
        }else{
            instance.lightButton(x, y, Color.WHITE);
        }
    }

	@Override
	public void onOKButtonPress(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMatrixButtonPress(MouseEvent e, int buttonColumn, int buttonRow) {
		// TODO Auto-generated method stub
		
	}
}