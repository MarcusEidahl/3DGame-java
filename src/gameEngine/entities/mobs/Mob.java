package gameEngine.entities.mobs;

import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import gameEngine.entities.Entity;
import gameEngine.entities.projectiles.Projectile;
import gameEngine.renderEngine.DisplayManager;

public abstract class Mob extends Entity{

	//*****Assign these values in the constructor for specific mob types**********
	protected float health;
	protected float hitBoxRadius;
	protected float runSpeed;
	protected float turnSpeed;
	protected float jumpVelocity;
	
	//Note: This is for gravity
	protected float yVelocity = 0;
	
	
	public void update() {
		updateMovement();
		updateShooting();
		updateAbilities();
		updateStatus();
	}
	
	public void updateIsOtherPlayer(){
		updateOtherPlayerMovement();
	}
	

	protected void move(float currentRunSpeed, float currentTurnSpeed, float yVelocity) {
		increaseRotation(0, currentTurnSpeed * DisplayManager.getDelta(), 0);
		float distance = currentRunSpeed * DisplayManager.getDelta();
		float dx = (float) (distance * Math.sin(Math.toRadians(getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(getRotY())));
		float dy = yVelocity * DisplayManager.getDelta();
		float slope = distance / dy;
		//System.out.println(slope);
		if(slope >= 1) increasePosition(dx, 0 , dz);
		else increasePosition(dx, dy , dz);
	}
	
	protected void move(float currentRunSpeed, float dx, float dy, float dz) {
		float distance = currentRunSpeed * DisplayManager.getDelta();
		increasePosition(dx * distance, dy * distance, dz * distance);
	}
	
	protected void updateMovement() {
		
	}
	
	protected void updateOtherPlayerMovement(){
		
	}
	
	protected void updateShooting() {
		
	}
	
	protected void updateAbilities() {
		
	}
	
	protected void updateReloading() {
		
	}
	
	protected void updateStatus() {
		//*******PROJECTILE + MOB COLLISION***********
		List<Projectile> projectiles = level.getProjectiles();
		for(int i = 0; i < projectiles.size(); i++){
			Projectile p = projectiles.get(i);
			Vector3f pos = p.getPosition();
			if(Math.sqrt((position.x - pos.x) * (position.x - pos.x) + 
					(position.y - pos.y) * (position.y - pos.y) + 
					(position.z - pos.z) * (position.z - pos.z)) < hitBoxRadius
					&& p.getCaster() != this){
				health -= p.getDamage();
				p.remove();
			}
		}
		if(health <= 0) {
			remove();
		}
		
		
	}
	
	public float getHealth(){
		return health;
	}
	
	public void setHealth(float x){
		health = x;
	}
}
