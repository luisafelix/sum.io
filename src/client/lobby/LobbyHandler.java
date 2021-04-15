package client.lobby;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import client.MainClient;
import client.engine.ImageCache;
import common.communication.LobbyPack;

/*
 *  LobbyHandler set up the visual support to manage the multiplayer and add bots.
 */

public class LobbyHandler{
	
	//FIXME: Hard coded
	public final static int BORDER_WIDTH = 7;
	public final static int BORDER_HEIGHT = 30;
	private final int NUMBERMAX_PLAYERS = 12;
	
	private MainClient callback;
	private JPanel mainPanel;
	private ArrayList<JPanel> panelsList;
	private String player;
	private LobbyPack lobbyPack;
	
	private JFrame window;
	private Hashtable<String,BufferedImage> imageMap = null;
	
	
	private static int botCount = 0;
	
	public LobbyHandler(MainClient callback, String title, int w, int h, ImageCache imageCache)
	{
		this.callback = callback;
		imageMap = imageCache.getImageMap();
		
		window = new JFrame(title);
		lobbyPack = new LobbyPack();
		window.setSize(w,h);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		
		setupMainPanel();
		setupPlayerPanel();
		window.add(mainPanel);
		window.setVisible(true);
	}
	
	public LobbyPack getLobbyPack() {return lobbyPack;}
	public JFrame getJFrame(){return window;}
	public String getPlayer() {return player;}
	public Hashtable<String,BufferedImage> getImageMap(){return imageMap;}
	
	private void setupMainPanel()
	{
		mainPanel = new JPanel(new GridLayout(8,2,20,10));
	}
	
	private void setupPlayerPanel()
	{
		panelsList = new ArrayList<JPanel>(NUMBERMAX_PLAYERS);
		
		for(int i = 0;i<2;i++ )
		{
			JPanel empty = new JPanel();
			panelsList.add(empty);
			mainPanel.add(empty);
		}
		
		for(int i = 0; i< NUMBERMAX_PLAYERS;i++)
		{
			PlayerPanel pp = new PlayerPanel(this);
			panelsList.add(pp);
			mainPanel.add(pp);
		}
		
		JPanel empty = new JPanel();
		panelsList.add(empty);
		mainPanel.add(empty);
		
		setupStartButton();
	}
	
	private void setupStartButton()
	{
		LobbyPanel start = new LobbyPanel(this);
		start.setPalet(1);
		
		JButton startBt = new JButton("START");
		startBt.setFont(LobbyPanel.FONT);
		startBt.setContentAreaFilled(false);
		//startBt.setBorderPainted(false);
		startBt.addActionListener(new ActionListener() 
									{
										@Override
										public void actionPerformed(ActionEvent e) 
										{
											onStart();
										}
									}
								 );
		
		start.add(startBt);
		
		panelsList.add(start);
		mainPanel.add(start);
	}
	
	public void addBotToLobby()
	{
		if(lobbyPack.getPlayerList().size()<12)
		{
			lobbyPack.addPlayer(".BOT " + botCount);
			callback.getCommsHandler().sendLobbyPack(lobbyPack);
			botCount++;
		}
	}
	
	public void onStart()
	{	
		lobbyPack.setStartFlag(true);
		callback.getCommsHandler().sendLobbyPack(lobbyPack);
	}
	
	public void onPlayerConnectServer(String name)
	{
		this.player = name;
		lobbyPack.addPlayer(name);
		callback.getCommsHandler().connectNetwork();
		callback.getCommsHandler().sendLobbyPack(lobbyPack);
	}
	
	public void syncLobby(LobbyPack lPack) 
	{
		String playerSkin = "devil";
		lobbyPack = lPack;
		LinkedList<String> playerList = lobbyPack.getPlayerList();
		int index = 2;
		for(String s:playerList)
		{
			((PlayerPanel)panelsList.get(index)).setName(s);
			((PlayerPanel)panelsList.get(index)).disableButton();
			if(s.startsWith(".BOT"))
			{
				((PlayerPanel)panelsList.get(index)).setImage(imageMap.get("bot"));
			}
			else
			{
				((PlayerPanel)panelsList.get(index)).setImage(imageMap.get(playerSkin));
			}
			index++;
		}
		for(int i = index; i < NUMBERMAX_PLAYERS+4-index; i++ )
		{
			panelsList.get(i).setName("< EMPTY >");
			((PlayerPanel)panelsList.get(index)).enableButton();
			((PlayerPanel)panelsList.get(index)).setImage(null);
		}
		
		if(lobbyPack.getStartFlag())
		{
			callback.launchGame();
		}
	}
}
