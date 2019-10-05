package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.json.Json;
import javax.json.JsonObject;

/**
 * The Treasure class. As well as being used to hold Treasure objects, it has a
 * static counter built into it. This allows for the total treasures in a level
 * to be counted up easily.
 * 
 * @author Ethan Munn
 *
 */
public class Treasure implements Entity {

	private static int totalInLevel;
	private static int totalCollected;

	/**
	 * The treasure constructor doesn't really need anything inside of it
	 */
	public Treasure() {
		totalInLevel++;
	}

	/**
	 * Convert this Treasure object to JSON format
	 * 
	 * @return this object represented in JSON
	 */
	@Override
	public JsonObject toJSON() {
		JsonObject value = Json.createObjectBuilder().add("EntityClass", Treasure.class.getSimpleName()).build();
		return value;
	}

	// -------------------------------------//
	// STATIC METHODS //
	// -------------------------------------//

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
	 * 
	 * @return true or false, depending on this condition
	 */
	public static boolean allCollected() {
		return totalCollected == totalInLevel;
	}
	
	/**
	 * Returns the amount of treasures collected
	 * 
	 * @return the total treasures collected
	 */
	public static int getTotalCollected() {
		return totalCollected;
	}

	/**
	 * Returns the amount of treasures in the level
	 * 
	 * @return the total treasures in the level
	 */
	public static int getTotalInLevel() {
		return totalInLevel;
	}
	
	/**
	 * Serialize the static fields within Treasure to JSON object format
	 * 
	 * @return Json object representation of the static fields.
	 */
	public static JsonObject toJSONStatic() {
		JsonObject value = Json.createObjectBuilder().add("totalInLevel", totalInLevel)
				.add("totalCollected", totalCollected).build();
		return value;
	}

	/**
	 * Set the treasure counters to the correct values from the file.
	 * 
	 * @param totalInLevelFromFile   amount of treasure in the map originally from
	 *                               file.
	 * @param totalCollectedFromFile amount of treasure the player has already
	 *                               collected.
	 */
	public static void setTreasureCountersUponLoad(int totalInLevelFromFile, int totalCollectedFromFile) {
		totalInLevel = totalInLevelFromFile;
		totalCollected = totalCollectedFromFile;
	}
}
