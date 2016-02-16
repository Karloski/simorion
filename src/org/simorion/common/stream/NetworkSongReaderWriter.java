package org.simorion.common.stream;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;

import org.simorion.common.ReadonlySong;
import org.simorion.common.SongBuilder;

/**
 * Reader and Writer of songs over a Socket connection
 * 
 * @author Edmund Smith
 */
public class NetworkSongReaderWriter implements SongWriter, SongReader {

	private Socket socket;
	
	/**
	 * @param s The socket to be read from and/or written to
	 */
	public NetworkSongReaderWriter(final Socket s) {
		socket = s;
	}
	
	/** {@inheritDoc} */
	@Override
	public void write(SongFormat format, ReadonlySong s) throws IOException {
		socket.getOutputStream().write(format.serialise(s));
	}
	
	/** {@inheritDoc} */
	@Override
	public void readTo(SongFormat format, SongBuilder song) throws IOException {
		byte[] buf = new byte[2048];
		socket.getInputStream().read(buf);
		format.deserialise(song, buf);
	}
}