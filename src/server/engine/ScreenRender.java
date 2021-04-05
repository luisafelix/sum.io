package server.engine;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.JPanel;

import common.environment.GameObject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import java.io.File;
import javax.imageio.ImageIO;

public class ScreenRender extends JPanel{
	
	private PriorityBlockingQueue<GameObject> renderingQueue;
	private Hashtable<String,BufferedImage> imageMap = null;
	
	public ScreenRender()
	{
		renderingQueue = new PriorityBlockingQueue<GameObject>();
		imageMap = new Hashtable<String,BufferedImage>();
		setBackground(new Color(142,203,224));
		loadImages();
	}
	
	private void loadImages()
	{
		//Find all the images names in the res folder
		File res = new File(System.getProperty("user.dir") + System.getProperty("file.separator") +"res"+ System.getProperty("file.separator")+"images");
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
			this.addToRender(go);
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
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		for(GameObject go:renderingQueue)
		{
			if(!go.isAwake())
			{
				continue;
			}
			
			BufferedImage currentImage = imageMap.get(go.getName());
			if(currentImage == null)
			{
				currentImage = imageMap.get("noTexture");
			}
			g.drawImage(currentImage,
					this.getWidth()/2 + (int)(go.getX()-go.getWidth()/2.0),
					this.getHeight()/2 + (int)(go.getY()-go.getHeight()/2.0),
					go.getWidth(),
					go.getHeight(),
					null);
			
		}
	}
}

