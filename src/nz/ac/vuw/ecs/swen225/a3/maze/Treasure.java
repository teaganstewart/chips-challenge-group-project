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
	 * @param row
	 * 		the row
	 * @param col
	 * 		the col
	 */
	public Treasure(int row, int col) {
		super(row,col);
	}

	@Override
	public void onTouch(Entity player) {
		
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
