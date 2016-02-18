import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;
import java.util.Vector;
import java.util.Timer;
import javax.imageio.ImageIO;

public class autoFighterV2
{
	public static int width = 500;
	public static int height = 320;
	public static int x_offset = 105;
	public static int y_offset = 130;
	public static int x_precision = 20;
	public static int y_precision = 20;
	public static int grid_width = (int) width / x_precision; // 20
	public static int grid_height = (int) height / y_precision; // 13
	public static int grid_obstacle[][] = new int[grid_width][grid_height];
	public static int grid_NPC[][] = new int[grid_width][grid_height];
	public static int[] NPC_x_locs = new int[20];
	public static int[] NPC_y_locs = new int[20];
	public static int x_center = 0;
	public static int y_center = 0;
	public static int x_grid_center = 0;
	public static int y_grid_center = 0;
	public static boolean food_left = true;
	public static int food_count = 28;
	public static boolean drop = false;
	public static int drop_x = 0;
	public static int drop_y = 0;
	
	public static int number_of_NPCs = 2;
	public static boolean[] NPC_used = new boolean[number_of_NPCs];
	public static int[] NPC_red = new int[number_of_NPCs];
	public static int[] NPC_green = new int[number_of_NPCs];
	public static int[] NPC_blue = new int[number_of_NPCs];
	public static int[] NPC_tolerance = new int[number_of_NPCs];
	public static int number_of_obstacles = 4;
	public static boolean[] obstacle_used = new boolean[number_of_obstacles];
	public static int[] obstacle_red = new int[number_of_obstacles];
	public static int[] obstacle_green = new int[number_of_obstacles];
	public static int[] obstacle_blue = new int[number_of_obstacles];
	public static int[] obstacle_tolerance = new int[number_of_obstacles];
	public static int number_of_items = 6;
	public static boolean[] item_used = new boolean[number_of_items];
	public static int[] item_red = new int[number_of_items];
	public static int[] item_green = new int[number_of_items];
	public static int[] item_blue = new int[number_of_items];
	public static int[] item_tolerance = new int[number_of_items];
	public static String[] item_category = new String[number_of_items];
	public static Rectangle screen_rectangle = new Rectangle(x_offset, y_offset, width, height);
	public static BufferedImage screen_capture;
	public static long wait_time;
	
	// Function Glossary
	// main()
	// initializeCenter(robot)
	// scanGrid(screen_capture)
	// scanForObstacles(color)
	// scanForNPCs(color)
	// colorMatch(color, red, green, blue, tolerance)
	// findClosestNPC()
	// searchPath(grid, start x, start y, end x, end y)
	// leftClick(robot, x, y)
	// rightClick(robot, x, y)
	// addNPCs()
	// addObstacles()
	
	/*public autoFighterV2(int x, int y)
	{
		width = 460;
		height = 300;
		x_offset = x_center - width / 2;
		y_offset = y_center - height / 2;
		screen_rectangle = new Rectangle(x_offset, y_offset, x, y);
	}*/
	
	public static void main(String[] args) throws AWTException, InterruptedException
	{
		Robot robot = new Robot();
		Random random = new Random();
		setup(robot);
		int play_time = 120 + random.nextInt(30);
		trainZombies(robot, play_time);
		//trainAlKharid(screen_rectangle, screen_capture, robot, play_time);
	}
	
	public static void setup(Robot robot) throws InterruptedException
	{
		initializeCenter(robot);
		addItems();
		addObstacles();
		addNPCs();
	}
	
	public static void trainZombies(Robot robot, int play_time) throws AWTException, InterruptedException
	{
		food_left = true;
		food_count = 28;
		Random random = new Random();
		long start_time = System.currentTimeMillis();
		long last_time_active = System.currentTimeMillis();
		while (food_left && ((System.currentTimeMillis() - start_time) / 1000 / 60) < play_time)
		{
			updateScreen(robot);
			if (isEngaged(30, 45, 0))
			{
				last_time_active = System.currentTimeMillis();
				System.out.println("Engaged!");
			}
			if (isLowHealth(robot))
			{
				eatFood(robot);
				System.out.println("Eating...");
			}
			if (drop)
			{
				System.out.println("Picking up drop");
				pickUpDrop(robot);
			}
			if ((System.currentTimeMillis() - last_time_active) / 1000 > (20 + random.nextInt(5)))
			{
				System.out.println("Renewing aggressiveness...");
				renewAggressiveness(robot);
				last_time_active = System.currentTimeMillis();
			}
			else if ((System.currentTimeMillis() - last_time_active) / 1000 > 10 + random.nextInt(3))
			{
				leftClickRandom(robot, 739, 211, 10);
				Thread.sleep(2500 + random.nextInt(3500));
			}
			// Random human-like behavior
			if (random.nextInt(500) == 1)
			{
				leftClickRandom(robot, 740, 311, 10); // Inventory Icon
				Thread.sleep(200 + random.nextInt(400));
				rotateCameraRightOrLeft(robot);
			}
			if (random.nextInt(700) == 1)
			{
				checkStrengthXP(robot);
			}
			detectRandoms(robot);
			Thread.sleep(100 + random.nextInt(200));
		}
		bankWestVarrock(robot);
		//returnToThreshold
	}
	
	public static void trainAlKharid(Rectangle screen_rectangle, BufferedImage screen_capture, Robot robot, int play_time) throws InterruptedException, AWTException
	{
		food_left = true;
		Random random = new Random();
		long start_time = System.currentTimeMillis();
		long last_time_active = System.currentTimeMillis();
		while (food_left && ((System.currentTimeMillis() - start_time) / 1000 / 60) < play_time)
		{
			updateScreen(robot);
			scanGrid();
			if (!isEngaged( 16, 25, 5))
			{
				System.out.println("Searching for NPC...");
				findClosestNPC(robot);
			}
			else
			{
				last_time_active = System.currentTimeMillis();
				System.out.println("Engaged in combat!");
			}
			
			if (isLowHealth(robot))
			{
				System.out.println("Low health!");
			}
			if ((System.currentTimeMillis() - last_time_active) / 1000 > 120)
			{
				rotateCameraRightOrLeft(robot);
				last_time_active = System.currentTimeMillis();
			}
			detectRandoms(robot);
			robot.setAutoDelay(200 + random.nextInt(200));
		    robot.setAutoWaitForIdle(true);
		    //displayBothGrids();
		}
	}
	
	
	
	public static void updateScreen(Robot robot)
	{
		screen_capture = robot.createScreenCapture(screen_rectangle);
	}
	
	public static void displayBothGrids()
	{
		for (int y = 0; y < grid_height; y++)
		{
			for (int x = 0; x < grid_width; x++)
			{
				if (x == x_grid_center && y == y_grid_center)
				{
					System.out.print("*");
				}
				if (grid_obstacle[x][y] == 0 && grid_NPC[x][y] == 0)
				{
					System.out.print(".");
				}
				else if (grid_NPC[x][y] > 0)
				{
					System.out.print(grid_NPC[x][y]);
				}
				else
				{
					System.out.print("x");
				}
			}
			System.out.println();
		}
	}
	
	public static void displayNPCs()
	{
		for (int y = 0; y < grid_height; y++)
		{
			for (int x = 0; x < grid_width; x++)
			{
				if (grid_NPC[x][y] == 0)
				{
					System.out.print(".");
				}
				else
				{
					System.out.print(grid_NPC[x][y]);
				}
			}
			System.out.println();
		}
	}
	
	public static void displayGrid(int x_end, int y_end)
	{
		for (int y = 0; y < grid_height; y++)
		{
			for (int x = 0; x < grid_width; x++)
			{
				if (x == x_end && y== y_end)
				{
					System.out.print("0");
				}
				else if (x == 10 && y == 6)
				{
					System.out.print("*");
				}
				else if (grid_obstacle[x][y] == 1)
				{
					System.out.print("x");
				}
				else
				{
					System.out.print("-");
				}
			}
			System.out.println();
		}
	}
	
	public static void initializeCenter(Robot robot) throws InterruptedException
	{
		System.out.println("Initializing... Please wait 5 seconds...");
		Thread.sleep(5000);
		PointerInfo a = MouseInfo.getPointerInfo();
		Point b = a.getLocation();
		int x = (int) b.getX();
		int y = (int) b.getY();
		x_center = x;
		y_center = y;
		x_grid_center = (int) ((x - x_offset)/ x_precision);
		y_grid_center = (int) ((y - y_offset) / y_precision);
		System.out.println("Center at: (" + x_center + ", " + y_center + ").");
	}
	
	public static void scanGrid()
	{
		for (int x = 0; x < grid_width; x++)
		{
			for (int y = 0; y < grid_height; y++)
			{
				grid_obstacle[x][y] = 0;
				grid_NPC[x][y] = 0;
			}
		}
		for (int i = 0; i < 10; i++)
		{
			NPC_x_locs[i] = 0;
			NPC_y_locs[i] = 0;
		}
		
		int NPC_count = 0;	
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				int color = screen_capture.getRGB(x, y);
				if (scanForObstacles(color))
				{
					int grid_x = (int) (x / x_precision);
					int grid_y = (int) (y / y_precision);
					grid_obstacle[grid_x][grid_y] = 1;
				}
				if (scanForNPCs(color))
				{
					int grid_x = (int) (x / x_precision);
					int grid_y = (int) (y / y_precision);
					if (grid_NPC[grid_x][grid_y] == 0)
					{
						grid_NPC[grid_x][grid_y] = NPC_count + 1;
						NPC_x_locs[NPC_count] = x;
						NPC_y_locs[NPC_count] = y;
						NPC_count++;
					}
				}
				if (scanForDrops(color))
				{
					drop = true;
					drop_x = x;
					drop_y = y;
				}
			}
		}
		for (int x = 0; x < grid_width; x++)
		{
			for (int y = 0; y < grid_height; y++)
			{
				if (grid_obstacle[x][y] == 1)
				{
					grid_NPC[x][y] = 0;
				}
			}
		}
		grid_obstacle[x_grid_center][y_grid_center] = 0;
	}
	
	
	
	public static boolean scanForObstacles(int color)
	{
		for (int i = 0; i < number_of_obstacles; i++)
		{
			if (obstacle_used[i] && colorMatch(color, obstacle_red[i], obstacle_green[i], obstacle_blue[i], obstacle_tolerance[i]))
			{
				return true;
			}
		}
		return false;
	}
	
	public static boolean scanForNPCs(int color)
	{
		for (int i = 0; i < number_of_NPCs; i++)
		{
			if (NPC_used[i] && colorMatch(color, NPC_red[i], NPC_green[i], NPC_blue[i], NPC_tolerance[i]))
			{
				return true;
			}
		}
		return false;
	}
	
	public static boolean scanForDrops(int color)
	{
		for (int i = 0; i < number_of_items; i++)
		{
			if (item_used[i] && item_category[i] == "Drop" && colorMatch(color, item_red[i], item_green[i], item_blue[i], item_tolerance[i]))
			{
				return true;
			}
		}
		return false;
	}
	
	public static void checkStrengthXP(Robot robot) throws AWTException, InterruptedException
	{
		Random random = new Random();
		leftClickRandom(robot, 675, 312, 12);
		Thread.sleep(400 + random.nextInt(500));
		robot.mouseMove(677 + random.nextInt(10) - 5, 377 + random.nextInt(10) - 5);
		Thread.sleep(1500 + random.nextInt(2500));
		// Switch back to inventory
		leftClickRandom(robot, 740, 311, 10); // Inventory Icon
	}
	
	public static boolean colorMatch(int input_color, int red_match, int green_match, int blue_match, int tolerance)
	{
		int color = input_color;
		int blue = color & 0xff;
		int green = (color & 0xff00) >> 8;
		int red = (color & 0xff0000) >> 16;
		int red_diff = Math.abs(red - red_match);
		int green_diff = Math.abs(green - green_match);
		int blue_diff = Math.abs(blue - blue_match);
		if (red_diff < tolerance && green_diff < tolerance && blue_diff < tolerance)
		{
			return true;
		}
		return false;
	}
	
	public static boolean isEngaged(int bar_precision_x, int bar_precision_y_1, int bar_precision_y_2)
	{
		for (int x = x_center - x_offset - bar_precision_x; x < x_center - x_offset + bar_precision_x; x++)
		{
			for (int y = y_center - y_offset - bar_precision_y_1; y < y_center - y_offset - bar_precision_y_2; y++)
			{
				int color = screen_capture.getRGB(x, y);
				if (colorMatch(color, 0, 255, 0, 5))
				{
					return true;
				}
				else if (colorMatch(color, 255, 0, 0, 5))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean isLowHealth(Robot robot)
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
	
	public static boolean foodLeft()
	{
		return true;
	}
	
	public static void eatFood(Robot robot) throws AWTException, InterruptedException
	{
		Rectangle inventory_rect = new Rectangle(660, 340, 160, 240);
		BufferedImage inventory_capture = robot.createScreenCapture(inventory_rect);
		boolean found_food = false;
		for (int x = 0; x < 160; x++)
		{
			for (int y = 0; y < 240; y++)
			{
				int color = inventory_capture.getRGB(x, y);
				for (int k = 0; k < number_of_items; k++)
				{
					if (item_used[k] && item_category[k] == "Food" && colorMatch(color, item_red[k], item_green[k], item_blue[k], 3))
					{
						found_food = true;
						food_left = true;
						leftClickRandom(robot, 665 + x, 340 + y, 10);
						//food_count--;
						Thread.sleep(700 + 500);
						x = 160;
						y = 545;
						break;
					}
				}
			}
		}
		if (!found_food)
		{
			food_left = false;
    	}
	}
	
	public static void findClosestNPC(Robot robot) throws AWTException, InterruptedException // for now, find any accessible NPC
	{
		Random random = new Random();
		double[] NPC_distances = new double[20];
		for (int i = 0; i < 20; i++)
		{
			NPC_distances[i] = 999;
		}
		
		int count = 0;
		for (int x = 0; x < grid_width; x++)
		{
			for (int y = 0; y < grid_height; y++)
			{
				if (grid_NPC[x][y] > 0)
				{
					int[][] grid_obstacle_copy = new int[grid_width][grid_height]; 
					for (int m = 0; m < grid_width; m++)
					{
						for (int n = 0; n < grid_height; n++)
						{
							grid_obstacle_copy[m][n] = grid_obstacle[m][n];
						}
					}
					if (searchPath(grid_obstacle_copy, x_grid_center, y_grid_center, x, y))
					{
						count++;
						NPC_distances[grid_NPC[x][y] - 1] = Math.sqrt(Math.pow((x_grid_center - x), 2) + Math.pow((y_grid_center - y), 2));				
						System.out.println("Found NPC with distance " + NPC_distances[grid_NPC[x][y] - 1]);
					}
				}
			}
		}
		
		if (count > 0)
		{
			double minimum = NPC_distances[0];
			int index_of_minimum = 0;
			for (int i = 1; i < 20; i++)
			{
				if (NPC_distances[i] < minimum)
				{
					index_of_minimum = i;
					minimum = NPC_distances[i];
				}
			}
			
			int NPC_x_loc = NPC_x_locs[index_of_minimum] + x_offset;
			int NPC_y_loc = NPC_y_locs[index_of_minimum] + y_offset;
			System.out.println("NPC found at (" + NPC_x_loc + ", " + NPC_y_loc + ")");
			leftClickRandom(robot, NPC_x_loc, NPC_y_loc, 4);
			Thread.sleep(4000 + random.nextInt(2000));
		}
		else
		{
			System.out.println("No NPCs were found");
		}
	}
	
	public static void renewAggressiveness(Robot robot) throws AWTException, InterruptedException
	{
		Random random = new Random();
		centerView(robot);
		long start_time = System.currentTimeMillis();
		while (((System.currentTimeMillis() - start_time) / 1000) < (22 + random.nextInt(3)))
		{
			int x = 739;
			int y = 158;
			leftClickRandom(robot, x, y, 12);
			Thread.sleep(1200 + random.nextInt(1000));
		}
		start_time = System.currentTimeMillis();
		while (((System.currentTimeMillis() - start_time) / 1000) < (17 + random.nextInt(3)))
		{
			int x = 739;
			int y = 257;
			leftClickRandom(robot, x, y, 10);
			Thread.sleep(1200 + random.nextInt(1000));
		}
		leftClickRandom(robot, 739, 190, 6);
		Thread.sleep(1200 + random.nextInt(1000));
	}
	
	public static void toggleRunOn(Robot robot)
	{
		
	}
	
	public static boolean searchPath(int[][] search_grid, int x, int y, int x_end, int y_end) // Returns true if path exists
	{
		if (x == x_end && y == y_end)
		{
			return true;
		}
		else if (x < 0 || y < 0 || x >= grid_width || y >= grid_height) 
		{
			return false;
		}
		else if (search_grid[x][y] > 0) 
		{
			return false;
		}
		else
		{
			search_grid[x][y] = 1;
			if (searchPath(search_grid, x, y - 1, x_end, y_end))
			{
				return true;
			}
			if (searchPath(search_grid, x + 1, y, x_end, y_end))
			{
				return true;
			}
			if (searchPath(search_grid, x, y + 1, x_end, y_end))
			{
				return true;
			}
			if (searchPath(search_grid, x - 1, y, x_end, y_end))
			{
				return true;
			}
			search_grid[x][y] = 1;
			return false;
		}
	}
	
	public static void detectRandoms(Robot robot) throws AWTException, InterruptedException
	{
		boolean found_dialogue = false;
		int x_length = 0;
		int x_location = 0;
		int y_location = 0;
		for (int x = 0; x < 490; x += 5)
		{
			for (int y = 0; y < 310; y += 1)
			{
				int color = screen_capture.getRGB(x, y);
				if (colorMatch(color, 255, 255, 0, 1))
				{
					found_dialogue = true;
					x_location = x + x_offset;
					y_location = y + y_offset;
					for (int k = 0; k < 490; k += 10)
					{
						for (int m = 0; m < 10; m++)
						{
							for (int n = 0; n < 10; n++)
							{
								int color2 = screen_capture.getRGB(k + m, y + n);
								if (colorMatch(color2, 255, 255, 0, 1))
								{
									x_length += 10;
									m = 10;;
									n = 10;
									break;
								}
							}
						}
					}
					x = 490;
					y = 310;
					break;
				}
			}
		}

		int chat_x_center = x_location + x_length / 2 - 10; // the center of the chat
		int chat_y_center = y_location + 15;
		if (found_dialogue && x_length > 50 && Math.abs(chat_x_center - x_center) < 100 && Math.abs(chat_y_center - y_center) < 75)
		{
			boolean found_random = false;
			boolean found_pickpocket = false;

			Rectangle dialogue_rect = new Rectangle(chat_x_center - 100, chat_y_center + 35, 200, 5);
			BufferedImage dialogue_capture = returnClickWindow(robot, chat_x_center, chat_y_center, dialogue_rect);
			int x_click = 0;
			int y_click = 0;
			
			for (int x = 0; x < 200; x++)
			{
				for (int y = 0; y < 5; y++)
				{
					int color = dialogue_capture.getRGB(x, y);
					if (colorMatch(color, 255, 255, 0, 1))
					{
						found_random = true;
						if (x_click == 0)
						{	
							x_click = chat_x_center + 100 - x;
							y_click = chat_y_center + 34 + y;
						}
					}
					else if (colorMatch(color, 0, 255, 0, 1))
					{
						found_pickpocket = true;
						x = 200;
						y = 5;
						break;
					}
				}
			}
			Random random = new Random();
			if (found_random && !found_pickpocket) 
			{
				Thread.sleep(500 + random.nextInt(200));
				leftClickPrecise(robot, x_click, y_click);
				Thread.sleep(800 + random.nextInt(800));
			}
			else
			{
				int x_hover = random.nextInt(500) + x_offset;
				int y_hover = random.nextInt(320) + y_offset;
				Thread.sleep(700 + random.nextInt(1000));
				robot.mouseMove(x_hover, y_hover);
			}
		}
		else
		{
			System.out.println("No randoms were found");
		}
	}
	
	public static void bankWestVarrock(Robot robot) throws AWTException, InterruptedException
	{
		Random random = new Random();
		centerView(robot);
		long start_time = System.currentTimeMillis();
		while (((System.currentTimeMillis() - start_time) / 1000) < (12 + random.nextInt(3)))
		{
			int x = 728;
			int y = 158;
			leftClickRandom(robot, x, y, 16);
			Thread.sleep(1200 + random.nextInt(1000));
		}
	}
	
	public static BufferedImage returnClickWindow(Robot robot, int x, int y, Rectangle dialogueRect) throws InterruptedException
	{
		Random random = new Random();
		robot.mouseMove(x, y);
		Thread.sleep(40 + random.nextInt(20));
		robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
		BufferedImage dialogueWindow = robot.createScreenCapture(dialogueRect);
		Thread.sleep(40 + random.nextInt(20));
		robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
		return dialogueWindow;
	}
	
	
	public static void detectChat(Robot robot) throws IOException
	{
		Rectangle chat_rect = new Rectangle();
		BufferedImage chat_capture = robot.createScreenCapture(chat_rect);
		boolean found_chat = false;
		for (int x = 0 ; x < 500; x += 5)
		{
			for (int y = 0; y < 30; y++)
			{
				int color = chat_capture.getRGB(x, y);
				if (colorMatch(color, 0, 0, 255, 1))
				{
					
				}
			}
		}
		if (found_chat)
		{
			File chat_output = new File("chat_output.jpg");
			ImageIO.write(chat_capture, "jpg", chat_output);
		}
	}
	
	public static void pickUpDrop(Robot robot) throws AWTException, InterruptedException
	{
		System.out.println("Picking up drop...");
		Random random = new Random();
		rightClick(robot, drop_x, drop_y - 5);
		Thread.sleep(200 + random.nextInt(300));
		leftClickRandom(robot, drop_x, drop_y + 23, 2);
		Thread.sleep(2500 + random.nextInt(1500));
		drop = false;
		drop_x = 0;
		drop_y = 0;
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
	
	public static void leftClickPrecise(Robot robot, int x, int y) throws AWTException, InterruptedException
	{
		Random random = new Random();
		robot.mouseMove(x, y);
		Thread.sleep(40 + random.nextInt(20));
		robot.mousePress(InputEvent.BUTTON1_MASK);
		Thread.sleep(40 + random.nextInt(20));
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	public static void rightClick(Robot robot, int x, int y) throws AWTException, InterruptedException
	{
		Random random = new Random();
		robot.mouseMove(x, y);
		Thread.sleep(40 + random.nextInt(20));
		robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
		Thread.sleep(60 + random.nextInt(20));
		robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
	}
	
	public static void rotateCameraRightOrLeft(Robot robot) throws InterruptedException
	{
		Random random = new Random();
		if (random.nextBoolean())
		{
			robot.keyPress(KeyEvent.VK_RIGHT);
			Thread.sleep(200 + random.nextInt(400));
			robot.keyRelease(KeyEvent.VK_RIGHT);
			Thread.sleep(60 + random.nextInt(20));
		}
		else
		{
			robot.keyPress(KeyEvent.VK_LEFT);
			Thread.sleep(200 + random.nextInt(400));
			robot.keyRelease(KeyEvent.VK_LEFT);
			Thread.sleep(60 + random.nextInt(20));
		}
	}
	
	public static void centerView(Robot robot) throws AWTException, InterruptedException
	{
		Random random = new Random();
		leftClickRandom(robot, 658, 146, 16);
		robot.keyPress(KeyEvent.VK_UP);
		Thread.sleep(1200 + random.nextInt(1700));
		robot.keyRelease(KeyEvent.VK_UP);
		Thread.sleep(60 + random.nextInt(20));
	}
	
	public static void addNPCs()
	{
		// Al-Kharid Warrior (Level 9)
		NPC_used[0] = false;
		NPC_red[0] = 129;
		NPC_green[0] = 14;
		NPC_blue[0] = 128;
		NPC_tolerance[0] = 3;
		
		// Fresh Crawler (Level 41)
		
		// Zombie (Level 44)
		NPC_used[1] = false;
		NPC_red[1] = 251;
		NPC_green[1] = 182;
		NPC_blue[1] = 77;
		NPC_tolerance[1] = 1;
	}
	
	public static void addObstacles()
	{
		// Al-Kharid castle wall bright
		obstacle_used[0] = true;
		obstacle_red[0] = 186;
		obstacle_green[0] = 185;
		obstacle_blue[0] = 175;
		obstacle_tolerance[0] = 5;
		
		// Al-Kharid castle wall dark
		obstacle_used[1] = true;
		obstacle_red[1] = 174;
		obstacle_green[1] = 174;
		obstacle_blue[1] = 162;
		obstacle_tolerance[1] = 2;
		
		// Door + Wooden furniture
		obstacle_used[2] = true;
		obstacle_red[2] = 106;
		obstacle_green[2] = 72;
		obstacle_blue[2] = 38;
		obstacle_tolerance[2] = 5;

		// Al-Kharid castle wall darkest
		obstacle_used[3] = true;
		obstacle_red[3] = 168;
		obstacle_green[3] = 168;
		obstacle_blue[3] = 156;
		obstacle_tolerance[3] = 1;
	}
	
	public static void addItems()
	{
		// Trout
		item_used[0] = true;
		item_red[0] = 164;
		item_green[0] = 135;
		item_blue[0] = 130;
		item_tolerance[0] = 1;
		item_category[0] = "Food";
		
		// Salmon
		item_used[1] = true;
		item_red[1] = 243;
		item_green[1] = 80;
		item_blue[1] = 28;
		item_tolerance[1] = 1;
		item_category[1] = "Food";
		
		// Strength Potion
		item_used[2] = false;
		item_red[2] = 0;
		item_green[2] = 0;
		item_blue[2] = 0;
		item_tolerance[2] = 1;
		item_category[2] = "Potion";
		
		// Uncut Sapphire
		item_used[3] = false;
		item_red[3] = 0;
		item_green[3] = 0;
		item_blue[3] = 0;
		item_tolerance[3] = 1;
		item_category[3] = "Drop";
		
		// Uncut Emerald
		item_used[4] = false;
		item_red[4] = 0;
		item_green[4] = 0;
		item_blue[4] = 0;
		item_tolerance[4] = 1;
		item_category[4] = "Drop";
		
		// Steel Arrow
		item_used[5] = true;
		item_red[5] = 111;
		item_green[5] = 22;
		item_blue[5] = 14;
		item_tolerance[5] = 1;
		item_category[5] = "Drop";
	}
	
	public static int generateLogRandom()
	{
		Random random = new Random();
		
		int x = 0;
		return x;
	}

	public static void displayMouseStats() throws InterruptedException, AWTException
	{
		Robot robot = new Robot();
		Rectangle screenRect = new Rectangle(1920, 1200);
		BufferedImage screen_cap = robot.createScreenCapture(screenRect);
		PointerInfo a = MouseInfo.getPointerInfo();
		Point b = a.getLocation();
		int x = (int) b.getX();
		int y = (int) b.getY();
		int color = screen_cap.getRGB(x, y);
		int blue = color & 0xff;
		int green = (color & 0xff00) >> 8;
		int red = (color & 0xff0000) >> 16;
		int red_diff = Math.abs(red - obstacle_red[0]);
		int green_diff = Math.abs(green - obstacle_green[0]);
		int blue_diff = Math.abs(blue - obstacle_blue[0]);
		if (red_diff <= 10 && green_diff <= 10 && blue_diff <= 10)
		{
			System.out.println("Match at " + x + ", " + y);
		}
		else
		{
			System.out.println("x: " + x + " y: " + y + " RGB: (" + red + ", " + green + ", " + blue + ")");
			//System.out.println("x: " + x + " y: " + y + " RGB: (" + red_diff + ", " + green_diff + ", " + blue_diff + ")");
		}
	}
}
