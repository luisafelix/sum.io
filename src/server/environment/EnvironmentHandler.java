package server.environment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.Timer;

import common.communication.ActionPack;
import common.communication.SyncPack;
import common.environment.ActionHandler;
import common.environment.CircleColider;
import common.environment.Platform;
import common.environment.Player;
import server.MainServer;

public class EnvironmentHandler 
{
	
	private final int PRIORITYRENDER_BACKGROUND = 1;
	private final int PRIORITYRENDER_PLAYER = 100;
	
	private final int PLATFORM_SIZE = 1200;
	
	private ArrayList<Player> playerMap;
	private Platform platform;
	private MainServer callback;
	
	private SyncPack syncPack;
	
	private Timer updateTimer;
	private int updateRate = 20;
	
	private double friction = 0.02;
	
	//FIXME: temp
	private static int clientNumber = 1;
	
	public EnvironmentHandler(MainServer callback)
	{
		playerMap = new ArrayList<Player>();
		this.callback = callback;
		syncPack = new SyncPack();
		setupPlatform();
		setupUpdateTimer();
		updateTimer.start();
	}
	
	public ArrayList<Player> getPlayerMap() {return playerMap;}
	public SyncPack getSyncPack() {return syncPack;}
	
	public void connectPlayer(String clientIP)
	{
		//TODO: A system that stores the ips already connected, store the position and the caracteristics of the player
		Player currentPlayer = new Player(
										"ball"+clientNumber,
										(int)( 600* Math.cos(Math.PI/6 *(clientNumber-1))),
										(int)( 600* Math.sin(Math.PI/6*(clientNumber-1))),
										50,
										50,
										clientIP,PRIORITYRENDER_PLAYER
										);
		clientNumber++;
		callback.getEngineHandler().getScreenRender().addToRender(currentPlayer);
		playerMap.add(currentPlayer);
		syncPack.addPersonalPlayer(currentPlayer);
		syncPack.addPlayerMap(playerMap);
		callback.getCommsHandler().sendSyncPack(syncPack);
	}
	
	private void setupPlatform()
	{
		Platform platform = new Platform("platform2",0,0,1600,1600,PRIORITYRENDER_BACKGROUND);
		callback.getEngineHandler().getScreenRender().addToRender(platform);
		this.platform = platform;
		syncPack.addPlatform(platform);
	}
	
	private void setupUpdateTimer()
	{
		updateTimer = new Timer(updateRate,
							new ActionListener() {
								public void actionPerformed(ActionEvent ae)
								{
									updateEnvironment();
								}
							});
	}
	
	//Take care to insert codes here, because this methode is called really fast
	public void updateEnvironment()
	{
		colisionHanlder();
		movementHandler();
	}
	
	public void doPlayerAction(ActionPack aPack)
	{
		//Update the pointer of the player that needs to be moved
		//FIXME: Use a while loop.
		for(Player p : playerMap)
		{
			if(p.equals(aPack.getPlayer()))
			{
				aPack.setPlayer(p);
			}
		}
		ActionHandler.doPlayerAction(aPack);
		callback.getEngineHandler().getScreenRender().repaint();
	}
	
	private void colisionHanlder()
	{
		//FIXME: This is a simple and a bad methode to find a colision. There are more fancy ways to do the same thing
		for(int i = 0; i< playerMap.size();i++)
		{
			Player p1 = playerMap.get(i);
			for(int j = 0; j<i ;j++)
			{
				Player p2 = playerMap.get(j);
				p1.didCollidTo((CircleColider)p2);
			}
		}
	}
	
	private void movementHandler() 
	{
		for(Player pTemp : playerMap)
		{
			if(pTemp.isMoving())
			{
				pTemp.translate((int)pTemp.getSpeedX(),(int)pTemp.getSpeedY());
				
				pTemp.setSpeedX(pTemp.getSpeedX()*(1-friction));
				pTemp.setSpeedY(pTemp.getSpeedY()*(1-friction));
				
				if(Math.abs(pTemp.getSpeedX()) < 0.001)
				{
					pTemp.setSpeedX(0);
				}
				if(Math.abs(pTemp.getSpeedY()) < 0.001)
				{
					pTemp.setSpeedY(0);
				}
				
				callback.getEngineHandler().getScreenRender().repaint();
			}
		}
	}
	
}
