package org.simorion.common.stream;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.simorion.common.MutableLayer;
import org.simorion.common.MutableRow;
import org.simorion.common.Song;
import org.simorion.common.SongBuilder;
import org.simorion.common.StandardSong;
import org.simorion.common.util.Util;

/**
 * Test the song formats 100 times with random data
 * @author Edmund Smith
 */
@RunWith(Parameterized.class)
public class SongFormatTest {

	
	public List<Song> getRandomSongs() {
		Random rand = new Random();
		ArrayList<Song> songs = new ArrayList<Song>();
		for(int i = 0; i < 100; i++) {
			Song s = new StandardSong();
			s.setBPM((byte)rand.nextInt(256));
			for(MutableLayer l : s.getLayers()) {
				for(MutableRow row : l.getRows()) {
					row.applyXor(rand.nextInt(1<<row.cellCount()));
				}
			}
			songs.add(s);
		}
		return songs;
	}
	
	@Parameterized.Parameters
	public static List<SongFormat[]> data() {
		ArrayList<SongFormat[]> formats = new ArrayList<SongFormat[]>();
		for(SongFormat format : Util.slice(SongFormats.formats, 1)) {
			formats.add(Util.asArray(format));
		}
		return formats;
	}
	
	public SongFormatTest(SongFormat f) { format = f; }
	
	private final SongFormat format;
	
	@Test
	public final void testIdentityOnSongs() throws UnsupportedEncodingException, UnsupportedSongFormatException, InsufficientSongDataException, IOException {
		Song loadTo;
		SongBuilder sb;
		for(Song s : getRandomSongs()) {
			loadTo = new StandardSong();
			sb = new SongBuilder();
			format.deserialise(sb, format.serialise(s));
			loadTo.loadFrom(sb);
			assertEquals(s, loadTo);
		}
	}
			
}
