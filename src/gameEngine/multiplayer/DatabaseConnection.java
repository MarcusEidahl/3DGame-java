package gameEngine.multiplayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.sql.*;
import java.util.ArrayList;

import gameEngine.entities.mobs.players.Player;

public class DatabaseConnection {

	static Connection myDBConnection;
	static Statement myStatement;

	public DatabaseConnection() {
		myDBConnection = null;
	}

	public static void init(String name) {
		testForJDBCDriver();
		connectToDB();

		uploadPlayerData(name, getMyIP());

	}

	public static boolean testForJDBCDriver() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			return false;
		}

		return true;
	}

	public static boolean connectToDB() {

		String URL = "jdbc:mysql://mysql.cs.iastate.edu:3306/db309grp24";
		String User = "dbu309grp24";
		String Password = "frhKMvzdLKb";

		try {
			myDBConnection = DriverManager.getConnection(URL, User, Password);

			if (myDBConnection == null) {
				return false;
			}

			myStatement = myDBConnection.createStatement();
			return true;
		} catch (SQLException e) {
			System.out.println("Cannot connect to DB. Check VPN or JDBC driver");
			e.printStackTrace();
			return false;
		}
	}

	public static String getMyIP() {

		// URL whatismyip = new URL("http://checkip.amazonaws.com");
		// BufferedReader in = new BufferedReader(new
		// InputStreamReader(whatismyip.openStream()));

		InetAddress ipAddr = null;
		try {
			ipAddr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			System.out.println("Cannot get IP from this Host");
			e.printStackTrace();
		}
		// System.out.println(ipAddr);
		// ipAddr = InetAddress.getByName("localhost");
		// System.out.println(ipAddr);

		return ipAddr.getHostAddress();

	}

	public static String getHostIP() {

		try {
			Statement newStmt = myDBConnection.createStatement();
			ResultSet myResultSet = newStmt.executeQuery("select * from Hosts");

			while (myResultSet.next()) {
					return myResultSet.getString("IP");
			}
		} catch (Exception e) {
			System.out.println("Cannot get the Hosts IP from this name. Check name or Host DB table is empty");
			e.printStackTrace();
		}
		return "No Host";
	}
	
	public static String getHostName() {

		try {
			
			ResultSet myResultSet = myStatement.executeQuery("select * from Hosts");

			while (myResultSet.next()) {
					return myResultSet.getString("Name");
			}
		} catch (Exception e) {
			System.out.println("Cannot get the Hosts Name from this name. Check name or Host DB table is empty");
			e.printStackTrace();
		}
		return "No Host";
	}

	public static String getHostStatus() {

		try {
			Statement newStmt = myDBConnection.createStatement();
			ResultSet myResultSet = newStmt.executeQuery("select * from Hosts");

			while (myResultSet.next()) {
					return myResultSet.getString("Status");
			}
		} catch (Exception e) {
			System.out.println("Cannot get the Hosts Status from this name. Check name or Host DB table is empty");
			e.printStackTrace();
		}
		return "No Host";
	}
	
	public static void setHostStatusToGame() {

		try {
			String sql = "update Hosts set Status='Game' where ID=1";
					myStatement.executeUpdate(sql);
				
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void setHostStatusToLobby() {

		try {
			String sql = "update Hosts set Status='Lobby' where ID=1";
					myStatement.executeUpdate(sql);
				
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static String getPlayerIP(String name){
			//System.out.println(name);
	
		try {
			Statement newStmt = myDBConnection.createStatement();
			ResultSet myResultSet = newStmt.executeQuery("select * from Lobby");

			while (myResultSet.next()) {
				String Name = myResultSet.getString("Name");
					//System.out.println(Name);
				if (name.contains(Name)) {
					return myResultSet.getString("IP");

				}
			}
		} catch (Exception e) {
			System.out.println("Cannot get IP from given Player name" + name);
			e.printStackTrace();
		}
		return "No Player";
	}
	
	public static String getPlayerConnected(String name){
		//System.out.println(name);

	try {
		Statement newStmt = myDBConnection.createStatement();
		ResultSet myResultSet = newStmt.executeQuery("select * from Players");

		while (myResultSet.next()) {
			String Name = myResultSet.getString("Name");
				//System.out.println(Name);
			if (name.contains(Name)) {
				return myResultSet.getString("Connected");

			}
		}
	} catch (Exception e) {
		System.out.println("Cannot get connected from given Player name" + name);
		e.printStackTrace();
	}
	return "No Player";
}
	
	public static String setPlayerConnectedYes(String name){
		//System.out.println(name);

		try {
			Statement newStmt = myDBConnection.createStatement();

				String sql = "update Players " + "set Connected='Yes'" + " where ID=" + DatabaseConnection.getPlayerID(name);
				newStmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	return "No Player";
}
	
	public static String setPlayerConnectedNo(String name){
		//System.out.println(name);

		try {
			Statement newStmt = myDBConnection.createStatement();

				String sql = "update Players " + "set Connected='No'" + " where ID=" + DatabaseConnection.getPlayerID(name);
				newStmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	return "No Player";
}
	
	public static String setPlayerKills(String name, int kills){
		//System.out.println(name);

		try {
			Statement newStmt = myDBConnection.createStatement();

				String sql = "update Players " + "set Kills='"+ kills + "'" + " where ID=" + (3 - DatabaseConnection.getPlayerID(name));
				newStmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	
	return "No Player";
}
	
	public static int getPlayerKills(String name){
		//System.out.println(name);

	try {
		Statement newStmt = myDBConnection.createStatement();
		ResultSet myResultSet = newStmt.executeQuery("select * from Players");

		while (myResultSet.next()) {
			String Name = myResultSet.getString("Name");
				//System.out.println(Name);
			if (name.contains(Name)) {
				return myResultSet.getInt("Kills");

			}
		}
	} catch (Exception e) {
		System.out.println("Cannot get Kills from given Player name" + name);
		e.printStackTrace();
	}
	return -1;
}

	public static int getLobbyID(String name) {

		try {
			ResultSet myResultSet = myStatement.executeQuery("select * from Lobby");
			int i = 1;

			while (myResultSet.next()) {
				String currentName = myResultSet.getString("Name");

				if (name.equalsIgnoreCase(currentName)) {
					return i;// myResultSet.getInt("ID");

				}
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int getPlayerID(String name) {

		try {
			ResultSet myResultSet = myStatement.executeQuery("select * from Players");
			int i = 1;

			while (myResultSet.next()) {
				String currentName = myResultSet.getString("Name");

				if (name.equalsIgnoreCase(currentName)) {
					return i;// myResultSet.getInt("ID");

				}
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static String getLobbyName(String IP) {

		try {
			ResultSet myResultSet = myStatement.executeQuery("select * from Lobby");
			

			while (myResultSet.next()) {
				String currentIP = myResultSet.getString("IP");

				if (IP.equalsIgnoreCase(currentIP)) {
					return myResultSet.getString("Name");

				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "No IP";
	}

	public static ArrayList<String> getLobbyIPs() {

		try {
			ResultSet myResultSet = myStatement.executeQuery("select * from Lobby");
			ArrayList<String> IPs = new ArrayList<String>(1);

			while (myResultSet.next()) {
				String currentIP = myResultSet.getString("IP");
				IPs.add(currentIP);
			}
			return IPs;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static ArrayList<String> getLobbyNames() {

		try {
			System.out.println(myDBConnection.isClosed());
			ResultSet myResultSet = myStatement.executeQuery("select * from Lobby");
			System.out.println(myResultSet);
			ArrayList<String> Names = new ArrayList<String>(1);

			while (myResultSet.next()) {
				String currentName = myResultSet.getString("Name");
				Names.add(currentName);
			}
			return Names;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void uploadPlayerData(String name, String IP){

		try {
			String sql = "insert into Players " + " (Name, IP, Connected)" + "values ('" + name + "', '" + IP + "', '" + "No" + "')";
			myStatement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void addPlayerToLobby(String name, String IP) {

		try {
			String sql = "insert into Lobby " + " (Name, IP)" + "values ('" + name + "', '" + IP + "')";
			myStatement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void addPlayerToHosts(String name, String IP) {

		try {
			String sql = "insert into Hosts " + " (Name, IP, Status)" + "values ('" + name + "', '" + IP + "', '" + "Lobby" + "')";
			myStatement.executeUpdate(sql);

			//DatabaseConnection.addPlayerToLobby(name, IP);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void clearTables(){

		try {
			String sql = "truncate Players";
			myStatement.executeUpdate(sql);

			sql = "truncate Hosts";
			myStatement.executeUpdate(sql);

			sql = "truncate Lobby";
			myStatement.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("Problem clearing tables in DB");
			e.printStackTrace();
		}

	}
}

/*
 * 
 * 
 * while(myResultSet.next())
 * 
 * {
 * 
 * System.out.print("ID: " + myResultSet.getString("ID")); System.out.print(
 * "|Name: " + myResultSet.getString("Name")); // System.out.print(
 * "|Player Type: " + // myResultSet.getString("Type")); System.out.print(
 * "|IP Address: " + myResultSet.getString("IP_Address")); System.out.println(
 * "|Time Connected: " + myResultSet.getTimestamp("Time_Connected")); }
 * 
 * }catch(
 * 
 * SQLException e2)
 * 
 * { System.out.println("Connection failed"); e2.printStackTrace(); return; }
 */