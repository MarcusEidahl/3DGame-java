package gameEngine.entities;

import org.lwjgl.util.vector.Vector3f;

import gameEngine.renderEngine.DisplayManager;
import gameEngine.textures.ModelTexture;
import gameEngine.textures.RawModel;
import gameEngine.textures.TexturedModel;

public class SceneryObject extends Entity{
	
	public SceneryObject(RawModel rawModel, ModelTexture texture, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		this.model = new TexturedModel(rawModel, texture);
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}
	
	public void update() {
		increaseRotation(0, 15f * DisplayManager.getDelta(), 0);
	}

}
