package client.communication;

import java.net.Socket;
import java.net.SocketException;

import common.communication.LobbyPack;
import common.communication.SyncPack;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ListeningThread extends Thread{
	
	private Socket socket;
	private CommsHandler callback;
	private Network network;
	
	public ListeningThread(Socket socket, CommsHandler callback, Network network)
	{
		this.callback = callback;
		this.socket = socket;
		this.network = network;
		start();
	}
	
	public void run()
	{
		try
		{
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			boolean loop = true;
			while (loop) 
			{
				try 
				{
					Object objectReceived = in.readObject();
					if(objectReceived==null)
					{
						loop = false;
					}
					else if (objectReceived instanceof SyncPack)
					{
						SyncPack sPack = (SyncPack)objectReceived;
						callback.receiveSyncPack(sPack);
					}
					else if(objectReceived instanceof LobbyPack)
					{
						LobbyPack lPack = (LobbyPack)objectReceived;
						callback.receiveLobbyPack(lPack);
					}
				}
				catch(ClassNotFoundException e)
				{
				}
			}
		}
		catch(SocketException e)
		{
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		network.close();
	}
}
	


