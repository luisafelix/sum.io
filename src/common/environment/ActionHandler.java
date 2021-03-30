package common.environment;

import java.util.LinkedList;

import common.communication.ActionPack;
import common.communication.PlayerAction;

public class ActionHandler 
{

	public static void doPlayerAction(ActionPack aPack)
	{
		Player p = aPack.getPlayer();
		LinkedList<PlayerAction> playerActionList = aPack.getPlayerActionList();
		for(PlayerAction currentP:playerActionList)
		{
			switch(currentP)
			{
				case MOVE_LEFT:
					p.accelerateX(-1);
					break;
				case MOVE_UP:
					p.accelerateY(-1);
					break;
				case MOVE_RIGHT:
					p.accelerateX(1);
					break;
				case MOVE_DOWN:
					p.accelerateY(1);
					break;
				case ATTACK_BOOST:
					p.attackBoost();
			}
		}
	}
}
