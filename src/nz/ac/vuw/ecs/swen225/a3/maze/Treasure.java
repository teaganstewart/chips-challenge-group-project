package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * The Treasure class. As well as being used to hold Treasure objects, it has a static counter
 * built into it. This allows for the total treasures in a level to be counted up easily.
 * @author Ethan Munn
 *
 */
public class Treasure extends Entity {
	
	private static int totalCollected;
	
	/**
	 * Creates a brand new Treasure.
	 * @param tileOn
	 * 		The tile the treasure is on
	 */
	public Treasure(Tile tileOn) {
		super(tileOn);
	}

	@Override
	public void onTouch(Entity player) {
		
		// even though the player class doesn't explicitly collect these, just a safe check
		if (!(player instanceof Player)) return;
		
		//	getTileOn().remove(this);
		//	setTileOn(null);
		
		// increments the static counter by 1
		totalCollected++;
		
	}
	
	//-------------------------------------//
	//           STATIC METHODS            //
	//-------------------------------------//
	
	/**
	 * Called to reset the Treasure counter.
	 */
	public static void reset() {
		totalCollected = 0;
	}
	
	/**
	 * Gets the total amount of collected Treasures
	 * in a level
	 * @return
	 * 		total count of treasures collected
	 */
	public static int getTotalCollected() {
		return totalCollected;
	}
	
}
