package nz.ac.vuw.ecs.swen225.a3.render;

import nz.ac.vuw.ecs.swen225.a3.maze.*;

import java.awt.BorderLayout;

import javax.swing.*;
import nz.ac.vuw.ecs.swen225.a3.application.*;
import nz.ac.vuw.ecs.swen225.a3.application.ui.*;

public class Render {
	private Game game;
	private GamePanel gamePanel;
	private Maze maze;
	private GraphicalView images;
	private JLabel[][] board;
	
	public Render(Game g, GamePanel gp, Maze m) {
		images = new GraphicalView(game, this, m);
		game = g;
		gamePanel = gp;
		maze = m;
	}
	
	
	public void createGrid() {
		Tile[][] tiles = maze.getTiles();
		board = new JLabel[tiles.length][tiles.length];
		
		for(int i = 0; i<tiles.length; i++) {
			for(int j = 0; j<tiles.length; j++) {

				JLabel tile = new JLabel(images.getImageIcon(i,j,maze));
				tile.setLayout(new BorderLayout());
				board[i][j] = tile;
			}
		}
	}
	
	public void createBoard() {
	}
	
	/**
	 * 
	 * @return
	 * 		Returns the board as an array of image icons
	 */
	public JLabel[][] getBoard() {
		return board;
	}
	
}
