package client.engine;

import client.MainClient;
import common.communication.ActionPack;

public class EngineHandler {

	private ScreenRender screenRender;
	private Window window;
	private MainClient callback;
	
	public EngineHandler(MainClient callback)
	{
		this.callback = callback;
		window = new Window("Application",600,600);;
		screenRender = new ScreenRender();
		window.addPanel(screenRender);
		
		//FIXME: ? adding a player here
		window.addKeyListener(new KeyboardListener(new UserAction(this,callback.getPlayer())));
		screenRender.addToRender(callback.getPlayer());
	}
	
	public void sendActionPack(ActionPack aPack)
	{
		callback.sendActionPack(aPack);
		screenRender.repaint();
	}
	
}
