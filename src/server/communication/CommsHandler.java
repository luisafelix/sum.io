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
import common.communication.SyncPack;
import common.environment.Player;
import server.MainServer;

public class CommsHandler extends Thread
{
	private MainServer callback;
	private ServerSocket serverSocket;
	private int port;
	private LinkedList<ClientConnexion> connexions;
	private LinkedList<SendHandler> sendHandler;
	
	private Timer sendUpdateTimer;
	private int updateRate = 20;
	
	public CommsHandler(int port, MainServer callback)
	{
		this.port = port;
		this.callback = callback;
		setupTimer();
	}
	
	public void startUpdateTimer()
	{
		sendUpdateTimer.start();
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
				//OPTIMIZATION: It is better to not store the clients IPs, maybe encrypt the data to create a new unique client identifier?
				connexions.add(new ClientConnexion(clientSocket,clientIP,this));
				sendHandler.add(new SendHandler(clientSocket));
				
				onPlayerConect(clientIP);

			}
		}
		catch(IOException e)
		{
			System.out.println("Problem to launch the server.");
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
			sHandler.send(sPack);
		}
	}
	
	public void generateSyncPack()
	{
		SyncPack sPack = callback.getEnvironmentHandler().getSyncPack();
		sendSyncPack(sPack);
	}

	public void actionPackReceived(ActionPack aPack)
	{
		callback.getEnvironmentHandler().doPlayerAction(aPack);
	}
	
	private void onPlayerConect(String clientIP)
	{
		callback.getEnvironmentHandler().connectPlayer(clientIP);
		startUpdateTimer();
		callback.getEngineHandler().getScreenRender().repaint();
	}
}
