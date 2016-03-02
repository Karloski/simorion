package org.simorion.common.stream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.simorion.common.SongBuilder;

/**
 * SongReader for reading a Song from a local file into a SongBuilder, ready
 * to populate a Song instance
 * 
 * @author Edmund Smith
 */
public class FileSongReader implements SongReader {

	private File file;
	
	/**
	 * @param f The local file to be read from
	 */
	public FileSongReader(final File f) {
		file = f;
	}
	
	/** {@inheritDoc} */
	@Override
	public void readTo(SongFormat format, SongBuilder song) throws UnsupportedSongFormatException, InsufficientSongDataException, StreamFailureException {
		byte[] data = new byte[(int)file.length()];
		try {
			FileInputStream fis = new FileInputStream(file);
			if(fis.read(data) < file.length()) {
				fis.close();
				throw new StreamFailureException("Failed to read the whole file"); //TODO: Can this happen?
			} else {
				fis.close();
				format.deserialise(song, data);
			}
		} catch(FileNotFoundException fnf) {
			throw new StreamFailureException(fnf.getMessage());
		} catch (IOException io) {
			throw new StreamFailureException(io.getMessage());
		}
	}

}
