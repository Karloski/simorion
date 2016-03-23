package org.simorion.ui.controller;

import java.awt.event.MouseEvent;

import org.simorion.ui.model.MutableModel;
import org.simorion.ui.view.View;

/**
 * Common interface for controllers, which will have an associated View
 * instance. More methods can be added when the GUI is added, so it is known
 * what else controllers need to do.
 * @author Edmund Smith
 * 
*/		
public interface Controller {

	/**
	 * Get the View associated with the particular device mode
	 * @return The associated View
	 */
	public View getView();
	
	/**
	 * Register the model to manipulate
	 * @param model Model being manipulated
	 */
	public void register(MutableModel model);	
	
	/**
	 * Functionality for when any of the left side buttons are pressed.
	 * @param e The mouse event.
	 * @param buttonNum The particular button that was pressed.
	 */
	public void onLButtonPress(MouseEvent e, int buttonNum);
	
	/**
	 * Functionality for when any of the right side buttons are pressed.
	 * @param e The mouse event.
	 * @param buttonNum The particular button that was pressed.
	 */
	public void onRButtonPress(MouseEvent e, int buttonNum);
	
	/**
	 * Functionality when the OK button is pressed.
	 * @param e The mouse event.
	 */
	public void onOKButtonPress(MouseEvent e);
	
	/**
	 * Functionality when the On/Off button is pressed.
	 * @param e The mouse event.
	 */
	public void onOnOffButtonPress(MouseEvent e);
	
	/**
	 * Functionality when the matrix button is pressed.
	 * @param e The mouse event.
	 * @param x The x coordinate on the matrix.
	 * @param y The y coordinate on the matrix.
	 */
	public void onMatrixButtonPress(MouseEvent e, int x, int y);

	/**
	 * Functionality when the mouse is dragged across the matrix buttons.
	 * @param e The mouse event.
	 * @param x The x coordinate on the matrix.
	 * @param y The y coordinate on the matrix.
	 * @param lit Whether the original matrix button was lit.
	 */
	public void onMatrixButtonPress(MouseEvent e, int x, int y, boolean lit);
	
	
}
