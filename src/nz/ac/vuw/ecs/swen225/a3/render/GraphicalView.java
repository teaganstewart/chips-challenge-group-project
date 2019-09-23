package nz.ac.vuw.ecs.swen225.a3.render;

import nz.ac.vuw.ecs.swen225.a3.maze.*;
import javax.swing.*;

public class GraphicalView {
	private Render renderer;
	private Maze maze;

	public GraphicalView(Render r, Maze m) {
		renderer = r;
		maze = m;
	}

	/**
	 * Used to create the board grid.
	 *
	 * @param i
	 * x-coordinate value of tile we are checking.
	 * @param j
	 * y-coordinate value of tile we are checking.
	 * @param m
	 * The current maze.
	 * @return
	 * Returns the correct image icon for this tile.
	 */
	public ImageIcon getTileIcon(int i, int j, Maze m) {
		Tile[][] tiles = m.getTiles();
		switch(tiles[i][j].getType()) {
		case FLOOR:
			return floor;
		case WALL:
			return null;
		case HINT:
			return null;
		case GOAL:
			return null;
		default:
			return null;
		}
	}

	/**
	 * Gets the key's icon.
	 *
	 * @param color
	 * The color of the key.
	 * @return
	 * The image of the key.
	 */
	public ImageIcon getKeyIcon(BasicColor color) {
		switch(color) {
		case RED:
			return null;
		case GREEN:
			return null;
		case YELLOW:
			return null;
		case BLUE:
			return null;
		default:
			return null;
		}

	}

	/**
	 * Gets the door's icon.
	 *
	 * @param color
	 * The color of the door.
	 * @return
	 * The image of the door..
	 */
	public ImageIcon getDoorIcon(BasicColor color) {
		switch(color) {
		case RED:
			return null;
		case GREEN:
			return null;
		case YELLOW:
			return null;
		case BLUE:
			return null;
		default:
			return null;
		}

	}


	/**
	 * A method that both resets all of the players and weapons on a grid, then draws
	 * them in their correct position.
	 *
	 */
	public JLabel[][] drawOnGrid() {

		for (int i = 0; i < renderer.getBoard().length; i++) {
			for (int j = 0; j <  renderer.getBoard().length; j++) {

				try {
					renderer.getBoard()[i][j].remove(1);
				}
				catch(ArrayIndexOutOfBoundsException e) {

				}

			}
		}
		
		return renderer.getBoard();
	}
	
	private final ImageIcon floor = new ImageIcon(getClass().getResource("icons/floor.png"));
}