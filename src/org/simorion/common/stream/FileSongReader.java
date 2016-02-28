package org.simorion.common.stream;

import java.io.File;
import java.io.IOException;
//import java.nio.file.Files;
import java.nio.file.Files;

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
	public void readTo(SongFormat format, SongBuilder song) throws IOException {
		byte[] data = Files.readAllBytes(file.toPath());
		format.deserialise(song, data);
	}

}
