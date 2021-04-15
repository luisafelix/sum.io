package client.communication;

import client.*;
import client.environment.EnvironmentHandler;
import client.lobby.LobbyHandler;
import common.communication.SyncPack;
import common.communication.ActionPack;
import common.communication.LobbyPack;

public class CommsHandler {
	
	private String serverIP;
	private int serverPort;
	private Network network;
	private MainClient callback;
	
	public CommsHandler(String serverIP, int serverPort, MainClient callback)
	{
		//FIXME: PROBLEME.
		this.serverIP= serverIP;
		this.serverPort = serverPort;
		
		this.callback = callback;
		System.out.println("Connecting to: "+ serverIP + ":" + serverPort);
		network = new Network(this);
	}
	
	public MainClient getMainClient() {return callback;}
	
	public void receiveSyncPack(SyncPack syncPack)
	{		
		EnvironmentHandler evironmentHandler = callback.getEnvironmentHandler();
		if(evironmentHandler != null)
		{
			evironmentHandler.syncClient(syncPack);
		}
	}
	
	public void connectNetwork()
	{
		network.connect(serverIP,serverPort);
	}
	
	public void receiveLobbyPack(LobbyPack lPack)
	{
		LobbyHandler lobbyHandler = callback.getLobbyHandler();
		
		if(lobbyHandler != null)
		{
			lobbyHandler.syncLobby(lPack);
		}
	}
	
	public void sendActionPack(ActionPack aPack)
	{
		network.sendActionPack(aPack);
	}
	
	public void sendLobbyPack(LobbyPack lPack)
	{

		network.sendLobbyPack(lPack);
	}
}

