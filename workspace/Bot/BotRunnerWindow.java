import java.awt.AWTException;
import java.awt.EventQueue;
import java.awt.Robot;

import javax.swing.JFrame;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.Random;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JEditorPane;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTabbedPane;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class BotRunnerWindow {

	private JFrame frame;
	private JButton btnLaunch;
	private JTabbedPane tabbedPane;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run()
			{
				try 
				{
					BotRunnerWindow window = new BotRunnerWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws AWTException 
	 */
	public BotRunnerWindow() throws AWTException 
	{
		initialize();
		//setupListeners();
	}

	
	/**
	 * Initialize the contents of the frame.
	 * @throws AWTException 
	 */
	private void initialize() throws AWTException 
	{
		frame = new JFrame("YoungDP Bot Launcher");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		btnLaunch = new JButton("Launch");
		btnLaunch.setBounds(0, 0, 0, 0);
		frame.getContentPane().add(btnLaunch);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 672, 490);
		frame.getContentPane().add(tabbedPane);
		
		BotPanel bot1 = new BotPanel();
		BotPanel bot2 = new BotPanel();
		BotPanel bot3 = new BotPanel();
		tabbedPane.addTab("Bot #1", null, bot1, null);
		tabbedPane.addTab("Bot #2", null, bot2, null);
		tabbedPane.addTab("Bot #3", null, bot3, null);
	}
	
	private void setupListeners()
	{
		btnLaunch.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				launchGame();
			}
		});
	}
	
	private void launchGame()
	{
		
	}
}
