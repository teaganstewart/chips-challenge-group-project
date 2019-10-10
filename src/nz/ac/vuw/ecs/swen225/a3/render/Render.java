package nz.ac.vuw.ecs.swen225.a3.render;

import nz.ac.vuw.ecs.swen225.a3.maze.*;
import java.awt.BorderLayout;
import javax.swing.*;
import nz.ac.vuw.ecs.swen225.a3.application.*;

/**
 * A class full of methods used to render the board for the game, works closely
 * with the GraphicalView class to supply the required images.
 * 
 * @author Teagan Stewart
 *
 */
public class Render {
	private Game game;
	private Maze maze;
	private GraphicalView images;
	private JLabel[][] board;
	private JLabel[] inventory;

	/**
	 * Creates the render to display the maze and creates all the images.
	 * 
	 * @param g Game holds the player and everything for rendering.
	 * @param m The maze used for getting the tiles for rendering.
	 */
	public Render(Game g, Maze m) {
		game = g;
		images = new GraphicalView(game, this);
		maze = m;
	}

	/**
	 * Method that creates the board of JLabels with the tiles and the entities
	 * on them.
	 * 
	 * @return Returns a 2d array of the JLabel's containing the ImageIcon's.
	 */
	public JLabel[][] createGrid() {

		Tile[][] tiles = maze.getTiles();
		board = new JLabel[tiles.length][tiles[0].length];

		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				JLabel tile = new JLabel(images.getTileIcon(i, j, maze));

				tile.setLayout(new BorderLayout());
				board[i][j] = tile;
				renderEntities(i, j, tiles);
				renderMoveables(i,j);
			}
		}
		return board;
	}

	/**
	 * Method to render everything that moves on the board.
	 * 
	 * @param i The row of the Moveable object.
	 * @param j The column of the Moveable object.
	 */
	public void renderMoveables(int i, int j) {

		Coordinate playerCoordinate = maze.getPlayer().getCoordinate();
		if(playerCoordinate.getRow()==i && playerCoordinate.getCol()==j) {
			board[i][j].add(new JLabel(images.getPlayerIcon(maze.getPlayer().getDirection())));
			return;
		}

		for(Moveable enemy : maze.getEnemyList()) {
			Coordinate enemyCoordinate = enemy.getCoordinate();
			if(enemyCoordinate.getRow()==i && enemyCoordinate.getCol()==j) {
				board[i][j].add(new JLabel(images.getEnemyIcon(enemy.getDirection())));
			}
		}

		for(Crate crate : maze.getCrateList()) {
			Coordinate crateCoordinate = crate.getCoordinate();
			if(crateCoordinate.getRow()==i && crateCoordinate.getCol()==j) {
				board[i][j].add(new JLabel(images.getCrateIcon()));
			}
		}

	}

	/**
	 * Renders the entities on the board e.g. keys, doors.
	 * 
	 * @param i The row that the entity is in.
	 * @param j The col that the entity is in.
	 * @param tiles Returns the new board with the new JLabel.
	 */
	public void renderEntities(int i, int j, Tile[][] tiles) {
		if (tiles[i][j].getEntity() != null) {
			Entity onTile = tiles[i][j].getEntity();
			if (onTile instanceof Key) {
				BasicColor keyColor = ((Key) onTile).getColor();
				board[i][j].add(new JLabel((images.getKeyIcon(keyColor))));
			} else if (onTile instanceof KeyDoor) {
				BasicColor doorColor = ((KeyDoor) onTile).getColor();
				board[i][j].add(new JLabel((images.getDoorIcon(doorColor))));
			} else {
				board[i][j].add(new JLabel((images.getEntityIcon(onTile))));
			}
		}

	}

	/**
	 * Renders the entities in the inventory.
	 * 
	 * @return Returns an array of the ImageIcons for displaying the inventory.
	 */
	public JLabel[] renderInventory() {
		Player player = maze.getPlayer();
		inventory = new JLabel[8];

		for(int i=0; i<player.getInventory().size(); i++) {
			Entity entity = player.getInventoryAt(i);

			inventory[i] = new JLabel(images.getSlotIcon());
			inventory[i].setLayout(new BorderLayout());
			if(entity instanceof Key) {

				BasicColor keyColor = ((Key) entity).getColor();

				inventory[i].add(new JLabel((images.getKeyIcon(keyColor))));
			}
			else if(entity instanceof FireBoots) {
				inventory[i].add(new JLabel(images.getEntityIcon(new FireBoots())));
			}
			else if(entity instanceof IceBoots) {
				inventory[i].add(new JLabel(images.getEntityIcon(new IceBoots())));
			}
		}

		for(int i= player.getInventory().size(); i<8; i++) {
			inventory[i] = new JLabel(images.getSlotIcon());
		}

		return inventory;
	}

	/**
	 * Returns the 9 x 9 board around the player, the player
	 *  being in the center.
	 * 
	 * @return Returns a 2d array of JLabels representing what 
	 * the user can see. 
	 */
	public JLabel[][] getVisibleBoard() {
		JLabel[][] visibleBoard = new JLabel[9][9];
		Coordinate playerCoord = maze.getPlayer().getCoordinate();

		int startRow = playerCoord.getRow() - 4;
		int startCol = playerCoord.getCol() - 4;

		if (!checkTop(playerCoord)) {
			startRow = 0;

		}
		if(!checkBottom(playerCoord)) {
			startRow = board.length-9;

		}
		if (!checkLeft(playerCoord)) {
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

	/**
	 * Gets the GraphicalView object used to access/ select the required image.
	 * 
	 * @return
	 * 		Returns the GraphicalView object.
	 */
	public GraphicalView getImages() {
		return images;
	}

	/**
	 * 
	 * Checks whether the player is within 4 tiles of the top of the board.
	 * 
	 * @param playerCoord The coordinate where the player is on the board.
	 * @return Returns whether player is close to top.
	 */
	public boolean checkTop(Coordinate playerCoord) {
		return (playerCoord.getRow() < 4) ? false : true;
	}

	/**
	 * 
	 * Checks whether the player is within 4 tiles of the left of the board.
	 * 
	 * @param playerCoord The coordinate where the player is on the board.
	 * @return Returns whether player is close to left.
	 */
	public boolean checkLeft(Coordinate playerCoord) {
		return (playerCoord.getCol() < 4) ? false : true;
	}

	/**
	 * 
	 * Checks whether the player is within 4 tiles of the right of the board.
	 * 
	 * @param playerCoord The coordinate where the player is on the board.
	 * @return Returns whether player is close to right.
	 */
	public boolean checkRight(Coordinate playerCoord) {
		return (playerCoord.getCol() > board[0].length - 5) ? false : true;
	}

	/**
	 * 
	 * Checks whether the player is within 4 tiles of the bottom of the board.
	 * 
	 * @param playerCoord The coordinate where the player is on the board.
	 * @return Returns whether player is close to bottom.
	 */
	public boolean checkBottom(Coordinate playerCoord) {

		return (playerCoord.getRow() > board.length-5) ? false : true;
	}

	/**
	 *	The board, used in the GUI and tests.
	 *
	 * @return Returns the board as an array of image icons.
	 */
	public JLabel[][] getBoard() {
		return board;
	}

	/**
	 * Sets the maze after switching levels.
	 * 
	 * @param m The new maze.
	 */
	public void setMaze(Maze m) {
		this.maze = m;
	}

}
