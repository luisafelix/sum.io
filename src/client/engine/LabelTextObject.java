package client.engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class LabelTextObject extends LabelObject
{
	
	private String labelText;
	private int fontSize;
	private Font font;
	private Color fontColor;

	public LabelTextObject(ScreenRender screenRender,String background, int x, int y, int width, int height, int fontSize) 
	{
		super(screenRender,background,x, y, width, height);
		this.fontSize = fontSize;
		labelText = "";
		font = new Font("Verdana", Font.BOLD, this.fontSize);
		fontColor = new Color(14,44,83);
	}
	
	public void draw(Graphics g)
	{
		g.setColor(fontColor);
		
		FontMetrics metrics = g.getFontMetrics(font);
		
		double newX = x - metrics.stringWidth(labelText)/2;
		double newY = y + metrics.getHeight()/4;
		g.setFont(font); 
		g.drawString(labelText, (int)newX, (int)newY);
	}
	
	public void updateText(String s)
	{
		labelText = s;
	}

}
