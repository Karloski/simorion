package org.simorion.ui.view;

import java.awt.Color;

import javax.swing.JButton;

import org.simorion.common.util.Util.Pair;

/**
 * View interface which defines methods for a view for the Simori-On.
 * <p>TODO: Exceptions. I wanted to do some concrete implementations first to get
 * a better feel for what exceptions would make sense.</p>
 * @author Karl
 *
 */
public interface View {

	/**
	 * Returns the size of the inner grid for this view as a {@code Pair<Integer, Integer>} where the left represents
	 * the number of rows and the right represents the number of columns.
	 * @return A {@code Pair<Integer, Integer>} representing the width and height of this views inner grid.
	 */
	Pair<Integer, Integer> gridSize();
	
	/**
	 * Retrieves and returns whether or not the button at coordinate {@code x}, {@code y} is lit.
	 * @param x The x coordinate of the button.
	 * @param y The y coordinate of the button.
	 * @return True if lit, false otherwise.
	 */
	boolean isLit(int x, int y);
	
	/**
	 * Determines if the row of buttons {@code row} is on are lit. 
	 * @param y The row to check.
	 * @return True if all buttons are lit, false otherwise.
	 */
	boolean isRowLit(int x);
	
	/**
	 * Retrieves and returns the first lit row for this view.
	 * @return The first row on the grid whose buttons are all lit.
	 */
	int getLitRow();
	
	/**
	 * Retrieves and returns a two-dimensional boolean array of the state of all matrix buttons for this view.
	 * @return A two-dimensional boolean array of the state of each matrix button for this view.
	 */
	boolean[][] getLit();
	
	/**
	 * Determines if the column of buttons {@code column} is on are lit. 
	 * @param x The column to check.
	 * @return True if all buttons are lit, false otherwise.
	 */
	boolean isColumnLit(int y);

	/**
	 * Retrieves and returns the first lit column for this view.
	 * @return The first column on the grid whose buttons are all lit.
	 */
	int getLitColumn();
	
	/**
	 * Retrieves and returns the current textual output of the LCD for this view.
	 * @return The textual output of the LCD.
	 */
	String getLCDMessage();
	
	/**
	 * Retrieves and returns the MIDI ID of the currently applied instrument for this view.
	 * @return The MIDI ID of the currently applied instrument for this view.
	 */
	int getVoiceId();
	
	/**
	 * Retrieves and returns the name of the currently applied instrument for this view.
	 * @return The name of the currently applied instrument for this view
	 */
	String getVoiceName();
	
	/**
	 * Retrieves and returns the ID of the currently applied layer for this view.
	 * @return The ID of the currently applied layer for this view.
	 */
	int getCurrentLayerId();
	
	/**
	 * Retrieves and returns the current loop point for this view.
	 * @return The current loop point for this view.
	 */
	int getLoopPoint();
	
	/**
	 * Retrieves and returns the current velocity for notes played on this view.
	 * @return The current velocity for notes played on this view.
	 */
	int getVelocity();
	
	/**
	 * Retrieves and returns the current note for row {@code y} for this view.
	 * @param y The row to check.
	 * @return The current note for row {@code y}
	 */
	byte getNote(int y);
}
