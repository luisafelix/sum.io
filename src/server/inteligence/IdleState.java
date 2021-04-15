package server.inteligence;

import java.io.Serializable;
import java.util.ArrayList;

import common.environment.GameObject;
import common.environment.Player;

public class IdleState extends AbstractFSMState
{
	
	public IdleState(PlayerBot player)
	{
		super(player);
	}
	
	@Override
	public void updateState() 
	{
		//Change the state of the ui
		ArrayList<Player> playerMap = player.getInteligenceBrain().getEnvironmentHandler().getPlayerMap();
		ArrayList<GameObject> interactableObjects = player.getInteligenceBrain().getEnvironmentHandler().getInteractableObjects();
		

		double squareMaxDist = Math.pow(player.getBotViewRange(), 2);
		
		
		//FIXME: HardCoded
		Player target= null;
		double min = 5000000;
		
		for(Player p : playerMap)
		{
			min = 5000000;
			//Test to search for a player that is not himself.
			if(!player.equals(p) && p.isAwake())
			{
				
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
			FollowState fState = new FollowState(player,target);
			this.exitState();
			player.getFiniteStateMachine().enterState(fState);
			return;
		}
		
		//Serching for boost if it is necessary
		GameObject boost = null;
		min = 5000000;
			
		for(GameObject g : interactableObjects)
			{
				//Test to search for a player that is not himself.
				if(!player.equals(g) && g.isAwake())
				{
					double squareDist = player.squareDistanceTo(g);
					if(squareDist < squareMaxDist && min > squareDist)
					{
						boost = g;
						min = squareDist;
					}
				}
		}
		
		if(boost!=null)
		{
			SearchBoostState sState = new SearchBoostState(player,boost);
			this.exitState();
			player.getFiniteStateMachine().enterState(sState);
			return;
		}
		
		player.getFiniteStateMachine().enterState(new CentralizeState(player));
		//TODO: Create a random and smartMovement to the bot.
	}
}
