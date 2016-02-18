import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.IOException;
import java.util.Random;

public class AutoLogin 
{
	public static void main(String[] args) throws InterruptedException, IOException, AWTException 
	{
		Robot robot = new Robot();
		int number_of_bots = 5;
		int x_locs[] = new int[number_of_bots];
		int y_locs[] = new int[number_of_bots];
		Thread.sleep(5000);
		for (int i = 0; i < number_of_bots; i++)
		{
			
			Thread.sleep(3000);
			System.out.println("Waiting bot " + i);
			PointerInfo mouse = MouseInfo.getPointerInfo();
			Point loc = mouse.getLocation();
			x_locs[i] = (int) loc.getX();
			y_locs[i] = (int) loc.getY();
			
			
		}
		
		for (int i = 0; i < number_of_bots; i++)
		{
			leftClickPrecise(robot, x_locs[i], y_locs[i]);
			Thread.sleep(75);
		}
	}
	public static void leftClickPrecise(Robot robot, int x, int y) throws AWTException, InterruptedException
	{
		Random random = new Random();
		robot.mouseMove(x, y);
		Thread.sleep(40 + random.nextInt(20));
		robot.mousePress(InputEvent.BUTTON1_MASK);
		Thread.sleep(40 + random.nextInt(20));
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
}
