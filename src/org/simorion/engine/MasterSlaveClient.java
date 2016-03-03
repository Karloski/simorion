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


public class MasterSlaveClient extends Thread {

	final ImmutableSong song;
	
	public MasterSlaveClient(final ImmutableSong song) {
		this.song = song;
	}
	
	public static void sendMasterToSlave(final ImmutableSong song) {
		try {
			for(int i = 0; i < 256; i++) {
				String address = "192.168.0."+i;
				if(InetAddress.getByName(address).isReachable(50)) {
					System.out.println("Found "+address+" on the network");
					try {
						sendSongTo(InetAddress.getByName(address), song);
						return;
					} catch(SocketException e) { System.out.println("Connection refused on "+address);
					//e.printStackTrace();
					} catch(Exception e) { e.printStackTrace(); }
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void sendSongTo(InetAddress serverAddr, final ImmutableSong song) throws IOException, StreamFailureException {
		Socket conn = new Socket(serverAddr, MasterSlaveServer.PORT);
		SongWriter sw = new NetworkSongReaderWriter(conn);
		sw.write(SongFormats.PREFERRED_FORMAT, song);
		conn.close();
	}
	
	private static final byte[] probeBuffer = new byte[]{(byte) 0xff};
	private static void sendSongToUDP(InetAddress serverAddr) throws IOException {
		DatagramSocket ds = null;
		try {
		ds = new DatagramSocket(MasterSlaveServer.PORT-1, serverAddr);
		System.out.println("Sending "+Arrays.toString(probeBuffer)+" to "+serverAddr.getHostAddress());
		ds.send(new DatagramPacket(probeBuffer, 1, serverAddr, MasterSlaveServer.PORT));
		System.out.println("Sent to "+serverAddr.getHostName());
		} catch (IOException e) {
			throw e;
		} finally {
			if(ds != null) ds.close();
		}
	}
	
	public void run() { sendMasterToSlave(song); }
	
}
