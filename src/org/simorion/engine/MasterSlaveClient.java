package org.simorion.engine;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;

import org.simorion.common.ImmutableSong;
import org.simorion.common.stream.NetworkSongReaderWriter;
import org.simorion.common.stream.SongFormats;
import org.simorion.common.stream.SongWriter;
import org.simorion.common.stream.StreamFailureException;
import org.simorion.common.util.Util;
import org.simorion.ui.model.MutableModel;

/**
 * Thread that finds other SimoriONs on the local area network in the range
 * selected and sends them the current song loaded.
 * 
 * @author Edmund Smith
 */
public class MasterSlaveClient extends Thread {

	private final MutableModel model;
	private final ImmutableSong song;
	private final int instanceID;
	private final Runnable onSent;
	public int alreadySearched;
	
	private static final String BLUE_ROOM_SUBNET = "144.173.36.";
	private static final String LAN_DEFAULT_SUBNET = "192.168.0.";
	
	/**
	 * Constructs and initialises the fields ready for the run method
	 * @param song
	 * @param instanceID
	 */
	public MasterSlaveClient(final MutableModel model, final ImmutableSong song, final int instanceID, final Runnable onSent) {
		this.model = model;
		this.song = song;
		this.instanceID = instanceID;
		this.onSent = onSent;
	}
	
	/**
	 * Calls sendMasterToSlave on the fields song and instanceID
	 */
	public void run() {
		try {
			sendMasterToSlave(model, song, instanceID, onSent, 
					new Runnable() { public void run() {alreadySearched++;}});
		} catch (StreamFailureException e) {
			model.setLCDDisplay(e.getMessage());
		}
	}
	
	/**
	 * Connects to a LAN SimoriON and sends it the current song using the
	 * preferred song format.
	 * @param song The song to send
	 * @param instanceID The instanceID of this engine, to avoid sending the
	 * song to itself
	 * @throws StreamFailureException
	 */
	public static void sendMasterToSlave(final MutableModel model, final ImmutableSong song, final int instanceID, final Runnable onSent, final Runnable updater) throws StreamFailureException {
		try {
			//Start searching from a random offset to avoid having one device
			//always being chosen
			int start = new Random().nextInt(256);
			for (int i = 0; i < 256; i++) {
				updater.run();
				String address = BLUE_ROOM_SUBNET + ((start+i) % 256);
				if (InetAddress.getByName(address).isReachable(50)) {
					Socket slave = null;
					try {
						slave = new Socket(InetAddress.getByName(address), MasterSlaveServer.PORT);
						
						//Receive declaration of SimoriON id
						byte[] buf = new byte[4];
						slave.getInputStream().read(buf);
						int otherInstanceID = Util.toInt(buf);
						
						//Is that SimoriON this SimoriON?
						if(instanceID == otherInstanceID) {
							continue;
						} else {
							SongWriter sw = new NetworkSongReaderWriter(slave);
							sw.write(SongFormats.PREFERRED_FORMAT, song);
							onSent.run();
							return;
						}
					} catch (Exception e) {
						continue;
					} finally {
						if(slave != null) slave.close();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			onSent.run();
			throw new StreamFailureException("Connection failed");
		}
		onSent.run();
		throw new StreamFailureException("No other SimoriON found");
	}
		
}
