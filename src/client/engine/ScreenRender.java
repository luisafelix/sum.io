package client.engine;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.ArrayList;
import java.util.Hashtable;

import common.environment.GameObject;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import java.io.File;
import javax.imageio.ImageIO;

public class ScreenRender{
	
	private int windowWidth;
	private int windowHeight;
	
	private int originX;
	private int originY;
	
	private BufferStrategy bufferStrategy;
	
	private ConcurrentLinkedQueue<GameObject> renderingQueue;
	//FIXME: try to find something better in the future.
	private Hashtable<String,BufferedImage> imageMap = null;
	
	private EngineHandler callback;
	
	private final int TEORICAL_FPS = 30;
	private int realFps = TEORICAL_FPS;
	private double oldT = 0;
	
	private RenderThread renderThread;
	
	
	public ScreenRender(EngineHandler callback, BufferStrategy bufferStrategy)
	{
		this.bufferStrategy = bufferStrategy;
		
		originX = 0;
		originY = 0;
		this.callback = callback;
		renderingQueue = new ConcurrentLinkedQueue<GameObject>();
		imageMap = new Hashtable<String,BufferedImage>();
		
		loadImages();
		
		renderThread = new RenderThread(this, TEORICAL_FPS);
	}
	
	public void setOrigin(int x,int y) 
	{
		this.originX= x; 
		this.originY = y;
	}
	
	private void loadImages()
	{
		//Find all the images names in the res folder
		File res = new File(System.getProperty("user.dir") + System.getProperty("file.separator")+"res");
		String[] imageNames = res.list();
		try{
			for(int i = 0;i < imageNames.length;i++)
			{
				//just loads image that are in png format.
				if(imageNames[i].endsWith(".png"))
				{
					BufferedImage image = ImageIO.read(new File(res + System.getProperty("file.separator") + imageNames[i]));
					imageMap.put(imageNames[i].substring(0,imageNames[i].length()-4),image);
				}
			}
		} catch(Exception e) {
			System.out.println("Error: Image did not load");
		}	
	}
	
	public void addToRender(GameObject go)
	{
		//Replace contains method by this explicit loop to take advantage of it and update X and Y position.
		for(GameObject g : renderingQueue) 
		{	
		if(g.equals(go)) 
			{
				g.setX(go.getX());
				g.setY(go.getY());
				return;
			}
		}
		renderingQueue.add(go);
	}
	
	public void addToRender(ArrayList<GameObject> goList)
	{
		for(GameObject go : goList )
		{
			addToRender(go);
		}
	}
	
	public void clearRender()
	{
		renderingQueue.clear();
	}
	
	public boolean removeToRender(GameObject go)
	{
		if(renderingQueue.remove(go))
		{
			return true;
		}
		System.out.println("Error: Rendering queue does not contains that object");	
		return false;
	}
	
	@Override
	public String toString()
	{
		String res = "";
		res += "Size: " + renderingQueue.size() + "\n";
		for(GameObject go : renderingQueue)
		{
			res += go.toString();
		}
		return res;
	}
	
	public void draw()
	{
		//TODO: Get a better way to render
		double t1 = System.currentTimeMillis();
		
		do 
		{
			do 
			{
				Graphics g = bufferStrategy.getDrawGraphics();
				windowHeight = callback.getWindow().getHeight();
				windowWidth = callback.getWindow().getWidth();
				g.clearRect(0, 0, windowWidth, windowHeight);
				
				for(GameObject go:renderingQueue)
				{
					BufferedImage currentImage = imageMap.get(go.getName());
					if(currentImage == null)
					{
						currentImage = imageMap.get("noTexture");
					}
					int tempPosX = (int)go.getX() - originX;
					int tempPosY = (int)go.getY() - originY;
					
					g.drawImage(currentImage,windowWidth/2 + tempPosX - go.getWidth()/2,windowHeight/2 + tempPosY-go.getHeight()/2,go.getWidth(),go.getHeight(),null);
				}
				g.dispose();
				
			}
			while(bufferStrategy.contentsRestored());
			
			bufferStrategy.show();
			
		}
		while(bufferStrategy.contentsLost());
		
		realFps = (int) (1000/(t1-oldT));
		//System.out.println("FPS: "+ realFps);
		oldT=t1;
		
	}
	
}

