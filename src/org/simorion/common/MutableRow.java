package org.simorion.common;

/**
 * Modelling a *proper* const system, this provides a mutable view to a Row
 * 
 * @author Edmund Smith
 */
public interface MutableRow extends ImmutableRow {

	/**
	 * Sets the appropriate cell to be lit/considered active
	 * @param cell the cell to activate
	 */
	public void setLit(int cell);
	
	/**
	 * Sets the appropriate cell to be unlit/considered inactive
	 * @param cell the cell to activate
	 */
	public void setUnlit(int cell);
	
	/**
	 * Toggles the state of the appropriate cell
	 * @param cell the cell to activate
	 */
	public void toggleLit(int cell);
	
	/**
	 * Set the note the row should play at
	 * @param note
	 */
	public void setNote(byte note);
	
	/**
	 * Applies an and mask with the parameter and, then an or mask with
	 * the parameter or
	 * @param mask
	 */
	public void applyMask(int and, int or);
	
	/**
	 * Applies an xor with the given mask to the lights
	 * @param xor
	 */
	public void applyXor(int xor);
}
