package common.environment;

import java.util.LinkedList;

import common.communication.ActionPack;
import common.communication.PlayerAction;

public class ActionHandler 
{
	public ActionHandler()
	{
		
	}

	public void doPlayerAction(ActionPack aPack)
	{
		Player p = aPack.getPlayer();
		LinkedList<PlayerAction> playerActionList = aPack.getPlayerActionList();
		for(PlayerAction currentP:playerActionList)
		{
			switch(currentP)
			{
				case MOVE_LEFT:
					p.translate(-p.getSpeedX(), 0);
					break;
				case MOVE_UP:
					p.translate(0,-p.getSpeedY());
					break;
				case MOVE_RIGHT:
					p.translate(+p.getSpeedX(),0);
					break;
				case MOVE_DOWN:
					p.translate(0,+p.getSpeedY());
					break;
			}
		}
	}
}
