package server.inteligence;

import java.io.Serializable;
import java.util.ArrayList;

import common.environment.Player;

public class IdleState extends AbstractFSMState
{
	
	private InteligenceBrain inteligenceBrain;
	private int botViewRange = 10;
	
	public IdleState(InteligenceBrain inteligenceBrain,PlayerBot player)
	{
		super(player);
		this.inteligenceBrain = inteligenceBrain;
		
	}
	@Override
	public boolean enterState()
	{
		boolean base = super.enterState();
		System.out.println("Entered Idle State");
		return base;
	}
	
	@Override
	public boolean exitState()
	{
		boolean base = super.exitState();
		System.out.println("Exited IdleState");
		return base;
	}
	
	@Override
	public void updateState() 
	{
		//Change the state of the ui
		
		ArrayList<Player> playerMap = inteligenceBrain.getEnvironmentHandler().getPlayerMap();
		Player target= null;
		
		//FIXME: HardCoded
		double min = 5000000;
		for(Player p : playerMap)
		{
			//Test to search for a player that is not himself.
			if(!player.equals(p) && p.isAwake())
			{
				double squareMaxDist = Math.pow(player.getBotViewRange(), 2);
				double squareDist = player.squareDistanceTo(p);
				if(squareDist < squareMaxDist && min > squareDist)
				{
					target = p;
					min = squareDist;
				}
			}
		}
		if(target!=null)
		{
			FollowState fState = new FollowState(player);
			fState.setTarget(target);
			this.exitState();
			player.getFiniteStateMachine().enterState(fState);
			return;
		}
		
		//TODO: Create a random and smartMovement to the bot.

	}

}
