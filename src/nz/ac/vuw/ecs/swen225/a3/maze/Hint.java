package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * The Hint class, which allows the player to view a hint upon touching this object
 * (and pressing a key, as a popup every time might get annoying).
 * 
 * @author Ethan Munn
 *
 */
public class Hint extends Entity {

	private final String message;
	
	/**
	 * 		The tile the hint is on
	 * @param message
	 * 		The message to display upon this hint being touched.
	 */
	public Hint(String message) {
		super();
		this.message = message;
	}

	@Override
	public void onTouch(Entity player) {
		
		// unsure what to put in here, maybe a toggle of some kind
		
	}

	/**
	 * Gets the helpful hint so the player knows what to do.
	 * @return
	 * 		The message
	 */
	public String getMessage() {
		return message;
	}
	
}