package gameEngine.level;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector4f;

import gameEngine.entities.Camera;
import gameEngine.entities.Entity;
import gameEngine.entities.Light;
import gameEngine.entities.mobs.players.Player;
import gameEngine.entities.projectiles.Projectile;
import gameEngine.renderEngine.renderers.MasterRenderer;
import gameEngine.terrains.Terrain;

public abstract class Level {

	protected float gravity = 0;
	protected Terrain terrain;

	ArrayList<Player> players = new ArrayList<Player>();
	ArrayList<Entity> entities = new ArrayList<Entity>();
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	ArrayList<Projectile> newProjectiles = new ArrayList<Projectile>();

	public Level(Terrain terrain) {
		this.terrain = terrain;
		spawnInitialMobs();
	}

	public void render(MasterRenderer renderer, Light light, Camera camera, Vector4f clipPlane) {
		renderer.processTerrain(terrain);
		for (int i = 0; i < entities.size(); i++) {
			renderer.processEntity(entities.get(i));
		}
		for (int i = 0; i < players.size(); i++) {
			renderer.processEntity(players.get(i));
		}
		for (int i = 0; i < projectiles.size(); i++) {
			renderer.processEntity(projectiles.get(i));
		}
		renderer.render(light, camera, clipPlane);
	}

	public void update() {
		remove();
		updateStatus();
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update();
		}

		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getHealth() <= 0) {
				players.get(i).remove();
			}

			if (!players.get(i).isOtherPlayer) {
				players.get(i).update();
			}
		}

		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).update();
		}
	}

	protected void remove() {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).isRemoved()) {
				entities.remove(i);
				i--;
			}
		}

		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).isRemoved()) {
				players.remove(i);
				i--;
			}
		}

		for (int i = 0; i < projectiles.size(); i++) {
			if (projectiles.get(i).isRemoved()) {
				projectiles.remove(i);
				i--;
			}
		}
	}

	protected void updateStatus() {

	}

	public void add(Entity e) {
		e.init(this);
		if (e instanceof Player) {
			players.add((Player) e);
		} else if (e instanceof Projectile) {
			projectiles.add((Projectile) e);
		} else {
			entities.add(e);
		}
	}

	public void addNewProj(Projectile p) {
		newProjectiles.add(p);
	}

	public void clearNewProj() {
		newProjectiles.clear();
		;
	}

	protected void spawnInitialMobs() {

	}

	public void reset() {

		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).remove();
		}

		for (int i = 0; i < players.size(); i++) {
			players.get(i).remove();
		}

		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).remove();
		}

		spawnInitialMobs();

	}

	public Terrain getTerrain() {
		return terrain;
	}

	public float getGravity() {
		return gravity;
	}

	public void setGravity(float newGrav) {
		gravity = newGrav;
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}
	
	public List<Entity> getEntity() {
		
		return entities;
	}

	public List<Projectile> getNewProj() {
		return newProjectiles;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public Player getPlayerByName(String Name) {

		for (int i = 0; i < this.getPlayers().size(); i++) {
			if (this.getPlayers().get(i).getName().equalsIgnoreCase(Name)) {
				return this.getPlayers().get(i);
			}
		}
		return null;
	}

	public void setPlayer(Player player, int i) {
		players.add(i, player);
	}

	
}
