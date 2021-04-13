package client.engine;

import javax.swing.JFrame;

import client.MainClient;
import common.communication.ActionPack;
import common.environment.ActionHandler;

public class EngineHandler {

	private InputHandler inputHandler;
	private ScreenRender screenRender;
	private Window window;
	private MainClient callback;
	private UserInterface userInterface;
	
	public EngineHandler(MainClient callback, JFrame jframe)
	{
		this.callback = callback;
		this.window = new Window(this,jframe);
		
		screenRender = new ScreenRender(this,window.getJFrame().getBufferStrategy());
		inputHandler = new InputHandler(this);
		
		//Why i need this to work properly ?
		this.window.getJFrame().requestFocus();
		window.getJFrame().addKeyListener(new KeyboardListener(inputHandler));
		
		userInterface = new UserInterface(this);
	}
	
	public ScreenRender getScreenRender() {return screenRender;}
	public MainClient getMainClient() {return callback;}
	public InputHandler getInputHandler() { return inputHandler;}
	public Window getWindow() { return window;}
	public UserInterface getUserInterface() {return userInterface;}
	
	public void sendActionPack(ActionPack aPack)
	{
		ActionHandler.doPlayerAction(aPack);
		callback.getEngineHandler().getScreenRender().setOrigin((int)aPack.getPlayer().getX(),(int)aPack.getPlayer().getY());
		callback.getCommsHandler().sendActionPack(aPack);
	}
}
