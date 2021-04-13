package common.communication;

import java.io.Serializable;
import java.util.LinkedList;

public class LobbyPack implements Serializable
{
	private LinkedList<String> playerList;
	private String player;
	private boolean startFlag = false;
	public LobbyPack ()
	{
		playerList = new LinkedList<String>();
	}
	
	public LobbyPack(LobbyPack lPack)
	{
		this.playerList = (LinkedList<String>) lPack.getPlayerList().clone();
		this.player = lPack.getPlayer();
		this.startFlag = lPack.getStartFlag();
	}
	
	public void addToPlayerList(String playerName)
	{
		this.playerList.add(playerName);
	}
	public LinkedList<String> getPlayerList(){return playerList;}
	public String getPlayer() {return player;}
	public boolean getStartFlag() {return startFlag;}
	public void setStartFlag(boolean startFlag) {this.startFlag = startFlag;}
	
	public void addPlayer(String name) 
	{
		player = name;
	}
	

}
