package org.simorion.ui.model;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import org.simorion.common.MutableLayer;
import org.simorion.common.MutableRow;
import org.simorion.common.Voice;
import org.simorion.common.stream.SongFormat;
import org.simorion.common.stream.SongReader;
import org.simorion.common.stream.SongWriter;

/**
 * Dummy Model for testing MVC loop
 * @author Edmund Smith
 */
public class DummyModel implements MutableModel {
	
	private static class DummyVoice implements Voice {

		@Override
		public int getMidiVoice() {
			return 0;
		}

		@Override
		public String getName() {
			return "Dummy Grande";
		}
		
	}
	
	private static class DummyLayer implements MutableLayer {

		DummyVoice voice = new DummyVoice();
		MutableRow[] rows;
		
		public DummyLayer() {
			rows = new MutableRow[16];
			for(int i = 0; i < 16; i++) rows[i] = new DummyRow(i);
		}
		
		
		@Override
		public int getLayerNumber() {
			return 0;
		}

		@Override
		public Voice getVoice() {
			return voice;
		}

		@Override
		public byte getVelocity() {
			return 20;
		}

		@Override
		public int getLoopPoint() {
			return 16;
		}

		@Override
		public MutableRow getRow(int i) {
			if(i < 0 || i > 15) throw new RuntimeException("Row index "+i+" not in range 0-16");
			return rows[i];
		}
		
		@Override
		public Iterable<? extends MutableRow> getRows() {
			return Arrays.asList(rows);
		}


		@Override
		public String getLCDMessage() {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public void setLoopPoint(byte loopPoint) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void setVelocity(byte velocity) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void setVoice(Voice v) {
			// TODO Auto-generated method stub
			
		}
	}
	
	private static class DummyRow implements MutableRow {

		private final byte note;
		
		public DummyRow(int row) {
			note = (byte) (row * 15 + 15);
		}
		
		@Override
		public boolean isLit(int cell) {
			return true;
		}

		@Override
		public byte getNote() {
			return note;
		}

		@Override
		public void setLit(int cell) {
			//Do nothing
		}

		@Override
		public void setUnlit(int cell) {
			//Do nothing
		}

		@Override
		public void toggleLit(int cell) {
			//Do nothing
		}

		@Override
		public void setNote(byte note) {
			//Do nothing
		}

		@Override
		public void applyMask(int and, int or) {
			//Do nothing
		}

		@Override
		public int cellCount() {
			return 16;
		}

		@Override
		public int getLit() {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}

	private final MutableLayer[] layers;
	private volatile int tick;
	private Timer ticker;
	
	public DummyModel() {
		layers = new DummyLayer[16];
		for(int i = 0; i < 16; i++) layers[i] = new DummyLayer();
		tick = 0;
		ticker = new Timer();
		ticker.schedule(new TimerTask() {
			public void run() {
				tick++;
			}
		},0, (long) (1/getTempo()));
	}

	@Override
	public void setVoice(MutableLayer l, Voice voice) {
		//Ignore
	}

	@Override
	public void setVelocity(MutableLayer l, byte velocity) {
		//Ignore
		
	}

	@Override
	public void setLoopPoint(MutableLayer l, byte loopPoint) {
		//Ignore
	}

	@Override
	public void setTempo(float beatsPerSecond) {
		//Ignore
	}

	@Override
	public float getTempo() {
		return 1.5f;
	}

	@Override
	public MutableLayer getCurrentLayer() {
		return layers[0];
	}

	@Override
	public int getCurrentLayerId() {
		return 0;
	}

	@Override
	public MutableLayer getLayer(int i) {
		return layers[i];
	}

	@Override
	public int getTick() {
		return tick;
	}

	//Example of behaviour
	@Override
	public void setLit(int layer, int xLoc, int yLoc) {
		layers[layer].getRow(yLoc).setLit(xLoc);
	}

	@Override
	public void setUnLit(int layer, int xLoc, int yLoc) {
		layers[layer].getRow(yLoc).setUnlit(xLoc);
	}

	@Override
	public void toggleLit(int layer, int xLoc, int yLoc) {
		layers[layer].getRow(yLoc).toggleLit(xLoc);
	}

	@Override
	public void sendToStream(SongWriter stream, SongFormat format) {
		//Ignore
	}

	@Override
	public void receiveFromStream(SongReader stream, SongFormat format) {
		//Ignore
	}

	@Override
	public void setTopmostLayer(int layerID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLCDDisplay(String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getLCDDisplay() {
		return "mock";
	}
	
}
