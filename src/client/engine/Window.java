package client.engine;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.event.KeyListener;
import java.awt.image.renderable.RenderableImage;

import javax.swing.JPanel;

public class Window extends JFrame{
	
	//FIXME: Hard coded
	public final static int BORDER_WIDTH = 7;
	public final static int BORDER_HEIGHT = 30;
	
	private JPanel mainPanel;
	
	public Window(String title, int w, int h)
	{
		super(title);
		setSize(w,h);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		mainPanel = new JPanel(new BorderLayout());
		
		add(mainPanel);
		setVisible(true);
		createBufferStrategy(2);
	}

	public void addPanel(JPanel panel, int position)
	{
		mainPanel.add(panel,position);
		mainPanel.revalidate();
		mainPanel.repaint();	
	}
	public void addPanel(JPanel screen)
	{
		this.addPanel(screen,0);
	}
}

