package server.inteligence;

import common.communication.ActionPack;
import common.communication.PlayerAction;
import common.environment.ActionHandler;
import common.environment.Player;
import server.environment.EnvironmentHandler;

public class PlayerBot extends Player
{
	private static String botID = "bot";
	
	private ActionPack aPack;
	
	public PlayerBot(String name, int x, int y, int width,int height)
	{
		super(name,x,y,width,height,botID ,EnvironmentHandler.PRIORITYRENDER_PLAYER);
		aPack = new ActionPack(this);
	}
	
	public ActionPack getActionPack() {return aPack;}

	public void updateAction()
	{
		if(Math.random()<0.5)
		{
			aPack.updateAction(PlayerAction.MOVE_UP);
		}
		else
		{
			aPack.updateAction(PlayerAction.MOVE_DOWN);
		}
		
		if(Math.random()<0.5)
		{
			aPack.updateAction(PlayerAction.MOVE_LEFT);
		}
		else
		{
			aPack.updateAction(PlayerAction.MOVE_RIGHT);
		}
		ActionHandler.doPlayerAction(aPack);
	}
	
	
}
