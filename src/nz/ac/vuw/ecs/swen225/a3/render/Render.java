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
		images = new GraphicalView(g, this);
		game = g;
		gamePanel = gp;
		maze = m;
	}


	public JLabel[][] createGrid() {

		Tile[][] tiles = maze.getTiles();
		board = new JLabel[tiles.length][tiles.length];
		Coordinate playerCoordinate =  maze.getPlayer().getCoordinate();
		for(int i = 0; i<tiles.length; i++) {
			for(int j = 0; j<tiles.length; j++) {
				JLabel tile = new JLabel(images.getTileIcon(i,j,maze));


				tile.setLayout(new BorderLayout());
				board[i][j] = tile;
				if(playerCoordinate.getRow()==i && playerCoordinate.getCol()==j) {
					board[i][j].add(new JLabel(images.getPlayerIcon("chip")));
				}
			}
		}
		return board;
	}

	/**
	 *
	 * @return
	 * Returns the board as an array of image icons
	 */
	public JLabel[][] getBoard() {
		return board;
	}

	public JLabel[][] getVisibleBoard() {
		JLabel[][] visibleBoard = new JLabel[9][9];
		Coordinate playerCoord= maze.getPlayer().getCoordinate();

		System.out.println(checkTop(playerCoord));
		System.out.println(checkBottom(playerCoord));
		if(checkTop(playerCoord) && checkLeft(playerCoord) && 
				checkRight(playerCoord) && checkBottom(playerCoord)) {
			
			int startRow = playerCoord.getRow()-4;
			int startCol = playerCoord.getCol() -4;
	
			for(int i=0; i<9; i++) {
				for(int j=0; j<9; j++) {
					
					visibleBoard[i][j] = board[startRow+i][startCol+j];
					
					
				}
			}
		}
		
		return visibleBoard;
	}
	
	public GraphicalView getImages() {
		return images;
	}

	public boolean checkTop(Coordinate playerCoord) {
		return (playerCoord.getRow() < 4) ? false : true;
	}

	public boolean checkLeft(Coordinate playerCoord) {
		return (playerCoord.getCol() < 4) ? false : true;
	}

	public boolean checkRight(Coordinate playerCoord) {
		return (playerCoord.getCol() > board[0].length-5)  ? false : true;
	}

	public boolean checkBottom(Coordinate playerCoord) {
		
		return (playerCoord.getRow() > board.length-5) ? false : true;
	}


}

