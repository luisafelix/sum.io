package client.engine;

//Question: Is it good pratice to declare with '*' instead of the name of each class ?
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

import javax.swing.JPanel;

import common.environment.GameObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import java.io.File;
import javax.imageio.ImageIO;

public class ScreenRender extends JPanel{
	
	private int windowWidth;
	private int windowHeight;
	
	private int originX;
	private int originY;
	
	private PriorityQueue<GameObject> renderingQueue;
	//FIXME: try to find something better in the future.
	private Hashtable<String,BufferedImage> imageMap = null;
	
	private EngineHandler callback;
	
	public ScreenRender(EngineHandler callback)
	{
		originX = 0;
		originY = 0;
		this.callback = callback;
		renderingQueue = new PriorityQueue<GameObject>();
		imageMap = new Hashtable<String,BufferedImage>();
		
		loadImages();
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
	
	//TODO: Double Buffering.
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		windowHeight = this.getHeight();
		windowWidth = this.getWidth();
		
		for(GameObject go:renderingQueue)
		{
			BufferedImage currentImage = imageMap.get(go.getName());
			if(currentImage == null)
			{
				currentImage = imageMap.get("noTexture");
			}
			int tempPosX = go.getX() - originX;
			int tempPosY = go.getY() - originY;
			
			g.drawImage(currentImage,windowWidth/2 + tempPosX-go.getWidth()/2,windowHeight/2 + tempPosY-go.getHeight()/2,go.getWidth(),go.getHeight(),null);
		}
	}
}

