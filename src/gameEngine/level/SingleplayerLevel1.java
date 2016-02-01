package gameEngine.level;

import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import gameEngine.entities.SceneryObject;
import gameEngine.entities.mobs.TestNPC;
import gameEngine.renderEngine.DisplayManager;
import gameEngine.terrains.Terrain;
import gameEngine.textures.ModelTexture;
import gameEngine.textures.RawModel;

public class SingleplayerLevel1 extends Level{
	
	private float timer = 0;
	private Random rand = new Random();
	
	public SingleplayerLevel1(Terrain terrain) {
		super(terrain);
		gravity = -5;
	}
	
	protected void spawnInitialMobs() {
       add(new SceneryObject(RawModel.tree1, ModelTexture.green, new Vector3f(600, 22, -35), 0, 0, 0 , 10));
       add(new SceneryObject(RawModel.tree1, ModelTexture.green, new Vector3f(550, 22, -45), 0, 0, 0 , 10));
       add(new SceneryObject(RawModel.tree1, ModelTexture.green, new Vector3f(500, 22, -55), 0, 0, 0 , 10));
       add(new SceneryObject(RawModel.tree1, ModelTexture.green, new Vector3f(700, 22, -65), 0, 0, 0 , 10));
       add(new SceneryObject(RawModel.tree1, ModelTexture.green, new Vector3f(650, 22, -65), 0, 0, 0 , 10));
       add(new SceneryObject(RawModel.tree1, ModelTexture.green, new Vector3f(500, 22, -75), 0, 0, 0 , 10));
       add(new SceneryObject(RawModel.tree1, ModelTexture.green, new Vector3f(725, 22, -85), 0, 0, 0 , 10));
       add(new SceneryObject(RawModel.tree1, ModelTexture.green, new Vector3f(700, 22, -35), 0, 0, 0 , 10));
       add(new SceneryObject(RawModel.tree1, ModelTexture.green, new Vector3f(600, 22, -95), 0, 0, 0 , 10));
       add(new SceneryObject(RawModel.tree1, ModelTexture.green, new Vector3f(700, 22, -85), 0, 0, 0 , 10));
       add(new SceneryObject(RawModel.tree1, ModelTexture.green, new Vector3f(725, 22, -100), 0, 0, 0 , 10));
       add(new SceneryObject(RawModel.tree1, ModelTexture.green, new Vector3f(675, 22, -110), 0, 0, 0 , 10));
       add(new SceneryObject(RawModel.tree1, ModelTexture.green, new Vector3f(740, 22, -120), 0, 0, 0 , 10));
       add(new SceneryObject(RawModel.tree1, ModelTexture.green, new Vector3f(700, 22, -120), 0, 0, 0 , 10));


       //add(new TestNPC(RawModel.darth, ModelTexture.darkRed, new Vector3f(200, 10, -200), 0, 20, 0, 3));
       //add(new TestNPC(RawModel.darth, ModelTexture.darkRed, new Vector3f(200, 10, -250), 0, 20, 0, 2));
	}
	
	protected void updateStatus() {
		if(timer >= 5) {
			float randX = rand.nextFloat();
			float randZ = rand.nextFloat();
			
			//add(new TestNPC(RawModel.darth, ModelTexture.darkRed, new Vector3f(randX * 500, 0, -randZ * 500), 0, 0, 0, 3));
			timer = 0;
		}
		timer += DisplayManager.getDelta();
	}
	
}
