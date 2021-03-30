package client.engine;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import common.environment.Player;

public class UserInterface 
{
	private final int PLAYERCOUNT_WIDTH = 200;
	private final int PLAYERCOUNT_HEIGHT = 50;
	
	private EngineHandler callback; 
	private LabelObject playerCount;
	
	public UserInterface(EngineHandler callback)
	{
		this.callback = callback;
		playerCount = new LabelObject(+PLAYERCOUNT_WIDTH/2+Window.BORDER_WIDTH,+PLAYERCOUNT_HEIGHT/2+Window.BORDER_HEIGHT,PLAYERCOUNT_WIDTH,PLAYERCOUNT_HEIGHT);
		playerCount.setAbsolute(true);
		callback.getScreenRender().addToRender(playerCount);
	}
	
	public void update()
	{
		updatePlayerCount();
	}
	
	private void updatePlayerCount()
	{
		
	}
	
}