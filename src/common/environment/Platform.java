package common.environment;

public class Platform extends GameObject implements CircleColider{

	public Platform(String name, int x, int y, int width, int height, int renderingPriority) 
	{
		super(name, x, y, width, height, renderingPriority);
	}

	@Override
	public int getRadiusColider() 
	{
		return width/2;
	}
	
	@Override
	public void onColision(CircleColider obj2) 
	{
		
	}
}
