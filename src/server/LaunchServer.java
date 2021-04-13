package server;

import java.util.LinkedList;

import common.communication.ActionPack;
import common.communication.SyncPack;
import server.communication.CommsHandler;
import server.engine.EngineHandler;
import server.environment.EnvironmentHandler;
import server.inteligence.InteligenceBrain;


public class LaunchServer 
{
	private CommsHandler commsHandler;
	private EngineHandler engineHandler;
	private EnvironmentHandler environmentHandler;
	
	public LaunchServer()
	{	
		int port = 8000;
		commsHandler = new CommsHandler(port,this);
		commsHandler.start();
	}
	
	public EngineHandler getEngineHandler() {return engineHandler;}
	public EnvironmentHandler getEnvironmentHandler() {return environmentHandler;}
	public CommsHandler getCommsHandler() {return commsHandler;}
	
	public void launchGame(LinkedList<String> playerList)
	{
		engineHandler = new EngineHandler(this);
		environmentHandler = new EnvironmentHandler(this);
		environmentHandler.startEnvironnment();
		
		for(String s : playerList)
		{
			environmentHandler.connectPlayer(s);

		}
		commsHandler.startUpdateTimer();
	}
}
