package org.simorion.common.stream;

import java.io.IOException;

import org.simorion.common.ReadonlySong;

/**
 * Common interface for writing a Song to somewhere external, such as a file
 * or a network location
 * 
 * @author Edmund Smith
 */
public interface SongWriter {

	/**
	 * Writes the Song to the writer's destination, as per the given format 
	 * @param format The serialisation format to use
	 * @param s The song to serialse
	 * @throws IOException In case of IO failure, e.g. FileNotFound
	 */
	public void write(final SongFormat format, final ReadonlySong s) throws IOException;
	
}
