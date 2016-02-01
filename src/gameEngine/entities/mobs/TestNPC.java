package gameEngine.entities.mobs;

import gameEngine.entities.mobs.players.Player;
import gameEngine.entities.projectiles.TestMobProjectile;
import gameEngine.renderEngine.DisplayManager;
import gameEngine.soundEngine.SoundManager;
import gameEngine.textures.ModelTexture;
import gameEngine.textures.RawModel;

import java.io.FileNotFoundException;
import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

public class TestNPC extends NPC{
		
	protected float timerShooting = 0;
	protected float fireRate = 0.25f;
	protected float reloadTime = 1f;
	protected float timerReload = reloadTime;
	protected int ammo;
	protected int ammoMax = 5;
	
	
	public TestNPC(RawModel rawModel, ModelTexture texture, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(rawModel, texture, position, rotX, rotY, rotZ, scale);
		health = 100;
		hitBoxRadius = 25;
		runSpeed = 25;
		turnSpeed = 150;
		jumpVelocity = 10;
	}
	
	//******OVERIDE MOB UPDATE METHODS HERE*************
	protected void updateMovement() {

		yVelocity += level.getGravity();	
		float terrainHeight = terrain.getTerrainHeight(position.x, position.z);
		if(position.y - .1f <= terrainHeight && yVelocity < 0) {
			yVelocity = 0;
			position.y = terrainHeight;
		}		
		float playerX = 0;
		float playerZ = 0;
		if(level.getPlayers().size() > 0) {
			Player player = level.getPlayers().get(0);
			playerX = player.getPosition().x;
			playerZ = player.getPosition().z;
		}
		float dx = 0;
		float dy = 0;
		float dz = 0;
		if (playerX > getPosition().x + 5f) {
			dx = 1;
		}else if(playerX < getPosition().x - 5f){
			dx = -1;
		}
		if(playerZ > getPosition().z  + 5f) {
			dz = 1;
		}else if(playerZ < getPosition().z - 5f){
			dz = -1;
		}
		dy += level.getGravity();
		if(position.y - .1f <= terrainHeight && dy < 0) {
			dy = 0;
			position.y = terrainHeight;
		}
		
		if ( level.getPlayers().size() > 0)
		{
			float mx = this.getPosition().x;
			float mz = this.getPosition().z;
			Player player = level.getPlayers().get(0);
			float px = player.getPosition().x;
			float pz = player.getPosition().z;
		
			if(px > mx && pz >= mz) {	//FIRST QUADRANT XZ
				rotY = (float) -(Math.toDegrees(Math.atan((pz - mz) / (px - mx))) + 270);
			}
			
			
			if(px <= mx && pz > mz) {	//SECOND QUADRANT XZ
				rotY = (float) -(Math.toDegrees(Math.atan((mx - px) / (pz - mz))) + 0);
			}
			
			if(px < mx && pz <= mz) {	//THIRD QUADRANT XZ
				rotY = (float) -(Math.toDegrees(Math.atan((mz - pz) / (mx - px))) + 90);
			}
			
			if(px >= mx && pz < mz) {	//FOURTH QUADRANT XZ
				rotY = (float) -(Math.toDegrees(Math.atan((px - mx) / (mz - pz))) + 180);
			}
		}
		
		move(runSpeed, dx, dy, dz);
	}
	
	protected void updateShooting() {
		
		if(level.getPlayers().size() > 0) {
			float rotX = 0;
			float rotY = 0;
			float rotZ = 0;
			float mx = this.getPosition().x;
			float my = this.getPosition().y;
			float mz = this.getPosition().z;
			Player player = level.getPlayers().get(0);
			float px = player.getPosition().x;
			float py = player.getPosition().y;
			float pz = player.getPosition().z;
		
			if(px > mx && pz >= mz && py >= my) {	//FIRST QUADRANT XZ
				rotY = (float) (Math.toDegrees(Math.atan((pz - mz) / (px - mx))) + 270);
				rotZ = (float) Math.toDegrees((Math.atan((py - my) / (Math.sqrt((px - mx) * (px - mx) + (pz - mz) * (pz - mz))))));
			}
			if(px > mx && pz >= mz && py < my) {	//FIRST QUADRANT XZ
				rotY = (float) (Math.toDegrees(Math.atan((pz - mz) / (px - mx))) + 270);
				rotZ = (float) -Math.toDegrees((Math.atan((my - py) / (Math.sqrt((px - mx) * (px - mx) + (pz - mz) * (pz - mz))))));
			}
			
			if(px <= mx && pz > mz && py >= my) {	//SECOND QUADRANT XZ
				rotY = (float) (Math.toDegrees(Math.atan((mx - px) / (pz - mz))) + 0);
				rotZ = (float) Math.toDegrees((Math.atan((py - my) / (Math.sqrt((px - mx) * (px - mx) + (pz - mz) * (pz - mz))))));
			}
			if(px <= mx && pz > mz && py < my) {	//SECOND QUADRANT XZ
				rotY = (float) (Math.toDegrees(Math.atan((mx - px) / (pz - mz))) + 0);
				rotZ = (float) -Math.toDegrees((Math.atan((my - py) / (Math.sqrt((px - mx) * (px - mx) + (pz - mz) * (pz - mz))))));
			}
			
			if(px < mx && pz <= mz && py >= my) {	//THIRD QUADRANT XZ
				rotY = (float) (Math.toDegrees(Math.atan((mz - pz) / (mx - px))) + 90);
				rotZ = (float) Math.toDegrees((Math.atan((py - my) / (Math.sqrt((px - mx) * (px - mx) + (pz - mz) * (pz - mz))))));
			}
			if(px < mx && pz <= mz && py < my) {	//THIRD QUADRANT XZ
				rotY = (float) (Math.toDegrees(Math.atan((mz - pz) / (mx - px))) + 90);
				rotZ = (float) -Math.toDegrees((Math.atan((my - py) / (Math.sqrt((px - mx) * (px - mx) + (pz - mz) * (pz - mz))))));
			}
			
			if(px >= mx && pz < mz  && py >= my) {	//FOURTH QUADRANT XZ
				rotY = (float) (Math.toDegrees(Math.atan((px - mx) / (mz - pz))) + 180);
				rotZ = (float) Math.toDegrees((Math.atan((py - my) / (Math.sqrt((px - mx) * (px - mx) + (pz - mz) * (pz - mz))))));
			}
			if(px >= mx && pz < mz && py < my) {	//FOURTH QUADRANT XZ
				rotY = (float) (Math.toDegrees(Math.atan((px - mx) / (mz - pz))) + 180);
				rotZ = (float) -Math.toDegrees((Math.atan((my - py) / (Math.sqrt((px - mx) * (px - mx) + (pz - mz) * (pz - mz))))));
			}
						
			Random rand = new Random();
			int bound = 4;
			int spread = rand.nextInt(bound) - bound/2;
			
			if(timerShooting > fireRate && ammo > 0 && timerReload > reloadTime) {
				timerShooting = 0;
				level.add(new TestMobProjectile(RawModel.rawSphere, ModelTexture.white, 1, this, rotX, -rotY + spread, rotZ));
				ammo--;
				try {
					SoundManager.play("res/wavefiles/Gun_Shot.wav");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			else {
				timerShooting += DisplayManager.getDelta();
			}
			
			if(ammo == 0) {
				timerReload = 0;
				ammo = ammoMax;
			}
			else {
				timerReload += DisplayManager.getDelta();
			}
			
		}
	}
	
	
}
