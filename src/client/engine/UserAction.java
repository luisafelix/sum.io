package client.engine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import client.communication.CommsHandler;
import common.communication.ActionPack;
import common.communication.PlayerAction;
import common.environment.Player;

public class UserAction
{
	private static int NUMBER_OF_KEYS = 126;
	private boolean[] keyStatus;
	
	private Player himself;
	private ActionPack aPack;
	private EngineHandler callback;
	
	//FIXME: Find a nice timer speed, to send a ActionPack to the server. To not saturate
	private Timer actionTimer;
	private int timerSpeed = 30;
	 
	//FIXME: Players priority render.
	public UserAction (EngineHandler engineHandler, Player player)
	{
		keyStatus = new boolean[NUMBER_OF_KEYS];
		this.callback = engineHandler;
		this.himself = player;
		aPack = new ActionPack(himself);
		setupTimer();
	}
	
	private void setupTimer()
	{
		actionTimer = new Timer(timerSpeed,
							new ActionListener() {
								public void actionPerformed(ActionEvent ae)
								{
									sendActionPack();
								}
							});
	}
	
	public void onKeyPressed(int keyId)
	{
		if(!keyStatus[keyId])
		{
			keyStatus[keyId]=true;
			updateActionPack(keyId);
			sendActionPack();
			actionTimer.start();
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
		
		if(aPack.empty())
		{
			actionTimer.stop();
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

