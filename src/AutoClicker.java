import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import java.util.Timer;
import javax.imageio.ImageIO;

public class AutoClicker
{
	public static int number_of_NPCs = 4;
	public static boolean[] NPCs_hunted = new boolean[number_of_NPCs];
	public static int[] NPCs_color_radius = new int[number_of_NPCs];
	public static int[] NPCs_red = new int[number_of_NPCs];
	public static int[] NPCs_green = new int[number_of_NPCs];
	public static int[] NPCs_blue = new int[number_of_NPCs];

	
	// NEXT: write function that detects if character is moving by checking multiple locations the map 
	// 		 and detecting whether the colors change in at least x locations in isMoving()
	
	
	public static void main(String[] args) throws InterruptedException, IOException, AWTException 
	{
		Robot robot = new Robot();
		//addNPCs();
		
		while (true) // do for 3 hours
		{
			train(robot);
			//bankAlKharid(robot);
			//returnToWarriors(robot);
		}
	}
	
	public static void train(Robot robot) throws AWTException, InterruptedException
	{
		Random random = new Random();
		Rectangle screenRect = new Rectangle(105, 130, 505, 330);
		BufferedImage screenCapture = robot.createScreenCapture(screenRect);
		long last_time_active = System.currentTimeMillis();
		int food_count = 0;
		
		while (food_count < 28)
		{
			screenCapture = robot.createScreenCapture(screenRect);
			long elapsed_time = (System.currentTimeMillis() - last_time_active) / 1000;
			if (elapsed_time > 50)
			{
				//walkToFountain(robot);
			}
			if (!engaged(robot, screenCapture))
			{
				System.out.println("Searching...");
				findNPCs(robot, screenCapture);
			}
			else
			{
				last_time_active = System.currentTimeMillis();
				System.out.println("Engaged!");
			}
			if (lowHealth(robot))
			{
				//eat(robot, food_count);
				//food_count++;
			}
			detectRandoms(robot, screenCapture);
			robot.setAutoDelay(200 + random.nextInt(200));
		    robot.setAutoWaitForIdle(true);
		}
		Thread.sleep(4500 + random.nextInt(700));
	}
	

	public static void clickMove(Robot robot, int x, int y) throws AWTException, InterruptedException // Clicks on given X and Y coordinates
	{
		Random random = new Random();
		robot.mouseMove(x, y);
		Thread.sleep(40 + random.nextInt(20));
		robot.mousePress(InputEvent.BUTTON1_MASK);
		Thread.sleep(80 + random.nextInt(50));
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	public static void rightClick(Robot robot, int x, int y) throws AWTException, InterruptedException // Clicks on given X and Y coordinates
	{
		Random random = new Random();
		robot.mouseMove(x, y);
		Thread.sleep(20 + random.nextInt(20));
		robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
		Thread.sleep(50 + random.nextInt(50));
		robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
	}
	
	public static BufferedImage returnClickWindow(Robot robot, int x, int y, Rectangle dialogueRect) throws InterruptedException
	{
		Random random = new Random();
		robot.mouseMove(x, y);
		Thread.sleep(20 + random.nextInt(20));
		robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
		BufferedImage dialogueWindow = robot.createScreenCapture(dialogueRect);
		Thread.sleep(20 + random.nextInt(10));
		robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
		return dialogueWindow;
	}
	
	public static void clickInventory(Robot robot, int x, int y) throws AWTException, InterruptedException // Clicks on given X and Y coordinates
	{
		Random random = new Random();
		robot.mouseMove(x, y);
		Thread.sleep(40 + random.nextInt(20));
		robot.mousePress(InputEvent.BUTTON1_MASK);
		Thread.sleep(80 + random.nextInt(50));
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		Thread.sleep(1200 + random.nextInt(400));
	}
	
	public static boolean isMoving(Robot robot)
	{
		return false;
	}
	
	public static void findNPCs(Robot robot, BufferedImage screenCapture) throws AWTException, InterruptedException // Finds positions where color changes
	// Finds patches of color that match the NPCs and directs the mouse to click
	{
		boolean start_NW = false;
		// Default sweep from NW
		int x_start = 10;
		int x_end = 495;
		int y_start = 10;
		int y_end = 320;
		int x_step = 5;
		int y_step = 5;
		
		if (!start_NW)
		{
			x_start = 495;
			x_end = 10;
			y_start = 320;
			y_end = 10;
			x_step = -1;
			y_step = -1;
		}

		for (int x = x_start; x != x_end; x += x_step)
		{
			for (int y = y_start; y != y_end; y += y_step)
			{
				int color = screenCapture.getRGB(x, y);
				int blue = color & 0xff;
				int green = (color & 0xff00) >> 8;
				int red = (color & 0xff0000) >> 16;
				for (int k = 0; k < number_of_NPCs; k++)
				{
					if (NPCs_hunted[k])
					{
						int red_diff = Math.abs(red - NPCs_red[k]);
						int green_diff = Math.abs(green - NPCs_green[k]);
						int blue_diff = Math.abs(blue - NPCs_blue[k]);
						if (red_diff <= NPCs_color_radius[k] && green_diff <= NPCs_color_radius[k] && blue_diff <= NPCs_color_radius[k])
						{
							int count = 0;
							for (int n = -10; n < 10; n++)
							{
								for (int m = -10; m < 10; m++)
								{
									int color2 = screenCapture.getRGB(x + n, y + m);
									int blue2 = color2 & 0xff;
									int green2 = (color2 & 0xff00) >> 8;
									int red2 = (color2 & 0xff0000) >> 16;
									int red_diff2 = Math.abs(red2 - NPCs_red[k]);
									int green_diff2 = Math.abs(green2 - NPCs_green[k]);
									int blue_diff2 = Math.abs(blue2 - NPCs_blue[k]);
									if (red_diff2 < NPCs_color_radius[k] && green_diff2 < NPCs_color_radius[k] && blue_diff2 < NPCs_color_radius[k])
									{
										count++;
									}
								}
							}
							if (count > 5)
							{
								System.out.println("Match at (" + (105 + x) + ", " + (130 + y) + ")!");
								clickMove(robot, 105 + x, 130 + y);
								Random random = new Random();
								Thread.sleep(3300 + random.nextInt(1500));
								return;
							}
						}
					}
				}
			}
		}
	}
	
	public static boolean engaged(Robot robot, BufferedImage screenCapture) throws AWTException
	// Checks for green/red color around hit-bar area to see if character is engaged in combat
	{
		for (int x = 220; x < 260; x++)
		{
			for (int y = 130; y < 160; y++)
			{
				int color = screenCapture.getRGB(x, y);
				int blue = color & 0xff;
				int green = (color & 0xff00) >> 8;
				int red = (color & 0xff0000) >> 16;
				if (red == 0 && green == 255 && blue == 0)
				{
					return true;
				}
				else if (red == 255 && green == 0 && blue == 0)
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean lowHealth(Robot robot)
	{
		Rectangle healthRect = new Rectangle(620, 180, 15, 15);
		BufferedImage healthCapture = robot.createScreenCapture(healthRect);
		// Color codes
		// Green: 0, 255, 0
		// Green-Yellow: 188, 255, 0
		// Yellow: 255, 228, 0
		// Orange: 255, 120, 0
		// Red: 255, 0, 0
		for (int x = 0; x < 15; x++)
		{
			for (int y = 0; y < 10; y++)
			{
				int color = healthCapture.getRGB(x, y);
				int green = (color & 0xff00) >> 8;
				if (green > 250)
				{
					return false;
				}
			}
		}
		return true;
	}
	
	public static void walkToFountain(Robot robot) throws AWTException, InterruptedException
	{
		int fountain_red = 68;
		int fountain_green = 66;
		int fountain_blue = 116;
		Random random = new Random();
		boolean standing_near_fountain = false;
		int attempt_count = 0;
		while (!standing_near_fountain)
		{
			if (attempt_count > 0)
			{
				Rectangle doorRect = new Rectangle(310, 255, 60, 90);
				BufferedImage doorCapture = robot.createScreenCapture(doorRect);
				// left door
				for (int x = 0; x < 60; x++)
				{
					for (int y = 0; y < 90; y++)
					{
						int color = doorCapture.getRGB(x, y);
						int blue = color & 0xff;
						int green = (color & 0xff00) >> 8;
						int red = (color & 0xff0000) >> 16;
						int red_diff = Math.abs(red - 108);
						int green_diff = Math.abs(green - 75);
						int blue_diff = Math.abs(blue - 40);
						if (red_diff < 3 && green_diff < 3 && blue_diff < 3)
						{
							clickMove(robot, x + 310, y + 255);
							Thread.sleep(1200 + random.nextInt(600));
							x = 60;
							y = 90;
							break;
						}
					}
				}
			}
			Rectangle minimapRect = new Rectangle(660, 135, 150, 135);
			BufferedImage minimapCapture = robot.createScreenCapture(minimapRect);
			int x_fountain = 0;
			int y_fountain = 0;
			for (int x = 0; x < 150; x++)
			{
				for (int y = 0; y < 135; y++)
				{
					int color = minimapCapture.getRGB(x, y);
					int blue = color & 0xff;
					int green = (color & 0xff00) >> 8;
					int red = (color & 0xff0000) >> 16;
					int red_diff = Math.abs(red - fountain_red);
					int green_diff = Math.abs(green - fountain_green);
					int blue_diff = Math.abs(blue - fountain_blue);
					if (red_diff < 8 && green_diff < 8 && blue_diff < 8)
					{
						x_fountain = 660 + x;
						y_fountain = 135 + y;
						x = 150;
						y = 135;
						break;
					}
				}
			}
			int x_random = random.nextInt(4) - 2;
			int y_random = random.nextInt(4) - 2;
			clickMove(robot, x_fountain + x_random, y_fountain + y_random);
			robot.mouseMove(random.nextInt(200), random.nextInt(200));
			Thread.sleep(8000 + random.nextInt(4500));	
			
			// Checks if you are at fountain
			minimapCapture = robot.createScreenCapture(minimapRect);
			for (int x = 60; x < 90; x++)
			{
				for (int y = 65; y < 90; y++)
				{
					int color = minimapCapture.getRGB(x, y);
					int blue = color & 0xff;
					int green = (color & 0xff00) >> 8;
					int red = (color & 0xff0000) >> 16;
					int red_diff = Math.abs(red - fountain_red);
					int green_diff = Math.abs(green - fountain_green);
					int blue_diff = Math.abs(blue - fountain_blue);
					if (red_diff < 8 && green_diff < 8 && blue_diff < 8)
					{
						standing_near_fountain = true;
						System.out.println("we're at the fountain!");
						x = 120;
						y = 110;
						break;
					}
				}
			}
			if (!standing_near_fountain)
			{
				System.out.println("We couldn't get there!");
			}
			attempt_count++;
		}
	}
	
	public static void returnToWarriors(Robot robot) throws AWTException, InterruptedException
	{
		Random random = new Random();
		int x_loc = 806 + random.nextInt(4) - 2;
		int y_loc = 181 + random.nextInt(16) - 8;
		clickMove(robot, x_loc, y_loc);
		Thread.sleep(23000 + random.nextInt(4000));
	}
	
	public static void eat(Robot robot, int food_count) throws AWTException, InterruptedException
	{
		System.out.println(food_count);
		int x = 675 + (food_count % 4) * 42;
		int food_row = (int) Math.floor(((food_count) / 4));
		int y = 355 + food_row * 35;
		Random random = new Random();
		int x_random = random.nextInt(12) - 6;
		int y_random = random.nextInt(12) - 6;
		if (food_count >= 27)
		{
			System.out.println("out of food");
		}
		clickInventory(robot, x + x_random, y + y_random);
	}
	
	public static void detectRandoms(Robot robot, BufferedImage screenCapture) throws AWTException, InterruptedException
	{
		boolean found_dialogue = false;
		int x_length = 0;
		int x_location = 0;
		int y_location = 0;
		for (int x = 0; x < 505; x += 5)
		{
			for (int y = 0; y < 305; y += 1)
			{
				int color = screenCapture.getRGB(x, y);
				int blue = color & 0xff;
				int green = (color & 0xff00) >> 8;
				int red = (color & 0xff0000) >> 16;
				if (red == 255 && green == 255 && blue == 0)
				{
					int count = 0;
					for (int m = 0; m < 5; m++)
					{
						for (int n = 0; n < 5; n++)
						{
							int color2 = screenCapture.getRGB(x + n, y + m);
							int blue2 = color2 & 0xff;
							int green2 = (color2 & 0xff00) >> 8;
							int red2 = (color2 & 0xff0000) >> 16;
							if (red2 == 255 && green2 == 255 && blue2 == 0)
							{
								count++;
							}
						}
					}
					if (count > 5)
					{
						found_dialogue = true;
						x_location = x + 105;
						y_location = y + 130;

						// now we're in a given row of Y
						for (int k = 0; k < 495; k += 10)
						{
							for (int m = 0; m < 10; m++)
							{
								for (int n = 0; n < 10; n++)
								{
									int color2 = screenCapture.getRGB(k + m, y + n);
									int blue2 = color2 & 0xff;
									int green2 = (color2 & 0xff00) >> 8;
									int red2 = (color2 & 0xff0000) >> 16;
									if (red2 == 255 && green2 == 255 && blue2 == 0)
									{
										x_length += 10;
										m = 10;;
										n = 10;
										break;
									}
								}
							}
						}
						x = 505;
						y = 305;
						break;
					}
				}
			}
		}
		int x_center = x_location + x_length / 2 - 5;
		int y_center = y_location + 15;
		if (found_dialogue)
		{
			boolean found_random = false;
			System.out.println("Found text at (" + x_location + ", " + y_location + ") with length " + x_length);
			System.out.println("Center at (" + x_center + ", " + (y_center + 5) + ").");
			
			Rectangle dialogueRect = new Rectangle(x_center - 100, y_center + 35, 200, 5);
			BufferedImage dialogueCapture = returnClickWindow(robot, x_center, y_center, dialogueRect);
			int x_click = 0;
			int y_click = 0;
			boolean found_pickpocket = false;
			for (int x = 0; x < 200; x++)
			{
				for (int y = 0; y < 5; y++)
				{
					int color = dialogueCapture.getRGB(x, y);
					int blue = color & 0xff;
					int green = (color & 0xff00) >> 8;
					int red = (color & 0xff0000) >> 16;
					if (red == 255 && green == 255 && blue == 0 && !found_random)
					{
						found_random = true;
						x_click = x_center + x - 100;
						y_click = y_center + y + 35;
					}
					else if (red == 0 && green == 255 && blue == 0)
					{
						found_pickpocket = true;
					}
				}
			}
			if (found_random && !found_pickpocket) 
			{
				clickMove(robot, x_click, y_click);
			}
			else
			{
				System.out.println("No dialogue (besides pickpocket) option was found");
			}
		}
		else
		{
			System.out.println("No randoms were found");
		}
	}
	
	public static void bankAlKharid(Robot robot) throws AWTException, InterruptedException
	{
		// Move to fountain
		walkToFountain(robot);
		
		// Move to cactus (53, 108, 49)
		Rectangle cactusRect = new Rectangle(660, 190, 25, 50);
		BufferedImage cactusCapture = robot.createScreenCapture(cactusRect);
		int x_cactus = 0;
		int y_cactus = 0;
		for (int x = 0; x < 25; x++)
		{
			for (int y = 0; y < 50; y++)
			{
				int color = cactusCapture.getRGB(x, y);
				int blue = color & 0xff;
				int green = (color & 0xff00) >> 8;
				int red = (color & 0xff0000) >> 16;
				int red_diff = Math.abs(red - 55);
				int green_diff = Math.abs(green - 115);
				int blue_diff = Math.abs(blue - 55);
				if (red_diff < 15 && green_diff < 20 && blue_diff < 15)
				{
					x_cactus = 660 + x;
					y_cactus = 190 + y;
					break;
				}
			}
		}
		Random random = new Random();
		int x_random = random.nextInt(4) - 2;
		int y_random = random.nextInt(4) - 2;
		clickMove(robot, x_cactus + x_random, y_cactus + y_random); 
		Thread.sleep(21000 + random.nextInt(5500));
		
		// Move inside bank (700, 225)
		x_random = random.nextInt(6) - 3;
		x_random = random.nextInt(6) - 3;
		clickMove(robot, 702 + x_random, 225 + y_random);
		Thread.sleep(7000 + random.nextInt(2000));
		
		// Talk to banker (146, 129, 98)
		
		Rectangle bankRect = new Rectangle(260, 280, 80, 60);
		BufferedImage bankCapture = robot.createScreenCapture(bankRect);
		int x_bank = 0;
		int y_bank = 0;
		for (int x = 0; x < 70; x++)
		{
			for (int y = 0; y < 50; y++)
			{
				int color = bankCapture.getRGB(x, y);
				int blue = color & 0xff;
				int green = (color & 0xff00) >> 8;
				int red = (color & 0xff0000) >> 16;
				int red_diff = Math.abs(red - 89);
				int green_diff = Math.abs(green - 86);
				int blue_diff = Math.abs(blue - 83);
				if (red_diff < 3 && green_diff < 3 && blue_diff < 3)
				{
					int count = 0;
					for (int n = 0; n < 10; n++)
					{
						for (int m = 0; m < 10; m++)
						{
							int color2 = bankCapture.getRGB(x + n, y + m);
							int blue2 = color2 & 0xff;
							int green2 = (color2 & 0xff00) >> 8;
							int red2 = (color2 & 0xff0000) >> 16;
							int red_diff2 = Math.abs(red2 - 89);
							int green_diff2 = Math.abs(green2 - 86);
							int blue_diff2 = Math.abs(blue2 - 83);
							if (red_diff2 < 10 && green_diff2 < 10 && blue_diff2 < 10)
							{
								count++;
							}
						}
					}
					if (count > 30)
					{
						x_bank = 260 + x;
						y_bank = 280 + y;
						x = 70;
						y = 70;
					}
				}
			}
		}
		
		x_random = random.nextInt(4) - 2;
	    y_random = random.nextInt(4) - 2;
		clickMove(robot, x_bank + x_random, y_bank + y_random);
		Thread.sleep(1200 + random.nextInt(1500));
		
		// Empty inventory
		x_random = random.nextInt(8) - 4;
		y_random = random.nextInt(8) - 4;
		clickMove(robot, 542 + x_random, 438 + y_random);
		Thread.sleep(300 + random.nextInt(200));
		
		// Right click trout
		x_random = random.nextInt(8) - 4;
		y_random = random.nextInt(8) - 4;
		rightClick(robot, 521 + x_random, 227 + y_random);
		Thread.sleep(300 + random.nextInt(200));
		
		// Withdraw all
		x_random = random.nextInt(30) - 15;
		y_random = random.nextInt(4) - 2;
		clickMove(robot, 521 + x_random, 327 + y_random);
		Thread.sleep(300 + random.nextInt(200));
	}

	public static void addNPCs()
	// Initialize NPC colors to look for
	{
		// Homogeneous cow
		NPCs_hunted[0] = true;
		NPCs_red[0] = 104;
		NPCs_green[0] = 89;
		NPCs_blue[0] = 84;
		NPCs_color_radius[0] = 2;
		// Mixed light cow
		NPCs_hunted[1] = true;
		NPCs_red[1] = 144;
		NPCs_green[1] = 126;
		NPCs_blue[1] = 113;
		NPCs_color_radius[1] = 2;
		// Mixed dark cow
		NPCs_hunted[2] = true;
		NPCs_red[2] = 89;
		NPCs_green[2] = 81;
		NPCs_blue[2] = 60;
		NPCs_color_radius[2] = 2;
		// Al-Kharid Warrior
		NPCs_hunted[3] = false;
		NPCs_red[3] = 129;
		NPCs_green[3] = 14;
		NPCs_blue[3] = 128;
		NPCs_color_radius[3] = 10;
	}
	
	public static void readDataIntoVector(ArrayList<String> lines_without_duplicates) throws IOException
	{
		ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get("Colors.txt"));
		ArrayList<String> item_names = new ArrayList<String>();
		ArrayList<Integer> item_red = new ArrayList<Integer>();
		ArrayList<Integer> item_green = new ArrayList<Integer>();
		ArrayList<Integer> item_blue = new ArrayList<Integer>();
		for (int i = 0; i < lines_without_duplicates.size(); i++)
		{
			String line = lines_without_duplicates.get(i);
			//System.out.println(line);
			String item_name = line.substring(0, line.indexOf(":"));
			line = line.substring(line.indexOf(":") + 1);
			//System.out.println(line);
			int red = Integer.parseInt((line.substring(0, line.indexOf(","))));
			line = line.substring(line.indexOf(",") + 1);
			//System.out.println(line);
			int green = Integer.parseInt((line.substring(0, line.indexOf(","))));
			line = line.substring(line.indexOf(",") + 1);
			//System.out.println(line);
			int blue = Integer.parseInt(line);
			item_names.add(item_name);
			item_red.add(red);
			item_green.add(green);
			item_blue.add(blue);
		}
		System.out.println("Printing arraylists: ");
		for (int j = 0; j < item_names.size(); j++)
		{
			System.out.println(item_names.get(j) + ": " + item_red.get(j) + ", " + item_green.get(j) + ", " + item_blue.get(j));
		}
		System.out.println();
	}
	
	public static void displayMouseStats(Robot robot) throws InterruptedException, AWTException
	// Displays mouse location and RGB color beneath
	{
			PointerInfo a = MouseInfo.getPointerInfo();
			Point b = a.getLocation();
			int x = (int) b.getX();
			int y = (int) b.getY();
			System.out.println("x: " + x + " y: " + y);
	}
}