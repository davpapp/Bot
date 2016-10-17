import java.awt.AWTException;
import java.awt.Color;
import java.awt.List;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class MouseStats
{
	public static int NPC_red = 144;
	public static int NPC_green = 126;
	public static int NPC_blue = 113;

	public static void main(String[] args) throws InterruptedException, AWTException, IOException  
	{
		Robot robot = new Robot();
		File file = new File("drop_data.txt");
		BufferedWriter output = new BufferedWriter(new FileWriter(file));
		
		Thread.sleep(2000);
		long start = System.currentTimeMillis();
		while (System.currentTimeMillis() - start < 10 * 1000)
		{
			//compareColors();
			displayMouseStats(true, output);
			Thread.sleep(50);
			//speedTest(robot);
		}	
		output.close();
		ArrayList<String> lines_without_duplicates = removeDuplicates();
		checkForDuplicateColors();
		readDataIntoVector(lines_without_duplicates);
	}
	
	public static void displayMouseStats(boolean read_to_file, BufferedWriter output) throws InterruptedException, AWTException, IOException
	{
		Robot robot = new Robot();
		Rectangle screenRect = new Rectangle(1920, 1200);
		BufferedImage screenCapture = robot.createScreenCapture(screenRect);
		PointerInfo a = MouseInfo.getPointerInfo();
		Point b = a.getLocation();
		int x = (int) b.getX();
		int y = (int) b.getY();
		int color = screenCapture.getRGB(x, y);
		int blue = color & 0xff;
		int green = (color & 0xff00) >> 8;
		int red = (color & 0xff0000) >> 16;
		if (read_to_file)
		{
			readColorsToFile(output, red, green, blue);
		}
		System.out.println("x: " + x + " y: " + y + " RGB: (" + red + ", " + green + ", " + blue + ")");
		/*int red_diff = Math.abs(red - NPC_red);
		int green_diff = Math.abs(green - NPC_green);
		int blue_diff = Math.abs(blue - NPC_blue);
		if (red_diff <= 2 && green_diff <= 2 && blue_diff <= 2)
		{
			System.out.println("Match at " + x + ", " + y);
		}
		else
		{
			System.out.println("x: " + x + " y: " + y + " RGB: (" + red + ", " + green + ", " + blue + ")");
			//System.out.println("x: " + x + " y: " + y + " RGB: (" + red_diff + ", " + green_diff + ", " + blue_diff + ")");
		}*/
	}
	
	public static void readColorsToFile(BufferedWriter output, int red, int green, int blue) throws IOException
	{
		output.write("Bones:" + red + "," + green + "," + blue);
		output.newLine();
	}
	
	public static ArrayList<String> removeDuplicates() throws IOException
	{
		System.out.println();
		ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get("drop_data.txt"));
		ArrayList<String> lines_without_duplicates = new ArrayList<String>();
	    for (int i = 0; i < lines.size(); i++)
		{
			if (!lines_without_duplicates.contains(lines.get(i)))
			{
				lines_without_duplicates.add(lines.get(i));
			}
		}
	    System.out.println("Printing without duplicates: ");
	    for (int j = 0; j < lines_without_duplicates.size(); j++)
	    {
	    	System.out.println(lines_without_duplicates.get(j));
	    }
	    return lines_without_duplicates;
	}
	
	public static void readDataIntoVector(ArrayList<String> lines_without_duplicates) throws IOException
	{
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
	}
	
	public static void checkForDuplicateColors() throws IOException
	{
		ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get("drop_data.txt"));
		for (int i = 0; i < lines.size(); i++)
		{
			String lineRGB = lines.get(i).substring(lines.get(i).indexOf(":") + 1, lines.get(i).length());
			System.out.println(lineRGB);
			for (int j = i + 1; j < lines.size(); j++)
			{
				int colon_index = lines.get(j).indexOf(":");
				String lineRGB2 = lines.get(j).substring(colon_index + 1, lines.get(j).length());
				//String lineRGB2 = lines.get(j).substring(lines.get(j).indexOf(":") + 1, lines.get(j).length());
				//System.out.println(lineRGB + " and " + lineRGB2);
				if (lineRGB.equals(lineRGB2))
				{
					System.out.println("Warning! Color overlap between two items: ");
					System.out.println(lines.get(i) + " and " + lines.get(j));
				}
				else
				{
					//System.out.println("different");
				}
			}
		}
	}
	
	public static void speedTest(Robot robot) throws AWTException
	{
		Rectangle screenRect = new Rectangle(1920, 1200);
		BufferedImage screenCapture = robot.createScreenCapture(screenRect);
		PointerInfo a = MouseInfo.getPointerInfo();
		for (int x = 0; x < 1920; x++)
		{
			for (int y = 0; y < 1200; y++)
			{
				if (screenCapture.getRGB(x, y) > 0)
				{
					// do nothing
				}
			}
		}
	}
}
