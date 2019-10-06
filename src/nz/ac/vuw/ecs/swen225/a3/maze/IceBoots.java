package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.json.Json;
import javax.json.JsonObject;

/**
 * @author Josh
 *
 * Class representing a pickup-able item use for walking on ice blocks
 */
public class IceBoots implements Entity {

	/**
	 * Constructor
	 */
	public IceBoots() {

	}

	@Override
	public JsonObject toJSON() {
		JsonObject value = Json.createObjectBuilder().add("EntityClass", IceBoots.class.getSimpleName())
				.build();
		return value;
	}
}
