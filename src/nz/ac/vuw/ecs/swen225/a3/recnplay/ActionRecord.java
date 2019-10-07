package nz.ac.vuw.ecs.swen225.a3.recnplay;

import nz.ac.vuw.ecs.swen225.a3.maze.*;
import nz.ac.vuw.ecs.swen225.a3.persistence.Saveable;

import javax.json.Json;
import javax.json.JsonObject;

/**
 * Used for the recnplay feature
 * 
 * @author Ethan Munn
 *
 */
public class ActionRecord implements Saveable {

	private Long timeSinceLevelStart;
	private Maze maze;

	public ActionRecord(long timeSinceLevelStart, Maze maze) {
		this.timeSinceLevelStart = timeSinceLevelStart;
		this.maze = maze;
	}


	public long getTimeSinceLevelStart() {
		return timeSinceLevelStart;
	}

	@Override
	public JsonObject toJSON() {
		JsonObject arJson = Json.createObjectBuilder()
				.add("timestamp", timeSinceLevelStart.toString())
				.add("maze", maze.toJSON())
				.build();
		return arJson;
	}
}
