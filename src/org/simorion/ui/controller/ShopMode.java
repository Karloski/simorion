package org.simorion.ui.controller;

import java.awt.event.MouseEvent;
import java.io.File;

import org.simorion.common.SongBuilder;
import org.simorion.common.stream.FileSongReader;
import org.simorion.common.stream.InsufficientSongDataException;
import org.simorion.common.stream.SongFormats;
import org.simorion.common.stream.StreamFailureException;
import org.simorion.common.stream.UnsupportedSongFormatException;

public class ShopMode extends DeviceMode {

	public String[] fileNames = {
			"vape_on_the_air_#.song",
			"venice_#.song",
			"another_one_huffs_the_vacuum_#.song",
			"orange_wednesdays_#.song",
			"cash_#.song"
	};
	
	public Runnable shopBoyRunnable;
	private boolean isRunning = false;
	
	public static final long SONG_DELAY = 30*1000;
	
	public ShopMode(ModeMaster m) {
		super(m);

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
	}

	class ShopBoyRunnable implements Runnable {
		public void run() {
			int offset = 0;
			while (isRunning) {
				long startTime = System.currentTimeMillis();
				int part = 2;

				// Load part 1 of the song
				String nextFile = fileNames[offset].replace("#", "1");
				FileSongReader fsr = new FileSongReader(new File("./demos/"
						+ nextFile));
				SongBuilder sb = new SongBuilder();
				try {
					fsr.readTo(SongFormats.PREFERRED_FORMAT, sb);
					model.getSong().loadFrom(sb);
				} catch (UnsupportedSongFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InsufficientSongDataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (StreamFailureException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// Continuously load the next part for 30 seconds
				while (System.currentTimeMillis() < startTime + SONG_DELAY) {
					// Next tick will loop to start: hot-replace the song
					int loop = model.getCurrentLayer().getLoopPoint();
					loop = (loop == 0) ? 16 : loop;
					if (model.getTick() + 1 % loop == 0) {
						nextFile = fileNames[offset].replace("#",
								Integer.toString(part));
						fsr = new FileSongReader(new File("./demos/"
								+ nextFile));
						sb = new SongBuilder();
						try {
							fsr.readTo(SongFormats.PREFERRED_FORMAT, sb);
							model.getSong().loadFrom(sb);
						} catch (UnsupportedSongFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InsufficientSongDataException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (StreamFailureException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				offset++;
				offset %= fileNames.length;
			}
		}
	}
	
}
