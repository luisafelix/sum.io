package client.engine;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.ArrayList;
import java.util.Hashtable;

import common.environment.GameObject;

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
		renderingQueue = new PriorityBlockingQueue<GameObject>();
		imageMap = new Hashtable<String,BufferedImage>();
		
		loadImages();
		
		renderThread = new RenderThread(this, TEORICAL_FPS);
	}
	
	public Hashtable<String,BufferedImage> getImageMap(){return imageMap;}
	
	public void setOrigin(int x,int y) 
	{
		this.originX= x; 
		this.originY = y;
	}
	
	private void loadImages()
	{
		//Find all the images names in the res folder
		File res = new File(System.getProperty("user.dir") + System.getProperty("file.separator")+"res"+ System.getProperty("file.separator")+"images");
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
		
		double nanot1 = System.nanoTime();
		
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
					
					int tempPosX = 0;
					int tempPosY = 0;
					
					int iX = 0;
					int iY = 0;
					
					if (go.isAbsolutePath())
					{
						tempPosX = (int)go.getX();
						tempPosY = (int)go.getY();
						iX = tempPosX- go.getWidth()/2;;
						iY = tempPosY- go.getHeight()/2;
					}
					else
					{
						tempPosX = (int)go.getX() - originX;
						tempPosY = (int)go.getY() - originY;
						iX = windowWidth/2 + tempPosX - go.getWidth()/2;
						iY = windowHeight/2 + tempPosY - go.getHeight()/2;
					}
					
					double scaleX = go.getWidth()/(double)currentImage.getWidth();
					double scaleY = go.getHeight()/(double)currentImage.getHeight();
					
					imageInScreen(
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
									);
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
	
	//FIXME: The implementation of rendering just what is in the screen is worst than just render everything ..., good job!
	private void imageInScreen(Graphics g, BufferedImage currentImage,int iX,int iY, int goWidth, int goHeight,double xScale, double yScale, int wWidth, int wHeight)
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
	}
}

