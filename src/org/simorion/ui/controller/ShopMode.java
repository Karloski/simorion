package org.simorion.ui.controller;

import java.awt.event.MouseEvent;
import java.io.File;

import org.simorion.common.ImmutableLayer;
import org.simorion.common.SongBuilder;
import org.simorion.common.stream.FileSongReader;
import org.simorion.common.stream.SongFormats;
import org.simorion.common.stream.StreamFailureException;
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
	
	public static final long SONG_DELAY = 30*1000;  // 30 seconds.
	
	public ShopMode(ModeMaster m) {
		super(m);
		view = new ShopModeView();
		shopBoyRunnable = new ShopBoyRunnable();
	}

	/**
	 * On changed to, set the flag for the thread as running and start it.
	 */
	@Override
	public void onChangedTo() {
		isRunning = true;
		shopBoyThread = new Thread(shopBoyRunnable);
		shopBoyThread.start();
	}
	
	/**
	 * When the OK button is pressed, interrupt the thread, reset the Simori-ON and send
	 * us back to the performance mode.
	 */
	@Override
	public void onOKButtonPress(MouseEvent e) {
		isRunning = false;
		shopBoyThread.interrupt();
		model.reset();
		model.startPlaying();
		changeMode(ModeMaster.PERFORMANCE_MODE);
	}
	
	/**
	 * If the OnOff button is pressed, stop the Simori-ON and send us to the On/Off Mode.
	 */
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
			
			while (isRunning) {
				// Prepare to overwrite the existing song
				model.stopPlaying();
				model.reset();
				model.startPlaying();
				
				// Reset the time to now.
				long startTime = System.currentTimeMillis();
				
				// Set the loop to 16 - this is default.
				int loop = 16;
				
				// Set the part to the start and load it.
				int part = 1;
				String nextFile = fileNames[offset].replace("#", Integer.toString(part));  // Replace # with part number.
				File demoFile = new File("./demos/" + nextFile);
				FileSongReader fsr = new FileSongReader(demoFile);
				SongBuilder sb = new SongBuilder();
				try {
					fsr.readTo(SongFormats.getFormatFor(Util.initialByte(demoFile)), sb);
					model.getSong().loadFrom(sb);
					loop = model.getCurrentLayer().getLoopPoint() == 0 ? 16 : model.getCurrentLayer().getLoopPoint();
				} catch (Exception e) {
					model.setLCDDisplay("Error loading ShopBoyMode");
				}
				
				// Continuously play the song for 30 seconds
				while (System.currentTimeMillis() < startTime + SONG_DELAY && isRunning) {
					// If we're at the end of the loop (tick % loop = 0), move to next part.
					if (model.getTick() > 1 && (model.getTick()+1) % loop == 0) {
						part++;
						nextFile = fileNames[offset].replace("#", Integer.toString(part));
							try {
								fsr = new FileSongReader(new File("./demos/"
										+ nextFile));
								sb = new SongBuilder();
								fsr.readTo(SongFormats.PREFERRED_FORMAT, sb);
								
								// Get the song and reset the loop, based on the song.
								model.getSong().loadFrom(sb);
								loop = model.getCurrentLayer().getLoopPoint() == 0 ? 16 : model.getCurrentLayer().getLoopPoint();
								// Sleep a beat
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
	
	/**
	 * View specifics for the ShopBoy Mode.
	 * @author Edmund Smith
	 *
	 */
	class ShopModeView extends DefaultView {
		
		
		/**
		 * Returns the current mode, appended by the song name.
		 * The song name will marquee across the screen.
		 */
		@Override
		public String getLCDMessage() {
			String songFile = fileNames[offset % fileNames.length];
			String song = "Playing: " + songFile.substring(0, songFile.length() - 7) + " |";
			int strOffset = (int) ((System.currentTimeMillis() / 500) % song.length());
			String loop = (song +" "+ song).substring(strOffset, strOffset+Math.min(song.length(), 16));
			return "Shop Boy Mode |" + loop;
		}
		
		/**
		 * Returns true if the button is set as lit in the model or is one of the four buttons where the clock hand
		 * currently resides.
		 */
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
