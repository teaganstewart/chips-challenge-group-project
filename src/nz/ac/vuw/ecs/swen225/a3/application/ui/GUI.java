package nz.ac.vuw.ecs.swen225.a3.application.ui;

import nz.ac.vuw.ecs.swen225.a3.application.Game;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

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
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    System.out.println("up");
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    System.out.println("down");
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    System.out.println("left");
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    System.out.println("right");
                }


            }
        });
    }

    public void createWindow(){
        main = new JFrame("Clap and Chap");
        main.setLayout(new BorderLayout());



        createMenuBar();
        gamePanel = new GamePanel(game);
        infoPanel = new InfoPanel(game);

        main.add(gamePanel, BorderLayout.CENTER);
        main.add(infoPanel, BorderLayout.LINE_END);
        //main.getContentPane().set
        main.setSize(new Dimension(800,600));

        main.setMinimumSize(new Dimension(800,600));
        main.setMaximumSize(new Dimension(1600,1800));



        main.pack();
        main.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        main.setLocation(dim.width/2-main.getSize().width/2, dim.height/2-main.getSize().height/2);

    }

    public void createMenuBar(){

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenu gameMenu = new JMenu("Game");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        gameMenu.setMnemonic(KeyEvent.VK_G);

        //File menu
        JMenuItem exitItem = new JMenuItem("Exit");
        KeyStroke ctrlXKeyStroke = KeyStroke.getKeyStroke("control X");
        exitItem.setAccelerator(ctrlXKeyStroke);
        exitItem.addActionListener((event) -> System.out.println("Exit"));

        JMenuItem saveAndExitItem = new JMenuItem("Save & Exit");
        KeyStroke ctrlSKeyStroke = KeyStroke.getKeyStroke("control S");
        saveAndExitItem.setAccelerator(ctrlSKeyStroke);
        saveAndExitItem.addActionListener((event) -> System.out.println("Save & Exit"));

        JMenuItem loadGameItem = new JMenuItem("Load game");
        KeyStroke ctrlRKeyStroke = KeyStroke.getKeyStroke("control R");
        loadGameItem.setAccelerator(ctrlRKeyStroke);
        loadGameItem.addActionListener((event) -> System.out.println("Load"));

        //Game Menu
        JMenuItem restart_level_Item= new JMenuItem("Restart Level");
        KeyStroke ctrlPKeyStroke = KeyStroke.getKeyStroke("control P");
        restart_level_Item.setAccelerator(ctrlPKeyStroke);
        restart_level_Item.addActionListener((event) -> System.out.println("Restart level"));

        JMenuItem restart_game_Item= new JMenuItem("Restart game");
        KeyStroke ctrl1KeyStroke = KeyStroke.getKeyStroke("control 1");
        restart_game_Item.setAccelerator(ctrl1KeyStroke);
        restart_game_Item.addActionListener((event) -> System.out.println("Restart game"));

        JMenuItem pause_Item= new JMenuItem("Pause game");
        pause_Item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0));
        pause_Item.addActionListener((event) -> System.out.println("Pause game"));

        JMenuItem resume_Item= new JMenuItem("Resume game",KeyEvent.VK_ESCAPE);
        resume_Item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
        resume_Item.addActionListener((event) -> System.out.println("Resume game"));

        //Add file items
        fileMenu.add(loadGameItem);
        fileMenu.add(saveAndExitItem);
        fileMenu.add(exitItem);

        //Add game items
        gameMenu.add(restart_level_Item);
        gameMenu.add(restart_game_Item);
        gameMenu.add(pause_Item);
        gameMenu.add(resume_Item);

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
         *      Unpause
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
