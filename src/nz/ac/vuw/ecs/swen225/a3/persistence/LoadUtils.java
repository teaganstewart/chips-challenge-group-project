package nz.ac.vuw.ecs.swen225.a3.persistence;

import nz.ac.vuw.ecs.swen225.a3.maze.Maze;

import java.io.File;
import java.io.FileFilter;


/**
 * The LoadUtils class contains methods that are used for loading games and levels
 * @author Matt Rothwell
 */
public class LoadUtils {

    public static String LEVELS_DIRECTORY = "levels";

    /**
     * Resumes the game from the last save made.
     */
    public static Maze resumeGame(){

        return null;
    }

    /**
     * Loads a new game from the starting level
     */
    public static void restartGame(){}

    /**
     * Loads the specified level in JSON format from file
     */
    private static void loadLevel(int i){

    }

    /**
     * Searches through the directory of saves, finding the most recent saved game.
     * @return the most recent saved game's json file.
     */
    private static File getMostRecentSave(){
        File newest = null;
        File directory = new File(SaveUtils.SAVES_DIRECTORY);
        FileFilter filter = pathname -> pathname.isFile() && pathname.toString().endsWith(".json");
        File[] files = directory.listFiles(filter);

        if (files != null) {
            for (File f : files){
                if (newest == null || f.lastModified() > newest.lastModified()){
                    newest = f;
                }
            }
        }

        return newest;
    }
}
