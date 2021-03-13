package server;

import common.communication.ActionPack;
import common.communication.SyncPack;
import server.communication.CommsHandler;
import server.engine.EngineHandler;
import server.environment.EnvironmentHandler;

public class MainServer 
{
	public static void main(String[] args) 
	{
		new MainServer();
	}
	
	//Atributs
	private CommsHandler commsHandler;
	private EngineHandler engineHandler;
	private EnvironmentHandler environmentHandler;
	
	public MainServer()
	{
		int port = 8000;
		commsHandler = new CommsHandler(port,this);
		commsHandler.start();
		engineHandler = new EngineHandler(this);
		environmentHandler = new EnvironmentHandler(this);
	}
	
	public EngineHandler getEngineHandler() {return engineHandler;}
	public EnvironmentHandler getEnvironmentHandler() {return environmentHandler;}
	
	public void connectPlayer(String clientIP)
	{
		environmentHandler.connectPlayer(clientIP);
		commsHandler.startUpdateTimer();
		
		engineHandler.getScreenRender().repaint();
	}
	//TODO: do a system that stops the update timer.
	public void sendSyncPack(SyncPack sPack)
	{
		commsHandler.sendSyncPack(sPack);
	}

	public void actionPackReceived(ActionPack aPack) {
		environmentHandler.doPlayerAction(aPack);
	}
}
