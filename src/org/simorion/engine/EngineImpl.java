package org.simorion.engine;

import java.util.Random;

import org.simorion.common.ImmutableLayer;
import org.simorion.common.MutableLayer;
import org.simorion.common.Song;
import org.simorion.common.SongBuilder;
import org.simorion.common.SoundSystem;
import org.simorion.common.StandardSong;
import org.simorion.common.Voice;
import org.simorion.common.stream.InsufficientSongDataException;
import org.simorion.common.stream.SongFormat;
import org.simorion.common.stream.SongReader;
import org.simorion.common.stream.SongWriter;
import org.simorion.common.stream.StreamFailureException;
import org.simorion.common.stream.UnsupportedSongFormatException;
import org.simorion.ui.view.GUI;

//TODO: who's worked on this file? Add yourselves as authors  -Ed

/**
 * Implementation for the Engine, using StandardSong and BasicLayer.
 * @author Edmund Smith
 * @author ...
 */
public class EngineImpl implements Engine {

	private final int instanceID;
	private StandardSong song;
	private Voice[] voices;
	private int topmostLayer;
	protected String lcdText;
	protected MasterSlaveServer masterSlaveServer;
	SoundSystem soundSystem = SoundSystem.getInstance();
	
	
	public EngineImpl() {
		instanceID = new Random().nextInt();
		song = new StandardSong();
		voices = new Voice[16];
		//Default voice
		for(int i = 0; i < voices.length; i++) voices[i] = MIDIVoices.getVoice(1);
		
		masterSlaveServer = new MasterSlaveServer(this);
		masterSlaveServer.start();
		
	}
	
	/** {@inheritDoc} */
	@Override
	public void setVoice(MutableLayer l, Voice voice) {
		l.setVoice(voice);
		soundSystem.setVoices(l.getLayerNumber(), voice.getMidiVoice());
	}

	@Override
	public void setVelocity(MutableLayer l, byte velocity) {
		l.setVelocity(velocity);
		soundSystem.setVoices(l.getLayerNumber(), velocity);
	}

	@Override
	public void setLoopPoint(MutableLayer l, byte loopPoint) {
		l.setLoopPoint(loopPoint);
		soundSystem.setLoopPoint(loopPoint);
	}

	@Override
	public void setTempo(float beatsPerSecond) {
		song.setTempo(beatsPerSecond);
		soundSystem.setLoopSpeed(beatsPerSecond);
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
		} catch (StreamFailureException e) {
			lcdText = e.getLocalizedMessage();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void receiveFromStream(SongReader stream, SongFormat f) {
		SongBuilder sb = new SongBuilder();
		try {
			stream.readTo(f, sb);
			song.loadFrom(sb);
			lcdText = "Loaded song from network";
			GUI.getInstance().update();
		} catch (UnsupportedSongFormatException e) {
			lcdText = e.getLocalizedMessage();
			e.printStackTrace();
			//TODO
		} catch (InsufficientSongDataException e) {
			lcdText = e.getLocalizedMessage();
			e.printStackTrace();
			//TODO
		} catch (StreamFailureException e) {
			lcdText = e.getLocalizedMessage();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setLCDDisplay(String text) {
		if(!text.equals(lcdText)) {
			lcdText = text;
			GUI.getInstance().update();
		}
	}

	@Override
	public String getLCDDisplay() {
		return lcdText;
	}

	@Override
	public int getInstanceID() {
		return instanceID;
	}
	
}
