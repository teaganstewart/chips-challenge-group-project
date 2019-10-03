package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.json.Json;
import javax.json.JsonObject;

/*
 * Class representing a pickupable item use for walking on fire blocks
 * */
public class FireBoots implements Entity {

	public FireBoots() {

	}

	@Override
	public JsonObject toJSON() {
		JsonObject value = Json.createObjectBuilder().add("EntityClass", FireBoots.class.getSimpleName())
				.build();
		return value;
	}
}
