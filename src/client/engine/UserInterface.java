package client.engine;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import common.environment.Player;

public class UserInterface 
{
	private final int PLAYERCOUNT_WIDTH = 200;
	private final int PLAYERCOUNT_HEIGHT = 50;
	private final int PLAYERCOUNT_FONTSIZE = 14;
	
	private final int FPSCOUNT_WIDTH = 50;
	private final int FPSCOUNT_HEIGHT = 25;
	private final int FPSCOUNT_FONTSIZE = 10;
	
	private EngineHandler callback;
	private LabelObject playerCount;
	private LabelObject fpsCount;
	
	public UserInterface(EngineHandler callback)
	{
		this.callback = callback;
		playerCount = new LabelObject(+PLAYERCOUNT_WIDTH/2 + Window.BORDER_WIDTH + 20,
									+PLAYERCOUNT_HEIGHT/2 + Window.BORDER_HEIGHT + 20,
									PLAYERCOUNT_WIDTH,
									PLAYERCOUNT_HEIGHT, 
									PLAYERCOUNT_FONTSIZE,
									"background1");
		fpsCount = new LabelObject( callback.getWindow().getWidth()-FPSCOUNT_WIDTH/2-Window.BORDER_WIDTH,
									+FPSCOUNT_HEIGHT/2+Window.BORDER_HEIGHT,
									FPSCOUNT_WIDTH,
									FPSCOUNT_HEIGHT,
									FPSCOUNT_FONTSIZE,
									"null"
									);
		
		
		callback.getScreenRender().addToRender(playerCount);
		callback.getScreenRender().addToRender(fpsCount);
	}
	
	public void revalidate()
	{
		fpsCount.revalidate(callback.getWindow().getWidth()-FPSCOUNT_WIDTH/2-Window.BORDER_WIDTH,
							+FPSCOUNT_HEIGHT/2+Window.BORDER_HEIGHT,
							FPSCOUNT_WIDTH,
							FPSCOUNT_HEIGHT);
	}
	
	public void update()
	{
		updatePlayerCount();
		updateFpsCount();
	}
	
	private void updatePlayerCount()
	{
		playerCount.updateText("Players remaining: " + callback.getMainClient().getEnvironmentHandler().getRemainingPlayers());
	}
	
	private void updateFpsCount()
	{
		fpsCount.updateText("FPS: "+ callback.getScreenRender().getFpsCount());
	}
	
}
