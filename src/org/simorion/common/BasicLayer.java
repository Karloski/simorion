package org.simorion.common;

import java.util.Collection;

public class BasicLayer implements Layer {

	WritableRow[] rows;
	Voice voice;
	byte velocity;
	int layerNumber;
	int loopPoint;
	
	BasicLayer(Collection<WritableRow> rows, Voice voice, byte velocity, int layerNumber, int loopPoint) {
		this.rows = rows.toArray(new WritableRow[0]); //Allocates its own array
		this.voice = voice;
		this.velocity = velocity;
		this.layerNumber = layerNumber;
		this.loopPoint = loopPoint;
	}
	
	@Override
	public int getLayerNumber() {
		return layerNumber;
	}

	@Override
	public Voice getVoice() {
		return voice;
	}

	@Override
	public byte getVelocity() {
		return velocity;
	}

	@Override
	public int getLoopPoint() {
		return loopPoint;
	}

	@Override
	public Row getRow(int i) {
		if(i < 0 || i >= rows.length) {
			throw new RuntimeException("Error: index "+i
					+" is outside the range 0 to "+rows.length);
		}
		return rows[i];
	}

	
	
}
