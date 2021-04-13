package client.engine;

import java.awt.Graphics;

public class LabelSliderObject extends LabelObject
{

	private LabelObject slider;
	
	public LabelSliderObject(ScreenRender screenRender, String background,String sliderRef, int x, int y, int width, int height) 
	{
		super(screenRender,background, x, y, width, height);
		slider = new LabelObject(screenRender,sliderRef,x+3,y-3,width,height,-1000);
	}
	
	@Override
	public void draw(Graphics g) 
	{
		
	}
	
	public void setSliderRatio(double ratio)
	{
		slider.setRatioCutted(ratio);
	}
	
	@Override
	public void revalidate(int x, int y, int width, int height)
	{
		//FIXME: Hard coded
		super.revalidate(x, y, width, height);
		slider.revalidate(x+3, y-3, width, height);
	}
	


}
