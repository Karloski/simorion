package org.simorion.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

import org.simorion.common.SongBuilder.AddLayer;
import org.simorion.common.SongBuilder.AddRow;
import org.simorion.common.util.Util;
import org.simorion.common.util.Util.Pair;
import org.simorion.engine.BasicLayer;
import org.simorion.engine.BasicRow;
import org.simorion.engine.MIDIVoices;

public class StandardSong implements Song {

	private BasicLayer[] layers;
	private float tempo;
	private byte bpm;
	
	public StandardSong() {
		layers = new BasicLayer[16];
		for(int i = 0; i < 16; i++) {
			Collection<MutableRow> rows = new ArrayList<MutableRow>();
			for(int j = 0; j < 16; j++) {
				rows.add(new BasicRow());
			}
			layers[i] = new BasicLayer(rows, MIDIVoices.getVoice(1), (byte)0, 0, 0);
		}
		tempo = 1;
	}
	
	public void loadFrom(final SongBuilder sb) {
		layers = new BasicLayer[sb.getLayerCount()];
		
		for(int i = 0; i < layers.length; i++) {
			Collection<MutableRow> rows = new ArrayList<MutableRow>(sb.getRows());
			for(int r = 0; r < sb.getRows(); r++) {
				rows.add(new BasicRow());
			}
			AddLayer al = sb.layers.get(i);
			for(Util.Pair<MutableRow, AddRow> pair : 
				Util.zip(rows, al.getRows())) {
				pair.left.applyMask(0, (int) pair.right.mask);
			}
			
			layers[i] = new BasicLayer(rows, 
					MIDIVoices.getVoice(al.getMIDIVoice()),
					(byte) al.getVelocity(),
					al.getLayerID(), al.getLoopPoint());
			
		}
		
		//tempo = sb.layers.get(0).getBPM() / 60f;
		bpm = sb.getBPM();
		
		Arrays.sort(layers, new Comparator<BasicLayer>() {
			@Override
			public int compare(BasicLayer o1, BasicLayer o2) {
				return  o1.layerNumber < o2.layerNumber ? -1 :
						o1.layerNumber > o2.layerNumber ?  1 :
							0;
			}});
	}

	@Override
	public Collection<? extends MutableLayer> getLayers() {
		return Arrays.asList(layers);
	}

	@Override
	public float getTempo() {
		return bpm / 60;
	}

	@Override
	public byte getBPM() {
		return bpm;
	}

	@Override
	public void setBPM(byte bpm) {
		this.bpm = bpm;
	}

	@Override
	public void setTempo(float bps) {
		if(Float.isNaN(bps) || Float.isInfinite(bps) || bps <= 0)
			throw new IllegalArgumentException("beats per second must be a" +
					" positive number");
		tempo = bps;
		bpm = (byte) (tempo * 60F);
	}
	
	public MutableLayer[] getLayerArray() {
		return layers;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof StandardSong) {
			StandardSong s = (StandardSong) o;
			for(Pair<BasicLayer,BasicLayer> pair : Util.zip(
					Util.iterable(layers),
					Util.iterable(s.layers))) {
				if(!pair.left.equals(pair.right)) return false;
			}
			if(bpm != s.bpm) return false;
			return true;
		} else
			return false;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("StandardSong {[");
		for(BasicLayer bl : layers) {
			sb.append(bl.toString()).append(",");
		}
		sb.append("], tempo = ").append(tempo);
		sb.append("}");
		return sb.toString();
	}
	
}
