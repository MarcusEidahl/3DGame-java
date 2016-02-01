package gameEngine.textures;

import gameEngine.renderEngine.fileLoading.OBJFileLoader;

public class RawModel {

	private int vaoID;
	private int vertexCount;
	private static OBJFileLoader loader = OBJFileLoader.loader;

    public static RawModel rawDragonModel = loader.loadOBJ("dragon");
    public static RawModel rawSphere = loader.loadOBJ("Sphere");
    public static RawModel darth = loader.loadOBJ("Darth_Sidious");
    //public static RawModel rawSlide = OBJLoader.loadObjModel("Slide", loader);
    public static RawModel tree1 = loader.loadOBJ("Tree1");

	public RawModel(int vaoID, int vertexCount) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}

	public int getVaoID() {
		return vaoID;
	}

	public int getVertexCount() {
		return vertexCount;
	}

}
