package server.environment;

import java.util.ArrayList;
import java.util.LinkedList;

import common.communication.ActionPack;
import common.communication.SyncPack;
import common.environment.ActionHandler;
import common.environment.GameObject;
import common.environment.Platform;
import common.environment.Player;
import server.MainServer;

public class EnvironmentHandler 
{
	private final int PRIORITYRENDER_BACKGROUND = 1;
	private final int PRIORITYRENDER_PLAYER = 100;
	
	private final int PLATFORM_SIZE = 1200;
	
	private ArrayList<Player> playerMap;
	private Platform platform;
	private MainServer callback;
	
	private SyncPack syncPack;
	
	public EnvironmentHandler(MainServer callback)
	{
		playerMap = new ArrayList<Player>();
		this.callback = callback;
		syncPack = new SyncPack();
		setupPlatform();
	}
	
	public ArrayList<Player> getPlayerMap() {return playerMap;}
	public SyncPack getSyncPack() {return syncPack;}
	
	public void connectPlayer(String clientIP)
	{
		//TODO: A system that stores the ips already connected, store the position and the caracteristics of the player
		Player currentPlayer = new Player("ball1",0,0,50,50,clientIP,PRIORITYRENDER_PLAYER);
		callback.getEngineHandler().getScreenRender().addToRender(currentPlayer);
		playerMap.add(currentPlayer);
		syncPack.addPersonalPlayer(currentPlayer);
		syncPack.addPlayerMap(playerMap);
		callback.sendSyncPack(syncPack);
	}
	
	private void setupPlatform()
	{
		Platform platform = new Platform("platform1",0,0,1200,1200,PRIORITYRENDER_BACKGROUND);
		callback.getEngineHandler().getScreenRender().addToRender(platform);
		this.platform = platform;
		syncPack.addPlatform(platform);
	}
	
	public void doPlayerAction(ActionPack aPack)
	{
		//Update the pointer of the player that needs to be moved
		//FIXME: Use a while loop.
		for(Player p : playerMap)
		{
			if(p.equals(aPack.getPlayer()))
			{
				aPack.setPlayer(p);
			}
		}
		ActionHandler.doPlayerAction(aPack);
		callback.getEngineHandler().getScreenRender().repaint();
	}
}
