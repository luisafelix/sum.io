package client.environment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.Timer;

import client.MainClient;
import common.communication.ActionPack;
import common.communication.SyncPack;
import common.environment.ActionHandler;
import common.environment.GameObject;
import common.environment.Platform;
import common.environment.Player;
import client.engine.EngineHandler;

public class EnvironmentHandler 
{
	private ActionHandler actionHandler;
	private ArrayList<Player> playerMap;
	private Platform platform;
	private Player himself;
	private MainClient callback;
	
	public EnvironmentHandler(MainClient callback)
	{
		actionHandler = new ActionHandler();
		playerMap = new ArrayList<Player>();
		this.callback = callback;
	}
	
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
		callback.getEngineHandler().getScreenRender().setOrigin(sPack.getPlayer().getX(),sPack.getPlayer().getY());
		//Platform setup in client side
		if(platform == null)
		{
			this.platform = sPack.getPlatform();
			engineHandler.getScreenRender().addToRender((GameObject)this.platform);
		}
		
		if(engineHandler != null)
		{
			//FIXME: We do not need to clear the render. Just update the position
			for(Player p : playerMap)
			{
				engineHandler.getScreenRender().addToRender((GameObject)p);
			}
			engineHandler.getScreenRender().repaint();
		}
	}
	public Player getPlayerClient() {return himself;}
	
}
