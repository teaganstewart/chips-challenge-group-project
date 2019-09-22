package nz.ac.vuw.ecs.swen225.a3.maze;

import nz.ac.vuw.ecs.swen225.a3.persistence.Saveable;

import javax.json.Json;
import javax.json.JsonObject;

/**
 * The level class stores information relating to the current level being played.
 * It stores the time details and
 * the maze, as well as the levelNumber
 *
 * @author Matt Rothwell
 */
public class Level implements Saveable {

    private Long levelBeginTime;
    private Long levelRunningTime;

    private Long levelStartTime;
    private int timeAllowed;

    private int level;
    private Maze maze;

    /**
     * Constructor for a new Level.
     * @param levelNumber the number of this level from file.
     * @param maze the maze object that stores the game data.
     * @param levelBeginTime the time that the level was first loaded. (Could be used for replaying)
     * @param levelRunningTime the total time this level has been in play.
     * @param timeAllowed the time allowed to complete this level
     */
    public Level(int levelNumber, Maze maze, long levelBeginTime, long levelRunningTime, int timeAllowed){
        this.levelRunningTime = levelRunningTime;
        this.levelBeginTime = levelBeginTime;
        level = levelNumber;
        this.maze = maze;
        this.timeAllowed = timeAllowed;
    }

    /**
     * Starts the timer by setting the start time to the current time.
     */
    public void startTimer(){
        levelStartTime = System.currentTimeMillis();
    }

    /**
     * Report how long the player has to complete this level in seconds.
     */
    public int reportTimeRemaining(){
        return timeAllowed - (int)(((System.currentTimeMillis() - levelStartTime) + levelRunningTime)/1000);
    }

    /**
     * Stop the timer, and add the time taken to the running time.
     */
    public void stopTimer(){
        levelRunningTime += (System.currentTimeMillis() - levelStartTime);
    }

    /**
     * Tells whether or not there is still time remaining on this level.
     * @return whether or not there is time left on the clock.
     */
    public boolean isTimeRemaining(){
        return timeAllowed > (int)(((System.currentTimeMillis() - levelStartTime) + levelRunningTime)/1000);
    }

    /**
     * Save this level object to a Json object so that it can be saved and reloaded
     * @return Json object representing this Java Object
     */
    @Override
    public JsonObject toJSON() {
        JsonObject level = Json.createObjectBuilder()
                .add("levelNumber", this.level)
                .add("levelBeginTime", levelBeginTime.toString())
                .add("totalRunningTime", levelRunningTime.toString())
                .add("timeAllowed", timeAllowed)
                .add("completed", false)
                .add("maze", maze.toJSON())
                .build();

        return level;
    }

    /**
     * Get the maze object.
     * @return the maze object containing the map.
     */
    public Maze getMaze() {
        return maze;
    }

    /**
     * Getter for the current level number.
     * @return level - the level in the maze.
     */
    public int getLevel() {
        return level;
    }
}
