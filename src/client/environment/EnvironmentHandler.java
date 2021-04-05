package client.environment;

import java.util.ArrayList;
import client.MainClient;
import common.communication.SyncPack;
import common.environment.GameObject;
import common.environment.Platform;
import common.environment.Player;
import client.engine.EngineHandler;

public class EnvironmentHandler 
{
	public static final int PRIORITYRENDER_UI = 1000;
	
	private ArrayList<Player> playerMap;
	//private ArrayList<InteractableObjects> interactableObjects;
	private Platform platform;
	private Player himself;
	private MainClient callback;
	
	private int playersCount = 0;
	
	
	public EnvironmentHandler(MainClient callback)
	{
		playerMap = new ArrayList<Player>();
		//interactableObjects = new ArrayList<InteractableObjects>();
		this.callback = callback;
	}

	public int getRemainingPlayers()
	{
		/*int count = 0;
		for(Player p : playerMap)
		{
			if(p.isAwake())
			{
				count++;
			}
		}*/
		return playersCount;
	}
	
	public Player getPlayerClient() {return himself;}
	
	public void syncClient(SyncPack sPack)
	{
		EngineHandler engineHandler = callback.getEngineHandler();
		this.playerMap = sPack.getPlayerMap();
		this.playersCount = sPack.getPlayersCount();
		//Player setup in client side
		if(himself == null)
		{
			himself = sPack.getPlayer();
			callback.getEngineHandler().getInputHandler().setPlayer(himself);
		}
		
		if(engineHandler != null)
		{
			for(Player p : playerMap)
			{
				engineHandler.getScreenRender().addToRender((GameObject)p);
			}
			
			
		}
		
		//Platform setup in client side
		if(platform == null)
		{
			this.platform = sPack.getPlatform();
			engineHandler.getScreenRender().addToRender((GameObject)this.platform);
		}
		
		//Center the player
		callback.getEngineHandler().getScreenRender().setOrigin((int)himself.getX(),(int)himself.getY());
		
		/*
		if(engineHandler != null && nonInteractableObjects.size() != sPack.getNonInteractableObjects().size())
		{
			interactableObjects = sPack.getinteractableObjects();
			engineHandler.getScreenRender().addToRender(interactableObjects);
		}
		*/
		
		callback.getEngineHandler().getUserInterface().update();
	}
}
