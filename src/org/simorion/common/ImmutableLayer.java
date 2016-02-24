package org.simorion.common;

/**
 * Immutable view of the Layer interface, exposing no mutable references
 * 
 * @author Edmund Smith
 */
public interface ImmutableLayer {


	/**
	 * Get the index of the layer within the model
	 * @return int between 0 and 16 exclusive ([0,1,..,14,15])
	 */
	public int getLayerNumber();
	
	/**
	 * Get the current voice used by the layer
	 * @return layer's voice
	 */
	public Voice getVoice();
	
	/**
	 * Get the MIDI velocity of the layer
	 * @return int between 0 and 127 inclusive
	 */
	public byte getVelocity();
	
	/**
	 * Get the looping point of the layer. 0 and numbers above the number of
	 * columns indicate every column is played.
	 * e.g. 0: loops entire layer
	 * 		1: loops only first column (column[0])
	 * 		2: loops first and second column (column[0], column[1])
	 * 		...
	 * 		15: loops all but last column
	 * 		16: loops entire layer
	 * 		17: loops entire layer
	 * 		...
	 * @return
	 */
	public int getLoopPoint();
	
	/**
	 * Get a container representing every cell on a particular row
	 * @param i the row index, indexed from bottom left up
	 * @return the row at index i
	 */
	public ImmutableRow getRow(int i);

	/**
	 * Get a collection of all rows for iteration
	 * @return a collection of all rows
	 */
	public Iterable<? extends ImmutableRow> getRows();
	
	/**
	 * Get the currently displaying LCD message.
	 * @return The currently displaying LCD message
	 */
	public String getLCDMessage();

}
