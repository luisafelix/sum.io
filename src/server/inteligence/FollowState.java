package server.inteligence;

import java.util.LinkedList;

import common.communication.ActionPack;
import common.communication.PlayerAction;
import common.environment.ActionHandler;
import common.environment.Player;

public class FollowState extends AbstractFSMState
{
	
	private Player target;
	
	public FollowState(PlayerBot player,Player target)
	{
		super(player);
		this.target = target;
	}
	
	public void setTarget(Player target){}
	
	@Override
	public boolean enterState()
	{
		boolean base = super.enterState();
		
		if(target == null)
		{
			return false;
		}
		return base;
	}
	
	@Override
	public boolean exitState()
	{
		boolean base = super.exitState();
		target = null;
		player.getFiniteStateMachine().enterState(new IdleState(player));
		return base;
	}
	
	@Override
	public void updateState() 
	{
		//aPack.reset();
		double wishPosX = target.getX() + target.getSpeedX();
		double wishPosY = target.getY() + target.getSpeedY();
		
		double directionX = wishPosX - player.getX();
		double directionY = wishPosY - player.getY();
		
		player.moveTo(directionX,directionY);
		
		int attackRange = 100;
		
		if(player.squareDistanceTo(target) < Math.pow(attackRange, 2))
		{
			player.attackBoost();
		}
		//Lose the target when mission accomplished
		if(!target.isAwake())
		{
			this.exitState();	
			return;
		}
		
		//Lose the player when the bot is following him
		if(player.squareDistanceTo(target)>Math.pow(player.getBotViewRange(),2))
		{
			this.exitState();
			return;
		}
	}

}
