package server.sound;

import javax.sound.sampled.Clip;
import client.engine.EngineHandler;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.FloatControl.Type;



public abstract class AudioClip {
	
	private final Clip clip;
	
	public AudioClip(Clip clip) {
		this.clip = clip;
		clip.start();
	}
	
	public void update(EngineHandler engineHandler) {
		final FloatControl control = (FloatControl) clip.getControl(Type.MASTER_GAIN);
		control.setValue(getVolume(engineHandler));	
	}
	
	protected abstract float getVolume(EngineHandler engineHandler);
	
	//r we done? j pense pas
	
	public boolean hasFinishedPlaying() {
		return !clip.isRunning();
		
	}
	public void cleanUp() {
		clip.close();
	}
}
