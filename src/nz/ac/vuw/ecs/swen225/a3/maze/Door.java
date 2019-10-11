package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * The door class, prevents the players from passing through the door if the
 * player does not have the right key equipped.
 * 
 * @author Ethan Munn - 300367257
 *
 */
public abstract class Door implements Entity {

	private boolean locked;

	/**
	 * Creates a new door object. By default, every new door should be locked.
	 *
	 */
	public Door() {
		this.locked = true;
	}

	/**
	 * Used to check whether or not the door is locked.
	 * 
	 * @return if the door is locked or unlocked
	 */
	public boolean isLocked() {
		return locked;
	}

	/**
	 * Used to unlock the door.
	 */
	public void unlock() {
		locked = false;
	}

	/**
	 * Used for the different forms of unlocking
	 * 
	 * @param player The player going through the door.
	 * @return True if player can move through door.
	 */
	public abstract boolean onTouch(Player player);

}
