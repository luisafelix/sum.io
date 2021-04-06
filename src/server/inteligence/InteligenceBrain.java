package server.inteligence;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.Timer;

import common.communication.PlayerAction;
import common.environment.ActionHandler;
import common.environment.Player;
import server.environment.EnvironmentHandler;

public class InteligenceBrain
{

	private ArrayList<PlayerBot> bots;
	private EnvironmentHandler callback;
	private ArrayList<Player> playerMap;
	
	private Timer updateTimer;
	private int updateRate = 20;
	
	private static int botCount = 0;
	
	public InteligenceBrain(EnvironmentHandler callback)
	{
		bots = new ArrayList<PlayerBot>();
		this.callback = callback;
		setupUpdateTimer();
		updateTimer.start();
	}
	
	public EnvironmentHandler getEnvironmentHandler() {return callback;}
	
	public void createBot(int botX,int botY)
	{
		PlayerBot pb = new PlayerBot(this,"ball11",botX,botY,50,50,botCount);
		bots.add(pb);
		callback.connectPlayer(pb);
		botCount++;
		
	}
	
	private void setupUpdateTimer()
	{
		updateTimer = new Timer(updateRate,
							new ActionListener() {
								public void actionPerformed(ActionEvent ae)
								{
									updateBotAction();
								}
							});
	}
	
	private void updateBotAction()
	{
		for(PlayerBot p : bots )
		{
			p.updateAction();
		}
	}
}
