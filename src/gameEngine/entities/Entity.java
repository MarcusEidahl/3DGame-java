package gameEngine.entities;

import org.lwjgl.util.vector.Vector3f;

import gameEngine.level.Level;
import gameEngine.terrains.Terrain;
import gameEngine.textures.TexturedModel;

public abstract class Entity {
	
	protected Level level = null;
	protected Terrain terrain;
	protected TexturedModel model;
	protected Vector3f position = new Vector3f(0, 0, 0);
	protected float rotX, rotY, rotZ;
	protected float scale;
	protected boolean removed = false;
	
	public void increasePosition(float dx, float dy, float dz) {
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
	}
	
	public void increaseRotation(float dx, float dy, float dz) {
		this.rotX += dx;
		this.rotY += dy;
		this.rotZ += dz;
	}

	public TexturedModel getModel() {
		return model;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getRotX() {
		return rotX;
	}

	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public void setRotY(float rotY) {
		this.rotY = rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
	
	public void update() {
		
	}
	
	public void remove() {
		removed = true;
	}
	
	public boolean isRemoved() {
		if(removed) return true;
		return false;
	}
	
	public void setIsRemoved(boolean x) {
		removed = x;
	}
	
	public void init(Level level) {
		this.level = level;
		terrain = level.getTerrain();
	}
	

	
}
