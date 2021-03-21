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
		screenRender = new ScreenRender(this,window.getBufferStrategy());
		inputHandler = new InputHandler(this);
		window.addKeyListener(new KeyboardListener(inputHandler));
		
	}
	
	public ScreenRender getScreenRender() {return screenRender;}
	public MainClient getMainClient() {return callback;}
	public InputHandler getInputHandler() { return inputHandler;}
	public Window getWindow() { return window;}
	
	public void sendActionPack(ActionPack aPack)
	{
		ActionHandler.doPlayerAction(aPack);
		callback.getEngineHandler().getScreenRender().setOrigin((int)aPack.getPlayer().getX(),(int)aPack.getPlayer().getY());
		callback.getCommsHandler().sendActionPack(aPack);
	}
}
