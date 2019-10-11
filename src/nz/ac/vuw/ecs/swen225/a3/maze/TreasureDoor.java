package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.json.Json;
import javax.json.JsonObject;

/**
 * Represents a Door which is locked until the player collects all the
 * treasure.
 * 
 * @author Ethan Munn - 300367257
 * 
 */
public class TreasureDoor extends Door {

	@Override
	public boolean onTouch(Player pl) {

		// safety check that the player has touched the door
		if (pl == null)
			return false;

		if (Treasure.allCollected()) {
			unlock();
			return true;
		}

		return false;

	}

	/**
	 * Produce a JSON object for this TreasureDoor
	 * 
	 * @return Json object representing this TreasureDoor
	 */
	@Override
	public JsonObject toJSON() {
		JsonObject value = Json.createObjectBuilder().add("EntityClass", TreasureDoor.class.getSimpleName())
				.add("locked", isLocked()).build();
		return value;
	}
}