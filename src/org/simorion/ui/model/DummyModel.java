package org.simorion.ui.model;

import java.util.Timer;
import java.util.TimerTask;

import org.simorion.common.Layer;
import org.simorion.common.Row;
import org.simorion.common.Stream;
import org.simorion.common.StreamFormat;
import org.simorion.common.Voice;

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
	
	private static class DummyLayer implements Layer {

		DummyVoice voice = new DummyVoice();
		Row[] rows;
		
		public DummyLayer() {
			rows = new Row[16];
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
	}
	
	private static class DummyRow implements Row {

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
		
	}

	private final Layer[] layers;
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
	
}
