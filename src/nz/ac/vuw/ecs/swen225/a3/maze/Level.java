package nz.ac.vuw.ecs.swen225.a3.maze;

import nz.ac.vuw.ecs.swen225.a3.persistence.Saveable;

import javax.json.Json;
import javax.json.JsonObject;

/**
 * The level class stores information relating to the current level being played.
 * It stores the time at which the game started and
 * the maze, as well as the levelNumber
 */
public class Level implements Saveable {

    private long levelTotalTime;

    private long levelBeginTime;
    private long levelRunningTime;
    private int level;
    private Maze maze;

    public Level(int levelNumber, Maze maze, boolean isFreshLevel){
        levelStart = System.currentTimeMillis();
        level = levelNumber;
        this.maze = maze;
    }

    public void startTimer(){}

    public void stopTimer(){}

    @Override
    public JsonObject toJSON() {
        JsonObject level = Json.createObjectBuilder()
                .add("levelNumber", this.level)
                .add("levelStartTime", levelStart)
                .add("totalLevelTime", levelTime)
                .add("maze", maze.toJSON())
                .build();

        return level;
    }
}
