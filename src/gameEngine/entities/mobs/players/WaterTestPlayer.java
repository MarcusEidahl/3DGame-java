package gameEngine.entities.mobs.players;


import org.lwjgl.util.vector.Vector3f;

import gameEngine.renderEngine.DisplayManager;
import gameEngine.textures.ModelTexture;
import gameEngine.textures.RawModel;

public class WaterTestPlayer extends Player{

//	private float fireRate = 0.07f;
//	private float timer = 0;
		
	private float timer = 0;
	
	public WaterTestPlayer(RawModel rawModel, ModelTexture texture, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(rawModel, texture, position, rotX, rotY, rotZ, scale);
		health = 100;
		hitBoxRadius = 25;
		runSpeed = 75;
		turnSpeed = 150;
		jumpVelocity = 175;
		ammo = 5;
	}
	
	protected void updateStatus() {
		if(timer >= 5) {
			health += 2;
			timer = 0;
		}
		timer += DisplayManager.getDelta();
		
	}
	
//	protected void updateShooting() {
//		
//		timer += DisplayManager.getDelta();
//		
//		if(Mouse.isButtonDown(1) && timer >= fireRate) {
//			timer = 0;
//			level.add(new TestProjectile(RawModel.rawSphere, ModelTexture.white, 1, this));
//		}
//	
//	}
}
