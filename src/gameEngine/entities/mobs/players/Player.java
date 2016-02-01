
package gameEngine.entities.mobs.players;
import java.io.FileNotFoundException;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import gameEngine.MainGameLoop;
import gameEngine.entities.Camera;
import gameEngine.entities.Entity;
import gameEngine.entities.mobs.Mob;
import gameEngine.entities.projectiles.AutomaticProjectile;
import gameEngine.entities.projectiles.Projectile;
import gameEngine.entities.projectiles.TestProjectile;
import gameEngine.renderEngine.DisplayManager;
import gameEngine.soundEngine.SoundManager;
import gameEngine.textures.ModelTexture;
import gameEngine.textures.RawModel;
import gameEngine.textures.TexturedModel;

public abstract class Player extends Mob{
	
	
	protected float currentRunSpeed = 0;
	protected float currentTurnSpeed = 0;
	protected Camera camera;
	protected boolean strafing;
	protected float reloadTime = 0.5f;
	protected float timerReload = reloadTime;
	protected int ammo;
	protected int ammoMax = 5;
	protected int autoAmmo;
	protected int autoAmmoMax = 30;
	protected boolean clicking = true;
	protected boolean rClicking = false;
	protected boolean zoomed = false;
	protected boolean auto = false;
	protected float fireCooldown = .1f;
	private float timer = 0;
	private boolean eDown = false;
	public boolean isOtherPlayer;
	public boolean isConnected = false;
	public String Name;
	public boolean isWinner = false;
	private int Deaths = 0;
	private boolean isJumping = false;
	private double hitBoxRadiusX = 1;
	private double hitBoxRadiusY = 1;
	private double hitBoxRadiusZ = 1;
	
	
	public Player(RawModel rawModel, ModelTexture texture, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		this.model = new TexturedModel(rawModel, texture);
		this.position.x = position.x;
		this.position.y = position.y;
		this.position.z = position.z;
		this.rotX = rotX;
		this.rotY = rotY;	
		this.rotZ = rotZ;
		this.scale = scale;
	}
	
	public Player(String Name,boolean isOtherPlayer, RawModel rawModel, ModelTexture texture, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		this.model = new TexturedModel(rawModel, texture);
		this.position.x = position.x;
		this.position.y = position.y;
		this.position.z = position.z;
		this.rotX = rotX;
		this.rotY = rotY;	
		this.rotZ = rotZ;
		this.scale = scale;
		this.Name = Name;
		this.isOtherPlayer = isOtherPlayer;

		
	}
	
	protected void updateOtherPlayerMovement(float x, float y, float z, float rx, float ry, float rz){
		
		this.position.x = x;
		this.position.y = y;
		this.position.z = z;
		this.rotX = rx;
		this.rotY = ry;
		this.rotZ = rz;
		
		
	}
	
	protected void updateMovement() {
		
		if (!this.isOtherPlayer){
		
		//*****FORWARD AND BACK*********
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			strafing = false;
			currentRunSpeed = runSpeed;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			strafing = false;
			currentRunSpeed = -runSpeed;
		}
		else currentRunSpeed = 0;
		
		//********TURNING**************
		if( camera.getView() == 3){
			if(Keyboard.isKeyDown(Keyboard.KEY_A)){
				strafing = false;
				currentTurnSpeed = turnSpeed;
			}
			else if(Keyboard.isKeyDown(Keyboard.KEY_D)){
				currentTurnSpeed = -turnSpeed; 
				strafing = false;
			}
			else {currentTurnSpeed = 0;}
			} else {
				if(Keyboard.isKeyDown(Keyboard.KEY_A)){
					strafing = true;
					currentRunSpeed = runSpeed;
					
				}
				else if(Keyboard.isKeyDown(Keyboard.KEY_D))
				{
					strafing = true;
					currentRunSpeed = -runSpeed;
				}
				//else currentRunSpeed = 0;
			}
		//*******Jumping************
		float terrainHeight = terrain.getTerrainHeight(position.x, position.z);
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE) && position.y <= terrainHeight && !isJumping) {
			yVelocity += jumpVelocity;
			isJumping = true;
		}else if(position.y > terrainHeight){
			yVelocity += level.getGravity();	
		}
		//System.out.println("y: " + position.y + "       terrainY: " + terrainHeight + "          dy: " + yVelocity);
		//*******Terrain Collision*********
		if(position.y - .1f <= terrainHeight && yVelocity <= 0) {
			yVelocity = 0;
			position.y = terrainHeight;
			isJumping = false;
		}		
		//*******Scenery Collision*********
		List<Projectile> projectiles = level.getProjectiles();
		List<Player> players = level.getPlayers();
		List<Entity> entities = level.getEntity();
		for(int i = 0; i < players.size(); i++){
			Player p = players.get(i);
			Vector3f pos = p.getPosition();
			if( Math.abs((position.x - pos.x)) < hitBoxRadiusX*p.getScale() &&  Math.abs((position.y - pos.y)) < hitBoxRadiusY*p.getScale() &&  Math.abs((position.z - pos.z)) < hitBoxRadiusZ*p.getScale()){
				
			}
		}
		for(int i = 0; i < projectiles.size(); i++){
			Projectile p = projectiles.get(i);
			Vector3f pos = p.getPosition();
			if (Math.abs((position.x - pos.x)) < hitBoxRadiusX*p.getScale() &&  Math.abs((position.y - pos.y)) < hitBoxRadiusY*p.getScale() &&  Math.abs((position.z - pos.z)) < hitBoxRadiusZ*p.getScale()){
				currentRunSpeed = -1;
			}
		}
		for(int i = 0; i < entities.size(); i++){
			Entity e = entities.get(i);
			Vector3f pos = e.getPosition();
			if( Math.abs((position.x - pos.x)) < hitBoxRadiusX*e.getScale() &&  Math.abs((position.y - pos.y)) < hitBoxRadiusY*e.getScale() &&  Math.abs((position.z - pos.z)) < hitBoxRadiusZ*e.getScale()){
				currentRunSpeed = -4;
			}
		}
		move(currentRunSpeed, currentTurnSpeed, yVelocity);
		}
	}
	
	protected void move(float currentRunSpeed, float currentTurnSpeed, float yVelocity) {
		increaseRotation(0, currentTurnSpeed * DisplayManager.getDelta(), 0);
		float dx, dz;
		float distance = currentRunSpeed * DisplayManager.getDelta();
		if(strafing){
			dx = (float) (distance * Math.sin(Math.toRadians(getRotY()+90)));
			dz = (float) (distance * Math.cos(Math.toRadians(getRotY()+90)));
		} else {
			dx = (float) (distance * Math.sin(Math.toRadians(getRotY())));
			dz = (float) (distance * Math.cos(Math.toRadians(getRotY())));
		}
		float dy = yVelocity * DisplayManager.getDelta();
		float nexty = level.getTerrain().getTerrainHeight(this.getPosition().x + dx, this.getPosition().z + dz);
		float thisy = level.getTerrain().getTerrainHeight(this.getPosition().x, this.getPosition().z);
		float slope =  (nexty - thisy) / (float)Math.sqrt(dx * dx + dz * dz);
		
		//out of bounds collision
		if((position.x + dx) < 5 ||(position.x + dx) > level.getTerrain().getSize()-5)
			dx = 0;
		if((position.z + dz) > -5 || (position.z + dz)< -(level.getTerrain().getSize())+5)
			dz = 0;
		//slope collision
		if(slope >= 1.5 && !isJumping) {
			dx = 0;
			dz = 0;
		}
		increasePosition(dx, dy , dz);	
	}
	
	//returns the player's position
		public Vector3f getPosition(float distance)
		{
			return this.position;
		}
		
		public void setCamera(Camera camera)
		{
			this.camera = camera;
		}
		
		public void addDeath(){
			this.Deaths++;
		}
		
		public int getDeaths(){
		return this.Deaths;
		}
		
		public void setIsOtherPlayer(boolean x){
			this.isOtherPlayer = x;
		}
		
		public void setIsConnected(boolean x){
			this.isConnected = x;
		}
		
		public boolean getIsConnected(){
			return this.isConnected;
		}
		
		public Camera getCamera()
		{
			return camera;
		}
		
		public String getName()
		{
			return Name;
		}
		
		
		protected void updateReloading(){

			
			if(Keyboard.isKeyDown(Keyboard.KEY_R)) {
				if (ammo < ammoMax) {

					try {
						SoundManager.play("res/wavefiles/reload.wav");
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}

		public int getAmmo()
		{
			return ammo;
		}
		
		//shoots projectile, may need an array for multiple bullets at a time
		protected void updateShooting() {
				
				if(Mouse.isButtonDown(0) && ammo > 0 && timerReload > reloadTime && !clicking && !auto && !MainGameLoop.esc_menu_open) {
					clicking = true;
					level.add(new TestProjectile(RawModel.rawSphere, ModelTexture.white, 1, this));
					TestProjectile p = new TestProjectile("Projectile" + this.Name,RawModel.rawSphere, ModelTexture.white, 1, this);
					//System.out.println(p.getName() + " " + p.getRotX()+ " " + p.getRotY()+ " " + p.getRotZ());
					level.addNewProj(p);
					try {
						SoundManager.play("res/wavefiles/Gun_Shot.wav");
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					ammo--;
				}
				
				if(Mouse.isButtonDown(0) && ammo > 0 && timerReload > reloadTime && auto && timer > fireCooldown && !MainGameLoop.esc_menu_open){
					clicking = true;
					timer = 0;
					level.add(new AutomaticProjectile(RawModel.rawSphere, ModelTexture.white, 1, this));
					level.addNewProj(new AutomaticProjectile("Projectile" + this.Name,RawModel.rawSphere, ModelTexture.white, 1, this));
					ammo--;
					try {
						SoundManager.play("res/wavefiles/Gun_Shot.wav");
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
				
				
				
				if( this.getCamera().getView() == 1)
				{
					if(Mouse.isButtonDown(1) && !rClicking && !zoomed)
					{
						zoomed = true;
						rClicking = true;
						this.getCamera().setZoom(100);
					}
					
					if(Mouse.isButtonDown(1) && !rClicking && zoomed)
					{
						zoomed = false;
						rClicking = true;
						this.getCamera().setZoom(0);
					}
				}
				
				if (!Mouse.isButtonDown(0))
				{
					 	clicking = false;
				}
				
				if (!Mouse.isButtonDown(1))
				{
					 	rClicking = false;
				}
				
				
				if(Keyboard.isKeyDown(Keyboard.KEY_R)) {
					timerReload = 0;
					ammo = ammoMax;
				}
				else {
					timerReload += DisplayManager.getDelta();
				}
				
				if(Keyboard.isKeyDown(Keyboard.KEY_E) && auto && !eDown) {
					auto = false;
					eDown = true;
				}
				
				if(Keyboard.isKeyDown(Keyboard.KEY_E) && !auto && !eDown) {
					auto = true;
					eDown = true;
				}
				
				if(!Keyboard.isKeyDown(Keyboard.KEY_E)) {
					eDown = false;
				}
				
			 timer += DisplayManager.getDelta();
			}
}
