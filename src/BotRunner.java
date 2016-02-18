import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Robot;
import java.io.IOException;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

public class BotRunner 
{
	public static void main(String[] args) throws InterruptedException, IOException, AWTException 
	{
		Robot robot = new Robot();
		Random random = new Random();

		int number_of_bots = 1;
		AutoFighter[] bots = new AutoFighter[number_of_bots];
		//bots[0] = new AutoFighter(robot, "Mine");
		for (int i = 0; i < number_of_bots; i++)
		{
			//bots[i].allignScreen(robot);
			bots[i] = new AutoFighter(robot, "Zombie");
		}
		bots[0].readDataIntoVector();
		long start_time = System.currentTimeMillis();
		long play_time = 180 * 60;
		/*
		while ((System.currentTimeMillis() - start_time) / 1000 < play_time)
		{
			bots[0].readInventory(robot);
			Thread.sleep(10000);
		}*/
		/*while (true)
		{
			bots[0].updateScreen(robot);
			bots[0].findTannerPro(robot);
			
			Thread.sleep(4000);
		}*/
		while ((System.currentTimeMillis() - start_time) / 1000 < play_time)
		{
			for (int i = 0; i < number_of_bots; i++)
			{
				if (bots[i].botType() == "Zombie")
				{
					trainZombies(robot, bots[i], i);
				}
				else if (bots[i].botType() == "Warrior")
				{
					trainAlKharid(robot, bots[i], i);
				}
				else if (bots[i].botType() == "Cow")
				{
					trainCows(robot, bots[i], i);
				}
				else if (bots[i].botType() == "Tan")
				{
						tanCowhide(robot, bots[i], i);
				}
				else if (bots[i].botType() == "Mine")
				{
					mineIron(robot, bots[i], i);
				}
			}
			Thread.sleep(100 + random.nextInt(200));
		}
	}
	
	public static void mineIron(Robot robot, AutoFighter bot, int bot_number) throws AWTException, InterruptedException
	{
		if (bot.doneWaiting())
		{
			bot.updateScreen(robot);
			//bot.detectRandoms(robot);
			bot.turnRunOn(robot);
			bot.executeActivityQueue(robot);
		}
	}
	
	public static void trainAlKharid(Robot robot, AutoFighter bot, int bot_number) throws AWTException, InterruptedException
	{
		if (bot.doneWaiting())
		{
			bot.updateScreen(robot);
			bot.scanGrid();
			bot.displayBothGrids();
			bot.readInventory(robot);
			if (bot.isEngaged() == false)
			{
				System.out.println("Bot #" + bot_number + " is searching for NPC...");
				bot.findClosestNPC(robot);
			}
			else
			{
				System.out.println("Bot #" + bot_number + " is engaged!");
			}
			if (bot.isLowHealth(robot))
			{
				System.out.println("Bot #" + bot_number + " is at low health. Eating food.");
				bot.eatFood(robot);
			}
			/*if (bot.isIdle(180, bot_number))
			{
				bot.addToActivityQueue("Move Around");
			}*/
			bot.detectRandoms(robot);
			bot.executeActivityQueue(robot);
		}
	}
	

	public static void trainZombies(Robot robot, AutoFighter bot, int bot_number) throws AWTException, InterruptedException
	{
		if (bot.doneWaiting())
		{
				bot.updateScreen(robot);					
				if (bot.isLowHealth(robot))
				{
					System.out.println("Bot #" + bot_number + " is at low health. Eating food.");
					bot.eatFood(robot);
				}	
				if (bot.isEngaged())
				{
					System.out.println("Bot #" + bot_number + " is engaged.");
				}
				if (bot.isIdle(10, bot_number))
				{
					bot.addToActivityQueue("Move Around");
				}
				if (bot.isIdle(20, bot_number))
				{
					bot.renewAggressiveness(robot);
				}
				bot.detectRandoms(robot);
				bot.executeActivityQueue(robot);
		}
		else
		{
			System.out.println("Bot #" + bot_number + " is waiting");
		}
	}
	
	public static void trainCows(Robot robot, AutoFighter bot, int bot_number) throws AWTException, InterruptedException
	{
		if (bot.doneWaiting())
		{
			bot.updateScreen(robot);
			bot.scanGrid();
			bot.displayBothGrids();
			if (bot.isEngaged() == false)
			{
				System.out.println("Bot #" + bot_number + " is searching for NPC...");
				bot.findClosestNPC(robot);
			}
			else
			{
				System.out.println("Bot #" + bot_number + " is engaged!");
			}
			bot.detectRandoms(robot);
			bot.executeActivityQueue(robot);
		}
	}
	
	public static void tanCowhide(Robot robot, AutoFighter bot, int bot_number) throws AWTException, InterruptedException
	{
		if (bot.doneWaiting())
		{
			bot.updateScreen(robot);
			//bot.detectRandoms(robot);
			bot.turnRunOn(robot);
			bot.executeActivityQueue(robot);
		}
	}
}
