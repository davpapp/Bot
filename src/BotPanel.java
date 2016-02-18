import java.awt.AWTException;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;

public class BotPanel extends JPanel
{
	/**
	 * 
	 */
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
	
	private JPanel drop_panel;
	private JLabel lblSelectDrops;
	private JCheckBox chckbxBones;
	private JCheckBox chckbxGems;
	private JCheckBox chckbxCoins;
	
	public BotPanel() throws AWTException
	{
		super();
		setUp();
		setUpListeners();
	}
	
	private void setUp()
	{
		// Main options
		
		panel = new JPanel();;
		panel.setVisible(true);
		panel.setLayout( new FlowLayout() );
		
		lblTypeOfBot = new JLabel("Choose type of bot:");
		lblTypeOfBot.setBounds(10, 14, 97, 14);
		rdbtnNone= new JRadioButton("None");
		rdbtnNone.setBounds(10, 35, 51, 23);
		rdbtnNone.setSelected(true);
		rdbtnAlkharidWarrior = new JRadioButton("Al-Kharid Warrior");
		rdbtnAlkharidWarrior.setBounds(10, 61, 107, 23);
		rdbtnFleshcrawler = new JRadioButton("Flesh-Crawler");
		rdbtnFleshcrawler.setBounds(10, 87, 91, 23);
		rdbtnTanner = new JRadioButton("Tanner");
		rdbtnTanner.setBounds(10, 113, 59, 23);
		
		// Button group
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnNone);
		group.add(rdbtnAlkharidWarrior);
		group.add(rdbtnFleshcrawler);
		group.add(rdbtnTanner);
		
		// Food panel
		food_panel = new JPanel();
		food_panel.setVisible(false);
		food_panel.setLayout( new FlowLayout() );
		
		lblSelectFood = new JLabel("Select food:");
		lblSelectFood.setBounds(10, 9, 58, 14);
		
		chckbxTrout = new JCheckBox("Trout");
		chckbxTrout.setBounds(10, 30, 51, 23);
		chckbxSalmon = new JCheckBox("Salmon");
		chckbxSalmon.setBounds(10, 56, 59, 23);
		chckbxTuna = new JCheckBox("Tuna");
		chckbxTuna.setBounds(10, 82, 49, 23);
		chckbxLobster = new JCheckBox("Lobster");
		chckbxLobster.setBounds(7, 108, 61, 23);
		
		// Drop Panel
		drop_panel = new JPanel();
		drop_panel.setVisible(false);
		drop_panel.setLayout( new FlowLayout() );
		
		lblSelectDrops = new JLabel("Select drops to pick up:");
		lblSelectDrops.setBounds(10, 9, 58, 14);
		
		chckbxBones = new JCheckBox("Bones");
		chckbxBones.setBounds(10, 30, 51, 23);
		chckbxGems = new JCheckBox("Gems");
		chckbxGems.setBounds(10, 56, 59, 23);
		chckbxCoins = new JCheckBox("Coins");
		chckbxCoins.setBounds(10, 82, 49, 23);
		
		// Add
		panel.add(lblTypeOfBot);
		panel.add(rdbtnNone);
		panel.add(rdbtnAlkharidWarrior);
		panel.add(rdbtnFleshcrawler);
		panel.add(rdbtnTanner);
		
		food_panel.add(lblSelectFood);
		food_panel.add(chckbxTrout);
		food_panel.add(chckbxSalmon);
		food_panel.add(chckbxTuna);
		food_panel.add(chckbxLobster);
		
		drop_panel.add(lblSelectDrops);
		drop_panel.add(chckbxBones);
		drop_panel.add(chckbxGems);
		drop_panel.add(chckbxCoins);
		
		panel.add(food_panel);
		panel.add(drop_panel);
		
		
		super.add(panel);
	}
	
	private void setUpListeners()
	{
		rdbtnNone.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				showFoodOptions(false);
				showDropOptions(false);
			}
		});
		rdbtnAlkharidWarrior.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				showFoodOptions(true);
				showDropOptions(true);
			}
		});
		rdbtnFleshcrawler.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				showFoodOptions(true);
				showDropOptions(true);
			}
		});
		rdbtnTanner.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				showFoodOptions(false);
				showDropOptions(false);
			}
		});
	}
	
	private void showFoodOptions(boolean show)
	{
		food_panel.setVisible(show);
		panel.repaint();
	}
	
	private void showDropOptions(boolean show)
	{
		drop_panel.setVisible(show);
		panel.repaint();
	}

}
