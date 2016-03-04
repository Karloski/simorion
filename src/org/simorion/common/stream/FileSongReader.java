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
			int bytesRead = 0;
			
			while((bytesRead += fis.read(data, bytesRead, data.length - bytesRead)) != data.length) {}
			
			fis.close();
			format.deserialise(song, data);
			
		} catch(FileNotFoundException fnf) {
			throw new StreamFailureException(fnf.getMessage());
		} catch (IOException io) {
			throw new StreamFailureException(io.getMessage());
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public SongFormat predictFormat() throws StreamFailureException, UnsupportedSongFormatException {
		try {
			FileInputStream fis = new FileInputStream(file);
			int initialByte = fis.read();
			fis.close();
			return SongFormats.getFormatFor(initialByte);
		} catch (IOException e) {
			throw new StreamFailureException("Error reading format type");
		}
	}
	
	/** Does nothing, as there is no caching in FileSongReader */
	@Override
	public void reset() {}

}
