package org.simorion.common.stream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.simorion.common.ImmutableSong;

/**
 * SongWriter for writing a song to a local file
 * 
 * @author Edmund Smith
 */
public class FileSongWriter implements SongWriter {

	private File file;
	
	/**
	 * @param f The file to be written to
	 */
	public FileSongWriter(final File f) {
		file = f;
	}
	
	/** {@inheritDoc} */
	@Override
	public void write(final SongFormat format, final ImmutableSong s) throws StreamFailureException {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(format.serialise(s));
		} catch (FileNotFoundException fnf) {
			throw new StreamFailureException(fnf.getMessage());
		} catch (IOException io) {
			throw new StreamFailureException(io.getMessage());
		}
	}

}
