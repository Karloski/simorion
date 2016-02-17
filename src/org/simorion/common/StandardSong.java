package org.simorion.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

import org.simorion.common.SongBuilder.AddLayer;
import org.simorion.common.SongBuilder.AddRow;
import org.simorion.common.util.Util;

public class StandardSong implements Song {

	private BasicLayer[] layers;
	
	
	public StandardSong() {
		layers = new BasicLayer[16];
	}
	
	public void loadFrom(final SongBuilder sb) {
		layers = new BasicLayer[sb.getLayerCount()];
		
		for(int i = 0; i < layers.length; i++) {
			Collection<WritableRow> rows = new ArrayList<WritableRow>(sb.getRows());
			AddLayer al = sb.layers.get(i);
			for(Util.Pair<WritableRow, AddRow> pair : 
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
	public Collection<? extends ReadonlyLayer> getLayers() {
		return Arrays.asList(layers);
	}

	@Override
	public float getTempo() {
		return 0; //TODO: is tempo per-layer or per-song?
	}

	@Override
	public byte getBPM() {
		// TODO: is BPM per-layer or per-song?
		return 0;
	}
	
}
