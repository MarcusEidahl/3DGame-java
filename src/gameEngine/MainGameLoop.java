
package gameEngine;

import gameEngine.entities.Camera;
import gameEngine.entities.Light;
import gameEngine.entities.mobs.players.Player;
import gameEngine.entities.mobs.players.TestPlayer;
import gameEngine.entities.mobs.players.WaterTestPlayer;
import gameEngine.level.Level;
import gameEngine.level.SingleplayerLevel1;
import gameEngine.level.TestingLevel;
import gameEngine.level.WaterTestingLevel;
import gameEngine.multiplayer.ClientReceive;
import gameEngine.multiplayer.ClientSend;
import gameEngine.multiplayer.DatabaseConnection;
import gameEngine.multiplayer.ServerReceive;
import gameEngine.multiplayer.ServerSend;
import gameEngine.renderEngine.DisplayManager;
import gameEngine.renderEngine.fileLoading.Loader;
import gameEngine.renderEngine.renderers.GUIRenderer;
import gameEngine.renderEngine.renderers.MasterRenderer;
import gameEngine.renderEngine.renderers.WaterRenderer;
import gameEngine.renderEngine.shaders.WaterShader;
import gameEngine.renderEngine.water.WaterFrameBuffers;
import gameEngine.renderEngine.water.WaterTile;
import gameEngine.soundEngine.SoundManager;
import gameEngine.terrains.Terrain;
import gameEngine.textures.GUITexture;
import gameEngine.textures.ModelTexture;
import gameEngine.textures.RawModel;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class MainGameLoop {

	public static boolean esc_menu_open = false;
	private static boolean ESC_pressed = false;
	private static String Name;
	private static int KillsToWin = 1;

	public enum GameState {
		MAIN_MENU, TEST_LEVEL, WATER_TEST, MULTIPLAYER, MULTIPLAYER_LOBBY, MULTIPLAYER_LOBBY_SELECT, SINGLEPLAYER, EXIT, HOST_OR_JOIN, SINGLEPLAYER_MODE_SELECT, CONTROLS
	}

	public enum PlayerStatus {
		HOST, NON_HOST
	}

	public static void main(String[] args) throws FileNotFoundException {

		// ***INITS****
		Name = "Player_" + Sys.getTime();

		DisplayManager.createDisplay();
		SoundManager.init();
		Loader loader = Loader.loader;
		Light light = new Light(new Vector3f(3000, 2000, 2000), new Vector3f(1, 1, 1));
		//DatabaseConnection.init(Name);

		Level level;
		Player playerMe;
		Camera camera;
		boolean clicking = false;
		boolean firstTimeThru = true, firstTimeThru2 = true, firstTimeThru3 = true;
		int isHost = -1;
		int counter = 0;

		// SoundManager.play("res/wavefiles/Dont_Fear_The_Reaper.wav");
		playerMe = new TestPlayer(Name, false, RawModel.darth, ModelTexture.black,
				new Vector3f((int) (Math.random() * 500), 30, (int) (Math.random() * -500)), 0, 0, 0, 3);
		// player = new TestPlayer(RawModel.rawDragonModel,
		// ModelTexture.darkRed, new Vector3f(250, 10, -250), 0, 0, 0, 1);
		camera = new Camera(playerMe);
		camera.setPlayer(playerMe);
		playerMe.setCamera(camera);
		level = new TestingLevel(Terrain.map1);
		level.add(playerMe);

		MasterRenderer renderer = new MasterRenderer(loader);

		GameState gameState = GameState.MAIN_MENU;
		PlayerStatus playerStatus = PlayerStatus.HOST;

		/*****
		 * GUI CODE******** 1. all this does is render a 2D texture ontop of
		 * everything else 2. this should probably all end up in the level
		 * class, or just somewhere else but idk yet 3. I probably should wrap
		 * this up into the master renderer...but it broke on my first attempt
		 * and im not feeling like messing with it now 4. eventually I should
		 * get these to be able to have transparent backgrounds fairly easily
		 */
		List<GUITexture> HUD = new ArrayList<GUITexture>();
		HUD.add(GUITexture.HUD);

		List<GUITexture> mainMenuGUIs = new ArrayList<GUITexture>();

		List<GUITexture> hostOrJoinGUIs = new ArrayList<GUITexture>();
		hostOrJoinGUIs.add(GUITexture.Main_menu_button_corner);
		hostOrJoinGUIs.add(GUITexture.hostOrJoin);

		List<GUITexture> multiplayerGUIs = new ArrayList<GUITexture>();
		multiplayerGUIs.add(GUITexture.Background);

		List<GUITexture> lobbyGUIs = new ArrayList<GUITexture>();

		List<GUITexture> lobby_selectGUIs = new ArrayList<GUITexture>();
		lobby_selectGUIs.add(GUITexture.Background);

		List<GUITexture> singleplayer_selectGUIs = new ArrayList<GUITexture>();

		List<GUITexture> control_GUI = new ArrayList<GUITexture>();
		control_GUI.add(GUITexture.Main_menu_button_corner);
		control_GUI.add(GUITexture.Controls);

		GUIRenderer guiRenderer = new GUIRenderer(loader);

		// ***TESTING WATER CODE**********
		WaterFrameBuffers waterBuffers = new WaterFrameBuffers();
		WaterShader waterShader = new WaterShader();
		WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(),
				waterBuffers);
		List<WaterTile> waterTiles = new ArrayList<WaterTile>();
		WaterTile water1 = new WaterTile(400, -350, -11);
		waterTiles.add(water1);

		// ********MAIN GAME LOOP**********
		displayloop: while (!Display.isCloseRequested()) {

			switch (gameState) {

			case MAIN_MENU:
				// DatabaseConnection.setPlayerConnectedNo(Name);
				mainMenuGUIs.removeAll(mainMenuGUIs);

				if ((Mouse.getX() < 730) && (Mouse.getX() > 550) && (Mouse.getY() < 435) && (Mouse.getY() > 390)) {
					if (!mainMenuGUIs.contains(GUITexture.Singleplayer_button_mo)) {
						mainMenuGUIs.add(GUITexture.Singleplayer_button_mo);
					}
					if (Mouse.isButtonDown(0) && !clicking) {
						clicking = true;
						mainMenuGUIs.removeAll(mainMenuGUIs);
						gameState = GameState.SINGLEPLAYER_MODE_SELECT;
					}
				}

				if ((Mouse.getX() < 730) && (Mouse.getX() > 550) && (Mouse.getY() < 330) && (Mouse.getY() > 285)) {

					if (!mainMenuGUIs.contains(GUITexture.Multiplayer_button_mo)) {
						mainMenuGUIs.add(GUITexture.Multiplayer_button_mo);
					}

					if (Mouse.isButtonDown(0) && !clicking) {
						clicking = true;
						mainMenuGUIs.removeAll(mainMenuGUIs);
						gameState = GameState.HOST_OR_JOIN;
					}
				} else {
					mainMenuGUIs.remove(GUITexture.Multiplayer_button_mo);
				}

				if (Mouse.getX() < 100 && Mouse.getY() > 500 && Mouse.isButtonDown(0) && !clicking) {
					level = new WaterTestingLevel(Terrain.waterTestMap);
					playerMe = new WaterTestPlayer(RawModel.rawDragonModel, ModelTexture.black,
							new Vector3f(250, 10, -250), 0, 135, 0, 1);
					level.add(playerMe);
					camera = new Camera(playerMe);
					playerMe.setCamera(camera);
					gameState = GameState.WATER_TEST;
					clicking = true;
					Mouse.setGrabbed(true);
				}

				if ((Mouse.getX() < 730) && (Mouse.getX() > 550) && (Mouse.getY() < 220) && (Mouse.getY() > 170)) {
					if (!mainMenuGUIs.contains(GUITexture.Controls_button_mo)) {
						mainMenuGUIs.add(GUITexture.Controls_button_mo);
					}
					if (Mouse.isButtonDown(0) && !clicking) {
						clicking = true;
						gameState = GameState.CONTROLS;
					}
				}

				if ((Mouse.getX() < 730) && (Mouse.getX() > 550) && (Mouse.getY() < 110) && (Mouse.getY() > 60)) {
					if (!mainMenuGUIs.contains(GUITexture.Exit_button_mo)) {
						mainMenuGUIs.add(GUITexture.Exit_button_mo);
					}
					if (Mouse.isButtonDown(0) && !clicking) {
						clicking = true;
						guiRenderer.cleanUp();
						gameState = GameState.EXIT;
					}
				} else {
					mainMenuGUIs.remove(GUITexture.Exit_button_mo);
				}
				
				if (playerMe.isWinner) {
					mainMenuGUIs.add(GUITexture.winner);
				}
				mainMenuGUIs.add(GUITexture.Multiplayer_button);
				mainMenuGUIs.add(GUITexture.Singleplayer_button);
				mainMenuGUIs.add(GUITexture.Controls_button);
				mainMenuGUIs.add(GUITexture.Exit_button);
				mainMenuGUIs.add(GUITexture.Background);

				// testing
				if (Keyboard.isKeyDown(Keyboard.KEY_L)) {
					System.out.println("X: " + Mouse.getX() + "       Y: " + Mouse.getY());
				}

				level.render(renderer, light, camera, new Vector4f(0, 0, 0, 0));
				guiRenderer.render(mainMenuGUIs);
				DisplayManager.updateDisplay();

				break;
			case SINGLEPLAYER_MODE_SELECT:
				singleplayer_selectGUIs.removeAll(singleplayer_selectGUIs);
				
				if ((Mouse.getX() < 730) && (Mouse.getX() > 550) && (Mouse.getY() < 430) && (Mouse.getY() > 385)) {
					if (!singleplayer_selectGUIs.contains(GUITexture.Play_button_mo)) {
						singleplayer_selectGUIs.add(GUITexture.Play_button_mo);
					}
					if(Mouse.isButtonDown(0) && !clicking) {
						clicking = true;
						level = new SingleplayerLevel1(Terrain.map1);
						waterTiles.clear();
						water1 = new WaterTile(250, -500, -20);
						waterTiles.add(water1);
						gameState = GameState.SINGLEPLAYER;
						level.reset();
						playerMe = new TestPlayer(Name, false, RawModel.darth, ModelTexture.black,
								new Vector3f(15, 10, -15), 0, 135, 0, 3);
						camera.setPlayer(playerMe);
						playerMe.setCamera(camera);
						level.add(playerMe);
						Mouse.setGrabbed(true);
					}
				}

				if ((Mouse.getX() < 730) && (Mouse.getX() > 550) && (Mouse.getY() < 330) && (Mouse.getY() > 285)) {
					if (!singleplayer_selectGUIs.contains(GUITexture.Test_button_mo)) {
						singleplayer_selectGUIs.add(GUITexture.Test_button_mo);
					}
					if (Mouse.isButtonDown(0) && !clicking) {
						clicking = true;
						level = new TestingLevel(Terrain.map1);
						gameState = GameState.TEST_LEVEL;
						level.reset();
						playerMe = new TestPlayer(Name, false, RawModel.darth, ModelTexture.black,
								new Vector3f(250, 10, -250), 0, 0, 0, 3);
						camera.setPlayer(playerMe);
						playerMe.setCamera(camera);
						level.add(playerMe);
						Mouse.setGrabbed(true);
					}
				}

				if ((Mouse.getX() < 190) && (Mouse.getX() > 10) && (Mouse.getY() < 710) && (Mouse.getY() > 660)) {
					if (Mouse.isButtonDown(0) && !clicking) {
						clicking = true;
						gameState = GameState.MAIN_MENU;
					}
				}

				// testing
				if (Keyboard.isKeyDown(Keyboard.KEY_L)) {
					System.out.println("X: " + Mouse.getX() + "       Y: " + Mouse.getY());
				}

				singleplayer_selectGUIs.add(GUITexture.Test_button);
				singleplayer_selectGUIs.add(GUITexture.Play_button);
				singleplayer_selectGUIs.add(GUITexture.Main_menu_button_corner);
				singleplayer_selectGUIs.add(GUITexture.Background);

				level.render(renderer, light, camera, new Vector4f(0, 0, 0, 0));
				guiRenderer.render(singleplayer_selectGUIs);
				DisplayManager.updateDisplay();
				break;
			case CONTROLS:

				if ((Mouse.getX() < 730) && (Mouse.getX() > 550) && (Mouse.getY() < 330) && (Mouse.getY() > 285)) {
					if (!singleplayer_selectGUIs.contains(GUITexture.Test_button_mo)) {
						singleplayer_selectGUIs.add(GUITexture.Test_button_mo);
					}
					if (Mouse.isButtonDown(0) && !clicking) {
						clicking = true;
						gameState = GameState.MAIN_MENU;

					}
				}

				if ((Mouse.getX() < 190) && (Mouse.getX() > 10) && (Mouse.getY() < 710) && (Mouse.getY() > 660)) {
					if (Mouse.isButtonDown(0) && !clicking) {
						clicking = true;
						gameState = GameState.MAIN_MENU;
					}
				}

				level.render(renderer, light, camera, new Vector4f(0, 0, 0, 0));
				guiRenderer.render(control_GUI);
				DisplayManager.updateDisplay();
				break;

			case TEST_LEVEL:
				if (Keyboard.isKeyDown(Keyboard.KEY_T)) {
					level.reset();
					playerMe = new TestPlayer(Name, false, RawModel.darth, ModelTexture.black,
							new Vector3f(250, 10, -250), 0, 0, 0, 3);
					camera.setPlayer(playerMe);
					playerMe.setCamera(camera);
					level.add(playerMe);
				}

				if ((Mouse.getX() < 730) && (Mouse.getX() > 550) && (Mouse.getY() < 385) && (Mouse.getY() > 335)
						&& esc_menu_open) {
					if (Mouse.isButtonDown(0) && !clicking) {
						clicking = true;
						gameState = GameState.MAIN_MENU;
						esc_menu_open = false;
					}
				}

				if ((Mouse.getX() < 730) && (Mouse.getX() > 550) && (Mouse.getY() < 300) && (Mouse.getY() > 250)
						&& esc_menu_open) {
					if (Mouse.isButtonDown(0) && !clicking) {
						clicking = true;
						gameState = GameState.EXIT;
					}
				}

				if ((Mouse.getX() < 730) && (Mouse.getX() > 550) && (Mouse.getY() < 300) && (Mouse.getY() > 250)
						&& esc_menu_open) {
					if (Mouse.isButtonDown(0) && !clicking) {
						clicking = true;
						gameState = GameState.EXIT;
					}
				}

				level.update();
				camera.move();

				level.render(renderer, light, camera, new Vector4f(0, 0, 0, 0));
				updateHUD(HUD, playerMe, level, clicking, gameState, camera);
				guiRenderer.render(HUD);
				DisplayManager.updateDisplay();
				break;

			case MULTIPLAYER:
				// Multi-player loop, right now an exact copy of testing
				// environment, will need to be getting, sending and doing all
				// server stuff here
				if (firstTimeThru && isHost == 1) {
					playerMe.setIsConnected(true);
					ArrayList<String> lobbyIPs = DatabaseConnection.getLobbyIPs();
					System.out.println(lobbyIPs.toString());
					System.out.println(lobbyIPs.size());

					for (int i = 0; i < lobbyIPs.size(); i++) {

						String currentName = DatabaseConnection.getLobbyName(lobbyIPs.get(i));
						level.add(new TestPlayer(currentName, true, RawModel.darth, ModelTexture.black,
								new Vector3f((int) (Math.random() * 500), 30, (int) (Math.random() * -500)), 0, 0, 0, 3));

						new ServerReceive(playerMe, Name, currentName, level.getPlayerByName(currentName), level)
								.start();

						if (!currentName.equalsIgnoreCase(Name)) {
							new ServerSend(Name, 54321 + i + 1, (TestPlayer) playerMe, (TestingLevel) level).start();
						}

					}

					firstTimeThru = false;
					DatabaseConnection.setHostStatusToGame();
				}

				// if (firstTimeThru && isHost == 0){
				//
				// }

				if (Keyboard.isKeyDown(Keyboard.KEY_T)) {
					level.reset();
					playerMe = new TestPlayer(Name, false, RawModel.rawDragonModel, ModelTexture.darkRed,
							new Vector3f(250, 10, -250), 0, 0, 0, 1);
					camera.setPlayer(playerMe);
					playerMe.setCamera(camera);
					level.add(playerMe);
				}

				if ((Mouse.getX() < 730) && (Mouse.getX() > 550) && (Mouse.getY() < 400) && (Mouse.getY() > 350)
						&& esc_menu_open) {
					if (Mouse.isButtonDown(0) && !clicking) {
						clicking = true;
						gameState = GameState.MAIN_MENU;
						esc_menu_open = false;
					}
				}

				if ((Mouse.getX() < 730) && (Mouse.getX() > 550) && (Mouse.getY() < 300) && (Mouse.getY() > 250)
						&& esc_menu_open) {
					if (Mouse.isButtonDown(0) && !clicking) {
						clicking = true;
						gameState = GameState.EXIT;
					}
				}
				if (counter % 300 == 0){
				if (DatabaseConnection.getPlayerKills(Name) == KillsToWin) {
					gameState = GameState.MAIN_MENU;
					playerMe.isWinner = true;
					DatabaseConnection.setHostStatusToLobby();
				}
				if(DatabaseConnection.getHostStatus().equalsIgnoreCase("Lobby")){
						gameState = GameState.MAIN_MENU;
					}
				counter = 0;
				}
				counter++;

				level.update();
				camera.move();

				level.render(renderer, light, camera, new Vector4f(0, 0, 0, 0));
				updateHUD(HUD, playerMe, level, clicking, gameState, camera);
				guiRenderer.render(HUD);
				DisplayManager.updateDisplay();
				break;

			case MULTIPLAYER_LOBBY:
				// Start game button, right now just creates an exact copy of
				// the testing environment but in a different gamestate

				if (firstTimeThru3 && isHost == 0) {
					// DatabaseConnection.setPlayerConnectedYes(Name);
					playerMe.setIsConnected(true);
					new ClientSend(54321 - DatabaseConnection.getLobbyID(Name), (TestPlayer) playerMe,
							(TestingLevel) level).start();
					;

					firstTimeThru3 = false;

				}

				if (playerStatus == PlayerStatus.HOST && !lobbyGUIs.contains(GUITexture.hosting)) {
					lobbyGUIs.add(GUITexture.hosting);
				}

				if (playerStatus == PlayerStatus.NON_HOST && !lobbyGUIs.contains(GUITexture.joining)) {
					lobbyGUIs.add(GUITexture.joining);
				}

				if (!lobbyGUIs.contains(GUITexture.lobby)) {
					lobbyGUIs.add(GUITexture.lobby);
				}

				if (firstTimeThru2 && isHost == 0 && DatabaseConnection.getHostStatus().equalsIgnoreCase("Game")) {

					for (int i = 0; i < DatabaseConnection.getLobbyNames().size(); i++) {

						String currentName = DatabaseConnection.getLobbyNames().get(i);

						if (currentName.equalsIgnoreCase(Name)) {

							level.add(new TestPlayer(DatabaseConnection.getHostName(), true, RawModel.darth,
									ModelTexture.black, new Vector3f(250, 10, -250), 0, 0, 0, 3));

						} else {

							level.add(new TestPlayer(currentName, true, RawModel.darth, ModelTexture.black,
									new Vector3f(250, 10, -250), 0, 0, 0, 3));

						}

					}

					// new ClientSend(54321 -
					// DatabaseConnection.getLobbyID(Name),(TestPlayer)
					// playerMe, (TestingLevel) level);
					new ClientReceive(playerMe, Name, level.getPlayerByName(DatabaseConnection.getHostName()), level)
							.start();

					gameState = GameState.MULTIPLAYER;
					Mouse.setGrabbed(true);
					firstTimeThru2 = false;

				}

				if ((Mouse.getX() < 770) && (Mouse.getX() > 470) && (Mouse.getY() < 215) && (Mouse.getY() > 110)
						&& isHost == 1) {

					if (Mouse.isButtonDown(0) && !clicking) {
						// DatabaseConnection.setPlayerConnectedYes(Name);
						playerMe.setIsConnected(true);
						clicking = true;
						gameState = GameState.MULTIPLAYER;
						level = new TestingLevel(Terrain.map1);
						playerMe = new TestPlayer(Name, false, RawModel.darth, ModelTexture.black,
								new Vector3f((int) (Math.random() * 500), 30, (int) (Math.random() * -500)), 0, 135, 0,
								3);
						level.add(playerMe);
						camera = new Camera(playerMe);
						playerMe.setCamera(camera);
						Mouse.setGrabbed(true);
					}
				}

				// testing
				if (Keyboard.isKeyDown(Keyboard.KEY_L)) {
					System.out.println("X: " + Mouse.getX() + "       Y: " + Mouse.getY());
				}

				level.render(renderer, light, camera, new Vector4f(0, 0, 0, 0));
				guiRenderer.render(lobbyGUIs);
				DisplayManager.updateDisplay();
				break;

			case HOST_OR_JOIN:
				if ((Mouse.getX() < 445) && (Mouse.getX() > 250) && (Mouse.getY() < 410) && (Mouse.getY() > 330)) {

					// HOST GAME BUTTON, right now just routes to a local lobby
					// gamestate, need to create a find-able game here
					if (Mouse.isButtonDown(0) && !clicking) {
						clicking = true;
						gameState = GameState.MULTIPLAYER_LOBBY;
						playerStatus = PlayerStatus.HOST;

						DatabaseConnection.addPlayerToHosts(Name, DatabaseConnection.getMyIP());
						isHost = 1;

					}

				} else {
					mainMenuGUIs.remove(GUITexture.Test_button_mo);
				}

				if ((Mouse.getX() < 1000) && (Mouse.getX() > 830) && (Mouse.getY() < 410) && (Mouse.getY() > 330)
						&& !DatabaseConnection.getHostName().equalsIgnoreCase("No Host")) {
					// JOIN GAME BUTTON, need to find way to join host's game
					// and wait, right now just goes to local lobby gamestate
					if (Mouse.isButtonDown(0) && !clicking) {
						clicking = true;
						gameState = GameState.MULTIPLAYER_LOBBY;
						playerStatus = PlayerStatus.NON_HOST;

						DatabaseConnection.addPlayerToLobby(Name, DatabaseConnection.getMyIP());
						isHost = 0;
					}
				}

				if ((Mouse.getX() < 190) && (Mouse.getX() > 10) && (Mouse.getY() < 710) && (Mouse.getY() > 660)) {
					if (Mouse.isButtonDown(0) && !clicking) {
						clicking = true;
						gameState = GameState.MAIN_MENU;
					}
				}
				// testing
				if (Keyboard.isKeyDown(Keyboard.KEY_L)) {
					System.out.println("X: " + Mouse.getX() + "       Y: " + Mouse.getY());
				}
				level.render(renderer, light, camera, new Vector4f(0, 0, 0, 0));
				guiRenderer.render(hostOrJoinGUIs);
				DisplayManager.updateDisplay();
				break;

			case SINGLEPLAYER:
				if (Keyboard.isKeyDown(Keyboard.KEY_T)) {
					level.reset();
					playerMe = new WaterTestPlayer(RawModel.darth, ModelTexture.black,
							new Vector3f(15, 0, -15), 0, 135, 0, 1);
					level.add(playerMe);
					camera = new Camera(playerMe);
					playerMe.setCamera(camera);
				}

				if ((Mouse.getX() < 730) && (Mouse.getX() > 550) && (Mouse.getY() < 400) && (Mouse.getY() > 350)
						&& esc_menu_open) {
					if (Mouse.isButtonDown(0) && !clicking) {
						clicking = true;
						gameState = GameState.MAIN_MENU;
						esc_menu_open = false;
					}
				}

				if ((Mouse.getX() < 730) && (Mouse.getX() > 550) && (Mouse.getY() < 300) && (Mouse.getY() > 250)
						&& esc_menu_open) {
					if (Mouse.isButtonDown(0) && !clicking) {
						clicking = true;
						gameState = GameState.EXIT;
					}
				}
				// ***UPDATES EVERYTHING********

				level.update();
				camera.move();

				// *****RENDERS EVERYTHING**********
				GL11.glEnable(GL30.GL_CLIP_DISTANCE0);

				// WATER REFLECTION FRAME
				waterBuffers.bindReflectionFrameBuffer();
				float distance = 2 * (camera.getPosition().y - water1.getHeight());
				camera.getPosition().y -= distance;
				camera.invertPitch();
				level.render(renderer, light, camera, new Vector4f(0, 1, 0, -water1.getHeight() + .5f));
				camera.getPosition().y += distance;
				camera.invertPitch();

				// WATER REFRACTION FRAME
				waterBuffers.bindRefractionFrameBuffer();
				level.render(renderer, light, camera, new Vector4f(0, -1, 0, water1.getHeight() + .5f));

				// DEFAULT FRAME
				waterBuffers.unbindCurrentFrameBuffer();
				level.render(renderer, light, camera, new Vector4f(0, 0, 0, 0)); // no
																					// clipping
				waterRenderer.render(waterTiles, camera, light);
				updateHUD(HUD, playerMe, level, clicking, gameState, camera);
				guiRenderer.render(HUD);
				DisplayManager.updateDisplay();
				break;

			case WATER_TEST:
				if (Keyboard.isKeyDown(Keyboard.KEY_T)) {
					level.reset();
					playerMe = new WaterTestPlayer(RawModel.rawDragonModel, ModelTexture.darkRed,
							new Vector3f(0, 10, 0), 0, 135, 0, 1);
					level.add(playerMe);
					camera = new Camera(playerMe);
					playerMe.setCamera(camera);
				}

				if ((Mouse.getX() < 730) && (Mouse.getX() > 550) && (Mouse.getY() < 400) && (Mouse.getY() > 350)
						&& esc_menu_open) {
					if (Mouse.isButtonDown(0) && !clicking) {
						clicking = true;
						gameState = GameState.MAIN_MENU;
						esc_menu_open = false;
					}
				}

				if ((Mouse.getX() < 730) && (Mouse.getX() > 550) && (Mouse.getY() < 300) && (Mouse.getY() > 250)
						&& esc_menu_open) {
					if (Mouse.isButtonDown(0) && !clicking) {
						clicking = true;
						gameState = GameState.EXIT;
					}
				}
				// ***UPDATES EVERYTHING********

				level.update();
				camera.move();

				// *****RENDERS EVERYTHING**********
				GL11.glEnable(GL30.GL_CLIP_DISTANCE0);

				// WATER REFLECTION FRAME
				waterBuffers.bindReflectionFrameBuffer();
				distance = 2 * (camera.getPosition().y - water1.getHeight());
				camera.getPosition().y -= distance;
				camera.invertPitch();
				level.render(renderer, light, camera, new Vector4f(0, 1, 0, -water1.getHeight() + .5f));
				camera.getPosition().y += distance;
				camera.invertPitch();

				// WATER REFRACTION FRAME
				waterBuffers.bindRefractionFrameBuffer();
				level.render(renderer, light, camera, new Vector4f(0, -1, 0, water1.getHeight() + .5f));

				// DEFAULT FRAME
				waterBuffers.unbindCurrentFrameBuffer();
				level.render(renderer, light, camera, new Vector4f(0, 0, 0, 0)); // no
																					// clipping
				waterRenderer.render(waterTiles, camera, light);
				updateHUD(HUD, playerMe, level, clicking, gameState, camera);
				guiRenderer.render(HUD);
				DisplayManager.updateDisplay();
				break;

			case EXIT:
				break displayloop;
			default:
				break displayloop;
			}

			if (!Mouse.isButtonDown(0)) {
				clicking = false;
			}

		}

		// *****CLEANS UP THE MEMORY*********

		// System.out.println(DatabaseConnection.getLobbyID("Player2"));
		// System.out.println(DatabaseConnection.getLobbyID("Player3"));

		// DatabaseConnection.setPlayerConnectedNo(Name);
		playerMe.setIsConnected(false);
		//DatabaseConnection.clearTables();// for simplicity, eventually will only
											// clear rows containing this
											// players names

		waterBuffers.cleanUp();
		renderer.cleanUp();
		waterShader.cleanUp();
		guiRenderer.cleanUp();
		loader.cleanUp();
		SoundManager.cleanUp();
		DisplayManager.closeDisplay();

	}

	private static void updateHUD(List<GUITexture> GUI, Player playr, Level level, boolean clicking,
			GameState gameState, Camera camera) {
		GUI.removeAll(GUI);
		if ((playr).getAmmo() == 5 && !GUI.contains(GUITexture.ammo_5))
			GUI.add(GUITexture.ammo_5);
		else if (playr.getAmmo() == 4 && !GUI.contains(GUITexture.ammo_4))
			GUI.add(GUITexture.ammo_4);

		else if (playr.getAmmo() == 3 && !GUI.contains(GUITexture.ammo_3))
			GUI.add(GUITexture.ammo_3);

		else if (playr.getAmmo() == 2 && !GUI.contains(GUITexture.ammo_2))
			GUI.add(GUITexture.ammo_2);

		else if (playr.getAmmo() == 1 && !GUI.contains(GUITexture.ammo_1))
			GUI.add(GUITexture.ammo_1);

		else if (playr.getAmmo() == 0 && !GUI.contains(GUITexture.ammo_0))
			GUI.add(GUITexture.ammo_0);

		if ((playr).getHealth() == 100)
			GUI.add(GUITexture.health_100);

		else if ((playr).getHealth() < 100 && (playr).getHealth() > 70 && !GUI.contains(GUITexture.health_80))
			GUI.add(GUITexture.health_80);

		else if ((playr).getHealth() <= 70 && (playr).getHealth() > 50 && !GUI.contains(GUITexture.health_60))
			GUI.add(GUITexture.health_60);

		else if ((playr).getHealth() <= 50 && (playr).getHealth() > 30 && !GUI.contains(GUITexture.health_40))
			GUI.add(GUITexture.health_40);

		else if ((playr).getHealth() <= 30 && (playr).getHealth() > 0 && !GUI.contains(GUITexture.health_20))
			GUI.add(GUITexture.health_20);

		else if ((playr).getHealth() == 0 && !GUI.contains(GUITexture.health_0))
			GUI.add(GUITexture.health_0);

		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && !esc_menu_open && !ESC_pressed) {
			Mouse.setGrabbed(false);
			esc_menu_open = true;
			ESC_pressed = true;
		}
		if (esc_menu_open) {
			GUI.add(GUITexture.Main_menu_button);
			GUI.add(GUITexture.Exit_button_esc);
			GUI.add(GUITexture.esc_menu);
		}

		if (playr.getCamera().getView() == 1 && !esc_menu_open) {
			GUI.add(GUITexture.retina_1);
		}

		if (playr.getCamera().getView() == 3 && !esc_menu_open) {
			GUI.add(GUITexture.retina_3);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && esc_menu_open && !ESC_pressed) {
			if (camera.getView() == 1) {
				Mouse.setGrabbed(true);
			}
			esc_menu_open = false;
			ESC_pressed = true;
		}

		if (!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			ESC_pressed = false;
		}

		if (!GUI.contains(GUITexture.HUD)) {
			GUI.add(GUITexture.HUD);
		}
	}
}
