package server.sound;

import javax.sound.sampled.Clip;
import client.engine.EngineHandler;

public class SoundClip extends AudioClip{

	public SoundClip(Clip clip) {
		super(clip);
	}

	@Override
	protected float getVolume(EngineHandler engineHandler) {
		return engineHandler.getMusicVolume();
	}
	

}
