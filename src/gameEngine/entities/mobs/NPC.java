package gameEngine.entities.mobs;

import org.lwjgl.util.vector.Vector3f;

import gameEngine.textures.ModelTexture;
import gameEngine.textures.RawModel;
import gameEngine.textures.TexturedModel;

public abstract class NPC extends Mob{

	public NPC(RawModel rawModel, ModelTexture texture, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		this.model = new TexturedModel(rawModel, texture);
		this.position.x = position.x;
		this.position.y = position.y;
		this.position.z = position.z;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}
}
