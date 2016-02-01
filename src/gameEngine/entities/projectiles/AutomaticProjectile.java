package gameEngine.entities.projectiles;

import gameEngine.entities.mobs.players.Player;
import gameEngine.renderEngine.DisplayManager;
import gameEngine.textures.ModelTexture;
import gameEngine.textures.RawModel;

public class AutomaticProjectile extends Projectile{
	
	
	private float totalDistance = 0;
	
	
	public AutomaticProjectile(RawModel rawModel, ModelTexture texture, float scale, Player caster) {
		super(rawModel, texture, scale, caster);
		damage = 10;
		speed = 1000;
		range = 1000;
	}
	
	public AutomaticProjectile(String name, RawModel rawModel, ModelTexture texture, float scale, Player caster) {
		super(name, rawModel, texture, scale, caster);
		damage = 10;
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