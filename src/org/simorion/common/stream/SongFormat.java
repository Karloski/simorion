package org.simorion.common.stream;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.simorion.common.ImmutableSong;
import org.simorion.common.SongBuilder;

/**
 * Common interface for a song format to provide, which is basically an
 * isomorphism between the Song and the byte[].
 * 
 * @author Edmund Smith
 */
public interface SongFormat {
	
	/**
	 * Reads the song into a byte array, per the instance's specific
	 * specification
	 * @param song The Song being serialised
	 * @return The byte array containing the serialised Song
	 * @throws UnsupportedEncodingException In unlikely event of UTF-8 not
	 * existing locally
	 * @throws IOException If anything IO-related goes wrong, hinting a retry
	 */
	byte[] serialise(final ImmutableSong song) throws UnsupportedEncodingException, IOException;
	
	/**
	 * Reads the byte array, deserialises it and primes the song builder, ready
	 * to load it into a Song instance.
	 * @param builder The songbuilder being primed, effectively borrowed
	 * @param data The byte array being deserialsed into the builder
	 */
	void deserialise(final SongBuilder builder, final byte[] data);
	

	public String getFormatName();
	
	public byte getFormatID();
}
