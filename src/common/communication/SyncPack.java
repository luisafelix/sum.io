package common.communication;

import java.util.ArrayList;
import common.environment.GameObject;
import common.environment.Platform;
import common.environment.Player;

public class SyncPack implements java.io.Serializable
{
	private ArrayList<Player> playerMap;
	private Player player;
	private Platform platform;
	
	public SyncPack()
	{
		
	}
	
	public SyncPack(SyncPack sPack)
	{
		this.playerMap = sPack.getPlayerMap();
		this.player = sPack.getPlayer();
	}
	
	public void addPlayerMap(ArrayList<Player> playerMap)
	{
		this.playerMap = (ArrayList<Player>)playerMap.clone();
	}
	
	public void addPersonalPlayer(Player player)
	{
		this.player = player;
	}
	
	public ArrayList<Player> getPlayerMap() {return playerMap;}
	public Player getPlayer() {return player;}

	public void addPlatform(Platform platform) 
	{
		this.platform = platform;
	}

	public Platform getPlatform() {return platform;}
}
