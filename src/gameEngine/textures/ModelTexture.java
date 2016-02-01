package gameEngine.textures;

import gameEngine.renderEngine.fileLoading.Loader;

public class ModelTexture {

	private static Loader loader = Loader.loader;
	
	public static ModelTexture darkRed = new ModelTexture(loader.loadModelTexture("DarkRed"), 4, 1, false, false);
	public static ModelTexture white = new ModelTexture(loader.loadModelTexture("White"), 4, 1, false, false);
	public static ModelTexture black = new ModelTexture(loader.loadModelTexture("Darth_Sidious_D"), 1, .1f, false, false);
	public static ModelTexture green = new ModelTexture(loader.loadModelTexture("Green"), 1, .1f, false, false);
	
	private int textureID;
	private float shineDamper;
	private float reflectivity;
	
	private boolean transparent = false;
	private boolean fakeLighting = false;
	
	public ModelTexture(int id, float shineDamper, float reflectivity, boolean transparent, boolean fakeLighting) {
		this.textureID = id;
		this.shineDamper = shineDamper;
		this.reflectivity = reflectivity;
		this.transparent = transparent;
		this.fakeLighting = fakeLighting;
	}
	
	public boolean isTransparent() {
		return transparent;
	}

	public void setTransparent(boolean hasTransparency) {
		this.transparent = hasTransparency;
	}

	public boolean isFakeLighting() {
		return fakeLighting;
	}

	public void setFakeLighting(boolean fakeLighting) {
		this.fakeLighting = fakeLighting;
	}

	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}


	public int getID() {
		return this.textureID;
	}
}
