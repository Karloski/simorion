package org.simorion.ui.controller;
import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import org.simorion.ui.model.Model;
import org.simorion.ui.view.View;
 
/**
 * ChangeLPointMode ready for full impl
 * @author George Young
 */
public class ChangeLSpeedMode extends DeviceMode {
 
    public ChangeLSpeedMode(ModeMaster m) {
		super(m);
		// TODO Auto-generated constructor stub
	}

	private ChangeLSpeedView instance = new ChangeLSpeedView();
     
    //Remember that inner classes can access outer classes with ChangeLSpeedMode.this
    private class ChangeLSpeedView implements View {

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
	public void register(Model model) {
		// TODO Auto-generated method stub
		
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