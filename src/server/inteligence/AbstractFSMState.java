package server.inteligence;

import java.io.Serializable;

public abstract class AbstractFSMState
{
	public ExecutionState executionState;
	public ExecutionState getExecutionState() {return executionState;}
	protected PlayerBot player;
	public void setExecutionState(ExecutionState executionState) {this.executionState = executionState;}
	
	public AbstractFSMState(PlayerBot player)
	{
		this.player = player;
		executionState = ExecutionState.NONE;
	}
	
	public boolean enterState()
	{
		executionState = ExecutionState.ACTIVE;
		return true;
	}
	 
	public abstract void updateState();
	
	public boolean exitState()
	{
		executionState = ExecutionState.COMPLETED; 
		return true;
	}
	
}
