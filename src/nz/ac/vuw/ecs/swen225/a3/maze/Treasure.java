package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.json.Json;
import javax.json.JsonObject;

/**
 * The Treasure class. As well as being used to hold Treasure objects, it has a
 * static counter built into it. This allows for the total treasures in a level
 * to be counted up easily.
 * 
 * @author Ethan Munn - 300367257
 *
 */
public class Treasure implements Entity {

	private static int totalInLevel;
	private static int totalCollected;

	/**
	 * The Treasure object constructor, adds one to the static total every time.
	 * Static so it can be accessed from any treasure.
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
	// -----------STATIC METHODS----------- //
	// -------------------------------------//

	/**
	 * Called to reset the Treasure counter.
	 */
	public static void reset() {
		totalCollected = 0;
		totalInLevel = 0;
	}

	/**
	 * Adds one to the total treasures collected.
	 */
	public static void incrTreasuresCollected() {
		totalCollected++;
	}

	/**
	 * Checks whether there are any treasures left to collect.
	 * 
	 * @return Returns true if they are, false if they aren't.
	 */
	public static boolean allCollected() {
		return totalCollected == totalInLevel;
	}

	/**
	 * The amount of treasures the user has collected.
	 * 
	 * @return Returns the total treasures collected
	 */
	public static int getTotalCollected() {
		return totalCollected;
	}

	/**
	 * The amount of treasures in the level
	 * 
	 * @return Returns the total treasures in the level
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
	 * @param totalInLevelFromFile Amount of treasure in the map originally from
	 *                               file.
	 * @param totalCollectedFromFile Amount of treasure the player has already
	 *                               collected.
	 */
	public static void setTreasureCountersUponLoad(int totalInLevelFromFile, int totalCollectedFromFile) {
		totalInLevel = totalInLevelFromFile;
		totalCollected = totalCollectedFromFile;
	}
}
