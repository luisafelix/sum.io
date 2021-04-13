package common.environment;

public class GameObject implements Comparable<GameObject>, java.io.Serializable{
	
	private int renderingPriority;
	
	private String name;
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	protected boolean isAwake = true;
	
	private boolean isAbsoluteCoordinates = false;
	
	
	public GameObject (String name, double x,double y, int width, int height)
	{
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		renderingPriority = 0;
	}
	
	public GameObject (String name, double x,double y, int width, int height,int renderingPriority)
	{
		this(name,x,y,width,height);
		this.renderingPriority = renderingPriority;
	}
	
	/*
	 * GETTERS AND SETTERS
	*/
	public int getRenderingPriority(){return renderingPriority;}
	public void setRenderingPriority(int renderingPriority){this.renderingPriority=renderingPriority;}
	public void setAbsolute(boolean absolute) {isAbsoluteCoordinates = absolute;}
	public boolean isAbsolutePath() {return isAbsoluteCoordinates;}
	public void sleepObject(){isAwake = false;}
	public void setState(boolean state) {this.isAwake = state;}
	
	public int getWidth(){return width;}
	public int getHeight(){return height;}
	public double getX(){return x;}
	public void setX(double x) {this.x = x;}
	public double getY(){return y;}
	public void setY(double y) {this.y = y;}
	public String getName(){return name;}
	public boolean isAwake(){return isAwake;}
	
	
	/*
	 * OVERRIDES
	*/
	//Used to sort the array by the renderingPriority
	@Override
	public int compareTo(GameObject go)
	{
		if(this.renderingPriority > go.getRenderingPriority()){return 1;}
		if(this.renderingPriority < go.getRenderingPriority()){return -1;}
		//To let the compareTo consistent
		if(go.equals(this))
		{
			return 1;
		}
		
		return 0;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o == null){return false;}
		if(!(o instanceof GameObject)){return false;}
	
		GameObject go = (GameObject)o;
		
		if(go.getX()==getX() && go.getY() == getY() && go.getWidth() == getWidth() && go.getHeight() == getHeight() && go.getName().equals(getName()))
		{
			return true;
		}
		
		return false;
	}
	
	@Override
	public String toString()
	{
			String res = "";
			res += "name: " + getName() + "\n";
			return res;
	}
	
	public void translate (double dx,double dy)
	{
		x+=dx;
		y+=dy;
	}
	
	public void translate (int dx,int dy)
	{
		translate((double)dx,(double)dy);
	}
	
	public double squareDistanceTo(GameObject obj1)
	{
		return Math.pow(obj1.getX() - this.getX(), 2) + Math.pow(obj1.getY() - this.getY(), 2);
	}
}

