package org.simorion.ui.view;

import java.awt.Color;

import javax.swing.JButton;

public interface View {

	/**
	 * Retrieves and returns the button at coordinate x, y for this view.
	 * @param x The x coordinate of the button.
	 * @param y The y cooridnate of the button.
	 * @return The matrix JButton at cooridnate x, y.
	 */
	JButton getMidiButton(int x, int y);
	
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
	 * Sets the colour of an individual midi button to {@code color} at position x, y for this view.
	 * @param x The x coordinate of the matrix button.
	 * @param y The y coordinate of the matrix button.
	 * @param color The colour to use.
	 */
	void lightMidiButton(int x, int y, Color color);
	
	/**
	 * Sets the colour of a row of buttons in the button matrix to {@code color} for this view.
	 * @param row - The row to set.
	 * @param color - The colour to use.
	 */
	void lightRow(int row, Color color);
	
	/**
	 * Sets the colour of a column of buttons in the button matrix to {@code color} for this view.
	 * @param column The column to set.
	 * @param color The colour to use.
	 */
	void lightColumn(int column, Color color);
	
	/**
	 * Retrieves and returns the current textual output of the LCD for this view.
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
	
	/**
	 * Retrieves and returns the button that was clicked for this view.
	 * @return The button that was clicked on.
	 */
	JButton buttonClicked();
}
