package client.engine;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class KeyboardListener implements KeyListener
{
	private InputHandler userAction;
	public KeyboardListener(InputHandler userAction)
	{
		this.userAction = userAction;
	}
	@Override
	public void keyPressed(KeyEvent e)
	{
		userAction.onKeyPressed(e.getKeyCode());
	}
	@Override
	public void keyReleased(KeyEvent e)
	{
		userAction.onKeyReleased(e.getKeyCode());
	}
	@Override
	public void keyTyped(KeyEvent e)
	{
	
	}
}

