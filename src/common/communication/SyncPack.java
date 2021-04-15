package common.communication;

import java.util.ArrayList;
import common.environment.GameObject;
import common.environment.Platform;
import common.environment.Player;

public class SyncPack implements java.io.Serializable
{
	private ArrayList<Player> playerMap;
	private ArrayList<GameObject> interactableObjects;
	private Player player; 
	private Platform platform;
	
	private int playersCount;
	
	public SyncPack()
	{
		playerMap = new ArrayList<Player>();
		interactableObjects = new ArrayList<GameObject>();
	}
	
	public SyncPack(SyncPack sPack)
	{
		this.platform = sPack.getPlatform();
		this.player = sPack.getPlayer();
		this.playersCount = sPack.getPlayersCount();
		this.playerMap = sPack.getPlayerMap();
		this.interactableObjects = sPack.getInteractableObjects();
	}
	
	public void setPlayersRemainingCount(int count) {this.playersCount = count;}
	
	public ArrayList<Player> getPlayerMap() {return playerMap;}
	public Player getPlayer() {return player;}
	public Platform getPlatform() {return platform;}
	public int getPlayersCount() {return playersCount;}
	public ArrayList<GameObject> getInteractableObjects() {return interactableObjects;}
	
	public void addPlayerMap(ArrayList<Player> playerMap)
	{
		this.playerMap = playerMap;
	}
	
	public void addPersonalPlayer(Player player)
	{
		this.player = player;
	}
	
	public void addPlatform(Platform platform) 
	{
		this.platform = platform;
	}

	public void setPlayerMap(ArrayList<Player> onlyPlayerInstance) 
	{
		playerMap = onlyPlayerInstance;
	}
	
	public void addInteractableObject(ArrayList<GameObject> interactableObjects) 
	{
		this.interactableObjects = interactableObjects;
	}

	public void addInteractableObject(GameObject interactableObjects) 
	{
		this.interactableObjects.add(interactableObjects);
	}
	

}
