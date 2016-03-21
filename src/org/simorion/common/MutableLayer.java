package org.simorion.common;

/**
 * Modelling a *proper* const-ness system, this is the writable side of the coin
 * @author Edmund Smith
 */
public interface MutableLayer extends ImmutableLayer {
	
	/**
	 * Set the loop point for this particular layer.
	 * @param loopPoint How many columns to play each loop
	 */
	public void setLoopPoint(byte loopPoint);
	
	/**
	 * Set how 'hard' each note is 'pressed' in the layer.
	 * @param velocity The 'hardness' of the press
	 */
	public void setVelocity(byte velocity);
	
	/**
	 * Set the voice for this layer
	 * @param v The Voice
	 */
	public void setVoice(Voice v);

	/**
	 * Get a specific row by row id
	 */
	public MutableRow getRow(int row);
	
	/**
	 * Get all of the rows at once, e.g. for looping
	 */
	public Iterable<? extends MutableRow> getRows();
	
}
