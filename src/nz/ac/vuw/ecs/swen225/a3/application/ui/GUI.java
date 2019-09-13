package nz.ac.vuw.ecs.swen225.a3.application.ui;

import nz.ac.vuw.ecs.swen225.a3.application.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GUI extends Frame {

    Game game;

    public GUI(){
        game = new Game();
        gameWindow();
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_X && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
                    System.out.println("exit");
                }
                if (e.getKeyCode() == KeyEvent.VK_S && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
                    System.out.println("save");
                }
                if (e.getKeyCode() == KeyEvent.VK_R && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
                    System.out.println("resume");
                }
                if (e.getKeyCode() == KeyEvent.VK_P && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
                    System.out.println("restart this level ");
                }
                if (e.getKeyCode() == KeyEvent.VK_1 && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
                    System.out.println("restart");
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    System.out.println("pause");
                }
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.out.println("esc");
                }
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

    public void gameWindow(){
        JFrame main = new JFrame();
        setLayout(new BorderLayout());
        setSize(800,800);
        setVisible(true);
    }

    public void displayTime(){

    }

    public void displayLevel(){

    }

    public void displayTreasure() {

    }




}
