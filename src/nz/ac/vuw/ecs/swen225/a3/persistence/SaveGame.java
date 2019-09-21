package nz.ac.vuw.ecs.swen225.a3.persistence;

import nz.ac.vuw.ecs.swen225.a3.maze.Maze;

import javax.json.Json;
import javax.json.JsonWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 *
 * This class contains methods relevant to saving of the current game state to a json file.
 *
 * @author Matt Rothwell
 */
public class SaveGame {

    public static String SAVES_DIRECTORY = "saves";


    /**
     * Save the game state so that the player can resume next time the program is
     * opened.
     * @param maze the board.
     */
    public static boolean saveGame(Maze maze){
        try{
            JsonWriter writer = Json.createWriter(new PrintStream(new File(SAVES_DIRECTORY+"\\"+System.currentTimeMillis()+".json")));
            writer.write(maze.toJSON());
            writer.close();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    /**
     * The player has chosen to exit the game, without saving the current game state
     * Therefore this method simply saves the number of the last unfinished level they were on
     * so that the game can resume on this next time.
     */
    public static void saveLevel(){

    }


}
