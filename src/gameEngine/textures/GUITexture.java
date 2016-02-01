package gameEngine.textures;

import org.lwjgl.util.vector.Vector2f;

import gameEngine.renderEngine.fileLoading.Loader;

public class GUITexture {
	
    private static Loader loader = Loader.loader;	         

	public static GUITexture HUD = new GUITexture(loader.loadGUITexture("TestGUI"), new Vector2f(0f, -.8f), new Vector2f(0.25f, 0.25f));

	public static GUITexture Background = new GUITexture(loader.loadGUITexture("Main_Menu_Screen"), new Vector2f(0f, 0f), new Vector2f(1f, 1f));
	public static GUITexture hostOrJoin = new GUITexture(loader.loadGUITexture("Host_or_Join"), new Vector2f(0f, 0f), new Vector2f(1f, 1f));
	public static GUITexture lobby = new GUITexture(loader.loadGUITexture("Lobby"), new Vector2f(0f, 0f), new Vector2f(1f, 1f));
	public static GUITexture Controls = new GUITexture(loader.loadGUITexture("controls"), new Vector2f(0f, 0f), new Vector2f(1f, 1f));
	
	public static GUITexture Controls_button = new GUITexture(loader.loadGUITexture("controls_button"), new Vector2f(0f, -.45f), new Vector2f(.15f, .075f));
	public static GUITexture Controls_button_mo = new GUITexture(loader.loadGUITexture("controls_button_mo"), new Vector2f(0f, -.45f), new Vector2f(.15f, .075f));
	
	public static GUITexture Play_button = new GUITexture(loader.loadGUITexture("play"), new Vector2f(0f, 0.15f), new Vector2f(.15f, .075f));
	public static GUITexture Play_button_mo = new GUITexture(loader.loadGUITexture("play_mo"), new Vector2f(0f, 0.15f), new Vector2f(.15f, .075f));
	
	public static GUITexture Singleplayer_button = new GUITexture(loader.loadGUITexture("single"), new Vector2f(0f, 0.15f), new Vector2f(.15f, .075f));
	public static GUITexture Singleplayer_button_mo = new GUITexture(loader.loadGUITexture("single_mo"), new Vector2f(0f, 0.15f), new Vector2f(.15f, .075f));
	
	public static GUITexture Multiplayer_button = new GUITexture(loader.loadGUITexture("multi"), new Vector2f(0f, -0.15f), new Vector2f(.15f, .075f));
	public static GUITexture Multiplayer_button_mo = new GUITexture(loader.loadGUITexture("multi_mo"), new Vector2f(0f, -0.15f), new Vector2f(.15f, .075f));
	
	public static GUITexture Test_button = new GUITexture(loader.loadGUITexture("test"), new Vector2f(0f, -0.15f), new Vector2f(.15f, .075f));
	public static GUITexture Test_button_mo = new GUITexture(loader.loadGUITexture("test_mo"), new Vector2f(0f, -0.15f), new Vector2f(.15f, .075f));
	
	public static GUITexture Main_menu_button = new GUITexture(loader.loadGUITexture("Main_menu_button"), new Vector2f(0f, 0f), new Vector2f(.15f, .075f));
	public static GUITexture Main_menu_button_corner = new GUITexture(loader.loadGUITexture("Main_menu_button"), new Vector2f(-.84f, .9f), new Vector2f(.15f, .075f));
	
	public static GUITexture Exit_button = new GUITexture(loader.loadGUITexture("exit"), new Vector2f(0f, -0.75f), new Vector2f(.15f, .075f));
	public static GUITexture Exit_button_esc = new GUITexture(loader.loadGUITexture("exit_esc"), new Vector2f(0f, -0.25f), new Vector2f(.15f, .075f));
	public static GUITexture Exit_button_mo = new GUITexture(loader.loadGUITexture("exit_mo"), new Vector2f(0f, -0.75f), new Vector2f(.15f, .075f));
	
	
	public static GUITexture ammo_0 = new GUITexture(loader.loadGUITexture("ammo_0"), new Vector2f(0.125f, -.91f), new Vector2f(.07f, .066f));
	public static GUITexture ammo_1 = new GUITexture(loader.loadGUITexture("ammo_1"), new Vector2f(0.125f, -.91f), new Vector2f(.07f, .066f));
	public static GUITexture ammo_2 = new GUITexture(loader.loadGUITexture("ammo_2"), new Vector2f(0.125f, -.91f), new Vector2f(.07f, .066f));
	public static GUITexture ammo_3 = new GUITexture(loader.loadGUITexture("ammo_3"), new Vector2f(0.125f, -.91f), new Vector2f(.07f, .066f));
	public static GUITexture ammo_4 = new GUITexture(loader.loadGUITexture("ammo_4"), new Vector2f(0.125f, -.91f), new Vector2f(.07f, .066f));
	public static GUITexture ammo_5 = new GUITexture(loader.loadGUITexture("ammo_5"), new Vector2f(0.125f, -.91f), new Vector2f(.07f, .066f));
	
	public static GUITexture health_0 = new GUITexture(loader.loadGUITexture("health_0"), new Vector2f(0.125f, -.666f), new Vector2f(.07f, .066f));
	public static GUITexture health_20 = new GUITexture(loader.loadGUITexture("health_20"), new Vector2f(0.125f, -.666f), new Vector2f(.07f, .066f));
	public static GUITexture health_40 = new GUITexture(loader.loadGUITexture("health_40"), new Vector2f(0.125f, -.666f), new Vector2f(.07f, .066f));
	public static GUITexture health_60 = new GUITexture(loader.loadGUITexture("health_60"), new Vector2f(0.125f, -.666f), new Vector2f(.07f, .066f));
	public static GUITexture health_80 = new GUITexture(loader.loadGUITexture("health_80"), new Vector2f(0.125f, -.666f), new Vector2f(.07f, .066f));
	public static GUITexture health_100 = new GUITexture(loader.loadGUITexture("health_100"), new Vector2f(0.125f, -.666f), new Vector2f(.07f, .066f));
	
	public static GUITexture retina_1 = new GUITexture(loader.loadGUITexture("Retina"), new Vector2f(-0.01f, 0f), new Vector2f(.005f, .005f));
	public static GUITexture retina_3 = new GUITexture(loader.loadGUITexture("Retina"), new Vector2f(-0.005f, 0.3f), new Vector2f(.005f, .005f));
	public static GUITexture esc_menu = new GUITexture(loader.loadGUITexture("esc_menu"), new Vector2f(0f, 0f), new Vector2f(.5f, .5f));
	
	public static GUITexture hosting = new GUITexture(loader.loadGUITexture("hosting"), new Vector2f(-.025f, 0f), new Vector2f(.15f, .075f));
	public static GUITexture joining = new GUITexture(loader.loadGUITexture("joined"), new Vector2f(-.025f, 0f), new Vector2f(.15f, .075f));
	public static GUITexture winner = new GUITexture(loader.loadGUITexture("winner"), new Vector2f(0f, .85f), new Vector2f(.75f, .375f));

	
	
	private int texture;
	private Vector2f position;
	private Vector2f scale;
	
	public GUITexture(int texture, Vector2f position, Vector2f scale) {
		this.texture = texture;
		this.position = position;
		this.scale = scale;
	}

	public int getTexture() {
		return texture;
	}

	public Vector2f getPosition() {
		return position;
	}

	public Vector2f getScale() {
		return scale;
	}
	
	
}
