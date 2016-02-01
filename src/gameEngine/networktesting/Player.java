package gameEngine.networktesting;

public class Player {

	String name = "";
	int PlayerX = 0;
	int PlayerY = 0;
	int PlayerZ = 0;
	int PlayerAngle = 0;
	boolean isConnected = false;
	
	public Player(String name){
		this.name = name;	
	}
	
	public Player(String name, int x, int y, int z, int rot){
		this.name = name;
		this.PlayerX = x;
		this.PlayerY = y;
		this.PlayerZ = z;
		this.PlayerAngle = rot;
	}
	
	
	public void updatePlayer(){
		int change = 25;
		
		PlayerX = (int) (PlayerX + (.5-Math.random()) * change);
    	PlayerY = (int) (PlayerX + (.5-Math.random()) * change);
    	PlayerZ = (int) (PlayerX + (.5-Math.random()) * change);
    	PlayerAngle = (int) (PlayerX + (.5-Math.random()) * change);
	}
	
public int[] getPlayerData(){
    	
    	int[] data = new int[10];
    	updatePlayer();
    	data[0] = PlayerX;
    	data[1] = PlayerY;
    	data[2] = PlayerZ;
    	data[3] = PlayerAngle;
    	
		return data;
    	
    }
public void connect(){
	isConnected = true;
}
}
