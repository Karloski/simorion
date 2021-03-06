package org.simorion.common.stream;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.Socket;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.simorion.common.MutableLayer;
import org.simorion.common.MutableRow;
import org.simorion.common.Song;
import org.simorion.common.SongBuilder;
import org.simorion.common.StandardSong;

/**
 * Tests the stream abstractions behave identically under the Stream interface,
 * testing that the Song -> Stream -> Song system works
 * @author Edmund Smith
 */
public class SongReaderWriterTest {

	public SongReader[] songReaders;
	public SongWriter[] songWriters;
	
	public SongFormat format;
	public Song song;
	
	@Before
	public void setUp() throws IOException {
		Socket loopback = new Socket() {
			PipedInputStream is = new PipedInputStream();
			OutputStream os = new PipedOutputStream(is);
			
			@Override
			public InputStream getInputStream() {
				return is;
			}
			
			@Override
			public OutputStream getOutputStream() {
				return os;
			}
		};
		NetworkSongReaderWriter nsrw = new NetworkSongReaderWriter(loopback);
		File f = new File("localtest.song");
		FileSongReader fsr = new FileSongReader(f);
		FileSongWriter fsw = new FileSongWriter(f);
		songReaders = new SongReader[]{nsrw,fsr};
		songWriters = new SongWriter[]{nsrw,fsw};
		
		format = new SongFormat_1();
		song = new StandardSong();
	}
	
	@Test
	public void testWholeWriteAndRead() throws UnsupportedSongFormatException, InsufficientSongDataException, StreamFailureException {
		for(SongWriter sw : songWriters) {
			sw.write(format, song);
		}
		for(SongReader sr : songReaders) {
			SongBuilder sb = new SongBuilder();
			sr.readTo(format, sb);
			Song s = new StandardSong();
			s.loadFrom(sb);
			assertTrue(song.equals(s));
		}
	}

	@Test
	public void testWholeWriteAndReadRandomData() throws IOException, UnsupportedSongFormatException, InsufficientSongDataException, StreamFailureException {
		Random seedRand = new Random(); 
		long randSeed = seedRand.nextLong();
		Random dataCreationRand = new Random(randSeed);
		
		for(MutableLayer ml : song.getLayers()) {
			for(MutableRow mr : ml.getRows()) {
				mr.applyMask(0, dataCreationRand.nextInt(1 << mr.cellCount()));
			}
		}
		
		for(SongWriter sw : songWriters) {
			sw.write(format, song);
		}
		for(SongReader sr : songReaders) {
			SongBuilder sb = new SongBuilder();
			sr.readTo(format, sb);
			Song s = new StandardSong();
			s.loadFrom(sb);
			assertTrue(song.equals(s));
		}
	}
	
}
