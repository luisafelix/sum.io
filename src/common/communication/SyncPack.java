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
	
	private int playersCount;
	
	public void setPlayersRemainingCount(int count) {this.playersCount = count;}
	
	public ArrayList<Player> getPlayerMap() {return playerMap;}
	public Player getPlayer() {return player;}
	public Platform getPlatform() {return platform;}
	public int getPlayersCount() {return playersCount;}
	
	public void addPlayerMap(ArrayList<Player> playerMap)
	{
		this.playerMap = playerMap;//(ArrayList<Player>)playerMap.clone();
	}
	
	public void addPersonalPlayer(Player player)
	{
		this.player = player;
	}
	
	public void addPlatform(Platform platform) 
	{
		this.platform = platform;
	}
}
