package client.lobby;

import java.awt.Graphics;

import javax.swing.JButton;

public class AddButton extends JButton
{
	
	LobbyPanel callback;
	public AddButton(LobbyPanel callback)
	{
		super();
		this.callback = callback;
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		//g.setColor(callback.getGreen());
		//g.setColor(callback.getLightGreen());
		//g.fillRect(0, 0, getWidth(), getHeight());
		
		//g.setColor(callback.getGreen());
		//g.fillRect(0,4*getHeight()/5,getWidth(),getHeight()-4*getHeight()/5);
		//g.fillRect(4*getWidth()/5,0,getWidth()-4*getWidth()/5,getHeight());
		//super.paintComponent(g);
	}
}
