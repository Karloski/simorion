package org.simorion.common;

import java.util.Collection;

/**
 * Immutable view of the Song interface, with no escaping mutability
 * @author edmund
 *
 */
public interface ReadonlySong {

	/**
	 * Gets the different layers to the song
	 * @return A collection of read-only layers
	 */
	public Collection<? extends ReadonlyLayer> getLayers();

	/**
	 * @return The tempo of the song
	 */
	public float getTempo();
	
	/**
	 * @return The BPM of the song
	 */
	public byte getBPM();

}
