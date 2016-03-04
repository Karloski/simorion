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
	 * @throws IOException In case of any IO failure, e.g. FileNotFound
	 * @throws UnsupportedSongFormatException when leading byte is different to formatID
	 */
	public void readTo(final SongFormat format, final SongBuilder song)
			throws StreamFailureException, UnsupportedSongFormatException, InsufficientSongDataException;
	//TODO: Specific exception classes
}
