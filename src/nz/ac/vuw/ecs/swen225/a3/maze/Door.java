package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * The door class, prevents the players from passing through the door
 * if the player does not have the right key equipped.
 * 
 * @author Ethan Munn
 *
 */
public class Door extends Entity {

	final BasicColor color;
	
	public Door(int row, int col, BasicColor color) {
		super(row, col);
		this.color = color;
	}
	
	/**
	 * Gets the BasicColour of the door.
	 * @return
	 * 		Door colour.
	 */
	public BasicColor getColor() {
		return color;
	}
	
	/**
	 * Checks a keyColor to see if it matches the door.
	 * @param keyColor
	 * 		Color of the key, using the BasicColor enum
	 * @return
	 * 		Whether or not the player can unlock this door.
	 */
	public boolean isRightKey(BasicColor keyColor) {
		return keyColor == color;
	}
	
}
