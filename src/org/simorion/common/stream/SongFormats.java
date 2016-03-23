package org.simorion.common.stream;

/**
 * Static class for managing the particular sound formats to use when and where
 * 
 * @author Edmund Smith
 */
public class SongFormats {

	//Don't want to be able to instantiate this class
	private SongFormats() { throw new RuntimeException("This class is not for instantiation"); }
	
	public static SongFormat[] formats = {
		null,
		new SongFormat_1(),
		new SongFormat_2()
	};
	
	public static SongFormat PREFERRED_FORMAT = formats[1];
	
	public static SongFormat getFormatFor(int initialByte) throws UnsupportedSongFormatException {
		if(initialByte > 0 && initialByte < SongFormats.formats.length) {
			return SongFormats.formats[initialByte];
		} else {
			throw new UnsupportedSongFormatException("Error reading song with format "+initialByte);
		}
	}
	
}
