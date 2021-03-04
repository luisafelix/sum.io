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
	
	private int screenWidth;
	private int screenHeight;
	
	private PriorityQueue<GameObject> renderingQueue;
	
	//FIXME: try to find something better in the future.
	private Hashtable<String,BufferedImage> imageMap = null;
	
	public ScreenRender()
	{
		renderingQueue = new PriorityQueue<GameObject>();
		imageMap = new Hashtable<String,BufferedImage>();
		loadImages();
	}
	
	public void loadImages()
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
		if(!renderingQueue.contains(go))
		{
			renderingQueue.add(go);
		}
	}
	
	public void addToRender(ArrayList<GameObject> goList)
	{
		for(GameObject go : goList )
		{
			addToRender(go);
		}
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
	
	//ADDLATER: Double Buffering.
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		for(GameObject go:renderingQueue)
		{
			BufferedImage currentImage = imageMap.get(go.getName());
			
			if(currentImage != null)
			{
				g.drawImage(imageMap.get(go.getName()),go.getX(),go.getY(),go.getWidth(),go.getHeight(),null);
			}
			else
			{
				g.drawImage(imageMap.get("noTexture"),go.getX(),go.getY(),go.getWidth(),go.getHeight(),null);
			}
		}
	}
}

