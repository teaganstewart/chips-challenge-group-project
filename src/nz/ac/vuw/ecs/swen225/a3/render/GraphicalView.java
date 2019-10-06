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

	/**
	 * Gets the desired image of the player.
	 * 
	 * @param dir
	 * 		The direction the player is facing.
	 * @return
	 * 		Returns an ImageIcon of the player.
	 */	
	public ImageIcon getPlayerIcon(Direction dir) {
		if(dir.equals(Direction.DOWN)) {
			return chipFront;
		}
		if(dir.equals(Direction.UP)) {
			return chipBack;
		}
		if(dir.equals(Direction.LEFT)) {
			return chipLeft;
		}
		if(dir.equals(Direction.RIGHT)) {
			return chipRight;
		}
		return chip;
	}
	
	/**
	 * Gets the desired image of the enemy.
	 * 
	 * @param dir The direction the enemy is facing.
	 * @return Returns an ImageIcon of the enemy.
	 */		
	public ImageIcon getEnemyIcon(Direction dir) {
		if(dir.equals(Direction.DOWN)) {
			return skellyFront;
		}
		if(dir.equals(Direction.UP)) {
			return skellyBack;
		}
		if(dir.equals(Direction.LEFT)) {
			return skellyLeft;
		}
		if(dir.equals(Direction.RIGHT)) {
			return skellyRight;
		}
		return skellyFront;
	}

	/**
	 * Gets the desired number image.
	 * 
	 * @param i The number you want the image of, > 9 if colon.
	 * @return Returns the ImageIcon of the desire number.
	 */
	public ImageIcon getNumberIcon(int i) {
		switch(i) {
			case 0:
				return zero;
			case 1:
				return one;
			case 2:
				return two;
			case 3:
				return three;
			case 4:
				return four;
			case 5:
				return five;
			case 6:
				return six;
			case 7:
				return seven;
			case 8:
				return eight;
			case 9:
				return nine;
			default:
				return colon;
		}
	}
	/**
	 * Used to create the board grid.
	 *
	 * @param i x-coordinate value of tile we are checking.
	 * @param j y-coordinate value of tile we are checking.
	 * @param m The current maze.
	 * @return Returns the correct image icon for this tile.
	 */
	public ImageIcon getTileIcon(int i, int j, Maze m) {
		Tile[][] tiles = m.getTiles();
		switch (tiles[i][j].getType()) {
		case FLOOR:
			return floor;
		case WALL:
			return wall;
		case HINT:
			return hint;
		case GOAL:
			return goal;
		case FIRE:
			return fire;
		case ICE:
			return ice;
		default:
			return null;
		}
	}

	/**
	 * Gets the key's icon.
	 *
	 * @param color The color of the key.
	 * @return The image of the key.
	 */
	public ImageIcon getKeyIcon(BasicColor color) {
		switch (color) {
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
	 * @param color The color of the door.
	 * @return The image of the door.
	 */
	public ImageIcon getDoorIcon(BasicColor color) {
		switch (color) {
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

	/**
	 * Gets image of an miscellaneous entity.
	 * 
	 * @param entity The entity type we want the image of.
	 * @return Returns the image of desired entity.
	 */
	public ImageIcon getEntityIcon(Entity entity) {
		if (entity instanceof Treasure) {
			return treasure;
		}
		if(entity instanceof TreasureDoor) {
			return treasureDoor;
		}
		return null;

	}
	
	public ImageIcon getSlotIcon() {
		return slot;
	}

	/**
	 * A method that both resets all of the players and weapons on a grid, then
	 * draws them in their correct position.
	 *
	 */
	public JLabel[][] drawOnGrid() {

		for (int i = 0; i < renderer.getBoard().length; i++) {
			for (int j = 0; j < renderer.getBoard().length; j++) {

				JLabel player = checkForPlayer(j, i);
				if (player != null) {
					renderer.getBoard()[i][j].add(player);
				}

				try {
					renderer.getBoard()[i][j].remove(1);
				} catch (ArrayIndexOutOfBoundsException e) {

				}

			}
		}

		return renderer.getBoard();
	}

	/**
	 * Allows our other methods to check where the players are/ if a player is on a
	 * tile.
	 * 
	 * @param x x-coordinate value of tile we are checking.
	 * @param y y-coordinate value of tile we are checking.
	 * @return Returns the image of the player that should be on the tile at the
	 *         give coordinates.
	 */
	private JLabel checkForPlayer(int x, int y) {
		Player p = game.getMaze().getPlayer();
		if (p.getCoordinate().getCol() == x && p.getCoordinate().getRow() == y) {
			return new JLabel(getPlayerIcon(p.getDirection()));
		}

		return null;
	}

	// Characters
	private final ImageIcon chip = new ImageIcon(getClass().getResource("icons/chip.png"));
	private final ImageIcon chipFront = new ImageIcon(getClass().getResource("icons/chip-front.png"));
	private final ImageIcon chipLeft = new ImageIcon(getClass().getResource("icons/chip-left.png"));
	private final ImageIcon chipBack = new ImageIcon(getClass().getResource("icons/chip-back.png"));
	private final ImageIcon chipRight = new ImageIcon(getClass().getResource("icons/chip-right.png"));
	
	private final ImageIcon skellyFront = new ImageIcon(getClass().getResource("icons/skelly-front.png"));
	private final ImageIcon skellyLeft = new ImageIcon(getClass().getResource("icons/skelly-left.png"));
	private final ImageIcon skellyBack = new ImageIcon(getClass().getResource("icons/skelly-back.png"));
	private final ImageIcon skellyRight = new ImageIcon(getClass().getResource("icons/skelly-right.png"));
	
	// Tiles
	private final ImageIcon floor = new ImageIcon(getClass().getResource("icons/wall.png"));
	private final ImageIcon wall = new ImageIcon(getClass().getResource("icons/floor2.png"));
	private final ImageIcon hint = new ImageIcon(getClass().getResource("icons/hint.png"));
	private final ImageIcon goal = new ImageIcon(getClass().getResource("icons/goal.png"));
	private final ImageIcon fire = new ImageIcon(getClass().getResource("icons/fire.png"));
	private final ImageIcon ice = new ImageIcon(getClass().getResource("icons/ice.png"));
	
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
	private final ImageIcon treasureDoor = new ImageIcon(getClass().getResource("icons/treasure-door.png"));
	
	// Entities
	private final ImageIcon treasure = new ImageIcon(getClass().getResource("icons/treasure.png"));
	private final ImageIcon slot = new ImageIcon(getClass().getResource("icons/slot.png"));
	private final ImageIcon iceBoots = new ImageIcon(getClass().getResource("icons/ice-boots.png"));
	private final ImageIcon fireBoots = new ImageIcon(getClass().getResource("icons/fire-boots.png"));
	private final ImageIcon crate = new ImageIcon(getClass().getResource("icons/crate.png"));

	// Numbers
	private final ImageIcon zero = new ImageIcon(getClass().getResource("icons/0.png"));
	private final ImageIcon one = new ImageIcon(getClass().getResource("icons/1.png"));
	private final ImageIcon two = new ImageIcon(getClass().getResource("icons/2.png"));
	private final ImageIcon three = new ImageIcon(getClass().getResource("icons/3.png"));
	private final ImageIcon four = new ImageIcon(getClass().getResource("icons/4.png"));
	private final ImageIcon five = new ImageIcon(getClass().getResource("icons/5.png"));
	private final ImageIcon six = new ImageIcon(getClass().getResource("icons/6.png"));
	private final ImageIcon seven = new ImageIcon(getClass().getResource("icons/7.png"));
	private final ImageIcon eight = new ImageIcon(getClass().getResource("icons/8.png"));
	private final ImageIcon nine = new ImageIcon(getClass().getResource("icons/9.png"));
	private final ImageIcon colon = new ImageIcon(getClass().getResource("icons/colon.png"));
}