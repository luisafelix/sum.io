package common.environment;

public class CollectableBoost extends GameObject implements CircleColider
{
	
	private int boostGain = 50;
	public CollectableBoost(String name, double x, double y, int width, int height) 
	{
		super(name, x, y, width, height,1000);
	}

	@Override
	public double getX()
	{
		return x;
	}

	@Override
	public double getY() 
	{
		return y;
	}

	@Override
	public int getRadiusColider() 
	{
		return width/2;
	}

	@Override
	public void onColision(CircleColider obj2) 
	{
		if(obj2 instanceof Player)
		{
			((Player)obj2).addBoost(boostGain);
			sleepObject();
		}
	}

}
