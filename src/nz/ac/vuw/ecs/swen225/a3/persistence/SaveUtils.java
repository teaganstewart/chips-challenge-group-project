package nz.ac.vuw.ecs.swen225.a3.persistence;

import nz.ac.vuw.ecs.swen225.a3.maze.Level;

import javax.json.Json;
import javax.json.JsonWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 *
 * The SaveUtils contains methods relevant to saving of the current game state to a Json file.
 *
 * @author Matt Rothwell
 */
public class SaveUtils {

    public static String SAVES_DIRECTORY = "saves";

    /**
     * Save the game state so that the player can resume next time the program is
     * opened.
     * @param level object to save.
     */
    public static boolean saveGame(Level level){
        try{
            constructSaves();
            JsonWriter writer = Json.createWriter(new PrintStream(new File(SAVES_DIRECTORY+"\\"+System.currentTimeMillis()+".json")));
            writer.write(level.toJSON());
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

    /**
     * Create the saves directory if it does not exist
     * @return whether or not it exists.
     */
    private static boolean constructSaves(){
        File directory = new File(SAVES_DIRECTORY);
        if (!directory.exists()){
            return directory.mkdir();
        }
        return true;
    }


}
