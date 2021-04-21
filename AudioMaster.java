package server.sound;

// Here im generating a "clip" that can be a music or a sound that ill atribute to a command

import javax.sound.sampled.*;

import client.engine.EngineHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class AudioMaster {
	
	private List<AudioClip> audioClips;
	
	public AudioMaster (){
		audioClips = new ArrayList<>();
	
	}
	
	public void update(EngineHandler engineHandler) {
		audioClips.forEach(audioClip -> audioClip.update(engineHandler));
		
		List.copyOf(audioClips).forEach(audioClip-> {
			if(audioClip.hasFinishedPlaying()) {
				audioClip.cleanUp();
				audioClips.remove(audioClip);
			}
		});
		
	}
	
	
	//For the music track
	
	public void playMusic(String fileName) {
		final Clip clip = getClip(fileName);
		audioClips.add(new MusicClip(clip));
		
	}
	
	//For the sound effects 
	public void playSound(String fileName) {
		final Clip clip = getClip(fileName);
		audioClips.add(new SoundClip(clip));
		
	}
	
	public Clip getClip(String fileName) {
		final URL soundFile= AudioMaster.class.getResource("/sounds/"+ fileName);
		try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile)){
			final Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.setMicrosecondPosition(0);
			return clip;
		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		
		return null;
	}
}
