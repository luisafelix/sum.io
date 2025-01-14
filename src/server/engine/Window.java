package server.engine;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

public class Window extends JFrame{
	
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

