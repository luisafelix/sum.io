package common.environment;

import java.io.IOException;

import server.inteligence.PlayerBot;

public class Player extends GameObject implements CircleColider
{
	private final int MAX_SPEED = 15;
	private final int MAX_BOOST = 25;
	
	private String playerIP;
	private int radiusColider;
	
	private double speedX = 0;
	private double speedY = 0;
	private double accX = 0.8;
	private double accY = 0.8;
	
	private int stunTime = 1000;
	private boolean flagColision = false;
	
	public Player(String name, double x, double y, int width, int height, String playerIP,int priorityRender) 
	{
		super(name, x, y, width, height, priorityRender);
		this.playerIP = playerIP;
		this.radiusColider = width/2;
	}
	
	public Player(String name, double x, double y, int width, int height, String playerIP,int priorityRender,boolean isAwake) 
	{
		super(name, x, y, width, height, priorityRender);
		this.playerIP = playerIP;
		this.radiusColider = width/2;
		this.isAwake = isAwake;
	}

	public String getPlayerIP() {return playerIP;}
	public double getSpeedX() {return speedX;}
	public double getSpeedY() {return speedY;}
	public void setSpeedX(double speedX) { this.speedX = speedX;}
	public void setSpeedY(double speedY) { this.speedY = speedY;}
	public double getAccX() {return accX;}
	public double getAccY() {return accY;}
	public void setAccX(double accX) { this.accX = accX;}
	public void setAccY(double accY) { this.accY = accY;}
	
	public int getRadiusColider() {return radiusColider;}
	public boolean getFlagColision() {return flagColision;}
	public void setFlagColision(boolean flagColision) {this.flagColision = flagColision;}
	
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
		//FIXME: Hard coded
		return "P:"+ this.getName();
	}
	
	public void onColision(CircleColider obj2)
	{
		if(obj2 instanceof Player)
		{
			colisionPlayerHandler((Player)obj2);
		}
	}
	
	private void colisionPlayerHandler(Player obj2)
	{
		//Remove the control from the player to handle the colision
		if(!getFlagColision())
		{
			new RemoveControls(this,stunTime);
			setFlagColision(true);
		}
		if(!obj2.getFlagColision())
		{
			new RemoveControls(((Player)obj2),stunTime);
			obj2.setFlagColision(true);
		}
		
		double tempX1 = this.getX();
		double tempY1 = this.getY();
		double tempX2 = obj2.getX();
		double tempY2 = obj2.getY();
		
		double ballsDistance = Math.sqrt(
										Math.pow(this.getX()-obj2.getX(),2)+
										Math.pow(this.getY()-obj2.getY(),2)
										);
		
		double overlapDistance = 0.5 * (ballsDistance - (radiusColider + obj2.getRadiusColider()));
		
		//TO avoid one ball enter inside the other
		this.translate( 
						+overlapDistance * (tempX2-tempX1)/ ballsDistance, 
						+overlapDistance * (tempY2-tempY1)/ ballsDistance
				 		);
		
		obj2.translate( 
								-overlapDistance * (tempX2-tempX1)/ ballsDistance, 
								-overlapDistance * (tempY2-tempY1)/ ballsDistance
			 					);
		
		
		ballsDistance = obj2.getRadiusColider() + radiusColider;
		//Normal Vectors
		double nX = (obj2.getX() - this.getX())/ballsDistance;
		double nY = (obj2.getY() - this.getY())/ballsDistance;
		
		//Tangent Vectors
		double tX = -nY;
		double tY = nX;
		
		//Dot product Tangential
		double dpTan1 = speedX*tX + speedY*tY;
		double dpTan2 = obj2.getSpeedX()*tX + ((Player)obj2).getSpeedY()*tY;
		
		//Dot product Normal
		double dpNorm1 = (speedX*nX + speedY*nY);
		double dpNorm2 = (obj2.getSpeedX()*nX + ((Player)obj2).getSpeedY()*nY);
		
		//Energy Created for the colision
		
		double m1 = (dpNorm1 * radiusColider - obj2.getRadiusColider() +
					2 * obj2.getRadiusColider() * dpNorm2)/(radiusColider + obj2.getRadiusColider());
		
		double m2 = (dpNorm2 * radiusColider - obj2.getRadiusColider() +
				2 * obj2.getRadiusColider() * dpNorm1)/(radiusColider + obj2.getRadiusColider());
		
		setSpeedX( tX*dpTan1 + nX*m1);
		setSpeedY( tY*dpTan1 + nY*m1);
		obj2.setSpeedX( tX*dpTan2 + nX*m2);
		obj2.setSpeedY( tY*dpTan2 + nY*m2);
	}
	
	public void accelerateX(int direction) 
	{
		if( ! ((speedY*speedY) + Math.pow((speedX + accX * direction),2) > MAX_SPEED*MAX_SPEED))
		{
			speedX = (speedX + accX* direction);
		}
	}
	
	public void accelerateY(int direction) 
	{		
		if( ! ((speedX*speedX) + Math.pow(speedY + accY*direction,2) > MAX_SPEED*MAX_SPEED) )
		{
			speedY = (speedY + accY*direction);
		}
	}
	
	public boolean isMoving()
	{
		return (speedX != 0 || speedY !=0);
	}

	//TODO: Change how the attackboost works.
	public void attackBoost() 
	{
		double contributionX = 0;
		if((Math.abs(speedX)>= 0.001 && Math.abs(speedY) >= 0.001 ))
		{
			contributionX = Math.abs(speedX) / (Math.abs(speedX) + Math.abs(speedY));
			speedX = (Math.abs(speedX)/speedX) * MAX_BOOST * contributionX;
			speedY = (Math.abs(speedY)/speedY) * MAX_BOOST * (1-contributionX);
		}
	}
}
