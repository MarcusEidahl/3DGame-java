package gameEngine.level;

import gameEngine.terrains.Terrain;

public class TestingLevel extends Level{
	
	public TestingLevel(Terrain terrain) {
		super(terrain);
		gravity = -5;
	}
	
	protected void spawnInitialMobs() {
       // add(new SceneryObject(RawModel.rawDragonModel, ModelTexture.darkRed, new Vector3f(150,0,-150),0,20,0, 3));
        //add(new SceneryObject(RawModel.rawSphere, ModelTexture.darkRed, new Vector3f(10, 10, -25), 0, 20, 0 , 1));
        //add(new TestNPC(RawModel.rawDragonModel, ModelTexture.white, new Vector3f(200, 10, -200), 0, 20, 0, 3));
        //add(new TestNPC(RawModel.rawDragonModel, ModelTexture.white, new Vector3f(10, 10, -25), 0, 20, 0, 2));
	}
	
}
