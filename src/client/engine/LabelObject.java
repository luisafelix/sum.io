package client.engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import client.environment.EnvironmentHandler;
import common.environment.GameObject;

public class LabelObject extends GameObject
{
	private ScreenRender screenRender;
	private String labelText;
	
	private Font font;
	
	public LabelObject(int x, int y, int width, int height, int fontSize, String background)
	{
		super(background,x,y,width,height,EnvironmentHandler.PRIORITYRENDER_UI);
		setAbsolute(true);
		labelText = "";
		
		font = new Font("TimesRoman", Font.BOLD, fontSize);
	}
	
	
	//Method called by the ScreenRender.
	public void draw(Graphics g)
	{
		
		g.setColor(Color.BLACK);
		
		FontMetrics metrics = g.getFontMetrics(font);
		
		double newX = x - metrics.stringWidth(labelText)/2;
		double newY = y - metrics.getHeight()/2;
		g.setFont(font); 
		g.drawString(labelText, (int)newX, (int)y);
	
	}
	
	public void updateText(String s)
	{
		labelText = s;
	}


	public void revalidate(int x, int y, int width, int height) 
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

}
