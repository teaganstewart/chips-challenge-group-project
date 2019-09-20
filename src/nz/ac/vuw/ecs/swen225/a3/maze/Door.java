package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * The door class, prevents the players from passing through the door
 * if the player does not have the right key equipped.
 * 
 * @author Ethan Munn
 *
 */
public class Door extends Entity {

	private boolean locked;
	private final BasicColor color;
	
	/**
	 * Creates a new door object, corresponding to a certain key
	 * colour. By default, every new door should be locked.
	 *
	 * @param color
	 * 		Color of the door, using BasicColor enum
	 */
	public Door(BasicColor color) {
		super();
		this.color = color;
		this.locked = true;
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
	public boolean isRightKey(Key key) {
		return key.getColor() == color;
	}

	@Override
	public void onTouch(Entity pl) {

		if (!(pl instanceof Player)) return;
		Player player = (Player) pl;
		
		Key key = null;
		for (Entity e : player.getInventory()) {
			if (e instanceof Key) {
				Key temp = (Key) e;
				if (isRightKey(temp)) {
					unlock();
					key = temp;
					break;
				}
			}
		}
		
		if (key != null) {
			//player.removeFromInvetory(key);
		}
		
	}

	/**
	 * Used to check whether or not the door is locked.
	 * @return
	 * 		if the door is locked or unlocked
	 */
	public boolean isLocked() {
		return locked;
	}

	/**
	 * A private method for unlocking the door. We don't want this
	 * to be public, as we don't want the state of the door being
	 * controlled by something outside of the door.
	 */
	private void unlock() {
		locked = false;
	}
	
}
