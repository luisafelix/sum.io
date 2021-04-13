package client;

import client.communication.CommsHandler;
import client.engine.EngineHandler;
import client.environment.EnvironmentHandler;
import client.lobby.LobbyHandler;
import common.communication.ActionPack;
import common.environment.Player;

import server.LaunchServer;

public class MainClient 
{
	public static void main(String[] args) 
	{
		if(args.length != 3)
		{
			System.out.println("Please enter the IP to the server (yours) followed by the port (if you didn't change it is 8000)");
			System.out.println("For example: java localhost 8000");
			return;
		}
		new MainClient(args);
	}
	/*
	 * ATRIBUTS
	 */
	private CommsHandler commsHandler;
	private EngineHandler engineHandler;
	private EnvironmentHandler environmentHandler;
	private LobbyHandler lobbyHandler;
	private LaunchServer launchServer;
	
	public MainClient(String[] args)
	{
		String ip = args[0];
		int port = Integer.parseInt(args[1]);
		
		//FIXME: HardCoded
		String playerName;
		switch(args[2])
		{
		case "1":
			playerName = "loslo";
			onCreateServer(playerName,ip,port);
			break;
		case "2":
			playerName = "loslo2";
			onConnectServer(playerName, ip, port);
			break;
		}
		/*
		commsHandler = new CommsHandler(args[0],Integer.parseInt(args[1]),this);
		engineHandler = new EngineHandler(this);
		environmentHandler = new EnvironmentHandler(this);
		*/
	}
	
	public EnvironmentHandler getEnvironmentHandler() {return environmentHandler;}
	public CommsHandler getCommsHandler() {return commsHandler;}
	public EngineHandler getEngineHandler() {return engineHandler;}
	public LobbyHandler getLobbyHandler() {return lobbyHandler;}
	public LaunchServer getLaunchServer() {return launchServer;}

	public void onCreateServer(String playerName, String ip,int port)
	{
		launchServer = new LaunchServer();
		connectPlayer(playerName,ip,port);
	}
	
	public void onConnectServer(String playerName, String ip,int port)
	{
		connectPlayer(playerName,ip,port);
	}
	
	public void connectPlayer(String playerName, String ip,int port)
	{
		commsHandler = new CommsHandler(ip,port,this);
		lobbyHandler = new LobbyHandler(this,"sum.io",800,600);
		lobbyHandler.onPlayerConnectServer(playerName);
	}
	
	public void launchGame()
	{
		//FIXME: Find a better way
		if(launchServer != null)
		{
			launchServer.launchGame(lobbyHandler.getLobbyPack().getPlayerList());
		}
		engineHandler = new EngineHandler(this,lobbyHandler.getJFrame());
		environmentHandler = new EnvironmentHandler(this);
	}
	
}
