package server.inteligence;

import java.io.Serializable;

public class FiniteStateMachine
{
	AbstractFSMState startingState;
	AbstractFSMState currentState;
	
	public FiniteStateMachine (AbstractFSMState startingState)
	{
		currentState = null;
		this.startingState = startingState;
		start();
	}
	
	public void start()
	{
		if(startingState != null)
		{
			enterState(startingState);
		}
	}
	
	public void update()
	{
		if(currentState != null)
		{
			currentState.updateState();
		}
	}
	
	public void enterState(AbstractFSMState nextState)
	{
		if(nextState == null)
		{
			return;
		}
		
		currentState = nextState;
		currentState.enterState();
	}
}
