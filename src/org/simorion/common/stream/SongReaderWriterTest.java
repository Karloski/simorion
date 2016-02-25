package org.simorion.common.stream;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.Socket;

import org.junit.Before;
import org.junit.Test;
import org.simorion.common.Song;
import org.simorion.common.SongBuilder;
import org.simorion.common.StandardSong;

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
	public void testWholeWriteAndRead() throws IOException {
		for(SongWriter sw : songWriters) {
			sw.write(format, song);
		}
		for(SongReader sr : songReaders) {
			SongBuilder sb = new SongBuilder();
			sr.readTo(format, sb);
			Song s = new StandardSong();
			s.loadFrom(sb);
			System.out.println(song.toString());
			System.out.println(s.toString());
			assertTrue(song.equals(s));
		}
	}

}
