package common.environment;

import java.io.IOException;

public class Player extends GameObject implements CircleColider
{
	private final int MAX_SPEED = 15;
	
	private String playerIP;
	private int radiusColider;
	
	
	private double speedX = 0 ;
	private double speedY = 0;
	private double accX = 1;
	private double accY = 1;
	
	public Player(String name, int x, int y, int width, int height, String playerIP,int priorityRender) 
	{
		super(name, x, y, width, height, priorityRender);
		this.playerIP = playerIP;
		this.radiusColider = width/2;
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
		//Remove the control from the player to handle the colision
		new RemoveControls(this,2000);
		new RemoveControls(((Player)obj2),2000);
		
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
		
		((Player)obj2).translate( 
								-overlapDistance * (tempX2-tempX1)/ ballsDistance, 
								-overlapDistance * (tempY2-tempY1)/ ballsDistance
			 					);
		
		
		ballsDistance = ((Player)obj2).getRadiusColider() + radiusColider;
		//Normal Vectors
		double nX = (((Player)obj2).getX() - this.getX())/ballsDistance;
		double nY = (((Player)obj2).getY() - this.getY())/ballsDistance;
		
		//Tangent Vectors
		double tX = -nY;
		double tY = nX;
		
		//Dot product Tangential
		double dpTan1 = speedX*tX + speedY*tY;
		double dpTan2 = ((Player)obj2).getSpeedX()*tX + ((Player)obj2).getSpeedY()*tY;
		
		//Dot product Normal
		double dpNorm1 = speedX*nX + speedY*nY;
		double dpNorm2 = ((Player)obj2).getSpeedX()*nX + ((Player)obj2).getSpeedY()*nY;
		
		double m1 = (dpNorm1 * radiusColider - ((Player)obj2).getRadiusColider() +
					2 * ((Player)obj2).getRadiusColider() * dpNorm2)/(radiusColider + ((Player)obj2).getRadiusColider());
		
		double m2 = (dpNorm2 * radiusColider - ((Player)obj2).getRadiusColider() +
				2 * ((Player)obj2).getRadiusColider() * dpNorm1)/(radiusColider + ((Player)obj2).getRadiusColider());
		
		setSpeedX( tX*dpTan1 + nX*m1);
		setSpeedY( tY*dpTan1 + nY*m1);
		((Player)obj2).setSpeedX( tX*dpTan2 + nX*m2);
		((Player)obj2).setSpeedY( tY*dpTan2 + nY*m2);
		
	}
	
	public void accelerateX(int direction) 
	{
		if(Math.abs(speedX + accX * direction) > MAX_SPEED)
		{
			speedX = MAX_SPEED * direction;
		}
		else
		{
			speedX = (speedX + accX* direction);
		}
	}
	
	public void accelerateY(int direction) 
	{
		if(Math.abs(speedY + accY*direction) > MAX_SPEED) 
		{
			speedY = MAX_SPEED*direction;
		}
		else
		{
			speedY = (speedY + accY*direction);
		}
	}
	
	public boolean isMoving() 
	{
		return (speedX != 0 || speedY !=0);
	}

}
