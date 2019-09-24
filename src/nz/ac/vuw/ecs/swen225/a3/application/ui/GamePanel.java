package nz.ac.vuw.ecs.swen225.a3.application.ui;

import nz.ac.vuw.ecs.swen225.a3.application.Game;
import nz.ac.vuw.ecs.swen225.a3.persistence.LevelMaker;
import nz.ac.vuw.ecs.swen225.a3.render.Render;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private Game game;

    public GamePanel(Game game){
        this.game = game;
        this.setLayout(new GridLayout(9,9,0,0));
        this.setBorder(BorderFactory.createEmptyBorder(30,30,30,20));
        
        
        
        // just for the basic version for integration day
        game.getMaze().setTiles(game.getMaze().getTiles());
        Render render = new Render(game,this,game.getMaze());
        game.getMaze().setRender(render);
        render.createGrid();
        JLabel[][] visibleBoard = render.getVisibleBoard();
       
        for (int y = 0; y < 9; y++) {
            for (int x1 = 0; x1 < 9; x1++) {
                System.out.println("hi");
                this.add(visibleBoard[y][x1]);

            }
        }
        
        
        
        setVisible(true);
    }
    
}
