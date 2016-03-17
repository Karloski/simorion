package org.simorion.engine;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.simorion.common.stream.NetworkSongReaderWriter;
import org.simorion.common.stream.SongReader;
import org.simorion.common.stream.StreamFailureException;
import org.simorion.common.stream.UnsupportedSongFormatException;
import org.simorion.common.util.Util;

/**
 * Server that listens for a new song constantly, and if it receives one,
 * updates the engine to the new song. Does not yet have user-visible error
 * reporting. TODO
 * 
 * @author Edmund Smith
 */
public class MasterSlaveServer extends Thread {
	
	public static final int PORT = 20160;

	private final Engine engine;
	
	public MasterSlaveServer(final Engine engine) {
		super("SlaveServer");
		this.engine = engine;
	}
	
	/**
	 * Continuously listens for a connection, and if it receives one, overwrite
	 * the current song with the received one
	 */
	public void run() {
		ServerSocket slave;
		try {
			slave = new ServerSocket(PORT);
			while (true) {
				Socket master = null;
				try {
					master = slave.accept();
					master.getOutputStream().write(Util.toBytes(engine.getInstanceID()));
					
					SongReader reader = new NetworkSongReaderWriter(master);
					engine.receiveFromStream(reader, reader.predictFormat());
				} catch (IOException e) {
					e.printStackTrace();
				} catch (StreamFailureException e) {
					e.printStackTrace();
				} catch (UnsupportedSongFormatException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if(master != null) master.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
