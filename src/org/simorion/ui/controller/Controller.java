package org.simorion.ui.controller;

import org.simorion.ui.model.Model;
import org.simorion.ui.view.View;

public interface Controller {

	/**
	 * Get the View associated with the particular device mode
	 * @return
	 */
	public View getView();
	
	/**
	 * Register the model to manipulate
	 * @param model Model being manipulated
	 */
	public void register(Model model); 
	
}
