package common.environment;

public class GameObject implements Comparable<GameObject>, java.io.Serializable{
	
	private int renderingPriority;
	
	private String name;
	private int x;
	private int y;
	private int width;
	private int height;
	private int id;
	private static int idCount = 0;
	
	public GameObject (String name, int x,int y, int width, int height)
	{
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		renderingPriority = 0;
		id = idCount;
		idCount++;
	}
	
	public GameObject (String name, int x,int y, int width, int height,int renderingPriority)
	{
		this(name,x,y,width,height);
		this.renderingPriority = renderingPriority;
	}
	
	/*
	 * GETTERS AND SETTERS
	*/
	public int getRenderingPriority(){return renderingPriority;}
	public void setRenderingPriority(int renderingPriority){this.renderingPriority=renderingPriority;}
	
	public int getWidth(){return width;}
	public int getHeight(){return height;}
	public int getX(){return x;}
	public void setX(int x) {this.x = x;}
	public int getY(){return y;}
	public void setY(int y) {this.y = y;}
	public String getName(){return name;}
	public int getId(){return id;}
	
	/*
	 * OVERRIDES
	*/
	//Used to sort the array by the renderingPriority
	@Override
	public int compareTo(GameObject go)
	{
		if(this.renderingPriority > go.getRenderingPriority()){return 1;}
		if(this.renderingPriority < go.getRenderingPriority()){return -1;}
		return 0;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o == null){return false;}
		if(!(o instanceof GameObject)){return false;}
	
		GameObject go = (GameObject)o;
		
		if(go.getId() != id){return false;}
		return true;
	}
	
	@Override
	public String toString()
	{
			String res = "";
			res += "renderingPriority: " + renderingPriority + "\n";
			return res;
	}
	
	public void translate (int dx,int dy)
	{
		x+=dx;
		y+=dy;
	}
}
