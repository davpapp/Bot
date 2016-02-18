import java.awt.AWTException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class BotTab2
{
	private JPanel panel;
	private JLabel lblTypeOfBot;
	private JRadioButton rdbtnNone;
	private JRadioButton rdbtnAlkharidWarrior;
	private JRadioButton rdbtnFleshcrawler;
	private JRadioButton rdbtnTanner;
	
	
	private JPanel food_panel;
	private JLabel lblSelectFood;
	private JCheckBox chckbxTrout;
	private JCheckBox chckbxSalmon;
	private JCheckBox chckbxTuna;
	private JCheckBox chckbxLobster;
	
	public BotTab2() throws AWTException
	{
		setUp();
		setUpListeners();
	}
	
	private void setUp()
	{
		// Main options
		lblTypeOfBot = new JLabel("Choose number of bots:");
		lblTypeOfBot.setBounds(10, 11, 159, 14);
	
		rdbtnNone= new JRadioButton("None");
		rdbtnNone.setBounds(10, 30, 51, 23);
		rdbtnNone.setSelected(true);
		rdbtnAlkharidWarrior = new JRadioButton("Al-Kharid Warrior");
		rdbtnAlkharidWarrior.setBounds(10, 56, 107, 23);
		rdbtnFleshcrawler = new JRadioButton("Flesh-Crawler");
		rdbtnFleshcrawler.setBounds(10, 82, 91, 23);
		rdbtnTanner = new JRadioButton("Tanner");
		rdbtnTanner.setBounds(10, 108, 59, 23);
		
		// Food panel
		JPanel food_panel = new JPanel();
		food_panel.setBounds(141, 11, 155, 154);
		food_panel.setVisible(false);
		
		lblSelectFood = new JLabel("Select food:");
		lblSelectFood.setBounds(159, 9, 84, 14);
		chckbxTrout = new JCheckBox("Trout");
		chckbxTrout.setBounds(159, 29, 97, 23);
		chckbxSalmon = new JCheckBox("Salmon");
		chckbxSalmon.setBounds(159, 55, 97, 23);
		chckbxTuna = new JCheckBox("Tuna");
		chckbxTuna.setBounds(159, 81, 97, 23);
		chckbxLobster = new JCheckBox("Lobster");
		chckbxLobster.setBounds(159, 107, 97, 23);
		
		
		
		// Add
		lblTypeOfBot.add(panel);
		rdbtnNone.add(panel);
		rdbtnAlkharidWarrior.add(panel);
		rdbtnFleshcrawler.add(panel);
		rdbtnTanner.add(panel);
		
		lblSelectFood.add(food_panel);
		chckbxTrout.add(food_panel);
		chckbxSalmon.add(food_panel);
		chckbxTuna.add(food_panel);
		chckbxLobster.add(food_panel);
	}
	
	private void setUpListeners()
	{
		rdbtnNone.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				showFoodOptions(false);
			}
		});
		rdbtnAlkharidWarrior.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				showFoodOptions(true);
			}
		});
		rdbtnFleshcrawler.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				showFoodOptions(true);
			}
		});
		rdbtnTanner.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				showFoodOptions(false);
			}
		});
	}
	
	private void showFoodOptions(boolean show)
	{
		food_panel.setVisible(show);
		panel.repaint();
	}
}
