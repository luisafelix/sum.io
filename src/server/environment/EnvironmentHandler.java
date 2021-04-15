package server.environment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.Timer;

import common.communication.ActionPack;
import common.communication.SyncPack;
import common.environment.ActionHandler;
import common.environment.CircleColider;
import common.environment.CollectableBoost;
import common.environment.GameObject;
import common.environment.Platform;
import common.environment.Player;
import server.LaunchServer;
import server.inteligence.InteligenceBrain;
import server.inteligence.PlayerBot;

public class EnvironmentHandler
{
	
	public static final int PRIORITYRENDER_BACKGROUND = 100;
	public static final int PRIORITYRENDER_PLAYER = 1000;
	
	private final int PLATFORM_SIZE = 1600;
	
	private ArrayList<Player> playerMap;
	private ArrayList<GameObject> interactableObjects;
	
	private Platform platform;
	private LaunchServer callback;
	private InteligenceBrain inteligenceBrain;
	
	private SyncPack syncPack;
	
	private Timer updateTimer;
	private int updateRate = 20;
	
	private int playersRemainingCount = 0;
	private double friction = 0.02;
	
	private int aparitionBoostTime = 2000; //
	
	private int boostTime = 0;
	
	//FIXME: temp
	private static int playerNumber = 1;
	
	public EnvironmentHandler(LaunchServer callback)
	{
		playerMap = new ArrayList<Player>();
		interactableObjects = new ArrayList<GameObject>();
		
		this.callback = callback;
		syncPack = new SyncPack();
		
		inteligenceBrain = new InteligenceBrain(this);
		
		setupPlatform();
		setupUpdateTimer();
		
		
	}
	
	public ArrayList<Player> getPlayerMap() {return playerMap;}
	public SyncPack getSyncPack() {return syncPack;}
	public InteligenceBrain getInteligenceBrain() {return inteligenceBrain;}
	public ArrayList<GameObject> getInteractableObjects(){return interactableObjects;}
	
	public void connectPlayer(String clientIP)
	{
		int xPos = (int)( 700* Math.cos(Math.PI/6 *(playerNumber-1)));
		int yPos = (int)( 700* Math.sin(Math.PI/6*(playerNumber-1)));
		
		
		if(clientIP.startsWith(".BOT"))
		{
			inteligenceBrain.createBot(xPos, yPos);
			return;
		}
		
		Player currentPlayer = new Player(
										"devil",
										xPos,
										yPos,
										50,
										50,
										clientIP,
										PRIORITYRENDER_PLAYER
										);
		connectPlayer(currentPlayer);
	}
	
	public void connectPlayer(Player currentPlayer)
	{
		playerMap.add(currentPlayer);
		syncPack.addPersonalPlayer(currentPlayer);
		syncPack.addPlayerMap(playerMap);
		callback.getCommsHandler().generateSyncPack();
		playerNumber++;
		increasePlayersRemaining();
	}
	
	private void increasePlayersRemaining()
	{
		playersRemainingCount++;
		syncPack.setPlayersRemainingCount(playersRemainingCount);
	}
	
	private void decreasePlayersRemaining()
	{
		playersRemainingCount--;
		syncPack.setPlayersRemainingCount(playersRemainingCount);
	}
	
	private void setupPlatform()
	{
		Platform platform = new Platform("platform1",0,0,PLATFORM_SIZE,PLATFORM_SIZE,PRIORITYRENDER_BACKGROUND);
		//callback.getEngineHandler().getScreenRender().addToRender(platform);
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
	
	private void addBoostHandler()
	{	
		if(boostTime*updateRate >= aparitionBoostTime)
		{
			int spawnRangeX = 700;
			int spawnRangeY = 700;
			
			int randonX = (int) (Math.random()*spawnRangeX)-spawnRangeX/2;
			int randonY = (int) (Math.random()*spawnRangeY)-spawnRangeY/2;
			
			int maxTry = 30;
			CollectableBoost cb = null;
			
			int count = 0;
			boolean isUnique = false;
			while(count<maxTry && !isUnique)
			{
				isUnique = true;
				
				Iterator<GameObject> itr = interactableObjects.iterator();
				do
				{
					
					if(interactableObjects.isEmpty())
					{
						cb = new CollectableBoost("boost", randonX, randonY, 50, 50);
						break;
					}
					GameObject go = itr.next();
					cb = new CollectableBoost("boost", randonX, randonY, 50, 50);
					if(((CircleColider)go).hasCollidedTo(cb) && go.isAwake())
					{
						isUnique = false;
						break;
					}
				}
				while(itr.hasNext());
				
				count ++;
			}
			boostTime = 0;
			
			if(cb != null && isUnique)
			{
				interactableObjects.add(cb);
				syncPack.addInteractableObject(cb);
			}
		}
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
	}
	
	private void colisionHanlder()
	{
		//FIXME: This is a simple and a bad methode to find a colision. There are more fancy ways to do the same thing
		for(int i = 0; i< playerMap.size();i++)
		{
			Player p1 = playerMap.get(i);
			if(p1.isAwake())
			{
				for(int j = 0; j<i ;j++)
				{
					Player p2 = playerMap.get(j);
					if(p2.isAwake())
					{
						onPlayerCollided(p1,p2);
					}
				}
				
				if(!p1.hasCollidedTo((CircleColider)platform))
				{
					onPlayerOut(p1);
				}
				
				for(GameObject go: interactableObjects)
				{
					if(go.isAwake() && ((CircleColider)go).hasCollidedTo(p1))
					{
						
					}
				}
				
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
			}
		}
	}
	
	public void startEnvironnment()
	{
		updateTimer.start();
	}
	
	private void onPlayerCollided(Player p1,Player p2)
	{
		p1.hasCollidedTo((CircleColider)p2);
	}
	
	private void onPlayerOut(Player p)
	{
		if(p instanceof PlayerBot)
		{
			//TODO: Destroy bot
		}
		p.sleepObject();
		decreasePlayersRemaining();
	}
	
	private void updateTime()
	{
		boostTime++;
	}
	
	//Take care to insert codes here, because this methode is called really fast
	public void updateEnvironment()
	{
		colisionHanlder();
		movementHandler();
		addBoostHandler();
		
		updateTime();
	}
}
