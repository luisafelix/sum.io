package client.engine;

public class RenderThread extends Thread 
{
		private int framesPerSecond;
		private ScreenRender callback;
		
		public RenderThread(ScreenRender callback, int framesPerSecond)
		{
			this.callback = callback;
			this.framesPerSecond = framesPerSecond;
			start();
		}
		
		@Override	
		public void run()
		{
			boolean quitThread = false;
			double time0 = System.currentTimeMillis();
			
			while(!quitThread)
			{
				double time = System.currentTimeMillis();
				double deltaTime = time - time0;
				
				if(deltaTime >= 1000/framesPerSecond)
				{
					callback.draw();
					time0 = time;
				}
			}
		}
}
