package org.simorion.ui.controller;

import java.awt.event.MouseEvent;
import java.io.File;

import org.simorion.common.SongBuilder;
import org.simorion.common.stream.FileSongReader;
import org.simorion.common.stream.InsufficientSongDataException;
import org.simorion.common.stream.SongFormats;
import org.simorion.common.stream.StreamFailureException;
import org.simorion.common.stream.UnsupportedSongFormatException;
import org.simorion.ui.view.DefaultView;
import org.simorion.ui.view.View;

public class ShopMode extends DeviceMode {

	public String[] fileNames = {
			"test_#.song",
			"vape_on_the_air_#.song",
			"venice_#.song",
			"another_one_huffs_the_vacuum_#.song",
			"orange_wednesdays_#.song",
			"cash_#.song"
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

	class ShopBoyRunnable implements Runnable {
		public void run() {
			int offset = 0;
			model.stopPlaying();
			model.reset();
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
				model.startPlaying();
				// Continuously load the next part for 30 seconds
				while (System.currentTimeMillis() < startTime + SONG_DELAY) {
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
	
	@Override
	public View getView() {
		return view;
	}
	
	class ShopModeView extends DefaultView {
		
		@Override
		public String getLCDMessage() {
			return "Shop Boy Mode";
		}
		
		@Override
		public boolean isLit(int x, int y) {
			int loop = model.getCurrentLayer().getLoopPoint();
    		loop = loop == 0?16:loop;
    		return model.getCurrentLayer().getRow(y).isLit(x) || (model.getTick() % loop == x && y % 5 == 0);
		}
		
	}
	
}
