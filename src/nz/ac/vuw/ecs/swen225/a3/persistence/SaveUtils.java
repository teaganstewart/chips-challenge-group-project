package nz.ac.vuw.ecs.swen225.a3.persistence;

import nz.ac.vuw.ecs.swen225.a3.maze.Level;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 *
 * The SaveUtils contains methods relevant to saving of the current game state
 * to a Json file.
 *
 * @author Matt Rothwell - 300434822
 */
public class SaveUtils {

	/**
	 * The directory of all the saves.
	 */
	public static String SAVES_DIRECTORY = "saves";

	/**
	 * Save the game state so that the player can resume next time the program is
	 * opened.
	 * 
	 * @param level object to save.
	 * @param saveName The name you want the level to be saved as.
	 * @return Returns true if the level was saved, false if it doesn't exist.
	 */
	public static boolean saveGame(Level level, String saveName) {
		try {
			constructSaves();
			JsonWriter writer = Json.createWriter(
					new PrintStream(new File(SAVES_DIRECTORY + "\\" + System.currentTimeMillis() + ".json")));

			JsonObject save = Json.createObjectBuilder()
					.add("LevelName", saveName)
					.add("Level", level.toJSON())
					.build();

			writer.write(save);
			writer.close();
			return true;
		} catch (FileNotFoundException e) {
			return false;
		}
	}

	/**
	 * The player has chosen to exit the game, without saving the current game state
	 * Therefore this method simply saves the number of the last unfinished level
	 * they were on so that the game can resume on this next time.
	 * 
	 * @param level The number of the level we want to save.
	 * @return Returns true if the level was saved, false if it doesn't exist.
	 */ 
	public static boolean saveLevel(int level) {
		try {
			constructSaves();
			JsonWriter writer = Json.createWriter(
					new PrintStream(new File(SAVES_DIRECTORY + "\\" + System.currentTimeMillis() + ".json")));

			JsonObject save = Json.createObjectBuilder()
					.add("LevelNum", level)
					.build();

			JsonObject toSave = Json.createObjectBuilder()
					.add("Level", save).build();

			writer.write(toSave);
			writer.close();

			return true;
		} catch (FileNotFoundException e) {
			return false;
		}
	}

	/**
	 * Create the saves directory if it does not exist
	 * 
	 * @return whether or not it exists.
	 */
	private static boolean constructSaves() {
		File directory = new File(SAVES_DIRECTORY);
		if (!directory.exists()) {
			return directory.mkdir();
		}
		return true;
	}


}