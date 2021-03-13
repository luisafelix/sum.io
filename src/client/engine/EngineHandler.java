package client.engine;

import client.MainClient;
import common.communication.ActionPack;
import common.environment.ActionHandler;

public class EngineHandler {

	private InputHandler inputHandler;
	private ScreenRender screenRender;
	private Window window;
	private MainClient callback;
	
	public EngineHandler(MainClient callback)
	{
		this.callback = callback;
		window = new Window("Client",600,600);;
		screenRender = new ScreenRender(this);
		window.addPanel(screenRender);
		inputHandler = new InputHandler(this);
		//FIXME: ? adding a player here
		window.addKeyListener(new KeyboardListener(inputHandler));
	}
	
	public ScreenRender getScreenRender() {return screenRender;}
	public MainClient getMainClient() {return callback;}
	public InputHandler getInputHandler() { return inputHandler;}
	
	public void sendActionPack(ActionPack aPack)
	{
		ActionHandler.doPlayerAction(aPack);
		callback.getEngineHandler().getScreenRender().setOrigin(aPack.getPlayer().getX(),aPack.getPlayer().getY());
		callback.getCommsHandler().sendActionPack(aPack);
	}
}
