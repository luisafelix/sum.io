package client.environment;

import java.util.ArrayList;
import client.MainClient;
import common.communication.SyncPack;
import common.environment.CollectableBoost;
import common.environment.GameObject;
import common.environment.Platform;
import common.environment.Player;
import client.engine.EngineHandler;

/*
 * This class is responsible to receive the SyncPack and update the client conditions.
 */

public class EnvironmentHandler 
{
	public static final int PRIORITYRENDER_UI = 1000;
	
	private ArrayList<Player> playerMap;
	private ArrayList<GameObject> interactableObjects;
	private Platform platform;
	private Player player;
	private MainClient callback;
	
	private int playersCount = 0;
	
	
	public EnvironmentHandler(MainClient callback)
	{
		playerMap = new ArrayList<Player>();
		interactableObjects = new ArrayList<GameObject>();
		this.callback = callback;
	}

	public int getRemainingPlayers()
	{
		return playersCount;
	}
	
	public Player getPlayerClient() {return player;}
	
	
	public void syncClient(SyncPack sPack)
	{
		EngineHandler engineHandler = callback.getEngineHandler();
		this.playerMap = sPack.getPlayerMap();
		this.playersCount = sPack.getPlayersCount();
		
		//the current client player is set in this part of the code.
		if(player == null)
		{
			for(Player p: playerMap)
			{
				if(p.getPlayerIP().equals(callback.getLobbyHandler().getPlayer()))
				{
					player = p;
					callback.getEngineHandler().getInputHandler().setPlayer(p);
				}
			}
		}
		
		//Update playerState.
		if(engineHandler != null)
		{
			for(Player p : playerMap)
			{
				if(p.equals(player))
				{
					player = p;
				}
				engineHandler.getScreenRender().addToRender((GameObject)p);
			}
		}
		
		//Platform setup in client side
		if(platform == null)
		{
			this.platform = sPack.getPlatform();
			engineHandler.getScreenRender().addToRender((GameObject)this.platform);
		}
		
		//Center the player and make the things move as the player moves.
		if(player != null)
		{
			callback.getEngineHandler().getScreenRender().setOrigin((int)player.getX(),(int)player.getY());
		}
		
		if(engineHandler != null)
		{
			interactableObjects = sPack.getInteractableObjects();
			engineHandler.getScreenRender().addToRender(interactableObjects);
		}
		
		callback.getEngineHandler().getUserInterface().update();
	}
}
