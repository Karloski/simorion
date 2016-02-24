package org.simorion.engine;

import java.io.IOException;

import org.simorion.common.ImmutableLayer;
import org.simorion.common.MutableLayer;
import org.simorion.common.Song;
import org.simorion.common.SongBuilder;
import org.simorion.common.StandardSong;
import org.simorion.common.Voice;
import org.simorion.common.stream.SongFormat;
import org.simorion.common.stream.SongReader;
import org.simorion.common.stream.SongWriter;

public class EngineImpl implements Engine {

	private StandardSong song;
	private Voice[] voices;
	private int topmostLayer;
	
	public EngineImpl() {
		song = new StandardSong();
		voices = new Voice[16];
		//Default voice
		for(int i = 0; i < voices.length; i++) voices[i] = MIDIVoices.getVoice(1);
	}
	
	@Override
	public void setVoice(MutableLayer l, Voice voice) {
		l.setVoice(voice);
		//TODO: soundSystem.setVoice(l, voice) or similar
	}

	@Override
	public void setVelocity(MutableLayer l, byte velocity) {
		l.setVelocity(velocity);
		//TODO: soundSystem.setVelocity(l, voice)
	}

	@Override
	public void setLoopPoint(MutableLayer l, byte loopPoint) {
		l.setLoopPoint(loopPoint);
		//TODO: update sound system
	}

	@Override
	public void setTempo(float beatsPerSecond) {
		song.setTempo(beatsPerSecond);
		//TODO: update sound system
	}

	@Override
	public void setLit(int layer, int xLoc, int yLoc) {
		song.getLayerArray()[layer].getRow(yLoc).applyMask(-1, 1 << xLoc);
	}

	@Override
	public void setUnLit(int layer, int xLoc, int yLoc) {
		song.getLayerArray()[layer].getRow(yLoc).applyMask(~(1 << xLoc), 0);
	}

	@Override
	public void toggleLit(int layer, int xLoc, int yLoc) {
		if(song.getLayerArray()[layer].getRow(yLoc).isLit(xLoc)) {
			setUnLit(layer, xLoc, yLoc);
		} else {
			setLit(layer, xLoc, yLoc);
		}
	}

	@Override
	public float getTempo() {
		return song.getTempo();
	}

	@Override
	public ImmutableLayer getCurrentLayer() {
		return song.getLayerArray()[topmostLayer];
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

	/*@Override
	public void load(Song s) {
		//TODO
	}*/

	@Override
	public Song getSong() {
		return song;
	}
	
	@Override
	public void setTopmostLayer(int layerID) {
		topmostLayer = layerID;
	}

	@Override
	public void sendToStream(SongWriter stream, SongFormat f) {
		try {
			stream.write(f, song);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void receiveFromStream(SongReader stream, SongFormat f) {
		SongBuilder sb = new SongBuilder();
		try {
			stream.readTo(f, sb);
		} catch (IOException e) {
			//TODO
			e.printStackTrace();
		}
	}
	
}
