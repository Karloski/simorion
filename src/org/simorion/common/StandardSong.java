package org.simorion.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

import org.simorion.common.SongBuilder.AddLayer;
import org.simorion.common.SongBuilder.AddRow;
import org.simorion.common.util.Util;
import org.simorion.engine.BasicLayer;
import org.simorion.engine.MIDIVoices;

public class StandardSong implements Song {

	private BasicLayer[] layers;
	private float tempo;
	
	public StandardSong() {
		layers = new BasicLayer[16];
		tempo = 1;
	}
	
	public void loadFrom(final SongBuilder sb) {
		layers = new BasicLayer[sb.getLayerCount()];
		
		for(int i = 0; i < layers.length; i++) {
			Collection<MutableRow> rows = new ArrayList<MutableRow>(sb.getRows());
			AddLayer al = sb.layers.get(i);
			for(Util.Pair<MutableRow, AddRow> pair : 
				Util.zip(rows, al.getRows())) {
				pair.left.applyMask(0, (int) pair.right.mask);
			}
			
			layers[i] = new BasicLayer(rows, 
					MIDIVoices.getVoice(al.getMIDIVoice()),
					(byte) al.getVelocity(),
					al.getLayerID(), al.getLoopPoint(), al.getLCDMessage());
			
		}
		
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
		return tempo;
	}

	@Override
	public byte getBPM() {
		return (byte)(tempo * 60);
	}

	@Override
	public void setBPM(byte bpm) {
		tempo = bpm/60;
	}

	@Override
	public void setTempo(float bps) {
		if(Float.isNaN(bps) || Float.isInfinite(bps) || bps <= 0)
			throw new IllegalArgumentException("beats per second must be a" +
					" positive number");
		tempo = bps;
	}
	
	public MutableLayer[] getLayerArray() {
		return layers;
	}
	
}
