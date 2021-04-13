package server.engine;

import common.communication.ActionPack;
import server.LaunchServer;

public class EngineHandler {

	private ScreenRender screenRender;
	private Window window;
	private LaunchServer callback;
	
	public EngineHandler(LaunchServer callback)
	{
		this.callback = callback;
		/*
		window = new Window("Server",600,600);
		screenRender = new ScreenRender();
		window.addPanel(screenRender);
		screenRender.repaint();
		*/
	}
	
	public ScreenRender getScreenRender() 
	{
		return screenRender;
	}
}
