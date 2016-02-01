package gameEngine.entities.projectiles;

import gameEngine.entities.mobs.players.Player;
import gameEngine.renderEngine.DisplayManager;
import gameEngine.textures.ModelTexture;
import gameEngine.textures.RawModel;

public class TestProjectile extends Projectile{
	
	
	private float totalDistance = 0;
	
	
	public TestProjectile(RawModel rawModel, ModelTexture texture, float scale, Player caster) {
		super(rawModel, texture, scale, caster);
		damage = 20;
		speed = 1000;
		range = 1000;
	}
	
	public TestProjectile(String Name, RawModel rawModel, ModelTexture texture, float scale, Player caster) {
		super(Name, rawModel, texture, scale, caster);
		damage = 20;
		speed = 1000;
		range = 1000;
	}
	
	public TestProjectile(String Name, RawModel rawModel, ModelTexture texture, float scale, float posX, float posY, float posZ, float rotX, float rotY, float rotZ) {
		super(Name, rawModel, texture, scale, posX, posY, posZ, rotX, rotY, rotZ);
		damage = 20;
		speed = 1000;
		range = 1000;
	}

	public void update()
	{
		
		float terrainHeight = terrain.getTerrainHeight(position.x, position.z);
		
		float distance = speed * DisplayManager.getDelta();
		float dx = (float) (distance * Math.sin(Math.toRadians(rotY)));
		float dz = -(float) (distance * -Math.cos(Math.toRadians(rotY)));
		float dy = -(float) (distance * -Math.sin(Math.toRadians(rotZ)));
		totalDistance += distance;

		dy += level.getGravity() * totalDistance / 1000;
		
		//*******Terrain Collision*********
		if(position.y <= terrainHeight) remove();
		
		if(totalDistance <= range) {
			this.increasePosition(dx, dy, dz);			
		}
		else {
			remove();
		}
		
	}
	
}
