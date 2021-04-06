package server.inteligence;

import java.util.LinkedList;

import common.communication.ActionPack;
import common.communication.PlayerAction;
import common.environment.ActionHandler;
import common.environment.Player;

public class FollowState extends AbstractFSMState
{
	
	private Player target;
	
	public FollowState(PlayerBot player)
	{
		super(player);
		target = null;
	}
	
	public void setTarget(Player target){this.target = target;}
	
	@Override
	public boolean enterState()
	{
		boolean base = super.enterState();
		
		if(target == null)
		{
			return false;
		}
		System.out.println("Entered FollowState!");
		return base;
	}
	
	@Override
	public boolean exitState()
	{
		boolean base = super.exitState();
		
		target = null;
		
		return base;
	}
	
	@Override
	public void updateState() 
	{
		ActionPack aPack = player.getActionPack();
		LinkedList<PlayerAction> playerActionList = aPack.getPlayerActionList();
		
		double wishPosX = target.getX() + target.getSpeedX();
		double wishPosY = target.getY() + target.getSpeedY();
		
		double directionX = wishPosX - player.getX();
		double directionY = wishPosY - player.getY();
		
		//aPack.reset();
		
		
		if(directionX > 0)
		{
			if(playerActionList.contains(PlayerAction.MOVE_LEFT))
			{
				aPack.updateAction(PlayerAction.MOVE_LEFT);
			}
			if(!playerActionList.contains(PlayerAction.MOVE_RIGHT))
			{
				aPack.updateAction(PlayerAction.MOVE_RIGHT);
			}
		}
		else if(directionX < 0)
		{
			if(playerActionList.contains(PlayerAction.MOVE_RIGHT))
			{
				aPack.updateAction(PlayerAction.MOVE_RIGHT);
			}
			
			if(!playerActionList.contains(PlayerAction.MOVE_LEFT))
			{
				aPack.updateAction(PlayerAction.MOVE_LEFT);
			}
		}
		
		if(directionY < 0)
		{
			if(playerActionList.contains(PlayerAction.MOVE_DOWN))
			{
				aPack.updateAction(PlayerAction.MOVE_DOWN);
			}
			
			if(!playerActionList.contains(PlayerAction.MOVE_UP))
			{
				aPack.updateAction(PlayerAction.MOVE_UP);
			}
		}
		else if(directionY > 0)
		{
			if(playerActionList.contains(PlayerAction.MOVE_UP))
			{
				aPack.updateAction(PlayerAction.MOVE_UP);
			}
			if(!playerActionList.contains(PlayerAction.MOVE_DOWN))
			{
				aPack.updateAction(PlayerAction.MOVE_DOWN);
			}
		}
		ActionHandler.doPlayerAction(aPack);
		
	}

}
