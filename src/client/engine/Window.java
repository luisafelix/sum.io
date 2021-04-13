package client.engine;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyListener;
import java.awt.image.renderable.RenderableImage;

import javax.swing.JPanel;

public class Window{
	
	//FIXME: Hard coded
	public final static int BORDER_WIDTH = 7;
	public final static int BORDER_HEIGHT = 30;
	
	private EngineHandler callback;
	private JFrame window;
	
	public Window(EngineHandler callback, String title, int w, int h)
	{
		window = new JFrame(title);
		this.callback = callback;
		
		window.setSize(w,h);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBackground(new Color(142,203,224));
		
		window.setLocationRelativeTo(null);
		
		window.addComponentListener(new ComponentAdapter() 
														{
														public void componentResized(ComponentEvent componentEvent) {
															onWindowResized();
													    }	
														});
		window.setVisible(true);
		window.createBufferStrategy(2);
	}
	
	public Window(EngineHandler callback, JFrame window)
	{
		this.window = window;
		this.window.removeAll();
		
		this.callback = callback;
		
		window.setBackground(new Color(142,203,224));
		window.addComponentListener(new ComponentAdapter() 
		{
		public void componentResized(ComponentEvent componentEvent) {
			onWindowResized();
	    }	
		});
		window.createBufferStrategy(2);
	}
	
	public void onWindowResized()
	{
		if(callback.getUserInterface() != null)
		{
			callback.getUserInterface().revalidate();
		}
	}

	public JFrame getJFrame() {return window;}
}

