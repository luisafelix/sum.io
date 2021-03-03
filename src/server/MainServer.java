package server;

import server.communication.CommsHandler;

public class MainServer 
{
	public static void main(String[] args) 
	{
		int port = 8000;
		CommsHandler server = new CommsHandler(port);
		server.start();
	}
}
