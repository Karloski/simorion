package org.simorion.ui.model;

import org.simorion.common.Layer;
import org.simorion.common.Stream;
import org.simorion.common.StreamFormat;

public interface Model {

	public void sendToStream(Stream s, StreamFormat f);
	
	public void receiveFromStream(Stream s, StreamFormat f);
	
	/**
	 * Get the current tempo in beats per second
	 * @return
	 */
	public float getTempo();
	
	/**
	 * Get a reference to the layer currently being manipulated
	 * @return Layer being manipulated
	 */
	public Layer getCurrentLayer();
	
	/**
	 * Get the index of the layer currently being manipulated
	 * @return the layer's index
	 */
	public int getCurrentLayerId();
	
	/**
	 * Get the layer at index i
	 * @param i The index of the layer
	 * @return layer number i
	 */
	public Layer getLayer(int i);
	
	//720720 is the lowest common multiple of [1..16] inclusive
	/**
	 * Gets the current tick number, which increments on every beat. It wraps
	 * at 720720, going back to 0.
	 * @return The current tick modulo 720720
	 */
	public int getTick();
	
}
