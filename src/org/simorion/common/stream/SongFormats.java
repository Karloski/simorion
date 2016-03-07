package org.simorion.common.stream;

public class SongFormats {

	//Don't want to be able to instantiate this class
	private SongFormats() { throw new RuntimeException("This class is not for instantiation"); }
	
	public static SongFormat[] formats = {
		null,
		new SongFormat_1()
	};
	
	public static SongFormat PREFERRED_FORMAT = formats[1];
	
	static SongFormat getFormatFor(int initialByte) throws UnsupportedSongFormatException {
		if(initialByte > 0 && initialByte < SongFormats.formats.length)
			return SongFormats.formats[initialByte];
		else {
			
			throw new UnsupportedSongFormatException("Error reading song with format "+initialByte);
		}
	}
	
}
