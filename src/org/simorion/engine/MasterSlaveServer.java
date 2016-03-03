package org.simorion.engine;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

import org.simorion.common.stream.NetworkSongReaderWriter;
import org.simorion.common.stream.SongReader;
import org.simorion.common.stream.StreamFailureException;
import org.simorion.common.stream.UnsupportedSongFormatException;

/**
 * Server that listens for a new song constantly, and if it receives one, updates
 * the engine to the new song
 * 
 * @author Edmund Smith
 */
public class MasterSlaveServer extends Thread {
	
	public static final int PORT = 20160;

	private final Engine engine;
	
	public MasterSlaveServer(final Engine engine) {
		this.engine = engine;
	}
	
	public void run() {
		new MasterSlaveClient(engine.getSong()).start();
		
		/*DatagramSocket udpServer;
		try {
			udpServer = new DatagramSocket(PORT);
			while (true) {
				try {
					System.out.println("Waiting for connections");
					DatagramPacket probe = new DatagramPacket(new byte[1], 1);
					udpServer.receive(probe);
					System.out.println("\tRead "+Arrays.toString(probe.getData()));
					udpServer.send(new DatagramPacket(new byte[]{(byte)-2}, 1, probe.getAddress(), probe.getPort()));
					SongReader reader = new NetworkSongReaderWriter(udpServer);
					engine.receiveFromStream(reader, reader.predictFormat());
				} catch (IOException e) {
					e.printStackTrace();
				} catch (StreamFailureException e) {
					System.out.println("Error loading a song over the network");
					e.printStackTrace();
				} catch (UnsupportedSongFormatException e) {
					System.out.println("Error loading a song over the network");
					e.printStackTrace();
				} finally {
					udpServer.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		ServerSocket tcpServer;
		try {
			tcpServer = new ServerSocket(PORT);
			//while (true) {
				try {
					System.out.println("Waiting for connections");
					Socket conn = tcpServer.accept();
					//tcpServer.send(new DatagramPacket(new byte[]{(byte)-2}, 1, probe.getAddress(), probe.getPort()));
					SongReader reader = new NetworkSongReaderWriter(conn);
					engine.receiveFromStream(reader, reader.predictFormat());
				} catch (IOException e) {
					e.printStackTrace();
				} catch (StreamFailureException e) {
					System.out.println("Error loading a song over the network");
					e.printStackTrace();
				} catch (UnsupportedSongFormatException e) {
					System.out.println("Error loading a song over the network");
					e.printStackTrace();
				} finally {
					tcpServer.close();
				}
			//}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
