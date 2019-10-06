package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.json.Json;
import javax.json.JsonObject;

/**
 * @author Josh
 *
 * Class representing a pickup-able item use for walking on fire blocks
 */
public class FireBoots implements Entity {

	/**
	 * Constructor
	 */
	public FireBoots() {

	}

	@Override
	public JsonObject toJSON() {
		JsonObject value = Json.createObjectBuilder().add("EntityClass", FireBoots.class.getSimpleName())
				.build();
		return value;
	}
}
