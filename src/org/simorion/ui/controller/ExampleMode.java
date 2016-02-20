package org.simorion.ui.controller;

import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import org.simorion.ui.model.ImmutableModel;
import org.simorion.ui.view.View;

//Example
public class ExampleMode extends DeviceMode {

	public ExampleMode(ModeMaster m) {
		super(m);
	}

	private ExampleView instance = new ExampleView(16, 16);
	private ImmutableModel model;
	
	/**
	 * An example implementation of a controller view.
	 * <p>The final implementation should replace all instances of JButton with its extended version.
	 * It should also include proper error checking, which will likely be done in the form of exceptions.</p>
	 * @author Karl
	 *
	 */
	private class ExampleView implements View {

		private int rows; // The number of rows of matrix buttons. This is essentially the height.
		private int columns; // The number of columns of matrix buttons. This is essentially the width.
		private String lcdOutput = ""; // The current LCD output.
		
		JButton[] buttons; // An array of all matrix buttons for this view.
		
		/**
		 * Constructor for this view.
		 * @param columns The number of columns of matrix buttons.
		 * @param rows The number of rows of matrix button.
		 */
		public ExampleView(int rows, int columns) {	
			buttons = new JButton[rows * columns];	
			this.columns = columns;
			this.rows = rows;
		}
		
		/** {@inheritDoc} */
		@Override
		public JButton getButton(int x, int y) {
			// if (outOfBounds(x) || outOfBounds(y)) throw new OutOfBoundsException();
			return buttons[x * rows + y];
		}

		/** {@inheritDoc} */
		@Override
		public JButton[] getButtons() {
			return buttons;
		}

		/** {@inheritDoc} */
		@Override
		public JButton[] getColumn(int y) {
			// if (outOfBounds(y)) throw new OutOfBoundsException();
			// The length of this array should be equal to the number of rows (height).
			JButton[] ret = new JButton[rows];
			
			// Add the button to ret for each button on this column, keeping y (the column) constant.
			for (int i = 0; i < rows; i++) {
				ret[i] = getButton(y, i);
			}
			
			return ret;
		}

		/** {@inheritDoc} */
		@Override
		public JButton[] getRow(int x) {
			// if (outOfBounds(x)) throw new OutOfBoundsException();
			// The length of this array should be equal to the number of columns (width).
			JButton[] ret = new JButton[columns];
			
			// Add the button to ret for each button on this column, keeping x (the row) constant.
			for (int i = 0; i < columns; i++) {
				ret[i] = getButton(i, x);
			}

			return ret;
		}

		/** {@inheritDoc} */
		@Override
		public void lightButton(int x, int y, Color color) {
			// if (outOfBounds(x) || outOfBounds(y)) throw new OutOfBoundsException();
			getButton(x, y).setBackground(color);
		}
		
		/** {@inheritDoc} */
		@Override
		public void lightRow(int row, Color color) {
			for (int i = 0; i < columns; i++) {
				lightButton(i, row, color);
			}
		}

		/** {@inheritDoc} */
		@Override
		public void lightColumn(int column, Color color) {
			for (int i = 0; i < rows; i++) {
				lightButton(column, i, color);
			}	
		}

		/** {@inheritDoc} */
		@Override
		public boolean isLit(int x, int y) {
			return getButton(x, y).getBackground() == Color.ORANGE ? true : false;
		}

		/** {@inheritDoc} */
		@Override
		public boolean isRowLit(int row) {
			for (int i = 0; i < columns; i++) {
				if (!isLit(i, row)) return false;
			}
			return true;
		}

		/** {@inheritDoc} */
		@Override
		public boolean isColumnLit(int column) {
			for (int i = 0; i < columns; i++) {
				if (!isLit(column, i)) return false;
			}
			return true;
		}

		/** {@inheritDoc} */
		@Override
		public String getLCDOutput() {
			return lcdOutput;
		}

		/** {@inheritDoc} */
		@Override
		public void setLCDOutput(String text) {
			this.lcdOutput = text;
		}

		/** {@inheritDoc} */
		// TODO: This will be determined based on what the application is doing.
		@Override
		public boolean isActive() {
			// TODO Auto-generated method stub
			return false;
		}

		/** {@inheritDoc} */
		@Override
		public void clearButtons() {
			for (int r = 0; r < rows; r++) {
				for (int c = 0; c < columns; c++) {
					lightButton(r, c, null);
				}
			}
		}		
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