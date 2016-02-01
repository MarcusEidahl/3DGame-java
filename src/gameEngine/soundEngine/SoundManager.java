package gameEngine.soundEngine;

import static org.lwjgl.openal.AL10.AL_BUFFER;
import static org.lwjgl.openal.AL10.alBufferData;
import static org.lwjgl.openal.AL10.alDeleteBuffers;
import static org.lwjgl.openal.AL10.alGenBuffers;
import static org.lwjgl.openal.AL10.alGenSources;
import static org.lwjgl.openal.AL10.alSourcePlay;
import static org.lwjgl.openal.AL10.alSourcei;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.newdawn.slick.openal.WaveData;


public class SoundManager {


	public static boolean walking = false;
	
	public static void init() throws FileNotFoundException {
		
		try {
			AL.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
	}

	public static void play(String path) throws FileNotFoundException
	{
		if (path == "res/wavefiles/0514.wav")
		{
			walking = true;
		}
		FileInputStream fileStream = new FileInputStream(path);
		BufferedInputStream buffStream = new BufferedInputStream(fileStream);
		WaveData sound = WaveData.create(buffStream);
		int buffer = alGenBuffers();
		alBufferData(buffer, sound.format, sound.data, sound.samplerate);
		sound.dispose();
		int source = alGenSources();
		alSourcei(source, AL_BUFFER, buffer);
		alSourcePlay(source);
		alDeleteBuffers(buffer);
		
	}
	
	
	
	public static void cleanUp()
	{
		AL.destroy();
	}
	


}