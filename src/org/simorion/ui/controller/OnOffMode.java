package org.simorion.ui.controller;
import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import org.simorion.common.util.Util;
import org.simorion.common.util.Util.Pair;
import org.simorion.ui.view.View;
 
public class OnOffMode extends DeviceMode {
 
    public OnOffMode(ModeMaster m) {
		super(m);
		// TODO Auto-generated constructor stub
	}

	private OnOffView instance = new OnOffView();
     
    /**
     * Implementation of the View interface for the OnOffView
     * @author Karl Brown
     *
     */
    private class OnOffView implements View {

		/** {@inheritDoc} */
		@Override
    	public Pair<Integer, Integer> gridSize() {    		
			// Return a new pair representing the size of this view's button matrix.
    		return new Pair<Integer, Integer>(Util.count(model.getCurrentLayer().getRows()), model.getCurrentLayer().getRow(0).cellCount());
    	}
    	
		/** {@inheritDoc} */
		@Override
		public boolean isLit(int x, int y) {
			return model.getCurrentLayer().getRow(y).isLit(x);
		}
		
		/** {@inheritDoc} */
		@Override
		public boolean[][] getLit() {
			// Create the two-dimensional boolean array to return, lengths each equal to the size of this view's grid.
			boolean[][] grid = new boolean[gridSize().left][gridSize().right];
			
			// Iterate over each row.
			for (int r = 0; r < grid.length; r++) {
				// Iterate over each column.
				for (int c = 0; c < grid[r].length; c++) {
					// If the button at coor r, c is lit, set it to true in the grid variable.
					if (isLit(r, c)) grid[r][c] = true;
				}
			}
			
			// Return the grid.
			return grid;
		}

		/** {@inheritDoc} */
		@Override
		public boolean isRowLit(int x) {
			// Each row should contain the same number of buttons.
			int columns = gridSize().right;
			
			// Keeping the row constant, check the state of each button.
			for (int i = 0; i < columns; i++) {
				if (!isLit(x, i)) return false;
			}
			
			// Not all the buttons on this row are lit.
			return true;
		}
		
		/** {@inheritDoc} */
		@Override
		public int getLitRow() {
			
			// Get the number of rows from the gridSize method.
			int rows = gridSize().left;
			
			// Iterate over each row.
			for (int r = 0; r < rows; r++) {
				// If this row is lit, return it.
				if (isRowLit(r)) return r;
			}
			
			// No rows are lit.
			return -1;			
		}

		/** {@inheritDoc} */
		@Override
		public boolean isColumnLit(int y) {			
			// Each row should contain the same number of buttons.
			int rows = gridSize().left;
			
			// Keeping the row constant, check the state of each button.
			for (int i = 0; i < rows; i++) {
				if (!isLit(i, y)) return false;
			}
			
			// Not all the buttons on this row are lit.
			return true;
		}
		
		/** {@inheritDoc} */
		@Override
		public int getLitColumn() {
			
			// Get the number of columns from the gridSize method.
			int columns = gridSize().right;
			
			// Iterate over each column.
			for (int c = 0; c < columns; c++) {
				// If this column is lit, return it.
				if (isColumnLit(c)) return c;
			}
			
			// No column is lit.
			return -1;			
		}

		/** {@inheritDoc} */
		@Override
		public String getLCDMessage() {
			return model.getCurrentLayer().getLCDMessage();
		}
		
		/** {@inheritDoc} */
		@Override
		public int getVoiceId() {
			return model.getCurrentLayer().getVoice().getMidiVoice();
		}
		
		/** {@inheritDoc} */
		@Override
		public String getVoiceName() {
			return model.getCurrentLayer().getVoice().getName();
		}
		
		/** {@inheritDoc} */
		@Override
		public int getCurrentLayerId() {
			return model.getCurrentLayer().getLayerNumber();
		}
		
		/** {@inheritDoc} */
		@Override
		public int getLoopPoint() {
			return model.getCurrentLayer().getLoopPoint();
		}
		
		/** {@inheritDoc} */
		@Override
		public int getVelocity() {
			return model.getCurrentLayer().getVelocity();
		}
		
		/** {@inheritDoc} */
		@Override
		public byte getNote(int y) {
			return model.getCurrentLayer().getRow(y).getNote();
		}      
        
    }
     
    public View getView() {
        return instance;
    }
     
    @Override
    public void onOnOffButtonPress(MouseEvent e){
        changeMode(ModeMaster.PERFORMANCE_MODE);
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