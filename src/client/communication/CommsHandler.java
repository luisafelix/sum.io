package client.communication;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import client.*;
import client.engine.*;
import common.communication.SyncPack;
import common.communication.ActionPack;

public class CommsHandler {
	
	private String serverIP;
	private int serverPort;
	private Network network;
	
	public CommsHandler(String serverIP, int serverPort)
	{
		//FIXME: PROBLEME.
		this.serverIP= serverIP;
		System.out.println("Connecting to: "+ serverIP + ":" + serverPort);

		network = new Network(this);
		network.connect(serverIP,serverPort);
	}
	
	public void receive(SyncPack coPack)
	{
		System.out.println("Change the game");
	}
	
	public void sendActionPack(ActionPack aPack)
	{
		network.sendActionPack(aPack);
	}
	
}

