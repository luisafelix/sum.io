package client.engine;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.ArrayList;
import java.util.Hashtable;

import common.environment.GameObject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
	
	private PriorityBlockingQueue<GameObject> renderingQueue;
	//FIXME: try to find something better in the future.
	private Hashtable<String,BufferedImage> imageMap = null;
	
	private final int TEORICAL_FPS = 60;
	private int realFps = TEORICAL_FPS;
	private double oldT = 0;
	
	private RenderThread renderThread;
	private ImageCache imageCache;
	private EngineHandler callback;
	
	public ScreenRender(EngineHandler callback, BufferStrategy bufferStrategy, ImageCache imageCache)
	{
		this.bufferStrategy = bufferStrategy;
		
		originX = 0;
		originY = 0;
		this.callback = callback;
		this.imageCache = imageCache;
		
		renderingQueue = new PriorityBlockingQueue<GameObject>();
		imageMap = imageCache.getImageMap();
		
		//loadImages();
		
		renderThread = new RenderThread(this, TEORICAL_FPS);
		renderThread.start();
	}
	
	public Hashtable<String,BufferedImage> getImageMap(){return imageMap;}
	
	public void setOrigin(int x,int y) 
	{
		this.originX= x; 
		this.originY = y;
	}
	
	
	public void addToRender(GameObject go)
	{
		//Replace contains method by this explicit loop to take advantage of it and update X and Y position.		
		for(GameObject g : renderingQueue) 
		{	
		if(g.equals(go)) 
			{
				g.setState(go.isAwake());
				g.setX(go.getX());
				g.setY(go.getY());
				return;
			}
		}
		renderingQueue.add(go);
	}
	
	public void addToRender(ArrayList<GameObject> goList)
	{
		if(goList == null)
		{
			return;
		}
		
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
	
	public int getFpsCount(){return realFps;}
	
	public void draw()
	{
		//TODO: Get a better way to render
		double t1 = System.currentTimeMillis();
		do 
		{
			do 
			{
				Graphics g = bufferStrategy.getDrawGraphics();
				windowHeight = callback.getWindow().getJFrame().getHeight();
				windowWidth = callback.getWindow().getJFrame().getWidth();
				
				g.clearRect(0, 0, windowWidth, windowHeight);
				
				for(GameObject go:renderingQueue)
				{	
					if(!go.isAwake())
					{
						continue;
					}
					
					if(!(go instanceof LabelObject))
					{
						drawGameObject(g,go);
					}
					
				}
				
				for(GameObject go: renderingQueue)
				{
					if(go instanceof LabelObject)
					{
						if(((LabelObject) go).getRatioCutted() < 1)
						{
							drawGameObject(g, go, ((LabelObject) go).getRatioCutted());
						}
						else
						{
							drawGameObject(g, go);
						}
						((LabelObject)go).draw(g);
					}
					
				}
				g.dispose();	
			}
			while(bufferStrategy.contentsRestored());
			
			bufferStrategy.show();
			
		}
		while(bufferStrategy.contentsLost());
		
		realFps = (int) (1000/(t1-oldT));
		oldT=t1;
	}
	
	private void drawGameObject(Graphics g, GameObject go)
	{
		double ratioCutted = 1;
		drawGameObject(g,go,ratioCutted);
	}
	
	private BufferedImage getImageCutted(GameObject go, double ratioCutted)
	{
		BufferedImage currentImage = imageMap.get(go.getName());
		if(ratioCutted == 0)
		{
			return null;
		}
		if(ratioCutted == 1)
		{
			return currentImage;
		}
		
		return cutImage(currentImage, ratioCutted);
		
	}
	
	private BufferedImage cutImage(BufferedImage currentImage,double ratioCutted)
	{
		currentImage = currentImage.getSubimage(0,0,(int)(currentImage.getWidth()*ratioCutted),currentImage.getHeight());
		return currentImage;
	}
	
	private void drawGameObject(Graphics g, GameObject go, double ratioCutted)
	{
		BufferedImage currentImage = getImageCutted(go,ratioCutted);
		
		if(currentImage == null && !go.getName().equals("null"))
		{
			currentImage = imageMap.get("noTexture");
		}
		
		double tempPosX = 0;
		double tempPosY = 0;
		
		double iX = 0;
		double iY = 0;
		
		if (go.isAbsolutePath())
		{
			tempPosX = go.getX();
			tempPosY = go.getY();
			iX = tempPosX- go.getWidth()/2.0;
			iY = tempPosY- go.getHeight()/2.0;
		}
		else
		{
			tempPosX = go.getX() - originX;
			tempPosY = go.getY() - originY;
			iX = windowWidth/2.0 + tempPosX - go.getWidth()/2.0;
			iY = windowHeight/2.0 + tempPosY - go.getHeight()/2.0;
		}
		
		g.drawImage(
				currentImage,
				(int)iX,
				(int)iY,
				(int)(go.getWidth()*ratioCutted),
				(int)go.getHeight(),
				null);
	}
	//FIXME: The implementation of rendering just what is in the screen is worst than just render everything ..., good job!
	
	/*
	double scaleX = go.getWidth()/(double)currentImage.getWidth();
	double scaleY = go.getHeight()/(double)currentImage.getHeight();
	*/
	/*imageInScreen(
					g,
					currentImage,
					iX,
					iY,
					go.getWidth(),
					go.getHeight(),
					scaleX,
					scaleY,
					windowWidth,
					windowHeight
					);*/
	
	/*private void imageInScreen(Graphics g, BufferedImage currentImage,int iX,int iY, int goWidth, int goHeight,double xScale, double yScale, int wWidth, int wHeight)
	{
		
		int xCut = 0;
		int yCut = 0;
		int wCut = (goWidth);
		int hCut = (goHeight);
		
		if(iX + goWidth < 0)
		{
			return;
		}
		
		if(iX > wWidth)
		{
			return;
		}
		
		if(iY + goHeight < 0)
		{
			return;
		}
		
		if(iY > wHeight)
		{
			return;
		}
		
		if (iX <= 0)
		{
			xCut = -(iX);
			wCut = wCut - xCut;
		}
		
		if (iX + goWidth > wWidth)
		{
			wCut = wCut - ( iX + goWidth - wWidth);
		}
		
		if (iY <= 0)
		{
			yCut = -(iY);
			hCut = hCut - yCut;
		}
		
		if (iY + goHeight > wHeight)
		{
			hCut = hCut - ( iY + goHeight - wHeight);
		}
		
		if(wCut > 0 && hCut > 0)
		{
			currentImage = currentImage.getSubimage(
					(int)(xCut/xScale),
					(int)(yCut/yScale),
					(int)((wCut)/xScale),
					(int)((hCut)/yScale)
					);
		}
		else
		{
			return;
		}
		
		g.drawImage(
					currentImage,
					iX + xCut,
					iY + yCut,
					(int)(currentImage.getWidth()*xScale),
					(int)(currentImage.getHeight()*yScale),
					null);
	}*/
}

