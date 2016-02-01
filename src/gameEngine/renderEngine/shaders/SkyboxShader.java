package gameEngine.renderEngine.shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import gameEngine.entities.Camera;
import gameEngine.renderEngine.DisplayManager;
import gameEngine.toolbox.Maths;

public class SkyboxShader extends ShaderProgram{
 
    private static final String VERTEX_FILE = "/gameEngine/renderEngine/shaders/skyboxVertexShader.txt";
    private static final String FRAGMENT_FILE = "/gameEngine/renderEngine/shaders/skyboxFragmentShader.txt";
     
    private static final float ROTY_SPEED = .1f;
    
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_fogColor;
    
    private float rotY = 0;
     
    public SkyboxShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
     
    public void loadProjectionMatrix(Matrix4f matrix){
        super.loadMatrix(location_projectionMatrix, matrix);
    }
 
    public void loadViewMatrix(Camera camera){
        Matrix4f matrix = Maths.createViewMatrix(camera);
        matrix.m30 = 0;
        matrix.m31 = 0;
        matrix.m32 = 0;
        rotY += ROTY_SPEED * DisplayManager.getDelta();
        Matrix4f.rotate((float)Math.toRadians(rotY), new Vector3f(0, 1, 0) , matrix, matrix);
        super.loadMatrix(location_viewMatrix, matrix);
    }
    
    public void loadFogColor(float r, float g, float b) {
    	super.loadVector(location_fogColor, new Vector3f(r, g, b));;
    }
     
    protected void getAllUniformLocations() {
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_fogColor = super.getUniformLocation("fogColor");

    }
 
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }
    
    
 
}