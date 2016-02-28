package org.simorion.ui.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JComponent;

import org.simorion.common.ImmutableRow;
import org.simorion.common.util.Util;
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
	 * The title of this view.
	 * @return The title of this view.
	 */
	String getTitle();
	
	/**
	 * The total size of this view, including all elements, in pixels.
	 * @return The total size of this view in pixels.
	 */
	Pair<Integer, Integer> getSize();
	
	/**
	 * Returns the size of the inner grid for this view as a {@code Pair<Integer, Integer>} where the left represents
	 * the number of rows and the right represents the number of columns.
	 * @return A {@code Pair<Integer, Integer>} representing the width and height of this views inner grid.
	 */
	Pair<Integer, Integer> getMatrixSize();
	
	/**
	 * This view's implementation of the outer panel as a JComponent.
	 * @return This view's implementation of the outer panel.
	 */
	JComponent getOuterPanel();
	
	/**
	 * This view's implementation of the button panel as a JComponent.
	 * @return This view's implementation of the button panel.
	 */
	JComponent getButtonPanel();
	
	/**
	 * This view's ON button implementation. Should include attributes such as colour, position and so forth.
	 * @return This view's ON button implementation.
	 */
	AbstractButton getOnButton();
	
	/**
	 * This view's OK button implementation. Should include attributes such as colour, position and so forth.
	 * @return This view's OK button implementation.
	 */
	AbstractButton getOKButton();
	
	/**
	 * An iterable list of all the mode buttons for this view.
	 * @return An iterable list of all the mode buttons for this view.
	 */
	Iterable<AbstractButton> getModeButtons();
	
	/**
	 * An iterable list of all the MIDI buttons for this view.
	 * @return An iterable list of all the MIDI buttons for this view.
	 */
	AbstractButton[] getMidiButtons();
	
	/**
	 * This view's implementation of the LCD Screen.
	 * @return This view's implementation of the LCD Screen.
	 */
	JComponent getLCDScreen();
	
	/**
	 * Retrieves and returns whether or not the button at coordinate {@code x}, {@code y} is lit.
	 * @param x The x coordinate of the button.
	 * @param y The y coordinate of the button.
	 * @return True if lit, false otherwise.
	 */
	public boolean isLit(int x, int y);
	
	/**
	 * Retrieves and returns a collection of iterable booleans representing all the currently lit matrix buttons.
	 * @return A collection of iterable booleans representing the currently lit buttons.
	 */
	public Collection<Iterable<Boolean>> getLitButtons();
	
	/**
	 * Performs the lighting operation for a button pressed at {@code x}, {@code y} for this view.
	 * @param x The x coordinate of the matrix button.
	 * @param y The y coordinate of the matrix button.
	 */
	public void setLit(int x, int y);

	/**
	 * Determines if the row of buttons {@code row} is on are lit. 
	 * @param y The row to check.
	 * @return True if all buttons are lit, false otherwise.
	 */
	public boolean isRowLit(int x);

	/**
	 * Determines if the column of buttons {@code column} is on are lit. 
	 * @param x The column to check.
	 * @return True if all buttons are lit, false otherwise.
	 */
	public boolean isColumnLit(int y);

	/**
	 * Retrieves and returns the current textual output of the LCD.
	 * @return The textual output of the LCD.
	 */
	public String getLCDMessage();
	
	/**
	 * Sets the LCD output for this view.
	 */
	public void setLCDMessage();

	/**
	 * Retrieves and returns the MIDI ID of the currently applied instrument.
	 * @return The MIDI ID of the currently applied instrument for this view.
	 */
	public int getVoiceId();

	/**
	 * Retrieves and returns the name of the currently applied instrument.
	 * @return The name of the currently applied instrument for this view
	 */
	public String getVoiceName();

	/**
	 * Retrieves and returns the ID of the currently applied layer.
	 * @return The ID of the currently applied layer for this view.
	 */
	public int getCurrentLayerId();

	/**
	 * Retrieves and returns the current loop point.
	 * @return The current loop point for this view.
	 */
	public int getLoopPoint();

	/**
	 * Retrieves and returns the current velocity for notes played.
	 * @return The current velocity for notes played on this view.
	 */
	public int getVelocity();
	
	/**
	 * Retrieves and returns the current note for row {@code y}.
	 * @param y The row to check.
	 * @return The current note for row {@code y}
	 */
	public byte getNote(int y);
}
