package org.simorion.common.stream;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.Before;
import org.junit.Test;
import org.simorion.common.SongBuilder;
import org.simorion.common.util.Util;

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
	
	@Test(expected=FileNotFoundException.class)
	public void errorOnFileNotFound() throws FileNotFoundException {
		try {
			if(testFile.exists()) assertTrue(testFile.delete());
			fsr.readTo(format, song);
		} catch(FileNotFoundException fnf) {
			throw fnf;
		} catch(IOException io) {
			fail(io.getMessage());
		}
	}
	
	/*
	 * TODO: consult over the best exception class to use
	 */
	@Test(expected=IOException.class)
	public void errorOnEmptyFile() throws IOException {
		if(!testFile.exists()) assertTrue(testFile.createNewFile());
		Files.write(testFile.toPath(), new byte[0]);
		fsr.readTo(format, song);
	}
	
	/*
	 * TODO: consult over the best exception class to use
	 */
	@Test()
	public void errorOnInvalidFormat() throws Exception {
		Files.write(testFile.toPath(), "\u00ff".getBytes("UTF-8"));
		fsr.readTo(format, song);
		fail("TODO: throw exception");
	}

	@Test
	public void readsEmptySong() throws IOException {
		Files.write(testFile.toPath(), "\u0000\u0000\u0000\u0000".getBytes("UTF-8"));
		fsr.readTo(format, song);
		assertEquals(song.getLayerCount(), 0);
		assertEquals(song.getRows(), 0);
		assertEquals(song.getCells(), 0);
	}
	
}
