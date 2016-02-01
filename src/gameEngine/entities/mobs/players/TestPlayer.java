package gameEngine.entities.mobs.players;

import org.lwjgl.util.vector.Vector3f;

import gameEngine.textures.ModelTexture;
import gameEngine.textures.RawModel;

public class TestPlayer extends Player{

	
	
	
	
	public TestPlayer(String Name, boolean isOtherPlayer, RawModel rawModel, ModelTexture texture, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(Name, isOtherPlayer,rawModel, texture, position, rotX, rotY, rotZ, scale);
		health = 100;
		hitBoxRadius = 25;
		runSpeed = 50;
		turnSpeed = 150;
		jumpVelocity = 175;
		ammo = 5;
		autoAmmo = 100;
	}

	
		
		
		
		
		
		
}
