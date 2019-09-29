package nz.ac.vuw.ecs.swen225.a3.render;

import nz.ac.vuw.ecs.swen225.a3.application.Game;
import nz.ac.vuw.ecs.swen225.a3.maze.*;

import javax.swing.*;

public class GraphicalView {
	private Render renderer;
	private Game game;

	public GraphicalView(Game g, Render r) {
		renderer = r;
		game = g;
	}

	public ImageIcon getPlayerIcon(String type) {
		if(type.equals("chip")) {
			return chip;
		}
		return null;
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
			return wall;
		case HINT:
			return hint;
		case GOAL:
			return goal;
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
			return redKey;
		case GREEN:
			return greenKey;
		case YELLOW:
			return yellowKey;
		case BLUE:
			return blueKey;
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
			return redDoor;
		case GREEN:
			return greenDoor;
		case YELLOW:
			return yellowDoor;
		case BLUE:
			return blueDoor;
		default:
			return null;
		}

	}

	
	public ImageIcon getEntityIcon(Entity entity) {
		if(entity instanceof Treasure) {
			return treasure;
		}
		
		return null;
	}

	/**
	 * A method that both resets all of the players and weapons on a grid, then draws
	 * them in their correct position.
	 *
	 */
	public JLabel[][] drawOnGrid() {

		for (int i = 0; i < renderer.getBoard().length; i++) {
			for (int j = 0; j <  renderer.getBoard().length; j++) {

				JLabel player = checkForPlayer(j,i);
				if (player != null) {
					renderer.getBoard()[i][j].add(player);
				}

				try {
					renderer.getBoard()[i][j].remove(1);
				}
				catch(ArrayIndexOutOfBoundsException e) {

				}

			}
		}

		return renderer.getBoard();
	}

	/**
	 * 	Allows our other methods to check where the players are/ if a player is on a tile.
	 * 
	 * @param x
	 * 		x-coordinate value of tile we are checking.
	 * @param y
	 * 		y-coordinate value of tile we are checking.
	 * @return
	 * 		Returns the image of the player that should be on the tile at the give coordinates.
	 */
	private JLabel checkForPlayer(int x, int y) {
		Player p = game.getMaze().getPlayer();
		if (p.getCoordinate().getCol() == x && p.getCoordinate().getRow() == y) {
			return new JLabel(getPlayerIcon("chip"));
		}

		return null;
	}

	// Characters
	private final ImageIcon chip = new ImageIcon(getClass().getResource("icons/chip.png"));
	
	// Tiles
	private final ImageIcon floor = new ImageIcon(getClass().getResource("icons/wall.png"));
	private final ImageIcon wall = new ImageIcon(getClass().getResource("icons/floor2.png"));
	private final ImageIcon hint = new ImageIcon(getClass().getResource("icons/hint.png"));
	private final ImageIcon goal = new ImageIcon(getClass().getResource("icons/goal.png"));
	
	// Keys
	private final ImageIcon greenKey = new ImageIcon(getClass().getResource("icons/green-key.png"));
	private final ImageIcon blueKey = new ImageIcon(getClass().getResource("icons/blue-key.png"));
	private final ImageIcon redKey = new ImageIcon(getClass().getResource("icons/red-key.png"));
	private final ImageIcon yellowKey = new ImageIcon(getClass().getResource("icons/yellow-key.png"));
	
	// Doors
	private final ImageIcon greenDoor = new ImageIcon(getClass().getResource("icons/green-keydoor.png"));
	private final ImageIcon blueDoor = new ImageIcon(getClass().getResource("icons/blue-keydoor.png"));
	private final ImageIcon redDoor = new ImageIcon(getClass().getResource("icons/red-keydoor.png"));
	private final ImageIcon yellowDoor = new ImageIcon(getClass().getResource("icons/yellow-keydoor.png"));

	// Entities
	private final ImageIcon treasure = new ImageIcon(getClass().getResource("icons/treasure.png"));
	
}