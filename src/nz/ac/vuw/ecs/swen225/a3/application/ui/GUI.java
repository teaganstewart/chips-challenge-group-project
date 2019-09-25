package nz.ac.vuw.ecs.swen225.a3.application.ui;

import nz.ac.vuw.ecs.swen225.a3.application.Game;
import nz.ac.vuw.ecs.swen225.a3.maze.Direction;
import nz.ac.vuw.ecs.swen225.a3.maze.Maze;
import nz.ac.vuw.ecs.swen225.a3.maze.Player;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {

    private Game game;
    private JFrame main;
    private JPanel gamePanel;
    private JPanel infoPanel;

    public GUI(){
        game = new Game();
        createWindow();
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
                Player player = game.getPlayer();
                int row,col;
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    System.out.println("up");
                    maze.movePlayer(Direction.UP);
                    System.out.println(player.getCoordinate());
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    System.out.println("down");
                    maze.movePlayer(Direction.DOWN);
                    System.out.println(player.getCoordinate());
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    System.out.println("left");
                    maze.movePlayer(Direction.LEFT);
                    System.out.println(player.getCoordinate());
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    System.out.println("right");
                    maze.movePlayer(Direction.RIGHT);
                    System.out.println(player.getCoordinate());
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
            }
        }
    }

    public void saveAndExitPopup(){
        game.saveGame();
        JOptionPane.showMessageDialog(null, "Game has been saved. Goodbye", "Save and Exit", JOptionPane.PLAIN_MESSAGE);
        System.exit(0);
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
        main.setMinimumSize(new Dimension(800,600));
        main.setMaximumSize(new Dimension(1600,1800));


        //Display window at the center of the screen
        main.pack();
        main.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        main.setLocation(dim.width/2-main.getSize().width/2, dim.height/2-main.getSize().height/2);
        main.setFocusable(true);
    }

    /**
     * Create menu bar with all the menu items
     */
    public void createMenuBar(){

        //Declare variable
        JMenuBar menuBar;
        JMenu fileMenu, gameMenu;
        JMenuItem exitItem, saveAndExitItem, loadGameItem, restart_level_Item, restart_game_Item, resume_Item, pause_Item, help_Item;

        //Initialize variables
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        gameMenu = new JMenu("Game");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        gameMenu.setMnemonic(KeyEvent.VK_G);

        //File Menu item initialize and key bind
        exitItem = new JMenuItem("Exit");
        KeyStroke ctrlXKeyStroke = KeyStroke.getKeyStroke("control X");
        exitItem.setAccelerator(ctrlXKeyStroke);
        exitItem.addActionListener((event) -> main.dispatchEvent(new WindowEvent(main, WindowEvent.WINDOW_CLOSING)));

        saveAndExitItem = new JMenuItem("Save & Exit");
        KeyStroke ctrlSKeyStroke = KeyStroke.getKeyStroke("control S");
        saveAndExitItem.setAccelerator(ctrlSKeyStroke);
        saveAndExitItem.addActionListener((event) -> saveAndExitPopup());

        loadGameItem = new JMenuItem("Load game");
        KeyStroke ctrlRKeyStroke = KeyStroke.getKeyStroke("control R");
        loadGameItem.setAccelerator(ctrlRKeyStroke);
        loadGameItem.addActionListener((event) -> System.out.println("Load"));

        //Game Menu item initialize and key bind
        restart_level_Item = new JMenuItem("Restart Level");
        KeyStroke ctrlPKeyStroke = KeyStroke.getKeyStroke("control P");
        restart_level_Item.setAccelerator(ctrlPKeyStroke);
        restart_level_Item.addActionListener((event) -> System.out.println("Restart level"));

        restart_game_Item = new JMenuItem("Restart game");
        KeyStroke ctrl1KeyStroke = KeyStroke.getKeyStroke("control 1");
        restart_game_Item.setAccelerator(ctrl1KeyStroke);
        restart_game_Item.addActionListener((event) -> System.out.println("Restart game"));

        pause_Item = new JMenuItem("Pause game");
        pause_Item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0));
        pause_Item.addActionListener((event) -> System.out.println("Pause game"));

        resume_Item = new JMenuItem("Resume game",KeyEvent.VK_ESCAPE);
        resume_Item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
        resume_Item.addActionListener((event) -> System.out.println("Resume game"));

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

    public void displayTime(){
        int time = game.getTime();
    }

    public void displayLevel(){
        int level = game.getLevel();
    }

    public void displayTreasure() {
        int treasures = game.getTreasures();
    }




}
