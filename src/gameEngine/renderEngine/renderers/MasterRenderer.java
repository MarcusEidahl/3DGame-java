package gameEngine.renderEngine.renderers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

import gameEngine.entities.Camera;
import gameEngine.entities.Entity;
import gameEngine.entities.Light;
import gameEngine.renderEngine.fileLoading.Loader;
import gameEngine.renderEngine.shaders.EntityShader;
import gameEngine.renderEngine.shaders.TerrainShader;
import gameEngine.terrains.Terrain;
import gameEngine.textures.TexturedModel;

public class MasterRenderer {
	
	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 2000;
	
	private static final float RED = 0.7f;
	private static final float GREEN = 0.7f;
	private static final float BLUE = 0.8f;
	
	private Matrix4f projectionMatrix;
	
	private EntityShader entityShader = new EntityShader();
	private EntityRenderer entityRenderer;
	
	private TerrainShader terrainShader = new TerrainShader();
	private TerrainRenderer terrainRenderer;
	
	private SkyboxRenderer skyboxRenderer;
	
	
	private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	private List<Terrain> terrains = new ArrayList<Terrain>();
	
	public MasterRenderer(Loader loader) {
		enableCulling();
		createProjectionMatrix();
		entityRenderer = new EntityRenderer(entityShader, projectionMatrix);
		terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
		skyboxRenderer = new SkyboxRenderer(loader, projectionMatrix);
	}
	
	public static void enableCulling() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}	
	
	public static void disableCulling() {
		GL11.glDisable(GL11.GL_CULL_FACE);
	}
	
	public void render(Light light, Camera camera, Vector4f clipPlane) {
		prepare();
		
		entityShader.start();
		entityShader.loadClipPlane(clipPlane);
		entityShader.loadSkyColor(RED, GREEN, BLUE);
		entityShader.loadLight(light);
		entityShader.loadViewMatrix(camera);
		entityRenderer.render(entities);
		entityShader.stop();
		
		terrainShader.start();
		terrainShader.loadClipPlane(clipPlane);
		terrainShader.loadSkyColor(RED, GREEN, BLUE);
		terrainShader.loadLight(light);
		terrainShader.loadViewMatrix(camera);
		terrainRenderer.render(terrains);
		terrainShader.stop();
		
		skyboxRenderer.render(camera, RED, GREEN, BLUE);
		
		terrains.clear();
		entities.clear();
	}
	
	private void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(RED, GREEN, BLUE, 1);

	}
	
	private void createProjectionMatrix() {
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;
		
		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;
	}
	
	 public Matrix4f getProjectionMatrix() {
	        return this.projectionMatrix;
	    }
	
	public void processEntity(Entity entity) {
		TexturedModel entityModel = entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		if(batch != null) {
			batch.add(entity);
		}else{
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
		}
	}
	
	public void processTerrain(Terrain terrain) {
		terrains.add(terrain);
	}
	
	public void cleanUp() {
		entityShader.cleanUp();
		terrainShader.cleanUp();
	}
}


