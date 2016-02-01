package gameEngine.renderEngine.shaders;

import org.lwjgl.util.vector.Matrix4f;

public class GUIShader extends ShaderProgram{
	
	    private static final String VERTEX_FILE = "/gameEngine/renderEngine/shaders/guiVertexShader.txt";
	    private static final String FRAGMENT_FILE = "/gameEngine/renderEngine/shaders/guiFragmentShader.txt";
	     
	    private int location_transformationMatrix;
	 
	    public GUIShader() {
	        super(VERTEX_FILE, FRAGMENT_FILE);
	    }
	     
	    public void loadTransformation(Matrix4f matrix){
	        super.loadMatrix(location_transformationMatrix, matrix);
	    }
	 
	    protected void getAllUniformLocations() {
	        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
	    }
	 
	    protected void bindAttributes() {
	        super.bindAttribute(0, "position");
	    }
}
