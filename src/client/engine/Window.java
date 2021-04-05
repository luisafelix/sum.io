package client.engine;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyListener;
import java.awt.image.renderable.RenderableImage;

import javax.swing.JPanel;

public class Window extends JFrame{
	
	//FIXME: Hard coded
	public final static int BORDER_WIDTH = 7;
	public final static int BORDER_HEIGHT = 30;
	
	private EngineHandler callback;
	
	public Window(EngineHandler callback, String title, int w, int h)
	{
		super(title);
		this.callback = callback;
		
		setSize(w,h);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(new Color(142,203,224));
		
		setLocationRelativeTo(null);
		
		this.addComponentListener(new ComponentAdapter() 
														{
														public void componentResized(ComponentEvent componentEvent) {
															onWindowResized();
													    }	
														});
		setVisible(true);
		createBufferStrategy(2);
	}
	
	public void onWindowResized()
	{
		if(callback.getUserInterface() != null)
		{
			callback.getUserInterface().revalidate();
		}
	}
}

