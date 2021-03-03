package client.engine;

import client.communication.CommsHandler;
import common.communication.ActionPack;
import common.communication.PlayerAction;

public class UserAction
{
	private static int NUMBER_OF_KEYS = 126;
	private boolean[] keyStatus;
	
	private ActionPack aPack;
	private EngineHandler callback;
	 
	//FIXME: Players priority render.
	public UserAction (EngineHandler engineHandler)
	{
		keyStatus = new boolean[NUMBER_OF_KEYS];
		this.callback = engineHandler;
		aPack = new ActionPack();
	}
	
	public void onKeyPressed(int keyId)
	{
		if(!keyStatus[keyId])
		{
			keyStatus[keyId]=true;
			updateActionPack(keyId);
			sendActionPack();
		}
	}
	
	public void onKeyReleased(int keyId)
	{
		if(keyStatus[keyId])
		{
			keyStatus[keyId] = false;
			updateActionPack(keyId);
			sendActionPack();
		}
	}
	
	private ActionPack updateActionPack(int keyId) 
	{
		switch(keyId)
		{
			case 37:
				aPack.updateAction(PlayerAction.MOVE_LEFT);
			break;
			case 38:
				aPack.updateAction(PlayerAction.MOVE_UP);
			break;
			case 39:
				aPack.updateAction(PlayerAction.MOVE_RIGHT);
			break;
			case 40:
				aPack.updateAction(PlayerAction.MOVE_DOWN);
			break;
		}
		return aPack;		
	}
	
	private void sendActionPack()
	{
		callback.sendActionPack(aPack);
	}
}

