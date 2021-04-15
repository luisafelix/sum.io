package client.engine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;

import javax.imageio.ImageIO;

public class ImageCache 
{
	private Hashtable<String,BufferedImage> imageMap = null;
	
	public ImageCache ()
	{
		imageMap = new Hashtable<String,BufferedImage>();
		loadImages();
	}
	
	public Hashtable<String,BufferedImage> getImageMap(){return imageMap;}
	
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
	
	
	
}
