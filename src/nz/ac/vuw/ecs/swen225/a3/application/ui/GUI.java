package nz.ac.vuw.ecs.swen225.a3.application.ui;

import nz.ac.vuw.ecs.swen225.a3.application.Game;
import nz.ac.vuw.ecs.swen225.a3.maze.Direction;
import nz.ac.vuw.ecs.swen225.a3.maze.Maze;
import nz.ac.vuw.ecs.swen225.a3.maze.Player;
import nz.ac.vuw.ecs.swen225.a3.persistence.LoadUtils;
import nz.ac.vuw.ecs.swen225.a3.persistence.SaveUtils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GUI extends JFrame {

	private Game game;
	public static JFrame main;
	private GamePanel gamePanel;
	//private JPanel infoPanel;
	private JDialog fileLoaderWindow;
	private JDialog pauseWindow;
	//Declare variable menu item variable
	private JMenuBar menuBar;
	private JMenu fileMenu, gameMenu;
	private JMenuItem exitItem, saveAndExitItem, loadGameItem, restart_level_Item, restart_game_Item, resume_Item, pause_Item, help_Item;

	private JRadioButton lvl[] = new JRadioButton[2];
	private InfoPanel infoPanel;
	private static Timer gameLoop;
	private static boolean timeToggle = true;

	public GUI(){

		setupTimer();
		game = new Game();
		createWindow();
		main.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		main.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				Maze maze = game.getMaze();
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					maze.movePlayer(Direction.UP);
					updateBoard();
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					maze.movePlayer(Direction.DOWN);
					updateBoard();
				}
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					maze.movePlayer(Direction.LEFT);
					updateBoard();
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					maze.movePlayer(Direction.RIGHT);
					updateBoard();
				}
				if (maze.isGoalReached() && game.getLevelNum() < 2) {
					game.loadLevel(gamePanel, game.getLevelNum()+1);
					updateBoard();

				}


			}
		});
		main.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				exitPopup();
			}
		});

	}

	/**
	 * Create Game window
	 */
	public void createWindow(){
		main = new JFrame("Chap's Challenge");
		main.setLayout(new BorderLayout());

		//Generate menu
		createMenuBar();

		//window layout
		gamePanel = new GamePanel(game);
		infoPanel = new InfoPanel(game);

		main.add(gamePanel, BorderLayout.CENTER);
		main.add(infoPanel, BorderLayout.LINE_END);

		//Set window size
		main.setSize(new Dimension(800,600));
		main.setResizable(false);
		main.setMinimumSize(new Dimension(800,600));
		main.setMaximumSize(new Dimension(1600,1800));


		//Display window at the center of the screen
		main.pack();
		main.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		main.setLocation(dim.width/2-main.getSize().width/2, dim.height/2-main.getSize().height/2);
		main.setFocusable(true);

	}


	public void exitPopup(){
		int prompt = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the game?", "Close Window?",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (prompt == JOptionPane.YES_OPTION){
			int save = JOptionPane.showConfirmDialog(null, "Would you like to save before leaving?", "Save option",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(save == JOptionPane.YES_OPTION){
				saveAndExitPopup();
			}else{
				JOptionPane.showMessageDialog(null, "Game has not been saved. Goodbye", "Save and Exit", JOptionPane.PLAIN_MESSAGE);
				System.exit(0);
			}

		}


	}

	public void saveAndExitPopup(){
		String fileName = JOptionPane.showInputDialog("Enter a name for the save file.");
		if(fileName != null) {
			SaveUtils.saveGame(game.getLevel(), fileName);
			JOptionPane.showMessageDialog(null, "Game has been saved. Goodbye", "Save and Exit", JOptionPane.PLAIN_MESSAGE);
			System.exit(0);
		}else{
			JOptionPane.showMessageDialog(null, "No input for files name or process had been cancelled.");
		}
	}


	/**
	 * Create menu bar with all the menu items
	 */
	public void createMenuBar(){
		//Initialize variables
		initializeGameItems();
		initializeFileItems();

		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		gameMenu = new JMenu("Game");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		gameMenu.setMnemonic(KeyEvent.VK_G);

		//Add file items to file menu
		fileMenu.add(loadGameItem);
		fileMenu.add(saveAndExitItem);
		fileMenu.add(exitItem);

		//Add game items to game menu
		gameMenu.add(restart_level_Item);
		gameMenu.add(restart_game_Item);
		gameMenu.add(pause_Item);
		gameMenu.add(resume_Item);
		gameMenu.add(help_Item);

		//Add file and game menu to menu bar
		menuBar.add(fileMenu);
		menuBar.add(gameMenu);
		main.setJMenuBar(menuBar);

		/**
		 * File Menu
		 *      Load Game
		 *      Save & Exit
		 *      Exit
		 * Game Menu
		 *
		 *      Restart Level
		 *      Restart Game
		 *      Pause
		 *      Resume
		 *      Help
		 */
	}

	public void initializeFileItems(){
		//File Menu item initialize and key bind
		exitItem = new JMenuItem("Exit");
		KeyStroke ctrlXKeyStroke = KeyStroke.getKeyStroke("control X");
		exitItem.setAccelerator(ctrlXKeyStroke);
		exitItem.addActionListener((event) -> System.exit(0));

		saveAndExitItem = new JMenuItem("Save & Exit");
		KeyStroke ctrlSKeyStroke = KeyStroke.getKeyStroke("control S");
		saveAndExitItem.setAccelerator(ctrlSKeyStroke);
		saveAndExitItem.addActionListener((event) -> saveAndExitPopup());

		loadGameItem = new JMenuItem("Load game");
		KeyStroke ctrlRKeyStroke = KeyStroke.getKeyStroke("control R");
		loadGameItem.setAccelerator(ctrlRKeyStroke);
		loadGameItem.addActionListener((event) -> fileLoader());
	}

	public void initializeGameItems(){
		//Game Menu item initialize and key bind
		restart_level_Item = new JMenuItem("Restart Level");
		KeyStroke ctrlPKeyStroke = KeyStroke.getKeyStroke("control P");
		restart_level_Item.setAccelerator(ctrlPKeyStroke);
		restart_level_Item.addActionListener((event) -> restartLevel(game.getLevelNum()));

		restart_game_Item = new JMenuItem("Restart game");
		KeyStroke ctrl1KeyStroke = KeyStroke.getKeyStroke("control 1");
		restart_game_Item.setAccelerator(ctrl1KeyStroke);
		restart_game_Item.addActionListener((event) -> restartGame());

		pause_Item = new JMenuItem("Pause game");
		pause_Item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0));
		pause_Item.addActionListener((event) -> pauseWindow());

		resume_Item = new JMenuItem("Resume game",KeyEvent.VK_ESCAPE);
		resume_Item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
		resume_Item.addActionListener(e -> {
			System.out.println("Resume");
			if(pauseWindow != null) {

			}
		});

		help_Item = new JMenuItem("Help");
		KeyStroke ctrlHKeyStroke = KeyStroke.getKeyStroke("control H");
		help_Item.setAccelerator(ctrlHKeyStroke);
		help_Item.addActionListener(e -> {
			if(JOptionPane.showConfirmDialog(
					main,
					"Welcome to the help page  :D\n" +
							"Below are some instructions to helps get you started:\n\n" +
							"1. Player can move by using the arrow keys.\n" +
							"2. Different key color are use to unlock different door color.\n" +
							"3. Player must collect all the treasures to pass the level.\n" +
							"4. Player must complete the level before the timer runs out.\n\n" +

							"KeyBoard shortcuts:\n" +
							"Ctrl+X - Exit\n" +
							"Ctrl+S - Save & Exit\n" +
							"Ctrl+R - Load\n" +
							"Ctrl+P - Restart Level\n" +
							"Ctrl+1 - Restart Game\n" +
							"Space Bar - Pause\n" +
							"Esc - Resume\n\n\n" +
							"Lastly do not forget to have fun   ; )",


					"Help Page",

					JOptionPane.CLOSED_OPTION) == JOptionPane.YES_OPTION){
			}
		});
	}



	public void fileLoader(){

		Map<Long, String> loadUtil = LoadUtils.getSavesByID();

		fileLoaderWindow = new JDialog();
		fileLoaderWindow.setTitle("File Loader");
		fileLoaderWindow.setModal(true);

		//Create panel
		JPanel panel = new JPanel();
		JLabel text = new JLabel("Select a file to load:");
		text.setBounds(200,20,200,30);



		//Create a combo box
		JComboBox cb = new JComboBox();

		File directory = new File(SaveUtils.SAVES_DIRECTORY);
		FileFilter filter = pathname -> pathname.isFile() && pathname.toString().endsWith(".json");
		File[] files = directory.listFiles(filter);
		if(files!= null) {
			for (File f : files) {

				Long key = Long.parseLong(f.getName().substring(0, f.getName().length()-5));

				cb.addItem(loadUtil.get(key));
			}
		}

		cb.setBounds(50,60,400,20);
		cb.setSelectedIndex(1);


		JButton select = new JButton("Select");
		select.setBounds(200,100,100,30);
		select.addActionListener(event -> {
			fileLoaderWindow.dispose();
			//LoadUtils.loadLevel(loadUtil.get(cb.getSelectedItem()));
			//System.out.println(cb.getSelectedItem())
		});

		//Add items to panel
		panel.add(text);
		panel.add(cb);
		panel.add(select);
		panel.setLayout(null);

		//Add panel to the dialog
		fileLoaderWindow.add(panel);
		fileLoaderWindow.setSize(500,200);

		//Set to display at center of screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		fileLoaderWindow.setLocation(dim.width/2-fileLoaderWindow.getSize().width/2, dim.height/2-fileLoaderWindow.getSize().height/2);
		fileLoaderWindow.setVisible(true);

	}

	public JDialog popUpWindow(String name, int width, int height){
		JDialog window = new JDialog();
		window.setTitle(name);
		window.setModal(true);
		window.setSize(width,height);

		//Set to display at center of screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation(dim.width/2-window.getSize().width/2, dim.height/2-window.getSize().height/2);



		return window;
	}


	public void pauseWindow(){

		JPanel panel = new JPanel();
		JLabel message = new JLabel("GAME PAUSED");
		message.setBounds(98,50,200 ,30);

		JLabel instruct = new JLabel("PRESS ESC TO RESUME");
		instruct.setBounds(74,90,200 ,30);

		//Add items to panel
		panel.add(message);
		panel.add(instruct);
		panel.setLayout(null);

		pauseWindow = popUpWindow("Pause Window", 300,300);
		pauseWindow.add(panel);
        pauseWindow.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    pauseWindow.dispose();
                }
            }
        });

        pauseWindow.setVisible(true);


	}

	public void restartLevel(int lvl){
		game.loadLevel(gamePanel,lvl);

	}
	public void restartGame(){
		game.loadLevel(gamePanel,1);

	}

	/**
	 * Redraws the game panel.
	 */
	public void updateBoard() {
		gamePanel.clearBoard();
		gamePanel.drawBoard();

		infoPanel.setLevelDisplay();
		infoPanel.displayTime();

		if (game.getMaze().isOnHint()) infoPanel.setHint(game.getMaze().getHintMessage());
		else infoPanel.setDefaultHint();

		infoPanel.displayChips();
		infoPanel.clearInventory();
		infoPanel.drawInventory();
		gamePanel.updateUI();

	}

    /**
     * Only ever called once to create the timer and override it's action event
     * Runs once per second
     */
    private void setupTimer() {

    	gameLoop = new Timer(500, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					game.update();
					if (timeToggle) {
						updateBoard();
						game.setTime(game.getTime()-1);

						if (game.getTime() < 0) {
							game.loadLevel(gamePanel, game.getLevelNum());
						}
					}
					timeToggle = !timeToggle;
				} catch (NullPointerException e) {}
			}


    	});
		gameLoop.setInitialDelay(0);
		gameLoop.setRepeats(true);

    }

    public static void startTimer() {
    	timeToggle = true;
    	gameLoop.start();
    }

    public static void stopTimer() {
    	gameLoop.stop();
    }

}
