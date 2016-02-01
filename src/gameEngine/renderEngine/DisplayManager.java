package gameEngine.renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final int FPS_CAP = 60;
	public static final String TITLE = "3D Game Engine";
	
	private static long lastFrameTime;
	private static float delta;
	private static int FPStimer;
	
	public static void createDisplay() {
		
		ContextAttribs attribs = new ContextAttribs(3,2).withForwardCompatible(true).withProfileCore(true);
		
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle(TITLE);
			FPStimer = 0;
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		lastFrameTime = getCurrentTime();
	}
	
	public static void updateDisplay() {
		
		FPStimer++;
		Display.sync(FPS_CAP);
		if(FPStimer % 60 == 0) {
			FPStimer = 0;
			Display.setTitle(TITLE + "     FPS: " + (int) (1 / DisplayManager.getDelta()));
		}
		Display.update();
		long currentFrameTime = getCurrentTime();
		delta = (currentFrameTime - lastFrameTime) / 1000f;
		lastFrameTime = currentFrameTime;
	}
	
	public static float getDelta() {
		return delta;
	}
	
	private static long getCurrentTime() {
		return Sys.getTime()*1000/Sys.getTimerResolution();
	}
	
	public static void closeDisplay() {
		
		Display.destroy();
		
	}
	
}
