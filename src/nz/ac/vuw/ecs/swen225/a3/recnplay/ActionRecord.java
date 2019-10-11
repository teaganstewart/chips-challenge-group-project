package nz.ac.vuw.ecs.swen225.a3.recnplay;

import nz.ac.vuw.ecs.swen225.a3.maze.*;
import nz.ac.vuw.ecs.swen225.a3.persistence.Saveable;

import javax.json.Json;
import javax.json.JsonObject;

/**
 * Used for the recnplay feature
 * 
 * @author Ethan Munn, Matt Rothwell
 *
 */
public class ActionRecord implements Saveable {

	private int timeSinceLevelStart;
	private Maze maze;

	/**
	 * Constructor for a new ActionRecord.
	 * @param timeSinceLevelStart in milliseconds from level begin.
	 * @param maze the maze object representing the board.
	 */
	public ActionRecord(int timeSinceLevelStart, Maze maze) {
		this.timeSinceLevelStart = timeSinceLevelStart;
		this.maze = maze;
	}

	/**
	 * Get the integer representing the time at which this record was created.
	 * @return timestamp in milliseconds since creation.
	 */
	public int getTimeSinceLevelStart() {
		return timeSinceLevelStart;
	}

	/**
	 * Gets the maze object created by this ActionRecord.
	 * @return Maze.
	 */
	public Maze getMaze() {
		return maze;
	}

	/**
	 * Produce a JSON representation of this ActionRecord object to allow it to be saved.
	 * @return a JsonObject serialisation of this record.
	 */
	@Override
	public JsonObject toJSON() {
		return Json.createObjectBuilder()
				.add("timestamp", timeSinceLevelStart)
				.add("maze", maze.toJSON())
				.build();
	}
}
