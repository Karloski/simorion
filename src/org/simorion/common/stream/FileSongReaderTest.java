package org.simorion.common.stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.Before;
import org.junit.Test;
import org.simorion.common.SongBuilder;

/**
 * Tests FileSongReader and SongFormat with known inputs and outputs
 * @author Edmund Smith
 *
 */
public class FileSongReaderTest {

	private File testFile;
	private FileSongReader fsr;
	private SongBuilder song;
	private SongFormat format;
	
	@Before
	public void setUp() {
		testFile = new File("fileSongReaderTest.song");
		fsr = new FileSongReader(testFile);
		song = new SongBuilder();
		format = new SongFormat_1();
	}
	
	@Test(expected=StreamFailureException.class)
	public void errorOnFileNotFound() 
			throws FileNotFoundException, UnsupportedSongFormatException, InsufficientSongDataException, StreamFailureException {
		if(testFile.exists()) assertTrue(testFile.delete());
		fsr.readTo(format, song);
	}
	
	@Test(expected=InsufficientSongDataException.class)
	public void errorOnEmptyFile() 
			throws IOException, UnsupportedSongFormatException, InsufficientSongDataException, StreamFailureException {
		
		if(!testFile.exists()) assertTrue(testFile.createNewFile());
		
		FileOutputStream fos = new FileOutputStream(testFile);
		fos.write(new byte[0]);
		fos.close();
		
		fsr.readTo(format, song);
	}
	
	@Test(expected = UnsupportedSongFormatException.class)
	public void errorOnInvalidFormat() throws Exception {
		
		FileOutputStream fos = new FileOutputStream(testFile);
		fos.write("\u00ff".getBytes("UTF-8"));
		fos.close();
		
		fsr.readTo(format, song);
	}

	@Test
	public void readsEmptySong()
			throws IOException, UnsupportedSongFormatException, InsufficientSongDataException, StreamFailureException {
		Files.write(testFile.toPath(), "\u0001\u0000\u0000\u0000\u0000".getBytes("UTF-8"));
		fsr.readTo(format, song);
		assertEquals(0, song.getLayerCount());
		assertEquals(0, song.getRows());
		assertEquals(0, song.getCells());
		assertEquals(0, song.getBPM());
	}
	
}
