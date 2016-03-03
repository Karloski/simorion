package org.simorion.common.stream;

import java.io.IOException;

import org.simorion.common.SongBuilder;

/**
 * Standard interface for all SongReaders to adhere to: they can all...
 * read songs!
 * 
 * @author Edmund Smith
 *
 */
public interface SongReader {

	/**
	 * Read data into a borrowed SongBuilder instance, per the given format
	 * @param format The serialisation format used
	 * @param song The borrowed songbuilder used to load the song
	 * @throws StreamFailureException if any error occurs during streaming
	 * @throws UnsupportedSongFormatException when leading byte is different to formatID
	 */
	public void readTo(final SongFormat format, final SongBuilder song)
			throws StreamFailureException, UnsupportedSongFormatException, InsufficientSongDataException;
	
	/**
	 * Gets the song format indicated by the initial byte in the stream. Blocks
	 * until a byte is provided.
	 * @return The SongFormat corresponding to the first byte in the stream 
	 * @throws StreamFailureException In case of any IO failure
	 * @throws UnsupportedSongFormatException If the format byte is not recognised
	 */
	public SongFormat predictFormat() throws StreamFailureException, UnsupportedSongFormatException;
}
