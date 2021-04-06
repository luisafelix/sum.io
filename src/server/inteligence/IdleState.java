package server.inteligence;

import java.io.Serializable;
import java.util.ArrayList;

import common.environment.Player;

public class IdleState extends AbstractFSMState
{
	
	private InteligenceBrain inteligenceBrain;
	private int botViewRange = 1000;
	
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
		
		double squareMaxDist = Math.pow(botViewRange, 2);
		double min = squareMaxDist;
		
		for(Player p : playerMap)
		{
			//Test to search for a player that is not himself.
			if(!player.equals(p))
			{
				double squareDist = player.squareDistanceTo(p);
				if(squareDist < squareMaxDist && min > squareDist)
				{
					target = p;
				}
			}
		}
		
		if(target!=null)
		{
			FollowState fState = new FollowState(player);
			fState.setTarget(target);
			player.getFiniteStateMachine().enterState(fState);
		}

		//System.out.println("Updating State!");
	}

}
