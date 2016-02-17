package org.simorion.common;

/**
 * Basic interface for the Song abstraction, which is a collection of layers
 * of rows of cells, each layer with a set velocity, tempo, voice and loop point
 * 
 * @author Edmund Smith
 */
public interface Song extends ReadonlySong {
//TODO: Add more methods as Song is fleshed out
	
	/**
	 * Loads a song into this object from a prepared builder object
	 * @param sb The prepared SongBuilder instance
	 */
	void loadFrom(final SongBuilder sb);
	
}
