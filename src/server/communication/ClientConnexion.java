package server.communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import common.communication.ActionPack;
import common.communication.LobbyPack;
import common.communication.SyncPack;

public class ClientConnexion extends Thread
{
	private Socket socket;
	private CommsHandler callback;
	private ObjectInputStream fromClient;
	private String clientIP;
	
	private ObjectOutputStream toClient;

	public ClientConnexion(Socket socket, String clientIP, CommsHandler callback)
	{
		this.socket = socket;
		this.callback = callback;
		this.clientIP = clientIP;
		start();
	}
	
	public void run()
	{
		try
		{
			fromClient = new ObjectInputStream(socket.getInputStream());
			//Prepare a new thread to send the packages for each client.
			boolean loop = true;
			ActionPack actionPack;
			while(loop)
			{
				Object o = fromClient.readObject();
				if(o== null)
				{
					loop = false;
				}
				else if(o instanceof ActionPack)
				{
					actionPack = (ActionPack)o;
					actionPackReceived(actionPack);
				}
				else if(o instanceof LobbyPack)
				{
					LobbyPack lPack = (LobbyPack)o;
					lobbyPackReceived(lPack);
				}
				else
				{
					System.out.println("Object received isn't an Action");
				}
			}
		}
		catch(Exception e)
		{
			//The network closed.
			System.out.println("Client disconnected.");
			//e.printStackTrace();
		}
		close();
		callback.remove(this);
	}
	
	public void close()
	{
		try
		{
			if(socket !=null)
			{
				socket.close();
			}
		}
		catch(Exception e)
		{
			
		}
	}
	private void lobbyPackReceived(LobbyPack lPack)
	{
		callback.lobbyPackReceived(lPack);
	}
	
	private void actionPackReceived(ActionPack aPack)
	{
		callback.actionPackReceived(aPack);
	}
}
