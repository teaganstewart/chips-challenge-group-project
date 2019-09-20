package nz.ac.vuw.ecs.swen225.a3.persistence;

import nz.ac.vuw.ecs.swen225.a3.maze.Maze;

/**
 * This class contains methods relevant to saving of the current game state to a json file.
 *
 * @author Matt Rothwell
 */
public class SaveGame {

    /**
     * Save the game state so that the player can resume next time the program is
     * opened.
     * @param maze the board.
     */
    public static void saveGame(Maze maze){

    }

    /**
     * The player has chosen to exit the game, without saving the current game state
     * Therefore this method simply saves the number of the last unfinished level they were on
     * so that the game can resume on this next time.
     */
    public static void saveLevel(){

    }


}
