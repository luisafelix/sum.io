package server.communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import common.communication.SyncPack;

public class CommsHandler extends Thread
{
	private ServerSocket serverSocket;
	private int port;
	private LinkedList<ClientConnexion> connexions;
	
	public CommsHandler(int port)
	{
		this.port = port;
	}
	
	public void run()
	{
		try
		{
			serverSocket = new ServerSocket(port);
			connexions = new LinkedList<ClientConnexion>();
			
			while(true)
			{
				System.out.println("Waiting for connexions ...");
				Socket clientSocket = serverSocket.accept(); // blocked untill receive something
				//Connexion accepted.
				System.out.println ("   Nouvelle connexion acceptée depuis "+clientSocket.getRemoteSocketAddress().toString());
				connexions.add(new ClientConnexion(clientSocket,this));
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
	
	public void updateClients(SyncPack sync)
	{
		for(ClientConnexion c : connexions)
		{
			c.send(sync);
		}
	}

}
