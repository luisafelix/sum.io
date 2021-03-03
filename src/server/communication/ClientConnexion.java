package server.communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import common.communication.ActionPack;
import common.communication.SyncPack;

public class ClientConnexion extends Thread
{
	private Socket socket;
	private CommsHandler callback;
	private ObjectInputStream fromClient;
	private ObjectOutputStream toClient;

	public ClientConnexion(Socket socket, CommsHandler callback)
	{
		this.socket = socket;
		this.callback = callback;
		start();
	}
	
	public void run()
	{
		try
		{
			fromClient = new ObjectInputStream(socket.getInputStream());
			toClient = new ObjectOutputStream(socket.getOutputStream());
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
	public void send(SyncPack sync)
	{
		try
		{
			toClient.writeObject(sync);
		}
		catch(Exception e)
		{
			close();
		}
	}
	
	private void actionPackReceived(ActionPack actionPack)
	{
		System.out.println(actionPack);
	}
	
}
