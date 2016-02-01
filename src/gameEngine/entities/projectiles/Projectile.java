package gameEngine.entities.projectiles;

import gameEngine.entities.Camera;
import gameEngine.entities.Entity;
import gameEngine.entities.mobs.Mob;
import gameEngine.entities.mobs.players.Player;
import gameEngine.textures.ModelTexture;
import gameEngine.textures.RawModel;
import gameEngine.textures.TexturedModel;

public abstract class Projectile extends Entity{
	
	protected Mob caster;
	protected Camera camera;
	protected float velocity;
	protected float yVelocity;
	protected String Name;
	
	protected int damage;
	protected float speed;
	protected float range;
	
	public Projectile(RawModel rawModel, ModelTexture texture, float scale, Player caster) {
		this.model = new TexturedModel(rawModel, texture);
		this.position.x = caster.getPosition().x;
		this.position.y = caster.getPosition().y + 9.85f;
		this.position.z = caster.getPosition().z;
		this.rotX = caster.getRotX();
		this.rotY = caster.getRotY();
		if (caster.getCamera().getView() == 1)
		{
			this.rotZ = -caster.getCamera().getPitch();
		} else
		{
			this.rotZ = caster.getRotZ();
		}
		this.scale = scale;
		this.caster = caster;
	}
	
	public Projectile(String Name, RawModel rawModel, ModelTexture texture, float scale, Player caster) {
		this.model = new TexturedModel(rawModel, texture);
		this.position.x = caster.getPosition().x;
		this.position.y = caster.getPosition().y + 9.85f;
		this.position.z = caster.getPosition().z;
		this.rotX = caster.getRotX();
		this.rotY = caster.getRotY();
		if (caster.getCamera().getView() == 1)
		{
			this.rotZ = -caster.getCamera().getPitch();
		} else
		{
			this.rotZ = caster.getRotZ();
		}
		this.scale = scale;
		this.caster = caster;
		this.Name = Name;
	}
	
	public Projectile(RawModel rawModel, ModelTexture texture, float scale, Mob caster, float rotX, float rotY, float rotZ) {
		this.model = new TexturedModel(rawModel, texture);
		this.position.x = caster.getPosition().x;
		this.position.y = caster.getPosition().y + 9.85f;
		this.position.z = caster.getPosition().z;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		this.caster = caster;
	}
	
	public Projectile(String Name, RawModel rawModel, ModelTexture texture, float scale, float posX, float posY, float posZ, float rotX, float rotY, float rotZ) {
		this.model = new TexturedModel(rawModel, texture);
		this.position.x = posX;
		this.position.y = posY; //+ 9.85f;
		this.position.z = posZ;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		//this.caster = caster;
		this.Name = Name;
	}
	
	public Mob getCaster() {
		return caster;
	}
	
	public String getName() {
		return Name;
	}
	
	public int getDamage() {
		return damage;
	}
}
