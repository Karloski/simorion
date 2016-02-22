package org.simorion.ui.model;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import org.simorion.common.Layer;
import org.simorion.common.ReadonlyRow;
import org.simorion.common.Row;
import org.simorion.common.Stream;
import org.simorion.common.StreamFormat;
import org.simorion.common.Voice;
import org.simorion.common.WritableLayer;
import org.simorion.common.WritableRow;

/**
 * Dummy Model for testing MVC loop
 * @author Edmund Smith
 */
public class DummyModel implements Model, WritableModel {
	
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
	
	private static class DummyLayer implements WritableLayer {

		DummyVoice voice = new DummyVoice();
		WritableRow[] rows;
		
		public DummyLayer() {
			rows = new WritableRow[16];
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
		public Row getRow(int i) {
			if(i < 0 || i > 15) throw new RuntimeException("Row index "+i+" not in range 0-16");
			return rows[i];
		}
		
		@Override
		public WritableRow getWritableRow(int i) {
			if(i < 0 || i > 15) throw new RuntimeException("Row index "+i+" not in range 0-16");
			return rows[i];
		}


		@Override
		public Iterable<? extends Row> getRows() {
			return Arrays.asList(rows);
		}


		@Override
		public ReadonlyRow getReadonlyRow(int i) {
			return getWritableRow(i);
		}


		@Override
		public Iterable<? extends ReadonlyRow> getReadonlyRows() {
			return Arrays.asList(rows);
		}


		@Override
		public String getLCDMessage() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	private static class DummyRow implements WritableRow {

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

	private final WritableLayer[] layers;
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
	public void setVoice(Layer l, Voice voice) {
		//Ignore
	}

	@Override
	public void setVelocity(Layer l, int velocity) {
		//Ignore
		
	}

	@Override
	public void setLoopPoint(Layer l, int loopPoint) {
		//Ignore
	}

	@Override
	public void setTempo(float beatsPerSecond) {
		//Ignore
	}

	@Override
	public void sendToStream(Stream s, StreamFormat f) {
		//Ignore
	}

	@Override
	public void receiveFromStream(Stream s, StreamFormat f) {
		//Ignore
	}

	@Override
	public float getTempo() {
		return 1.5f;
	}

	@Override
	public Layer getCurrentLayer() {
		return layers[0];
	}

	@Override
	public int getCurrentLayerId() {
		return 0;
	}

	@Override
	public Layer getLayer(int i) {
		return layers[i];
	}

	@Override
	public int getTick() {
		return tick;
	}

	//Example of behaviour
	@Override
	public void setLit(int layer, int xLoc, int yLoc) {
		layers[layer].getWritableRow(yLoc).setLit(xLoc);
	}

	@Override
	public void setUnLit(int layer, int xLoc, int yLoc) {
		layers[layer].getWritableRow(yLoc).setUnlit(xLoc);
	}

	@Override
	public void toggleLit(int layer, int xLoc, int yLoc) {
		layers[layer].getWritableRow(yLoc).toggleLit(xLoc);
	}
	
}
