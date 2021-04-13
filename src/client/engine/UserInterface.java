package client.engine;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import common.environment.Player;

public class UserInterface 
{
	private final int BORDER_DISTANCE = 20;
	
	private final int PLAYERCOUNT_WIDTH = 200;
	private final int PLAYERCOUNT_HEIGHT = 50;
	private final int PLAYERCOUNT_FONTSIZE = 14;
	private final int FPSCOUNT_WIDTH = 50;
	private final int FPSCOUNT_HEIGHT = 25;
	private final int FPSCOUNT_FONTSIZE = 10;
	
	private final int SLIDERBOOST_WIDTH = 200;
	private final int SLIDERBOOST_HEIGHT = 75;
	
	private EngineHandler callback;
	private LabelTextObject playerCount;
	private LabelTextObject fpsCount;
	
	private LabelSliderObject boostLabel;
	
	public UserInterface(EngineHandler callback)
	{
		this.callback = callback;
		ScreenRender sr = callback.getScreenRender();
		playerCount = new LabelTextObject(sr,"background1",
									+PLAYERCOUNT_WIDTH/2 + Window.BORDER_WIDTH + BORDER_DISTANCE,
									+PLAYERCOUNT_HEIGHT/2 + Window.BORDER_HEIGHT + BORDER_DISTANCE,
									PLAYERCOUNT_WIDTH,
									PLAYERCOUNT_HEIGHT, 
									PLAYERCOUNT_FONTSIZE);
		fpsCount = new LabelTextObject(sr,"null",
									callback.getWindow().getJFrame().getWidth()-FPSCOUNT_WIDTH/2 - Window.BORDER_WIDTH,
									+FPSCOUNT_HEIGHT/2 + Window.BORDER_HEIGHT,
									FPSCOUNT_WIDTH,
									FPSCOUNT_HEIGHT,
									FPSCOUNT_FONTSIZE);
		
		boostLabel = new LabelSliderObject(sr,"speedui",
				"speedslider",
				SLIDERBOOST_WIDTH/2 + Window.BORDER_WIDTH + BORDER_DISTANCE ,
				callback.getWindow().getJFrame().getHeight() - SLIDERBOOST_HEIGHT/2 - Window.BORDER_HEIGHT/2 - BORDER_DISTANCE,
				SLIDERBOOST_WIDTH,
				SLIDERBOOST_HEIGHT);
		
	}
	
	public void setBoost(double boostRatio)
	{
		boostLabel.setSliderRatio(boostRatio);
	}
	
	public void revalidate()
	{
		fpsCount.revalidate(callback.getWindow().getJFrame().getWidth()-FPSCOUNT_WIDTH/2-Window.BORDER_WIDTH,
							+FPSCOUNT_HEIGHT + Window.BORDER_HEIGHT,
							FPSCOUNT_WIDTH,
							FPSCOUNT_HEIGHT);
		
		boostLabel.revalidate(SLIDERBOOST_WIDTH/2 + Window.BORDER_WIDTH + BORDER_DISTANCE ,
							callback.getWindow().getJFrame().getHeight() - SLIDERBOOST_HEIGHT/2 - Window.BORDER_HEIGHT/2 - BORDER_DISTANCE,
							SLIDERBOOST_WIDTH,
							SLIDERBOOST_HEIGHT);
	}
	
	public void update()
	{
		updatePlayerCount();
		updateFpsCount();
		updatePlayerBoost();
	}
	
	private void updatePlayerBoost()
	{
		if(callback.getMainClient().getEnvironmentHandler().getPlayerClient()!= null)
		{
			boostLabel.setSliderRatio(callback.getMainClient().getEnvironmentHandler().getPlayerClient().getBoostQuantity()/100.0);
		}
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
