package server.inteligence;

import java.io.Serializable;
import java.util.ArrayList;

import common.communication.ActionPack;
import common.communication.PlayerAction;
import common.environment.ActionHandler;
import common.environment.Player;
import server.environment.EnvironmentHandler;

public class PlayerBot extends Player
{
	private static String botID = "bot";
	
	private ActionPack aPack;
	private FiniteStateMachine finiteStateMachine;
	private InteligenceBrain inteligenceBrain;
	
	public PlayerBot(InteligenceBrain inteligenceBrain, String name, int x, int y, int width,int height, int botCount)
	{
		super(name,x,y,width,height,botID+botCount,EnvironmentHandler.PRIORITYRENDER_PLAYER);
		aPack = new ActionPack(this);
		this.inteligenceBrain = inteligenceBrain;
		
		finiteStateMachine = new FiniteStateMachine(new IdleState(inteligenceBrain,this));
	}
	
	public ActionPack getActionPack() {return aPack;}
	public void setActionPack(ActionPack aPack) {this.aPack = aPack;}
	public FiniteStateMachine getFiniteStateMachine() {return finiteStateMachine;}

	public void updateAction()
	{
		finiteStateMachine.update();
		/*
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
		*/
	}
}
