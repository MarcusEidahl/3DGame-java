package gameEngine.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import gameEngine.MainGameLoop;
import gameEngine.entities.mobs.players.Player;

public class Camera {

	private static final float MOUSE_WHEEL_SENSITIVITY = .1f; 
	
	private float distanceFromPlayer = 0; 
	private float angleAroundPlayer = 0;
	
	private Vector3f position = new Vector3f(0, 0, 0);
	private float pitch = 0;
	private float yaw;
	private float roll;
	
	private Player player;
	private int view = 1;
	private long lastZoomTime = 0;
	
	public Camera(Player player) {
		this.position.x = position.x;
		this.position.y = position.y;
		this.position.z = position.z;
		this.setPlayer(player);
	}
	
	public void move() {
		if(Mouse.isButtonDown(0) && Mouse.isButtonDown(1)) {
			angleAroundPlayer = 0;
			if (view == 3)
			{
				pitch = 20;
			}else {
				pitch = 0;
			}
			yaw = 0;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_Z) && ((System.currentTimeMillis() - lastZoomTime) > 1000 )&& (view == 1))
		{
			view = 3;
			lastZoomTime = System.currentTimeMillis();
			distanceFromPlayer = 50;
			pitch = 10;
			Mouse.setGrabbed(false);
		} else if(Keyboard.isKeyDown(Keyboard.KEY_Z) && ((System.currentTimeMillis() - lastZoomTime) > 1000 ) && (view == 3))
		{
			lastZoomTime = System.currentTimeMillis();
			view = 1;
			pitch = 0;
			distanceFromPlayer = 0;
			Mouse.setGrabbed(true);
		}
		calculateZoom();
		calculatePitch();
		calculateAngleAroundPlayer();
		float dz = calculateDz();
		float dy = calculateDy();
		calculateCameraPosition(dz, dy);

	}
	
	private void calculateCameraPosition(float dz, float dy) {
		if ( view == 1)
		{
			getPlayer().setRotY(getPlayer().getRotY() + angleAroundPlayer);
			angleAroundPlayer = 0;
		}
		float theta = getPlayer().getRotY() + angleAroundPlayer;
		float offsetX = (float) (dz * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (dz * Math.cos(Math.toRadians(theta)));
		position.x = getPlayer().getPosition().x - offsetX;
		position.z = getPlayer().getPosition().z - offsetZ;
		position.y = getPlayer().getPosition().y + 10 + dy;
		yaw = 180 - theta;
	}
	
	private float calculateDz() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	
	private float calculateDy() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}
	
	private void calculateZoom() {
		
		//float zoomLevel = Mouse.getDWheel() * MOUSE_WHEEL_SENSITIVITY;
		//distanceFromPlayer -= zoomLevel; //zoomLevel;
		
		//only zooming in 1st person
		if (view == 1)
		{
			float zoomLevel = Mouse.getDWheel() * MOUSE_WHEEL_SENSITIVITY;
			distanceFromPlayer -= zoomLevel; //zoomLevel;
			
			//zoom range
			//distanceFromPlayer = Math.min(0, distanceFromPlayer);
			//distanceFromPlayer = Math.max(-50, distanceFromPlayer);
		}
	}
	
	private void calculatePitch() {
		if(Mouse.isButtonDown(0) && view == 3) {
			float pitchChange = Mouse.getDY() * MOUSE_WHEEL_SENSITIVITY;
			pitch -= pitchChange;
		} else if((view == 1) && !MainGameLoop.esc_menu_open){
			float pitchChange = Mouse.getDY() * MOUSE_WHEEL_SENSITIVITY;
			pitch -= pitchChange;
		}
		
		if (pitch > 90)
		{
			pitch = 90;
		}
		
		if (pitch < -90)
		{
			pitch = -90;
		}
	}
	
	private void calculateAngleAroundPlayer() {
		if(Mouse.isButtonDown(0) && view == 3) {
			float angleChange = Mouse.getDX() * 0.3f;
			angleAroundPlayer -= angleChange;
		} else if((view == 1) && !MainGameLoop.esc_menu_open) {
			float angleChange = Mouse.getDX() * 0.3f;
			angleAroundPlayer -= angleChange;
		}
		
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}
	
	public void invertPitch() {
		pitch = -pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	public int getView() {
		return view;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void setZoom(int zoomAmt)
	{
		distanceFromPlayer = -zoomAmt;
	}
}
