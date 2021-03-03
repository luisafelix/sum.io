package common.communication;

import java.util.LinkedList;

public class ActionPack implements java.io.Serializable
{
	private LinkedList<PlayerAction> playerActionList = null;
	
	public ActionPack()
	{
		playerActionList = new LinkedList<PlayerAction>();
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
}
