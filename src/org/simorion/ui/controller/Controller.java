package org.simorion.ui.controller;

import java.awt.event.MouseEvent;

import org.simorion.ui.model.Model;
import org.simorion.ui.view.View;

/**
 * Common interface for controllers, which will have an associated View
 * instance. More methods can be added when the GUI is added, so it is known
 * what else controllers need to do.
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
	public void register(Model model);
	
	
	public void onLButtonPress(MouseEvent e, int buttonNum);
	
	public void onRButtonPress(MouseEvent e, int buttonNum);
	
	public void onOKButtonPress(MouseEvent e);
	
	public void onOnOffButtonPress(MouseEvent e);
	
	public void onMatrixButtonPress(MouseEvent e, int buttonColumn, int buttonRow);
	
	
}
