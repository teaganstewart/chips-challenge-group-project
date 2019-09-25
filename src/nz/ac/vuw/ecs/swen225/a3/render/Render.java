package nz.ac.vuw.ecs.swen225.a3.render;

import nz.ac.vuw.ecs.swen225.a3.maze.*;

import java.awt.BorderLayout;
import javax.swing.*;
import nz.ac.vuw.ecs.swen225.a3.application.*;
import nz.ac.vuw.ecs.swen225.a3.application.ui.*;

public class Render {
	private Game game;
	private Maze maze;
	private GraphicalView images;
	private JLabel[][] board;

	public Render(Game g, Maze m) {
		images = new GraphicalView(g, this);
		game = g;
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
				renderEntities(i,j,tiles);
			}
		}
		return board;
	}

	public void renderEntities(int i, int j, Tile[][] tiles) {
		if(tiles[i][j].getEntity()!=null) {
			Entity onTile = tiles[i][j].getEntity();
			if(onTile instanceof Key) {
				BasicColor keyColor = ((Key) onTile).getColor();
				board[i][j].add(new JLabel((images.getKeyIcon(keyColor))));
			}
			else if(onTile instanceof KeyDoor) {
				BasicColor doorColor = ((KeyDoor) onTile).getColor();
				board[i][j].add(new JLabel((images.getDoorIcon(doorColor))));
			}
			else {
				board[i][j].add(new JLabel((images.getEntityIcon(onTile))));
			}
		}
		
	}

	public JLabel[][] getVisibleBoard() {
		JLabel[][] visibleBoard = new JLabel[9][9];
		Coordinate playerCoord= maze.getPlayer().getCoordinate();

		int startRow = playerCoord.getRow()-4;
		int startCol = playerCoord.getCol() -4;

		if(!checkTop(playerCoord)) {
			startRow = 0;
			
		}
		if(!checkBottom(playerCoord)) {
			startRow = board.length-9;
			
		}
		if(!checkLeft(playerCoord)) {
			startCol = 0;
			
		}
		if(!checkRight(playerCoord)) {
			startCol = board[0].length-9;
			
		}
		
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				
				visibleBoard[i][j] = board[startRow+i][startCol+j];
				
				
			}
		}
		
		return visibleBoard;
	}
	
	public GraphicalView getImages() {
		return images;
	}

	/**
	 * 
	 * Checks whether the player is within 4 tiles of the top of the board.
	 * 
	 * @param playerCoord
	 * 		The coordinate where the player is on the board.
	 * @return
	 * 		Returns whether player is close to top.
	 */
	public boolean checkTop(Coordinate playerCoord) {
		return (playerCoord.getRow() < 4) ? false : true;
	}

	/**
	 * 
	 * Checks whether the player is within 4 tiles of the left of the board.
	 * 
	 * @param playerCoord
	 * 		The coordinate where the player is on the board.
	 * @return
	 * 		Returns whether player is close to left.
	 */
	public boolean checkLeft(Coordinate playerCoord) {
		return (playerCoord.getCol() < 4) ? false : true;
	}

	/**
	 * 
	 * Checks whether the player is within 4 tiles of the right of the board.
	 * 
	 * @param playerCoord
	 * 		The coordinate where the player is on the board.
	 * @return
	 * 		Returns whether player is close to right.
	 */
	public boolean checkRight(Coordinate playerCoord) {
		return (playerCoord.getCol() > board[0].length-5)  ? false : true;
	}

	/**
	 * 
	 * Checks whether the player is within 4 tiles of the bottom of the board.
	 * 
	 * @param playerCoord
	 * 		The coordinate where the player is on the board.
	 * @return
	 * 		Returns whether player is close to bottom.
	 */
	public boolean checkBottom(Coordinate playerCoord) {
		
		return (playerCoord.getRow() > board.length-5) ? false : true;
	}


	/**
	 *
	 * @return
	 * Returns the board as an array of image icons
	 */
	public JLabel[][] getBoard() {
		return board;
	}

}

