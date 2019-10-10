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

/**
 * 
 * GUI class, contains all the timers, windows and java swing objects necessary
 * to make the game run. This class was written by multiple people as there were
 * some aspects more relevant to the game and recnplay (the timers) while some were
 * more relevant to the GUI itself (JDialogs). While it would have been good to use
 * Model/Controller/View, we didn't consider this to start with and rather than risk
 * breaking our code, we decided to contain it here instead. Some of the methods have
 * been made static so the GUI object does not need to be passed between classes.
 * 
 * @authors Ethan Munn, Meng Veng Taing, Teagan Stewart
 *
 */
public class GUI extends JFrame {

	private Game game;
	public static JFrame main = new JFrame("Chap's Challenge");
	private static final int LEVEL_COUNT = 2; 
	
	// main panels
	private GamePanel gamePanel;
	private InfoPanel infoPanel;
	
	// windows
	private JDialog fileLoaderWindow, pauseWindow, deathWindow, finishLevelWindow;
	
	//Declare variable menu item variable
	private JMenuBar menuBar;
	private JMenu fileMenu, gameMenu;
	private JMenuItem exitItem, saveAndExitItem, loadGameItem, restart_level_Item, restart_game_Item, pause_Item, help_Item;

	private JRadioButton lvl[] = new JRadioButton[2];

    // game variables
    private static Timer gameLoop;
    private static int gameFrame;
    private static int gameSpeed = 10;
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

    //Current record ID
	private Long currentRecordID;
    /**
     * Constructs the game via the GUI
     */
	public GUI() {
		
		// sets up both timers and ensure the game by default starts on Game Mode, not Replay Mode
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

				// to shorten the code here, just directs the key event to inGame or inReplay
				if (!replayMode) inGameEvent(e);
				else inReplayEvent(e);


			}
		});
		main.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				
				// has to stop the timer here so the game doesn't keep running in the background
				stopTimer();
				exitPopup();
				
			}
		});

	}

	/**
	 * These controls are specifically used for the in game events, for
	 * moving the player and changing their direction
	 * @param e
	 * 		the key the user pressed to check against
	 */
	public void inGameEvent(KeyEvent e) {
		
		// basically a check to see if the key they pressed was an arrow key
		boolean moved = false;
		Maze maze = game.getMaze();

		// if the player is on ice, they should not be allowed to move or change their direction
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

		// if a move was successful, pushes this to the replay action record, and updates the board
		if (moved) {
			new Thread(() -> ReplayUtils.pushActionRecord(new ActionRecord((int)(System.currentTimeMillis() - ReplayUtils.getStartTime()), maze))).start();
			updateBoard();
		}
	}

	/**
	 * These controls are specifically used for the in replay events, for
	 * skipping the replay or restarting the replay etc.
	 * @param e
	 * 		the key the user pressed to check against
	 */
	public void inReplayEvent(KeyEvent e) {
		// "S" for Skip
		if (e.getKeyCode() == KeyEvent.VK_S) {
			
			// doesn't allow a skip if it's beyond the level count
			if (game.getLevelNum() < LEVEL_COUNT) {
				stopTimer();
				gameSpeed=10;
				saveReplayPopup();
				setReplayMode(false);
				game.loadLevel(gamePanel, game.getLevelNum()+1);
			}else{
				saveReplayPopup();
				JOptionPane.showMessageDialog(null, "Congratulation, You have won the game");
				finishLevelWindow();
			}
		}

		// "D" for Do-over/Redo
		if (e.getKeyCode() == KeyEvent.VK_D) {
			stopTimer();
			gameSpeed = 10;
			keyFrame = 0;
			recIndex = 0;
			startTimer();
		}

		// "," for go back one frame
		if (e.getKeyCode() == KeyEvent.VK_COMMA) {
			if(!infoPanel.getPause()) { stopTimer();}
			setRecIndex(Math.max(0,getRecIndex()-1));
			setKeyFrame(ReplayUtils.roundTimeToTen(ReplayUtils.getActionRecord(getRecIndex()).getTimeSinceLevelStart()));

			infoPanel.skipReset();
		}

		// "." for go forward one frame
		if (e.getKeyCode() == KeyEvent.VK_PERIOD) {
			if(!infoPanel.getPause()) { stopTimer();}
			setRecIndex(Math.min(getRecIndex()+1,ReplayUtils.replaySize()-1));
			setKeyFrame(ReplayUtils.roundTimeToTen(ReplayUtils.getActionRecord(getRecIndex()).getTimeSinceLevelStart()));

			infoPanel.skipReset();
		}

		// "F" for speed up replay
		if(e.getKeyCode() == KeyEvent.VK_F) {
			if (!infoPanel.getPause()) {
				stopTimer();
			}
			setSpeed(getSpeed() == 3 ? 10 : 3);
			setupTimer();
			if (!infoPanel.getPause()) {
				startTimer();
			}
		}

		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			if(infoPanel.getPause()) startTimer();
			else stopTimer();
			infoPanel.setPause(!infoPanel.getPause());
			updateBoard();
		}
	}



	/**
	 * Create Game window
	 */
	public void createWindow(){
		main.setLayout(new BorderLayout());

		//Generate menu
		createMenuBar();

		//Set window size
		main.setResizable(false);
		main.setSize(new Dimension(900,660));
		main.setPreferredSize(new Dimension(900,660));
		main.setMinimumSize(new Dimension(900,660));
		main.setMaximumSize(new Dimension(900,660));
		main.getContentPane().setBackground(new Color(40, 237, 66));
		//window layout
		gamePanel = new GamePanel(game);
		gamePanel.setBackground(new Color(40, 237, 66));
		infoPanel = new InfoPanel(game, this);
		
		main.add(gamePanel, BorderLayout.LINE_START);
		main.add(infoPanel, BorderLayout.LINE_END);
		
		//Display window at the center of the screen
		main.pack();
		main.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		main.setLocation(dim.width/2-main.getSize().width/2, dim.height/2-main.getSize().height/2);
		main.setFocusable(true);

	}

	/**
	 * The displayed pop up on attempting to exit
	 */
	public void exitPopup(){
		//Ask user if they really want to exit the game and if they would like to save before exiting
		int prompt = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the game?", "Close Window?",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (prompt == JOptionPane.YES_OPTION){
			//
			int save = JOptionPane.showConfirmDialog(null, "Would you like to save before leaving?", "Save option",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(save == JOptionPane.YES_OPTION){
				saveAndExitPopup();
			}else{
				JOptionPane.showMessageDialog(null, "Game has not been saved. Goodbye", "Save and Exit", JOptionPane.PLAIN_MESSAGE);
				SaveUtils.saveLevel(game.getLevelNum());
				stopTimer();
				System.exit(0);
			}

		}
		//Continue the timer
		if(!(infoPanel.getPause())) {
			startTimer();
		}
	}

	/**
	 * The displayed pop up on attempting to save and exit
	 */
	public void saveAndExitPopup(){

		String fileName = JOptionPane.showInputDialog("Enter a name for the save file.");
		if(fileName != null) {
				SaveUtils.saveGame(game.getLevel(), fileName);
				JOptionPane.showMessageDialog(null, "Game has been saved. Goodbye", "Save and Exit", JOptionPane.PLAIN_MESSAGE);
				stopTimer();
				System.exit(0);

		}else{
			JOptionPane.showMessageDialog(null, "No input for files name or process had been cancelled.");
			main.setFocusable(true);
		}
		startTimer();

	}
	public void saveReplayPopup(){


		int i = JOptionPane.showConfirmDialog(null,"Would you like to save the replay recording?", "Save Replay",JOptionPane.YES_NO_OPTION);
		if( i == 0){
			JOptionPane.showMessageDialog(null, "Replay had been saved", "Save Replay", JOptionPane.PLAIN_MESSAGE);
		}else{
			JOptionPane.showMessageDialog(null,"Replay had not been saved","Save Replay", JOptionPane.PLAIN_MESSAGE);

		}
		setReplayMode(false);
		main.setFocusable(true);
	}

	public void finishLevelWindow(){
		JPanel panel = new JPanel();
		JLabel message1 = new JLabel("Level completed.");
		message1.setBounds(105,20,200 ,30);
		JLabel message2 = new JLabel("Would you like to:");
		message2.setBounds(100,50,200 ,30);

		JButton restartButton = new JButton("Restart Level");
		restartButton.addActionListener(e -> {
			finishLevelWindow.dispose();
			restartLevel(game.getLevelNum());


		});
		restartButton.setBounds(75,100,150 ,30);

		JButton replayButton = new JButton("Watch replay");
		replayButton.addActionListener(e -> {
			finishLevelWindow.dispose();
			setReplayMode(true);
			currentRecordID = ReplayUtils.getStartTime();
			ReplayUtils.playBack(Long.toString(currentRecordID));
		});
		replayButton.setBounds(75,150,150 ,30);

		if(game.getLevelNum() < LEVEL_COUNT) {
			JButton nextButton = new JButton("Next Level");
			nextButton.addActionListener(e -> {
				finishLevelWindow.dispose();
				game.loadLevel(null, game.getLevelNum()+1);


			});
			nextButton.setBounds(75,200,150 ,30);
			panel.add(nextButton);
		}


		panel.add(message1);
		panel.add(message2);
		panel.add(restartButton);
		panel.add(replayButton);

		panel.setLayout(null);
		stopTimer();

		finishLevelWindow = popUpWindow("Level Complete", 300,300);
		finishLevelWindow.add(panel);
		finishLevelWindow.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				e.getWindow().dispose();
				if(!(infoPanel.getPause())) {
					startTimer();
				}
			}
		});

		finishLevelWindow.setVisible(true);
	}
	/**
	 * The displayed pop up on getting killed by a skeleton/falling in lava
	 */
	public void deathWindow(){
		JPanel panel = new JPanel();
		JLabel message = new JLabel("GAME OVER. YOU'RE DEAD. ");
		message.setBounds(70,50,200 ,30);

		JButton button = new JButton("Restart Level");
		button.addActionListener(e -> {
			deathWindow.dispose();
			restartLevel(game.getLevelNum());


		});
		button.setBounds(75,150,150 ,30);

		panel.add(message);
		panel.add(button);
		panel.setLayout(null);
		stopTimer();

		deathWindow = popUpWindow("Death Window", 300,300);
		deathWindow.add(panel);
		deathWindow.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				e.getWindow().dispose();
				if(!(infoPanel.getPause())) {
					startTimer();
				}
			}
		});

		deathWindow.setVisible(true);

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
		 *      Help
		 */
	}

	/**
	 * Creates all of the items for the game menu in the menu bar.
	 */
	public void initializeFileItems(){
		//File Menu item initialize and key bind
		exitItem = new JMenuItem("Exit");
		KeyStroke ctrlXKeyStroke = KeyStroke.getKeyStroke("control X");
		exitItem.setAccelerator(ctrlXKeyStroke);
		exitItem.addActionListener((event) -> {
			stopTimer();
			SaveUtils.saveLevel(game.getLevelNum());
			System.exit(0);
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

	/**
	 * Creates all of the items for the game menu in the menu bar.
	 */
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
			if (!replayMode) {
				stopTimer();
				pauseWindow();
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

	/**
	 * The menu displayed when the loading file option is selected
	 */
	public void fileLoader(){

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

		fileLoaderWindow = popUpWindow("File Loader",500,200);

		//Add panel to the dialog
		fileLoaderWindow.add(panel);

        // if closed out using X
		fileLoaderWindow.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                e.getWindow().dispose();
                
                // a check to make sure that it doesn't start the replay if that is paused, if a close is attempted
				if(!(infoPanel.getPause())) {
					startTimer();
				}
            }
        });
        
		fileLoaderWindow.setVisible(true);
		setReplayMode(false);
		main.setFocusable(true);

	}

	/**
	 * A General Popup window creator with a name, width and height
	 * @param name
	 * 		The name at the top of the window
	 * @param width
	 * 		The window width
	 * @param height
	 * 		The window height
	 * @return
	 * 		A popup window with these parameters
	 */
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

	/**
	 * Brings up a pause window
	 */
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
            	// perpetually has the timer stopped
                stopTimer();
                // starts the timer again
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    pauseWindow.dispose();
                    // although it's impossible to reach here outside of replay mode, this is the safety check
    				if(!(infoPanel.getPause())) {
    					startTimer();
    				}
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
                // although it's impossible to reach here outside of replay mode, this is the safety check
				if(!(infoPanel.getPause())) {
					startTimer();
				}
            }
        });
        
        pauseWindow.setVisible(true);

	}

	/**
	 * Quick method for restarting a level
	 * @param lvl
	 * 		level number
	 */
	public void restartLevel(int lvl){
		if (replayMode) return;
		game.loadLevel(gamePanel,lvl);

	}
	
	/**
	 * Quick method for restarting the game
	 */
	public void restartGame(){
		if (replayMode) return;
		game.loadLevel(gamePanel,1);

	}

	/**
	 * Redraws the game panel.
	 */
	public void updateBoard() {
		
		// redraws the board
		gamePanel.clearBoard();
		gamePanel.drawBoard();

		// will either show the level number, or the the date the recording was created
		infoPanel.setLevelDisplay(replayMode, flashIcon);
		infoPanel.displayTime(replayMode);

		// either displays the hint if a player walks across it, or keyboard shortcuts for replay mode
		if (game.getMaze().isOnHint() && !replayMode) infoPanel.setHint(game.getMaze().getHintMessage());
		else infoPanel.setDefaultHint(replayMode);

		// gets whatever the first and second number should be, whether counts for the treasures or counts for the board events
		int first = replayMode ? recIndex : Treasure.getTotalCollected();
		int secnd = replayMode ? ReplayUtils.replaySize()-1 : Treasure.getTotalInLevel();
		infoPanel.displayChips(replayMode, first, secnd);
		
		// inventory is displayed regardless
		infoPanel.clearInventory();
		infoPanel.drawInventory();
		
		// shows the buttons if in replay mode
		infoPanel.updateRec(replayMode);
		gamePanel.updateUI();

	}

    /**
     * Only ever called once to create the timer and override it's action event
     */
    void setupTimer() {

    	// sets up the game timer, which runs ten times a second
    	gameLoop = new Timer(250, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {

					// if it's just started, add in a record of the starting pos to the replay
					if (gameFrame == 0) {
						new Thread(() -> ReplayUtils.pushActionRecord(new ActionRecord(0, game.getMaze()))).start();
					}

					game.update(game.getMaze().isOnIce(), enemyToggle);

					// checks the level reset/ending conditions
					checkConditions(game.getMaze());

					// if the timer on screen needs to be updated
					if (timeToggle) {
						
						// decrements the time, if it's 0 restart
						game.setTime(game.getTime()-1);
						if (game.getTime() < 0) restartLevel(game.getLevelNum());
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
					if(globalFrame ==0) {
						infoPanel.setPause(false);
					}
					// grabs the time from the action record
					int t = ReplayUtils.getActionRecord(recIndex).getTimeSinceLevelStart();
					
					// depending on fast forward or not, this loop may run once or 3 times
					for(int i=0; i<(10.0/gameSpeed); i++) {

						// if there are still more frames to go
						boolean moreToGo = recIndex < ReplayUtils.replaySize()-1;
						
						// if after rounding, these two numbers are equal, shows the updated board
						if (ReplayUtils.roundTimeToTen(t) == keyFrame) {
	
							Maze m = ReplayUtils.getActionRecord(recIndex).getMaze();
							game.getRender().setMaze(m);
							game.setMaze(m);
							updateBoard();

							// if the replay isn't complete yet
							if (moreToGo) recIndex++;
							
						}
						
						// this fixes a bug where two actionRecords, if close enough together, can have the same rounded time
						if (ReplayUtils.roundTimeToTen(t) == keyFrame-10 && moreToGo) recIndex++;
	
						// increments these both by the delay speed
						keyFrame += replayLoop.getDelay();
						globalFrame += replayLoop.getDelay();
					}
					
					// a quick check to see whether or not a global counter should be updated
					if (globalFrameCheck(globalFrame)) updateBoard();
				} catch (NullPointerException e) {}
			}


    	});
    	replayLoop.setInitialDelay(0);
    	replayLoop.setRepeats(true);

    }

    /**
     * The only purpose of this method is to check if it's safe to flash the icon or not, ignoring 0
     * @param frame
     * 		The frame number
     * @return
     * 		Whether or not the board should be updated
     */
    private boolean globalFrameCheck(int frame) {
    	if (frame == 0) return false;
		if (frame % 400 == 0) {
			flashIcon = !flashIcon;
			return true;
		}
		return false;
    }
    
    /**
     * Starts the timers, and has extra conditions for if this timer is just starting or is unpausing
     */
    public static void startTimer() {
    	if (!replayMode) {
    		
			// has to create and initialise a new folder for all the recordings to be saved into, as well as reset
			// all the variables for toggling when enemies move, for example
    		if (!started) {
    			ReplayUtils.setStartTime(System.currentTimeMillis());
    			gameFrame = 0;
    			enemyToggle = false;
    	    	timeToggle = false;
    	    	started = true;
    		}
    		
	    	gameLoop.start();
    	} else {
    		
			// if replay mode is just starting, sets all the indexes to 0
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

    /**
     * Stops whichever timer is active, regardless of which mode its in
     */
    public static void stopTimer() {
    	if (!replayMode) gameLoop.stop();
    	else replayLoop.stop();
    }

    /**
     * Switches the replay mode and sets started to false for the timers
     * @param bool
     * 		true for Replay, false for inGame
     */
    public static void setReplayMode(boolean bool) {
    	replayMode = bool;
    	started = false;

    }
    
    /**
     * Sets the keyFrame (for classes outside of gui)
     * @param key
     * 		the keyframe to set to (should be a multiple of 10)
     */
    public void setKeyFrame(int key) {
    	keyFrame = key;
    }
    
    /**
     * Gets the keyFrame (for classes outside of this one)
     * @return
     * 		the keyframe at this time
     */
    public int getKeyFrame() {
    	return keyFrame;
    }
    
    /**
     * Sets the recIndex (for classes outside of gui)
     * @param rec
     * 		the recIndex to set to
     */
    public void setRecIndex(int rec) {
    	recIndex = rec;
    }
    
    /**
     * Gets the recIndex (for classes outside of this one)
     * @return
     * 		the recIndex at this time
     */
    public int getRecIndex() {
    	return recIndex;
    }
    
    /**
     * Sets the speed of the replay
     * @param speed
     * 		the speed to set to (10, or 3)
     */
    public void setSpeed(int speed) {
    	gameSpeed = speed;
    }
    
    /**
     * Gets the speed of the replay
     * @return
     * 		the speed
     */
    public int getSpeed() {
    	return gameSpeed;
    }

	/**
	 * Return the max level
	 * @return
	 */
	public int getLevelCount(){
    	return LEVEL_COUNT;
	}



    /**
     * Checks the conditions of the maze to see if the game needs
     * to be reset or moved onto the next level
     * @param maze
     * 		the current maze
     */
    private void checkConditions(Maze maze) {
		// if the goal has been reached
		if (maze.isGoalReached()) {
			stopTimer();
			finishLevelWindow();
			startTimer();
			updateBoard();
			main.setFocusable(true);
		}

		// Check if level needs to be reset. This could be if the player dies for
		// example
		if (maze.isResetLevel()) {
			deathWindow();
			game.loadLevel(gamePanel, game.getLevelNum());
			infoPanel.setPause(false);
		}
	}

}
