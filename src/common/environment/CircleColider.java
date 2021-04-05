package common.environment;

public interface CircleColider 
{
	public abstract double getX();
	public abstract double getY();
	public abstract int getRadiusColider();
	public abstract void onColision(CircleColider obj2);
	
	public default boolean hasCollidedTo(CircleColider obj2)
	{
		boolean haveColision = false;
		
		double xDist = Math.pow(getX() - obj2.getX(), 2);
		double yDist = Math.pow(getY() - obj2.getY(), 2);
		
		if(xDist + yDist < Math.pow(getRadiusColider() + obj2.getRadiusColider(),2))
		{
			haveColision = !haveColision;
		}
		
		if(haveColision)
		{
			onColision(obj2);
		}
		
		return haveColision;
	}
}