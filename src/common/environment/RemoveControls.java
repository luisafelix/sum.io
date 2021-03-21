package common.environment;

public class RemoveControls extends Thread 
{
	private Player player;
	private int timeSleep;
	
	public RemoveControls (Player player,int timeSleep)
	{
		this.player = player;
		this.timeSleep = timeSleep;
		start();
	}
	
	@Override
	public void run()
	{
		double accX1 = player.getAccX();
		double accY1 = player.getAccY();
		
		player.setAccX(0);
		player.setAccY(0);
		
		try {
			this.sleep(timeSleep);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		player.setAccX(accX1);
		player.setAccY(accY1);
	}

}
