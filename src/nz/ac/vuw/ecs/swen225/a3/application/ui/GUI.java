package nz.ac.vuw.ecs.swen225.a3.application.ui;

import nz.ac.vuw.ecs.swen225.a3.application.Game;
import nz.ac.vuw.ecs.swen225.a3.maze.Coordinate;
import nz.ac.vuw.ecs.swen225.a3.maze.Direction;
import nz.ac.vuw.ecs.swen225.a3.maze.Level;
import nz.ac.vuw.ecs.swen225.a3.maze.IceBoots;
import nz.ac.vuw.ecs.swen225.a3.maze.Maze;
import nz.ac.vuw.ecs.swen225.a3.maze.Treasure;
import nz.ac.vuw.ecs.swen225.a3.persistence.LoadUtils;
import nz.ac.vuw.ecs.swen225.a3.persistence.SaveUtils;
import nz.ac.vuw.ecs.swen225.a3.recnplay.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.nio.file.LinkOption;

public class GUI extends JFrame {

	private Game game;
	public static JFrame main = new JFrame("Chap's Challenge");
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

    // game variables
    private static Timer gameLoop;
    private static int gameFrame;
    private static boolean timeToggle;
    private static boolean enemyToggle;
    private static boolean started;
    
    // recnplay variables
    private static Timer replayLoop;
    private static int globalFrame;
    private static int keyFrame;
	private static int recIndex;
	private static boolean flashIcon;

	// switching between recnplay / game
    private static boolean replayMode;

	public GUI() {
		setReplayMode(false);
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

				if (!replayMode) inGameEvent(e);
				else inReplayEvent(e);


			}
		});
		main.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				exitPopup();
			}
		});

	}

	public void inGameEvent(KeyEvent e) {
		boolean moved = false;
		Maze maze = game.getMaze();

		if (!game.getMaze().isOnIce() || game.getPlayer().isInInventory(new IceBoots())) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				maze.movePlayer(Direction.UP);
				moved = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				maze.movePlayer(Direction.DOWN);
				moved = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				maze.movePlayer(Direction.LEFT);
				moved = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				maze.movePlayer(Direction.RIGHT);
				moved = true;
			}
		}

		if (moved) {
			ReplayUtils.pushActionRecord(new ActionRecord((int)(System.currentTimeMillis() - ReplayUtils.getStartTime()), maze));
			updateBoard();
		}
	}

	public void inReplayEvent(KeyEvent e) {
		// "S" for Skip
		if (e.getKeyCode() == KeyEvent.VK_S) {
			if (game.getLevelNum() < 3) {
				stopTimer();
				setReplayMode(false);
				game.loadLevel(gamePanel, game.getLevelNum()+1);
			}
		}

		// "D" for Do-over/Redo
		if (e.getKeyCode() == KeyEvent.VK_D) {
			stopTimer();
			keyFrame = 0;
			recIndex = 0;
			startTimer();
		}
	}


	/**
	 * Create Game window
	 */
	public void createWindow(){
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
		startTimer();
	}

	public void saveAndExitPopup(){
		String fileName = JOptionPane.showInputDialog("Enter a name for the save file.");
		if(fileName != null) {
			
			if (replayMode) {
				// possibly save them to the start of the next level, if possible? if it's the last level, maybe disable saving in this state?
			}
			
			SaveUtils.saveGame(game.getLevel(), fileName);
			JOptionPane.showMessageDialog(null, "Game has been saved. Goodbye", "Save and Exit", JOptionPane.PLAIN_MESSAGE);
			System.exit(0);
		}else{
			JOptionPane.showMessageDialog(null, "No input for files name or process had been cancelled.");
		}
		startTimer();
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
		exitItem.addActionListener((event) -> {
			stopTimer();
			exitPopup();
		});

		saveAndExitItem = new JMenuItem("Save & Exit");
		KeyStroke ctrlSKeyStroke = KeyStroke.getKeyStroke("control S");
		saveAndExitItem.setAccelerator(ctrlSKeyStroke);
		saveAndExitItem.addActionListener((event) -> {
			stopTimer();
			saveAndExitPopup();
		});

		loadGameItem = new JMenuItem("Load game");
		KeyStroke ctrlRKeyStroke = KeyStroke.getKeyStroke("control R");
		loadGameItem.setAccelerator(ctrlRKeyStroke);
		loadGameItem.addActionListener((event) -> {
			stopTimer();
			fileLoader();
		});
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
		pause_Item.addActionListener((event) -> {
			stopTimer();
			pauseWindow();
		});

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
			stopTimer();
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
			startTimer();
		});
	}


	public void fileLoader(){



		fileLoaderWindow = new JDialog();
		fileLoaderWindow.setTitle("File Loader");
		fileLoaderWindow.setModal(true);

		//Create panel
		JPanel panel = new JPanel();
		JLabel text = new JLabel("Select a file to load:");
		text.setBounds(200,20,200,30);



		//Create a combo box
		JComboBox<String> cb = new JComboBox<String>();


		for(String s: LoadUtils.getSavesByID().keySet()){
			cb.addItem(s);
		}

		cb.setBounds(50,60,400,20);


		JButton select = new JButton("Select");
		select.setBounds(200,100,100,30);
		select.addActionListener(event -> {
			stopTimer();
			if(cb.getSelectedIndex() !=-1) {
				fileLoaderWindow.dispose();
				Object selectItem = cb.getSelectedItem();
				Long saveId = LoadUtils.getSavesByID().get(selectItem);
				Level lvl = LoadUtils.loadById(saveId);
				game.loadSave(lvl);
			}

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
		
        // if closed out using X
		fileLoaderWindow.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                e.getWindow().dispose();
				startTimer();
            }
        });
        
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
                stopTimer();
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    pauseWindow.dispose();
    				startTimer();
                }
            }
        });
        
        // if closed out using X
        pauseWindow.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                e.getWindow().dispose();
				startTimer();
            }
        });
        
        pauseWindow.setVisible(true);

	}

	public void restartLevel(int lvl){
		if (replayMode) return;
		game.loadLevel(gamePanel,lvl);

	}
	
	public void restartGame(){
		if (replayMode) return;
		game.loadLevel(gamePanel,1);

	}

	/**
	 * Redraws the game panel.
	 */
	public void updateBoard() {
		gamePanel.clearBoard();
		gamePanel.drawBoard();

		infoPanel.setLevelDisplay(replayMode, flashIcon);
		infoPanel.displayTime(replayMode);

		if (game.getMaze().isOnHint()) infoPanel.setHint(game.getMaze().getHintMessage());
		else infoPanel.setDefaultHint();

		int first = replayMode ? recIndex : Treasure.getTotalCollected();
		int secnd = replayMode ? ReplayUtils.replaySize()-1 : Treasure.getTotalInLevel();
		infoPanel.displayChips(replayMode, first, secnd);
		infoPanel.clearInventory();
		infoPanel.drawInventory();
		gamePanel.updateUI();

	}

    /**
     * Only ever called once to create the timer and override it's action event
     */
    private void setupTimer() {

    	// sets up the game timer, which runs ten times a second
    	gameLoop = new Timer(250, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {

					// if it's just started, add in a record of the starting pos to the replay
					if (gameFrame == 0) {
						ReplayUtils.pushActionRecord(new ActionRecord(0, game.getMaze()));
					}

					game.update(game.getMaze().isOnIce(), enemyToggle);

					// checks the level reset/ending conditions
					checkConditions(game.getMaze());

					// if the timer on screen needs to be updated
					if (timeToggle) {

						game.setTime(game.getTime()-1);

						if (game.getTime() < 0) {
							game.loadLevel(gamePanel, game.getLevelNum());
						}

					}

					// updates the board regardless
					updateBoard();

					// checks to see whether it should toggle the timer
					timeToggle = gameFrame % 4 == 0 && gameFrame != 0;
					enemyToggle = gameFrame % 2 == 0 && gameFrame != 0;
					gameFrame++;

				} catch (NullPointerException e) {}
			}


    	});
		gameLoop.setInitialDelay(0);
		gameLoop.setRepeats(true);

		// sets up the replay timer, which runs on a different mechanism
    	replayLoop = new Timer(10, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {					
					Maze m = ReplayUtils.getActionRecord(recIndex).getMaze();
					int t = ReplayUtils.getActionRecord(recIndex).getTimeSinceLevelStart();

					if (ReplayUtils.roundTimeToTen(t) == keyFrame) {

						game.getRender().setMaze(m);
						game.setMaze(m);
						updateBoard();

						if (recIndex < ReplayUtils.replaySize()-1) {
							recIndex++;
						}
					}

					keyFrame += 10;
					globalFrame += 10;
					if (globalFrameCheck(globalFrame)) updateBoard();
				} catch (NullPointerException e) {}
			}


    	});
    	replayLoop.setInitialDelay(0);
    	replayLoop.setRepeats(true);

    }

    private boolean globalFrameCheck(int frame) {
    	if (frame == 0) return false;
		if (frame % 400 == 0) {
			flashIcon = !flashIcon;
			return true;
		}
		return false;
    }
    
    public static void startTimer() {
    	if (!replayMode) {
    		if (!started) {
    			ReplayUtils.setStartTime(System.currentTimeMillis());
    			gameFrame = 0;
    			enemyToggle = false;
    	    	timeToggle = false;
    	    	started = true;
    		}
    		
	    	gameLoop.start();
    	} else {
    		if (!started) {
    			globalFrame = 0;
        		keyFrame = 0;
        		recIndex = 0;
        		flashIcon = true;
        		started = true;
    		}
    		
    		replayLoop.start();
    	}
    }

    public static void stopTimer() {
    	if (!replayMode) gameLoop.stop();
    	else replayLoop.stop();
    }

    public static void setReplayMode(boolean bool) {
    	replayMode = bool;
    	started = false;
    }

    private void checkConditions(Maze maze) {
		// if the goal has been reached
		if (maze.isGoalReached()) {
			stopTimer();
			setReplayMode(true);
			ReplayUtils.playBack(Long.toString(ReplayUtils.getStartTime()));
			startTimer();
			updateBoard();
		}

		// Check if level needs to be reset. This could be if the player dies for
		// example
		if (maze.isResetLevel()) {
			game.loadLevel(gamePanel, game.getLevelNum());
		}
    }

}
