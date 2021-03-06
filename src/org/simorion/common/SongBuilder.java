package org.simorion.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder for Songs, featuring mutable layers and rows, with bit masks for
 * cells. Intended to have a relatively expressive interface.
 * 
 * @author Edmund Smith
 */
public class SongBuilder {

	public List<AddLayer> layers;
	private byte rows, cells, layerCount, bpm;
	
	public SongBuilder() {
		layers = new ArrayList<AddLayer>();
		layerCount = rows = cells = -1;
	}
	
	/**
	 * Adds a new layer to the song, and returns that layer for setting
	 * e.g.
	 * <code>
	 * new SongBuilder().addLayer().setVelocity(4);
	 * </code>
	 * @return The layer just added
	 */
	public AddLayer addLayer() {
		AddLayer al = new AddLayer(this);
		layers.add(al);
		return al;
	}
	
	public byte getRows() {
		return rows;
	}

	public SongBuilder setRows(byte rows) {
		this.rows = rows;
		return this;
	}

	public byte getCells() {
		return cells;
	}

	public SongBuilder setCells(byte cells) {
		this.cells = cells;
		return this;
	}

	public byte getLayerCount() {
		return layerCount;
	}

	public SongBuilder setLayerCount(byte layerCount) {
		this.layerCount = layerCount;
		return this;
	}
	
	public void setBPM(byte i) {
		bpm = i;
	}
	
	public byte getBPM() {
		return bpm;
	}

	public static class AddRow {
		
		private final AddLayer parent;
		
		public long mask;
		public byte note;
		
		private AddRow(AddLayer parent) {
			this.parent = parent;
		}
		
		public AddRow setMask(long l) {
			mask = l;
			return this;
		}
		
		public AddLayer done() {
			return parent;
		}
		
	}
	
	public static class AddLayer {
		
		private final SongBuilder parent;

		private int velocity, voice, loopPoint, layerId, bpm;
		private String lcdMessage;
		
		private List<AddRow> rows;
		
		private AddLayer(final SongBuilder parent) {
			rows = new ArrayList<AddRow>();
			this.parent = parent;
		}
		
		public AddLayer setVelocity(int i) {
			velocity = i;
			return this;
		}
		
		public AddLayer setMIDIVoice(int i) {
			voice = i;
			return this;
		}
		
		public AddLayer setNumber(int i) {
			layerId = i;
			return this;
		}
		
		public AddLayer setLoopPoint(int i) {
			loopPoint = i;
			return this;
		}
		
		public AddLayer setLCDMessage(String s) {
			lcdMessage = s;
			return this;
		}
		
		public List<AddRow> addRows(int rows) {
			for(int i = 0; i < rows; i++) {
				this.rows.add(new AddRow(this));
			}
			return this.rows;
		}
		
		public List<AddRow> getRows() { 
			return rows;
		}
		
		public int getVelocity() {
			return velocity;
		}
		
		public int getMIDIVoice() {
			return voice;
		}
		
		public int getLoopPoint() {
			return loopPoint;
		}
		
		public int getLayerID() {
			return layerId;
		}
		
		public String getLCDMessage() {
			return lcdMessage;
		}
		
		public SongBuilder done() {
			return parent; 
		}

	}
	
	
	
}
