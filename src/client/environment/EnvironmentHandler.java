package client.environment;

import java.util.ArrayList;
import client.MainClient;
import common.communication.SyncPack;
import common.environment.GameObject;
import common.environment.Platform;
import common.environment.Player;
import client.engine.EngineHandler;

public class EnvironmentHandler 
{
	private ArrayList<Player> playerMap;
	private Platform platform;
	private Player himself;
	private MainClient callback;
	
	public EnvironmentHandler(MainClient callback)
	{
		playerMap = new ArrayList<Player>();
		this.callback = callback;
	}
	
	public Player getPlayerClient() {return himself;}
	
	public void syncClient(SyncPack sPack)
	{
		EngineHandler engineHandler = callback.getEngineHandler();
		this.playerMap = sPack.getPlayerMap();
		
		//Player setup in client side
		if(himself == null)
		{
			himself = sPack.getPlayer();
			callback.getEngineHandler().getInputHandler().setPlayer(himself);
		}
		
		//Platform setup in client side
		if(platform == null)
		{
			this.platform = sPack.getPlatform();
			engineHandler.getScreenRender().addToRender((GameObject)this.platform);
		}
		
		if(engineHandler != null)
		{
			for(Player p : playerMap)
			{
				engineHandler.getScreenRender().addToRender((GameObject)p);
			}
		}
		callback.getEngineHandler().getScreenRender().setOrigin((int)himself.getX(),(int)himself.getY());
	}
}
