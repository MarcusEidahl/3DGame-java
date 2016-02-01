package gameEngine.textures;

import gameEngine.renderEngine.fileLoading.Loader;

public class TerrainTexture {
	
	private int textureID;
    private static Loader loader = Loader.loader;	         

    //actual terrain textures
	public static TerrainTexture bridge = new TerrainTexture(loader.loadTerrainTexture("Bridge"));
	public static TerrainTexture grass = new TerrainTexture(loader.loadTerrainTexture("Grass"));
	public static TerrainTexture gravel = new TerrainTexture(loader.loadTerrainTexture("Gravel"));
	public static TerrainTexture lava = new TerrainTexture(loader.loadTerrainTexture("Lava"));
	public static TerrainTexture sand = new TerrainTexture(loader.loadTerrainTexture("Sand"));
	public static TerrainTexture grassHR1 = new TerrainTexture(loader.loadTerrainTexture("HighResGrass1"));
	public static TerrainTexture sandHR1 = new TerrainTexture(loader.loadTerrainTexture("HighResSand1"));
	public static TerrainTexture stoneHR1 = new TerrainTexture(loader.loadTerrainTexture("HighResCobblestone"));
	public static TerrainTexture beachHR1 = new TerrainTexture(loader.loadTerrainTexture("HighResBeach1"));

	
	//texture maps
	public static TerrainTexture map1Texture = new TerrainTexture(loader.loadTerrainTexture("Map1Texture"));
	public static TerrainTexture waterTestMapTexture = new TerrainTexture(loader.loadTerrainTexture("WaterTestTexture"));
	
	
	public TerrainTexture(int textureID) {
		this.textureID = textureID;
	}
	
	public int getTextureID() {
		return textureID;
	}
}
