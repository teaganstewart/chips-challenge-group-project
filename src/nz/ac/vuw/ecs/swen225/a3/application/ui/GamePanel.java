package nz.ac.vuw.ecs.swen225.a3.application.ui;

import nz.ac.vuw.ecs.swen225.a3.application.Game;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private Game game;

    public GamePanel(Game game){
        this.game = game;
        this.setLayout(new GridLayout(9,9,0,0));
        this.setBorder(BorderFactory.createEmptyBorder(30,30,30,20));
        for(int i =0; i<81; i++) {
            this.add(new JLabel("Hi"));
        }
        setVisible(true);
    }
    
}
