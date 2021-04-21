package server.sound;

import javax.sound.sampled.Clip;

import client.engine.EngineHandler;

public class MusicClip extends AudioClip{

	public MusicClip(Clip clip) {
		super(clip);
		
	}

	@Override
	protected float getVolume(EngineHandler engineHandler) {
		return engineHandler.getMusicVolume();
	}
	
	

}
