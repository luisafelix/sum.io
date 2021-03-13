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
			System.out.println("Please enter the IP to the server (yours) followed by the port (if you didn't change it is 8000)");
			System.out.println("For example: java localhost 8000");
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
		engineHandler = new EngineHandler(this);
		environmentHandler = new EnvironmentHandler(this);
		commsHandler = new CommsHandler(args[0],Integer.parseInt(args[1]),this);
		
	}
	
	public EnvironmentHandler getEnvironmentHandler() {return environmentHandler;}
	public CommsHandler getCommsHandler() {return commsHandler;}
	public EngineHandler getEngineHandler() {return engineHandler;}
	
	//FIXME: Change sendActionPack for the specific class.
	public void sendActionPack(ActionPack aPack)
	{
		commsHandler.sendActionPack(aPack);
	}
}
