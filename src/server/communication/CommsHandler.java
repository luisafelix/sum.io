package server.communication;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.Timer;

import common.communication.ActionPack;
import common.communication.LobbyPack;
import common.communication.SyncPack;
import common.environment.Player;
import server.LaunchServer;
import server.inteligence.PlayerBot;

/*
 *  CommsHandler receive all the Packs from the client and manage to send them to their specific classes.
 *  In addiction, this class prepare the data to send to all clients.
 */

public class CommsHandler extends Thread
{
	private LaunchServer callback;
	private ServerSocket serverSocket;
	private int port;
	private LinkedList<ClientConnexion> connexions;
	private LinkedList<SendHandler> sendHandler;
	
	private Timer sendUpdateTimer;
	private int updateRate = 20;
	
	private LobbyPack lobbyPack;
	
	public CommsHandler(int port, LaunchServer callback)
	{
		this.port = port;
		this.callback = callback;
		lobbyPack = new LobbyPack();
		
		setupTimer();
	}
	
	public void startUpdateTimer()
	{
		if(!sendUpdateTimer.isRunning())
		{
			sendUpdateTimer.start();
		}
	}
	
	public void stopUpdateTimer()
	{
		sendUpdateTimer.stop();
	}
	
	private void setupTimer()
	{
		sendUpdateTimer = new Timer(updateRate,
							new ActionListener() {
								public void actionPerformed(ActionEvent ae)
								{
									generateSyncPack();
								}
							});
	}
	public void run()
	{
		try
		{
			serverSocket = new ServerSocket(port);
			connexions = new LinkedList<ClientConnexion>();
			sendHandler = new LinkedList<SendHandler>();
			
			while(true)
			{
				System.out.println("Waiting for connexions ...");
				Socket clientSocket = serverSocket.accept(); // blocked until receive something
				
				//Connexion accepted.
				String clientIP = clientSocket.getRemoteSocketAddress().toString();
				System.out.println (" New connectionAccepted from:  " + clientIP);
				
				connexions.add(new ClientConnexion(clientSocket,clientIP,this));
				sendHandler.add(new SendHandler(clientSocket));
			
			}
		}
		catch(IOException e)
		{
			System.out.println("Problem to launch the server.");
			System.out.println(e);
			System.exit(1);
		}
	}
	
	public void remove(ClientConnexion cnx)
	{
		connexions.remove(cnx);
	}
	
	public void sendSyncPack(SyncPack sPack)
	{
		for(SendHandler sHandler : sendHandler)
		{
			sHandler.sendSyncPack(sPack);
		}
	}
	
	public void sendLobbyPack(LobbyPack lPack)
	{
		for(SendHandler sHandler : sendHandler)
		{
			sHandler.sendLobbyPack(lPack);
		}
	}
	
	public void generateSyncPack()
	{
		
		if(callback.getEnvironmentHandler() == null)
		{
			return;
		}
		SyncPack sPack = callback.getEnvironmentHandler().getSyncPack();
		
		//We create a new player to avoid serializing things that don't need to be.
		//(to avoid serialize an instance of player bot)
		
		SyncPack newSPack = new SyncPack(sPack);
		ArrayList<Player> playerMap = sPack.getPlayerMap();
		ArrayList<Player> onlyPlayerInstance = new ArrayList<Player>();
		
		if(sPack.getPlayer() instanceof PlayerBot)
		{
			Player p = sPack.getPlayer();
			newSPack.addPersonalPlayer(new Player(p.getName(),p.getX(),p.getY(),p.getWidth(),p.getHeight(),p.getPlayerIP(),p.getRenderingPriority(),p.isAwake(),p.getBoostQuantity()));
		}
		
		for(Player p : playerMap)
		{
			if(p instanceof PlayerBot)
			{
				onlyPlayerInstance.add(new Player(p.getName(),p.getX(),p.getY(),p.getWidth(),p.getHeight(),p.getPlayerIP(),p.getRenderingPriority(),p.isAwake(),p.getBoostQuantity()));
				continue;
			}
			onlyPlayerInstance.add(p);
		}
		newSPack.setPlayerMap(onlyPlayerInstance);
		sendSyncPack(newSPack);
	}

	public void actionPackReceived(ActionPack aPack)
	{
		callback.getEnvironmentHandler().doPlayerAction(aPack);
	}
	
	public void lobbyPackReceived(LobbyPack lPack)
	{
		if(lPack.getPlayer()!= null)
		{
			lobbyPack.addToPlayerList(lPack.getPlayer());
		}
		lobbyPack.setStartFlag(lPack.getStartFlag());
		sendLobbyPack(lobbyPack);
	}
	
}
