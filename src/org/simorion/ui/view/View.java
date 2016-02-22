package org.simorion.ui.view;

import javax.swing.AbstractButton;
import javax.swing.JComponent;

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
	Iterable<AbstractButton> getMidiButtons();
	
	/**
	 * This view's implementation of the LCD Screen.
	 * @return This view's implementation of the LCD Screen.
	 */
	JComponent getLCDScreen();
}
