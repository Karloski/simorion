package org.simorion.engine;

import org.simorion.common.ImmutableLayer;
import org.simorion.common.MutableLayer;
import org.simorion.common.Song;
import org.simorion.common.Stream;
import org.simorion.common.StreamFormat;
import org.simorion.common.Voice;
import org.simorion.common.stream.SongReader;
import org.simorion.common.stream.SongWriter;

public class EngineImpl implements Engine {

	@Override
	public void setVoice(MutableLayer l, Voice voice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setVelocity(MutableLayer l, int velocity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLoopPoint(MutableLayer l, int loopPoint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTempo(float beatsPerSecond) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLit(int layer, int xLoc, int yLoc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setUnLit(int layer, int xLoc, int yLoc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void toggleLit(int layer, int xLoc, int yLoc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getTempo() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ImmutableLayer getCurrentLayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCurrentLayerId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ImmutableLayer getLayer(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTick() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void load(Song s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Song getSong() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendToStream(SongWriter stream, StreamFormat f) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveFromStream(SongReader stream, StreamFormat f) {
		// TODO Auto-generated method stub
		
	}

}
