package client.communication;

import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;

import common.communication.SyncPack;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;

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
					}else if (objectReceived instanceof SyncPack)
					{
						callback.receive((SyncPack)objectReceived);
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
	


