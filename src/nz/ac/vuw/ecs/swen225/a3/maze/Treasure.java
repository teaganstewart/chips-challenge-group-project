package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * The Treasure class. As well as being used to hold Treasure objects, it has a static counter
 * built into it. This allows for the total treasures in a level to be counted up easily.
 * @author Ethan Munn
 *
 */
public class Treasure implements Entity {
	
	private static int totalInLevel;
	private static int totalCollected;

	/**
	 * 	The treasure constructor doesn't really need anything inside of it
	 */
	public Treasure() {
		totalInLevel++;
	}

	
	//-------------------------------------//
	//           STATIC METHODS            //
	//-------------------------------------//
	
	/**
	 * Called to reset the Treasure counter.
	 */
	public static void reset() {
		totalCollected = 0;
		totalInLevel = 0;
	}
	
	/**
	 * Called to reset the Treasure counter.
	 */
	public static void incrTreasuresCollected() {
		totalCollected++;
	}
	
	/**
	 * Returns if the total collected equals total amount in the level
	 * @return
	 * 		true or false, depending on this condition
	 */
	public static boolean allCollected() {
		return totalCollected == totalInLevel;
	}
	
}
