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

//TODO: who's worked on this file? Add yourselves as authors  -Ed

/**
 * Implementation for the Engine, using StandardSong and BasicLayer.
 * @author Edmund Smith
 * @author ...
 */
public class EngineImpl implements Engine {

	private StandardSong song;
	private Voice[] voices;
	private int topmostLayer;
	protected String lcdText;
	
	public EngineImpl() {
		song = new StandardSong();
		voices = new Voice[16];
		//Default voice
		for(int i = 0; i < voices.length; i++) voices[i] = MIDIVoices.getVoice(1);
	}
	
	/** {@inheritDoc} */
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
		song.getLayerArray()[layer].getRow(yLoc).applyXor(1<<xLoc);
	}
	@Override
	public float getTempo() {
		return song.getTempo();
	}

	@Override
	public MutableLayer getCurrentLayer() {
		return song.getLayerArray()[topmostLayer];
	}

	@Override
	public int getCurrentLayerId() {
		return topmostLayer;
	}

	@Override
	public ImmutableLayer getLayer(int i) {
		return song.getLayerArray()[i];
	}

	@Override
	public int getTick() {
		// TODO Timing stuff
		return 0;
	}
	
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

	@Override
	public void setLCDDisplay(String text) {
		lcdText = text;
	}

	@Override
	public String getLCDDisplay() {
		return lcdText;
	}
	
}
