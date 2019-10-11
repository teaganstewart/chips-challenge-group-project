package nz.ac.vuw.ecs.swen225.a3.maze;

import nz.ac.vuw.ecs.swen225.a3.persistence.Saveable;

import javax.json.Json;
import javax.json.JsonObject;

/**
 * The level class stores information relating to the current level being
 * played. It stores the time details and the maze, as well as the levelNumber
 *
 * @author Matt Rothwell - 300434822
 */
public class Level implements Saveable {

	private Long levelBeginTime;
	private Long levelRunningTime;
	private int timeAllowed;

	private int level;
	private Maze maze;

	/**
	 * Constructor for a new Level.
	 * 
	 * @param levelNumber      the number of this level from file.
	 * @param maze             the maze object that stores the game data.
	 * @param levelBeginTime   the time that the level was first loaded. (Could be
	 *                         used for replaying)
	 * @param levelRunningTime the total time this level has been in play.
	 * @param timeAllowed      the time allowed to complete this level
	 */
	public Level(int levelNumber, Maze maze, long levelBeginTime, long levelRunningTime, int timeAllowed) {
		this.levelRunningTime = levelRunningTime;
		this.levelBeginTime = levelBeginTime;
		level = levelNumber;
		this.maze = maze;
		this.timeAllowed = timeAllowed;
	}

	/**
	 * Save this level object to a Json object so that it can be saved and reloaded
	 * 
	 * @return Json object representing this Java Object
	 */
	@Override
	public JsonObject toJSON() {
		JsonObject level = Json.createObjectBuilder().add("levelNumber", this.level)
				.add("levelBeginTime", levelBeginTime.toString()).add("totalRunningTime", levelRunningTime.toString())
				.add("timeAllowed", timeAllowed).add("completed", maze.isGoalReached()).add("maze", maze.toJSON())
				.build();

		return level;
	}

	/**
	 * Get the maze object.
	 * 
	 * @return the maze object containing the map.
	 */
	public Maze getMaze() {
		return maze;
	}

	/**
	 * Getter for the current level number.
	 * 
	 * @return level The level in the maze.
	 */
	public int getLevel() {
		return level;
	}
	
	
	/**
	 * Getter for the amount of time allowed.
	 * 
	 * @return timeAllowed Returns time for level.
	 */
	public int getTimeAllowed() {
		return timeAllowed;
	}
	
	/**
	 * Gets the time the level has been running
	 * @return
	 * 		the time the level has been running for
	 */
	public long getRunTime() {
		return levelRunningTime;
	}
	
	/**
	 * A setter for the running time, only used when paused
	 * @param time
	 * 		the time to be saved
	 */
	public void setRunningTime(long time) {
		levelRunningTime = time;
	}
	
	/**
	 * Gets the time in which this level began. Can be used as an id.
	 * @return
	 * 		the beginning time of the level (can be used as recording ID)
	 */
	public long getBeginTime() {
		return levelBeginTime;
	}
}
