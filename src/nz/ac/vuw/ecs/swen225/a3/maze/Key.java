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
	 * @param tileOn
	 * 		The tile the key is on
	 * @param color
	 * 		The color of this key.
	 */
	public Key (int row, int col, Tile tileOn, BasicColor color) {
		super(row,col,tileOn);
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
	public void onTouch(Entity pl) {
		
		if (!(pl instanceof Player)) return;
		Player player = (Player) pl;
		
		/*
		if (player.addToInvetory(this)) {
			getTileOn().remove(this);
			setTileOn(null);
		}
		 */
	}
	
}
