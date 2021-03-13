package common.environment;

public class Player extends GameObject implements java.io.Serializable
{
	private String playerIP;
	private int speedX=5;
	private int speedY=5;
	
	public Player(String name, int x, int y, int width, int height, String playerIP,int priorityRender) 
	{
		super(name, x, y, width, height, priorityRender);
		this.playerIP = playerIP;
	}
	public String getPlayerIP() {return playerIP;}
	public int getSpeedX() {return speedX;}
	public int getSpeedY() {return speedY;}
	
	public boolean equals(Object o)
	{
		if(o == null)
		{
			return false;
		}
		if(!(o instanceof Player))
		{
			return false;
		}
		Player p = (Player)o;
		if(!( p.getPlayerIP().equals(this.playerIP)))
		{
			return false;
		}
		return true;
	}
	
	public String toString()
	{
		return "P:"+ playerIP +"posX:"+ getX();
	}
}
