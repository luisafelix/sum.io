package server.inteligence;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.Timer;

import common.communication.PlayerAction;
import common.environment.ActionHandler;
import common.environment.Player;
import server.environment.EnvironmentHandler;

public class InteligenceBrain 
{

	private LinkedList<PlayerBot> bots;
	private EnvironmentHandler callback;
	
	private Timer updateTimer;
	private int updateRate = 20;
	
	public InteligenceBrain(EnvironmentHandler callback)
	{
		bots = new LinkedList<PlayerBot>();
		PlayerBot pb1 = new PlayerBot("ball11",0,0,50,50);
		bots.add(pb1);
		callback.connectPlayer(pb1);
		
		setupUpdateTimer();
		updateTimer.start();
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
		bots.get(0).updateAction();
	}
}
