package gameEngine.renderEngine.renderers;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import gameEngine.renderEngine.fileLoading.Loader;
import gameEngine.renderEngine.shaders.GUIShader;
import gameEngine.textures.GUITexture;
import gameEngine.textures.RawModel;
import gameEngine.toolbox.Maths;

public class GUIRenderer {

	private final RawModel model;
	private GUIShader shader;
	
	public GUIRenderer(Loader loader) {
		float [] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
		model = loader.loadtoVAO(positions);
		shader = new GUIShader();
	}
	
	public void render(List<GUITexture> guis) {
		shader.start();
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		for(GUITexture gui : guis) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.getTexture());
			Matrix4f matrix = Maths.createTransformationMatrix(gui.getPosition(), gui.getScale());
			shader.loadTransformation(matrix);
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, model.getVertexCount());
		}
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}
	
	public void cleanUp() {
		shader.cleanUp();
	}
}


