package client.communication;

import client.*;
import client.engine.*;
import client.environment.EnvironmentHandler;
import common.communication.SyncPack;
import common.communication.ActionPack;

public class CommsHandler {
	
	private String serverIP;
	private int serverPort;
	private Network network;
	private MainClient callback;
	
	public CommsHandler(String serverIP, int serverPort, MainClient callback)
	{
		//FIXME: PROBLEME.
		this.serverIP= serverIP;
		this.callback = callback;
		System.out.println("Connecting to: "+ serverIP + ":" + serverPort);
		network = new Network(this);
		network.connect(serverIP,serverPort);
	}
	
	public void receive(SyncPack syncPack)
	{
		EnvironmentHandler evironmentHandler = callback.getEnvironmentHandler();
		evironmentHandler.syncClient(syncPack);
		
	}
	
	public void sendActionPack(ActionPack aPack)
	{
		network.sendActionPack(aPack);
	}
}

