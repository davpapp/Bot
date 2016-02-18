import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

public class ironMiner
{
	private String bot_type;
	private int width = 500;
	private int height = 320;
	private static int x_center = 0;
	private static int y_center = 0;
	private static int x_corner = 0;
	private static int y_corner = 0;
	
	long wait_end;
	
	private Vector<String> activity_queue = new Vector<String>(0);
	
	public static void main(String[] args) throws InterruptedException, IOException, AWTException 
	{
		Robot robot = new Robot();
		initializeLocation(robot);
		mineIron(robot);
	}
	
	public static void mineIron(Robot robot) throws AWTException, InterruptedException
	{
		Random random = new Random();
		int count = 0;
		int x_north_rock = x_center;
		int y_north_rock = y_center - 26;
		int x_east_rock = x_center + 25;
		int y_east_rock = y_center;
		while (!isInventoryFull())
		{
			if (count % 2 == 0)
			{
				leftClickRandom(robot, x_north_rock, y_north_rock, 10);
			}
			else
			{
				leftClickRandom(robot, x_east_rock, y_east_rock, 10);
			}
			count++;
			Thread.sleep(6000 + random.nextInt(500));
		}
	}

	public static boolean isInventoryFull()
	{
		return false;
	}
	
	public static void leftClickRandom(Robot robot, int x, int y, int tolerance) throws AWTException, InterruptedException
	{
		Random random = new Random();
		robot.mouseMove(x + random.nextInt(tolerance) - tolerance / 2, y + random.nextInt(tolerance) - tolerance / 2);
		Thread.sleep(40 + random.nextInt(20));
		robot.mousePress(InputEvent.BUTTON1_MASK);
		Thread.sleep(40 + random.nextInt(20));
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}


	public static void initializeLocation(Robot robot) throws InterruptedException
	{
		System.out.println("Hover over the corner... Waiting 5 seconds...");
		Thread.sleep(5000);
		PointerInfo a = MouseInfo.getPointerInfo();
		Point b = a.getLocation();
		x_corner = (int) b.getX();
		y_corner = (int) b.getY();
		System.out.println("Corner at (" + x_corner + ", " + y_corner + ")");
		System.out.println("Hover over the center... Waiting 5 seconds...");
		Thread.sleep(5000);
		a = MouseInfo.getPointerInfo();
		Point c = a.getLocation();
		x_center = (int) c.getX();
		y_center = (int) c.getY();
		System.out.println("Corner at (" + x_center + ", " + y_center + ")");
	}
	
	public void setWait(int desired_wait)
	{
		wait_end = System.currentTimeMillis() + desired_wait;
	}
}
