package org.simorion.ui.view;

import java.awt.Color;

import javax.swing.JButton;

/**
 * View interface which defines methods for a view for the Simori-On. 
 * @author Karl
 *
 */
public interface View {

	/**
	 * Retrieves and returns the matrix button at coordinate x, y for this view.
	 * @param x The x coordinate of the button.
	 * @param y The y cooridnate of the button.
	 * @return The matrix JButton at cooridnate x, y.
	 */
	JButton getButton(int x, int y);
	
	/**
	 * Retrieves and returns all matrix buttons for this view.
	 * @return The buttons for this view.
	 */
	JButton[] getButtons();
	
	/**
	 * Retrieves and returns all buttons for the given column for this view.
	 * @param y The column to return.
	 * @return All matrix buttons for column {@code y}.
	 */
	JButton[] getColumn(int y);
	
	/**
	 * Retrieves and returns all buttons for the given row for this view.
	 * @param x The row to return.
	 * @return All matrix buttons for row {@code x}.
	 */
	JButton[] getRow(int x);
	
	/**
	 * Sets the colour of an individual matrix button to {@code color} at position x, y for this view.
	 * @param x The x coordinate of the matrix button.
	 * @param y The y coordinate of the matrix button.
	 * @param color The colour to use.
	 */
	void lightButton(int x, int y, Color color);
	
	/**
	 * Convenience method that lights the entire row that {@code row} is on.
	 * @param row The row to light.
	 * @param color The colour to use.
	 */
	void lightRow(int row, Color color);
	
	/**
	 * Convenience method that lights the entire column that {@code column} is on.
	 * @param column The column to light.
	 * @param color The color to use.
	 */
	void lightColumn(int column, Color color);
	
	/**
	 * Retrieves and returns whether or not the button at coordinate {@code x}, {@code y} is lit.
	 * <p>NOTE: I feel like this might be redundant and that the information could be retrieved through
	 * whatever implemention of the button we use (i.e., put a flag on the button, which can be
	 * retreved when the getButton method is called).</p>
	 * @param x The x coordinate of the button.
	 * @param y The y coordinate of the button.
	 * @return True if lit, false otherwise.
	 */
	boolean isLit(int x, int y);
	
	/**
	 * Determines if the row of buttons {@code row} is on are lit. 
	 * @param row The row to check.
	 * @return True if all buttons are lit, false otherwise.
	 */
	boolean isRowLit(int row);
	
	/**
	 * Determines if the column of buttons {@code column} is on are lit. 
	 * @param column The column to check.
	 * @return True if all buttons are lit, false otherwise.
	 */
	boolean isColumnLit(int column);
	
	/**
	 * Retrieves and returns the current textual output of the LCD for this view.
	 * @return The textual output of the LCD.
	 */
	String getLCDOutput();
	
	/**
	 * Sets the LCD output for this view.
	 * @param text The text to set
	 */
	void setLCDOutput(String text);
	
	/***
	 * Determines whether this view is the currently active view.
	 * @return True if this view is the currently active view, false otherwise.
	 */
	boolean isActive();
}
