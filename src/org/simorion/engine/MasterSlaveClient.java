package org.simorion.engine;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;

import org.simorion.common.ImmutableSong;
import org.simorion.common.stream.NetworkSongReaderWriter;
import org.simorion.common.stream.SongFormats;
import org.simorion.common.stream.SongWriter;
import org.simorion.common.stream.StreamFailureException;
import org.simorion.common.util.Util;


public class MasterSlaveClient extends Thread {

	final ImmutableSong song;
	final int instanceID;
	
	public MasterSlaveClient(final ImmutableSong song, final int instanceID) {
		this.song = song;
		this.instanceID = instanceID;
	}
	
	public void run() {
		try {
			sendMasterToSlave(song, instanceID);
		} catch (StreamFailureException e) {
			e.printStackTrace();
		}
	}
	
	public static void sendMasterToSlave(final ImmutableSong song, final int instanceID) throws StreamFailureException {
		try {
			for (int i = 0; i < 256; i++) {
				String address = "192.168.0." + i;
				if (InetAddress.getByName(address).isReachable(50)) {
					Socket slave = null;
					try {
						slave = new Socket(InetAddress.getByName(address), MasterSlaveServer.PORT);
						byte[] buf = new byte[4];
						slave.getInputStream().read(buf);
						int otherInstanceID = Util.toInt(buf);
						if(instanceID == otherInstanceID) {
							continue;
						} else {
							SongWriter sw = new NetworkSongReaderWriter(slave);
							sw.write(SongFormats.PREFERRED_FORMAT, song);
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
			throw new StreamFailureException("Connection failed");
		}
		throw new StreamFailureException("No other SimoriON found");
	}
		
}
