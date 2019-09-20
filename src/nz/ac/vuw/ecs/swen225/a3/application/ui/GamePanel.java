package nz.ac.vuw.ecs.swen225.a3.application.ui;

import nz.ac.vuw.ecs.swen225.a3.application.Game;
import nz.ac.vuw.ecs.swen225.a3.maze.*;
import nz.ac.vuw.ecs.swen225.a3.render.*;

import javax.swing.*;
import java.awt.*;


public class GamePanel extends JPanel {

    private Game game;
    private Render renderer;
    private Maze maze;
    private GUI gui;

    public GamePanel(Game game, GUI gui){
        this.game = game;
        this.gui = gui;
       // renderer = new Render(game, this, maze);
        this.setLayout(new GridLayout(9,9,1,1));
        this.setBackground(Color.blue);
        
//        for (int y = 0; y < maze.getTiles().length; y++) {
//			for (int x1 = 0; x1 < maze.getTiles().length; x1++) {
//
//				this.add(renderer.getBoard()[y][x1]);
//
//			}
//		}
        
       
        setVisible(true);
    }
    
    public Render getRenderer() {
    	return renderer;
    }
    
    public void setRenderer(Render r) {
    	renderer = r;
    }
    
   //code from https://coderanch.com/t/629253/java/create-JPanel-maintains-aspect-ratio
   @Override
   public final Dimension getPreferredSize() 
   {
       Dimension d = super.getPreferredSize();
      
       Dimension prefSize = null;
       Component c = getParent();
   
       if (c == null) 
       {
           prefSize = new Dimension(
                   (int)d.getWidth(),(int)d.getHeight());
       } else if (c!=null &&
               c.getWidth()>d.getWidth() &&
               c.getHeight()>d.getHeight()
               ) 
       {
           prefSize = c.getSize();
       } else {
           prefSize = d;
       }

       int w = (int) prefSize.getWidth() - gui.getInfoPanel().getWidth() - 60;
       int h = (int) prefSize.getHeight() - 60;
       // the smaller of the two sizes
       int s = (w>h ? h : w);
       return new Dimension(s,s);
   }
};
    
