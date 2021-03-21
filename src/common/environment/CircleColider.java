package common.environment;

public interface CircleColider 
{
	public abstract double getX();
	public abstract double getY();
	public abstract int getRadiusColider();
	
	public boolean colidedTo(CircleColider obj2);
	public default void onColision(CircleColider obj1, CircleColider obj2) 
	{
	}
}