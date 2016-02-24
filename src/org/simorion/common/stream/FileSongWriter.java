package org.simorion.common.stream;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
	public void write(final SongFormat format, final ImmutableSong s) throws IOException {
		Files.write(file.toPath(), format.serialise(s));
	}

}
