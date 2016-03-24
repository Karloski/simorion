package org.simorion.ui.controller;

import java.awt.event.MouseEvent;
import java.io.File;

import org.simorion.common.ImmutableLayer;
import org.simorion.common.SongBuilder;
import org.simorion.common.stream.FileSongReader;
import org.simorion.common.stream.InsufficientSongDataException;
import org.simorion.common.stream.SongFormats;
import org.simorion.common.stream.StreamFailureException;
import org.simorion.common.stream.UnsupportedSongFormatException;
import org.simorion.common.util.Util;
import org.simorion.ui.view.DefaultView;
import org.simorion.ui.view.View;

/**
 * Loads a song from the list of songs and plays each one for 30 seconds.
 * Every 16 beats, the next 16 are loaded, giving the illusion of continuous
 * play.
 * 
 * @author Edmund Smith
 * @author Karl Brown
 * @author Petar Krstic
 *
 */
public class ShopMode extends DeviceMode {

	public static final String[] fileNames = {
		    "fleenstones_#.song", 
			"Smoke_#.song",
			"rock_#.song",
			"mary_#.song"
	};
	
	public Runnable shopBoyRunnable;
	private boolean isRunning = false;
	private ShopModeView view;
	private int offset = 0;
	private Thread shopBoyThread;
	
	public static final long SONG_DELAY = 10*1000;
	
	public ShopMode(ModeMaster m) {
		super(m);
		view = new ShopModeView();
		shopBoyRunnable = new ShopBoyRunnable();
	}

	@Override
	public void onChangedTo() {
		isRunning = true;
		shopBoyThread = new Thread(shopBoyRunnable);
		shopBoyThread.start();
	}
	
	@Override
	public void onOKButtonPress(MouseEvent e) {
		isRunning = false;
		shopBoyThread.interrupt();
		model.reset();
		model.startPlaying();
		changeMode(ModeMaster.PERFORMANCE_MODE);
	}
	
	@Override
	public void onOnOffButtonPress(MouseEvent e) {
		isRunning = false;
		shopBoyThread.interrupt();
		model.reset();
		changeMode(ModeMaster.ON_OFF_MODE);		
	}

	/**
	 * Runnable that does the bulk of the work, swapping out the song from
	 * underneath the SimoriON every 16 beats and every 30 seconds.
	 * 
	 * @author Edmund Smith, Karl Brown
	 *
	 */
	class ShopBoyRunnable implements Runnable {
		public void run() {
			
			//Prepare to overwrite the existing song
			model.stopPlaying();
			model.reset();
			model.startPlaying();
			while (isRunning) {

				model.stopPlaying();
				model.reset();
				model.startPlaying();
				long startTime = System.currentTimeMillis();
				int part = 1;

				// Load part 1 of the song
				String nextFile = fileNames[offset].replace("#", Integer.toString(part));
				
				File demoFile = new File("./demos/" + nextFile);
				FileSongReader fsr = new FileSongReader(demoFile);
				SongBuilder sb = new SongBuilder();
				try {
					fsr.readTo(SongFormats.getFormatFor(Util.initialByte(demoFile)), sb);
					model.getSong().loadFrom(sb);
				} catch (Exception e) {
					model.setLCDDisplay("Error loading ShopBoyMode");
				}
				
				// Continuously load the next part for 30 seconds
				while (System.currentTimeMillis() < startTime + SONG_DELAY && isRunning) {
					// Next tick will loop to start: hot-replace the song
					int loop = model.getCurrentLayer().getLoopPoint();
					loop = (loop == 0) ? 16 : loop;
					if (model.getTick() > 1 && (model.getTick()+1) % loop == 0) {
						part++;
						nextFile = fileNames[offset].replace("#",
								Integer.toString(part));
							try {
								fsr = new FileSongReader(new File("./demos/"
										+ nextFile));
								sb = new SongBuilder();
								fsr.readTo(SongFormats.PREFERRED_FORMAT, sb);
								model.getSong().loadFrom(sb);
								//Sleep a beat
								Thread.sleep((long)(1000 * model.getTempo())+100);
							} catch (StreamFailureException e) {
								// File not found - we're at the end of the song chain.
								// Go back to start. We will exit when the 30 second timer is met.
								part = 0;
							} catch (Exception e) {
								// Failed to load the song. Move onto the next one.
								model.setLCDDisplay("Error loading song " + (offset + 1) + " part " + part);
								break;
							}
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						model.setLCDDisplay("ShopBoyMode interrupted");
					}
				}

				offset++;
				offset %= fileNames.length;
			}
		}
	}
	
	@Override
	public View getView() {
		return view;
	}
	
	class ShopModeView extends DefaultView {
		
		@Override
		public String getLCDMessage() {
			String songFile = fileNames[offset % fileNames.length];
			String song = "Playing: " + songFile.substring(0, songFile.length() - 7) + " |";
			int strOffset = (int) ((System.currentTimeMillis() / 500) % song.length());
			String loop = (song +" "+ song).substring(strOffset, strOffset+Math.min(song.length(), 16));
			return "Shop Boy Mode |" + loop;
		}
		
		//Same as performance mode
		@Override
		public boolean isLit(int x, int y) {
			ImmutableLayer layer = model.getCurrentLayer();
			if(layer == null) return false;
			int loop = layer.getLoopPoint();
    		loop = loop == 0 ? 16 : loop;
    		return model.getCurrentLayer().getRow(y).isLit(x) || (model.getTick() % loop == x && y % 5 == 0);
		}
		
	}
	
}
