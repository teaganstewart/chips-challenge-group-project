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
	 * @param color
	 * 		The color of this key.
	 */
	public Key (BasicColor color) {
		super();
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
