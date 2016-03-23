package org.simorion.ui.controller;

import java.awt.event.MouseEvent;
import java.io.File;

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
 *
 */
public class ShopMode extends DeviceMode {

	public static final String[] fileNames = {
			//"fleenstones_#.song"
	};
	
	public Runnable shopBoyRunnable;
	private boolean isRunning = false;
	private ShopModeView view;
	
	public static final long SONG_DELAY = 30*1000;
	
	public ShopMode(ModeMaster m) {
		super(m);
		view = new ShopModeView();
		shopBoyRunnable = new ShopBoyRunnable();
	}

	@Override
	public void onChangedTo() {
		isRunning = true;
		new Thread(shopBoyRunnable).start();
	}
	
	@Override
	public void onOKButtonPress(MouseEvent e) {
		isRunning = false;
		changeMode(ModeMaster.PERFORMANCE_MODE);
	}

	/**
	 * Runnable that does the bulk of the work, swapping out the song from
	 * underneath the SimoriON every 16 beats and every 30 seconds.
	 * 
	 * @author Edmund Smith
	 *
	 */
	class ShopBoyRunnable implements Runnable {
		public void run() {
			int offset = 0;
			
			//Prepare to overwrite the existing song
			model.stopPlaying();
			model.reset();
			while (isRunning) {
				long startTime = System.currentTimeMillis();
				int part = 2;

				// Load part 1 of the song
				String nextFile = fileNames[offset].replace("#", "1");
				File demoFile = new File("./demos/" + nextFile);
				FileSongReader fsr = new FileSongReader(demoFile);
				SongBuilder sb = new SongBuilder();
				try {
					fsr.readTo(SongFormats.getFormatFor(Util.initialByte(demoFile)), sb);
					model.getSong().loadFrom(sb);
				} catch (Exception e) {
					model.setLCDDisplay("Error loading ShopBoyMode");
					e.printStackTrace();
				}
				
				model.startPlaying();
				// Continuously load the next part for 30 seconds
				while (System.currentTimeMillis() < startTime + SONG_DELAY && isRunning) {
					// Next tick will loop to start: hot-replace the song
					int loop = model.getCurrentLayer().getLoopPoint();
					loop = (loop == 0) ? 16 : loop;
					if (model.getTick() > 1 && (model.getTick()+1) % loop == 0) {
						nextFile = fileNames[offset].replace("#",
								Integer.toString(part));
						fsr = new FileSongReader(new File("./demos/"
								+ nextFile));
						sb = new SongBuilder();
						try {
							fsr.readTo(SongFormats.PREFERRED_FORMAT, sb);
							model.getSong().loadFrom(sb);
						} catch (UnsupportedSongFormatException e) {
							model.setLCDDisplay("Error loading song " + (offset + 1) + " part " + part);
							e.printStackTrace();
						} catch (InsufficientSongDataException e) {
							model.setLCDDisplay("Error loading song " + (offset + 1) + " part " + part);
							e.printStackTrace();
						} catch (StreamFailureException e) {
							model.setLCDDisplay("Error loading song " + (offset + 1) + " part " + part);
							e.printStackTrace();
						}
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						model.setLCDDisplay("ShopBoyMode interrupted");
						e.printStackTrace();
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
			return "Shop Boy Mode";
		}
		
		//Same as performance mode
		@Override
		public boolean isLit(int x, int y) {
			int loop = model.getCurrentLayer().getLoopPoint();
    		loop = loop == 0 ? 16 : loop;
    		return model.getCurrentLayer().getRow(y).isLit(x) || (model.getTick() % loop == x && y % 5 == 0);
		}
		
	}
	
}
