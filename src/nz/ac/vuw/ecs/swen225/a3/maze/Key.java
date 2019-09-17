package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * The key class, used to unlock doors matching the BasicColor of this object.
 * 
 * @author Ethan Munn
 *
 */
public class Key extends Entity {
	
	final BasicColor color;
	
	/**
	 * Sets up a brand new key.
	 * @param row
	 * 		The row
	 * @param col
	 * 		The column
	 * @param color
	 * 		The color of this key.
	 */
	public Key (int row, int col, BasicColor color) {
		super(row,col);
		this.color = color;
	}
	
	/**
	 * Returns the color of this key
	 * @return
	 * 		The color.
	 */
	BasicColor getColor() {
		return color;
	}

	@Override
	public void onTouch(Entity player) {
		// if player has space in their inventory
		//   player.add(this)
		//   tell the tile at this coordinate to remove the key
	}
	
}
