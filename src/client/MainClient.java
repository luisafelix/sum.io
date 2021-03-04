package client;

import client.communication.CommsHandler;
import client.engine.EngineHandler;
import client.environment.EnvironmentHandler;
import common.communication.ActionPack;
import common.environment.Player;

public class MainClient {
	
	public static void main(String[] args) 
	{
		if(args.length != 2)
		{
			System.out.println("Please enter the IP to the server followed by the port (Only numbers)");
			System.out.println("For example: java exemple 192.122.1.1 1234");
			return;
		}
		new MainClient(args);
	}
	
	/*
	 * ATRIBUTS
	 */
	private CommsHandler commsHandler;
	private EngineHandler engineHandler;
	private EnvironmentHandler environmentHandler;
	
	public MainClient(String[] args)
	{
		commsHandler = new CommsHandler(args[0],Integer.parseInt(args[1]));	
		environmentHandler = new EnvironmentHandler();
		engineHandler = new EngineHandler(this);
	}
	
	public Player getPlayer(){return environmentHandler.getPlayer();}
	
	public void sendActionPack(ActionPack aPack)
	{
		environmentHandler.doPlayerAction(aPack);
		commsHandler.sendActionPack(aPack);
	}

}
