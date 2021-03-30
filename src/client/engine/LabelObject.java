package client.engine;

import java.awt.image.BufferedImage;

import client.environment.EnvironmentHandler;
import common.environment.GameObject;

public class LabelObject extends GameObject
{
	public LabelObject(int x, int y, int width, int height)
	{
		super("background1",x,y,width,height,EnvironmentHandler.PRIORITYRENDER_UI);
		//setAbsolute(true);
	}

}
