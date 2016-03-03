package org.simorion.common.stream;

import java.net.Socket;

import org.simorion.common.ImmutableSong;
import org.simorion.common.SongBuilder;

/**
 * Reader and Writer of songs over a Socket connection
 * 
 * @author Edmund Smith
 */
public class NetworkSongReaderWriter implements SongWriter, SongReader {

	private Socket socket;
	byte[] buf;
	
	/**
	 * @param s The socket to be read from and/or written to
	 */
	public NetworkSongReaderWriter(final Socket s) {
		socket = s;
	}
	
	/** {@inheritDoc} */
	@Override
	public void write(SongFormat format, ImmutableSong s) throws StreamFailureException {
		try {
			socket.getOutputStream().write(format.serialise(s));
		} catch (Exception e) {
			throw new StreamFailureException(e.getMessage());
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public void readTo(SongFormat format, SongBuilder song) throws UnsupportedSongFormatException, InsufficientSongDataException, StreamFailureException {
		try {
			if(buf == null) {
				byte[] buf = new byte[2048];
				socket.getInputStream().read(buf);
			}
			format.deserialise(song, buf);
		} catch (Exception e) {
			throw new StreamFailureException(e.getMessage());
		}
	}

	@Override
	public SongFormat predictFormat() throws StreamFailureException, UnsupportedSongFormatException {
		try {
			if(buf == null) {
				buf = new byte[2048];
				socket.getInputStream().read(buf);
			}
			int initialByte = buf[0];
			return SongFormats.getFormatFor(initialByte);
		} catch (Exception e) {
			e.printStackTrace();
			throw new StreamFailureException(e.getMessage());
		}
	}
}