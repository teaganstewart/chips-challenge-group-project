package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.json.Json;
import javax.json.JsonObject;

/*
 * Class representing a pickupable item use for walking on ice blocks
 * */
public class IceBoots implements Entity {

	public IceBoots() {

	}

	@Override
	public JsonObject toJSON() {
		JsonObject value = Json.createObjectBuilder().add("EntityClass", IceBoots.class.getSimpleName())
				.build();
		return value;
	}
}
