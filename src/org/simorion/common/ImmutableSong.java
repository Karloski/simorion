package org.simorion.common;

import java.util.Collection;

/**
 * Immutable view of the Song interface, with no escaping mutability
 * @author Edmund Smith
 *
 */
public interface ImmutableSong {

	/**
	 * Gets the different layers to the song
	 * @return A collection of read-only layers
	 */
	public Collection<? extends ImmutableLayer> getLayers();

	/**
	 * @return The tempo of the song in beats per second
	 */
	public float getTempo();
	
	/**
	 * @return The BPM of the song
	 */
	public byte getBPM();

}
