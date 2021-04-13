package client.lobby;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.ViewportLayout;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class LobbyPanel extends JPanel
{
	
	public static final int BORDER_SIZE = 3;
	
	protected LobbyHandler callback;
	
	public static final Font FONT = new Font("Verdana", Font.BOLD, 15); 
	
	//private Color lightBlue = new Color(142,203,224);
	public static final Color LIGHT_BLUE = new Color(45,136,252);
	public static final Color BLUE = new Color(38,116,216);
	public static final Color DARK_BLUE = new Color(14,44,83);
	public static final Color GREEN = new Color(38,216,148);
	public static final Color LIGHT_GREEN = new Color(43,249,170);
	public static final Color DARK_GREEN = new Color(38,173,148);
	
	private Color currentLightColor;
	private Color currentDarkColor;
	private Color currentColor;
	
	public LobbyPanel(LobbyHandler callback)
	{
		super(new GridLayout(1,3,10,0));
		this.callback = callback;
		
		setupColors();
	}
	
	public void setPalet(int value)
	{
		switch (value)
		{
			case 0:
				currentLightColor = LIGHT_BLUE;
				currentDarkColor = DARK_BLUE;
				currentColor = BLUE;
				break;
			case 1:
				currentLightColor = LIGHT_GREEN;
				currentDarkColor = DARK_GREEN;
				currentColor = GREEN;
				break;
			default: 
				currentLightColor = LIGHT_BLUE;
				currentDarkColor = DARK_BLUE;
				currentColor = BLUE;
			break;
		}
	}
	
	private void setupColors()
	{
		
		super.setBackground(DARK_BLUE);
		
		currentLightColor = LIGHT_BLUE;
		currentDarkColor = DARK_BLUE;
		currentColor = BLUE;
	}	
	
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		super.setBackground(currentDarkColor);
		g.setColor(currentLightColor);
		g.fillRect(BORDER_SIZE, BORDER_SIZE, getWidth()-2*BORDER_SIZE, getHeight()-2*BORDER_SIZE);
		g.setColor(currentColor);
		g.fillRect(BORDER_SIZE,4*getHeight()/5-BORDER_SIZE,getWidth()-2*BORDER_SIZE,getHeight()-4*getHeight()/5);
		g.fillRect(25*getWidth()/26-BORDER_SIZE,BORDER_SIZE,getWidth()-25*getWidth()/26,getHeight()-2*BORDER_SIZE);
	}
	
}
