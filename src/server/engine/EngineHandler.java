package server.engine;

import common.communication.ActionPack;
import server.MainServer;

public class EngineHandler {

	private ScreenRender screenRender;
	private Window window;
	private MainServer callback;
	
	public EngineHandler(MainServer callback)
	{
		this.callback = callback;
		window = new Window("Server",600,600);;
		screenRender = new ScreenRender();
		window.addPanel(screenRender);
		screenRender.repaint();
	}
	
	public ScreenRender getScreenRender() 
	{
		return screenRender;
	}
}
