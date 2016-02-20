package org.simorion.common;

import java.util.Collection;

/**
 * Basic interface for the Song abstraction, which is a collection of layers
 * of rows of cells, each layer with a set velocity, tempo, voice and loop point
 * 
 * @author Edmund Smith
 */
public interface Song extends ImmutableSong {
//TODO: Add more methods as Song is fleshed out
	
	/**
	 * Loads a song into this object from a prepared builder object
	 * @param sb The prepared SongBuilder instance
	 */
	void loadFrom(final SongBuilder sb);
	
	/**
	 * Sets the tempo of the song in beats per minute
	 * @param tempo The tempo to set to
	 */
	void setBPM(final byte bpm);
	
	/**
	 * Sets the tempo of the song in beats per second
	 */
	void setTempo(final float bps);
	
	public Collection<? extends MutableLayer> getLayers();
}
