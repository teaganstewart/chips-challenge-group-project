package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.json.Json;
import javax.json.JsonObject;

/**
 * Class representing a pickup-able item use for walking on fire blocks
 * 
 * @author Josh O'Hagan - 300442801
 */
public class FireBoots implements Entity {

	public String toString() {
		return "FireBoots";
	}

	@Override
	public JsonObject toJSON() {
		JsonObject value = Json.createObjectBuilder().add("EntityClass", FireBoots.class.getSimpleName())
				.build();
		return value;
	}
}
