package org.simorion.engine;

import java.util.Random;

import org.simorion.common.ImmutableLayer;
import org.simorion.common.MutableLayer;
import org.simorion.common.Song;
import org.simorion.common.SongBuilder;
import org.simorion.common.StandardSong;
import org.simorion.common.Voice;
import org.simorion.common.stream.InsufficientSongDataException;
import org.simorion.common.stream.SongFormat;
import org.simorion.common.stream.SongReader;
import org.simorion.common.stream.SongWriter;
import org.simorion.common.stream.StreamFailureException;
import org.simorion.common.stream.UnsupportedSongFormatException;
import org.simorion.sound.PlayableSound;
import org.simorion.sound.SoundThread;
import org.simorion.ui.view.GUI;

//TODO: who's worked on this file? Add yourselves as authors  -Ed

/**
 * Implementation for the Engine, using StandardSong and BasicLayer.
 * @author Edmund Smith
 */
public class EngineImpl implements Engine {

	private int instanceID;
	private StandardSong song;
	private Voice[] voices;
	private int topmostLayer, tick;
	protected String lcdText;
	protected MasterSlaveServer masterSlaveServer;
	protected boolean isPlaying;
	
	public SoundThread soundThread;
	
	public EngineImpl() {
		instanceID = new Random().nextInt();
		song = new StandardSong();
		voices = new Voice[16];
		//Default voice
		for(int i = 0; i < voices.length; i++) voices[i] = MIDIVoices.getVoice(1);
		
		masterSlaveServer = new MasterSlaveServer(this);
		masterSlaveServer.start();
		soundThread = new SoundThread(song, this);
		isPlaying = false;
		new Thread(soundThread, "Sound Thread").start();
	}
	
	/** {@inheritDoc} */
	@Override
	public void setVoice(MutableLayer l, Voice voice) {
		l.setVoice(voice);
	}

	@Override
	public void setVelocity(MutableLayer l, byte velocity) {
		l.setVelocity(velocity);
	}

	@Override
	public void setLoopPoint(MutableLayer l, byte loopPoint) {
		//Loop point is universal, rather than per-layer
		for (MutableLayer layer : song.getLayers()) {
			layer.setLoopPoint(loopPoint);
		}
	}

	@Override
	public void setTempo(float beatsPerSecond) {
		song.setTempo(beatsPerSecond);
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
	public void updateTick(int tick) {
		this.tick = tick;
	}

	@Override
	public int getTick() {
		return tick % 720720;
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
			e.printStackTrace();
		}
	}

	@Override
	public void receiveFromStream(SongReader stream, SongFormat f) {
		SongBuilder sb = new SongBuilder();
		try {
			stream.readTo(f, sb);
			song.loadFrom(sb);
			setLCDDisplay("Loaded song from network");
		} catch (UnsupportedSongFormatException e) {
			setLCDDisplay(e.getLocalizedMessage());
			e.printStackTrace();
		} catch (InsufficientSongDataException e) {
			setLCDDisplay(e.getLocalizedMessage());
			e.printStackTrace();
		} catch (StreamFailureException e) {
			setLCDDisplay(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void setLCDDisplay(String text) {
		if(!text.equals(lcdText)) {
			lcdText = text.substring(0, Math.min(32, text.length()));
		}
		GUI.getInstance().update();
	}

	@Override
	public String getLCDDisplay() {
		return lcdText;
	}

	@Override
	public int getInstanceID() {
		return instanceID;
	}

	@Override
	public void reset() {
		instanceID = new Random().nextInt();
		song = new StandardSong();
		voices = new Voice[16];
		
		//Default voices
		for(int i = 0; i < voices.length; i++) voices[i] = MIDIVoices.getVoice(1);
		topmostLayer = 0;
		lcdText = "";
		
		soundThread.updateSong(song);
		isPlaying = false;
	}

	@Override
	public void setBPM(byte bpm) {
		song.setBPM(bpm);
	}
	
	@Override
	public byte getBPM() {
		return song.getBPM();
	}
	
	@Override
	public void enqueueSound(PlayableSound sound) {
		soundThread.enqueueSound(sound);
	}

	@Override
	public void startPlaying() {
		isPlaying = true;
	}

	@Override
	public void stopPlaying() {
		isPlaying = false;
	}

	@Override
	public boolean isPlaying() {
		return isPlaying;
	}
	
	
}
