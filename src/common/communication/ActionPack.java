package common.communication;

import java.util.LinkedList;

import common.environment.Player;

public class ActionPack implements java.io.Serializable
{
	private LinkedList<PlayerAction> playerActionList = null;
	private Player player;
	
	public ActionPack(Player player)
	{
		playerActionList = new LinkedList<PlayerAction>();
		this.player = player;
	}
	
	public ActionPack(ActionPack aPack)
	{
		playerActionList = (LinkedList<PlayerAction>) aPack.getPlayerActionList().clone();
	}
	
	public LinkedList<PlayerAction> getPlayerActionList(){return playerActionList;}
	
	public void updateAction(PlayerAction action)
	{
		if(!playerActionList.contains(action))
		{
			playerActionList.add(action);
		}	
		else
		{
			playerActionList.remove(action);
		}
	}
	
	
	public String toString()
	{
		String res ="";
		res+= playerActionList.toString();
		return res;
	}

	public Player getPlayer() {return player;}
	
	public boolean empty() 
	{	
		return this.getPlayerActionList().isEmpty();
	}
}
