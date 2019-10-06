package nz.ac.vuw.ecs.swen225.a3.persistence;

import nz.ac.vuw.ecs.swen225.a3.maze.*;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.*;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The LoadUtils class contains methods that are used for loading games and
 * levels.
 *
 * @author Matt Rothwell
 */
public class LoadUtils {

	private static String LEVELS_DIRECTORY = "levels";

	/**
	 * Resumes the game from the last save made.
	 * 
	 * @return the most recent level played by the player, null if there is not a
	 *         save
	 */
	public static Level resumeGame() {
		File recentSave = getMostRecentSave();
		JsonObject jsonObjectRecentSave = extractLevel(readJsonFromFile(recentSave));
		return loadLevel(jsonObjectRecentSave);
	}

	/**
	 * Loads the specified level in JSON format from default source
	 * 
	 * @param levelNumber - the level to load
	 */
	public static Level loadLevel(int levelNumber) {
		File levelFile = new File(LEVELS_DIRECTORY + "\\" + levelNumber + ".json");
		JsonObject jsonObject = extractLevel(readJsonFromFile(levelFile));
		return loadLevel(jsonObject);
	}

	/**
	 * Load a level by it's level ID
	 * @param saveID the ID that is the main component of the file name
	 * @return the constructed level object
	 */
	public static Level loadById(Long saveID){
		File file = new File(SaveUtils.SAVES_DIRECTORY+"\\"+saveID+".json");
		try {
			return loadLevel(readJsonFromFile(file));
		}
		catch (NullPointerException e){
			return null;
		}
	}

	/**
	 * Creates a hashmap from ID -> Formatted String for GUI
	 * @return a HashMap containing ID's to a neatly formatted string for GUI display.
	 */
	public static Map<Long, String> getSavesByID(){
		Map<Long, String> namesToId = new HashMap<>();

		File directory = new File(SaveUtils.SAVES_DIRECTORY);
		FileFilter filter = pathname -> pathname.isFile() && pathname.toString().endsWith(".json");
		File[] files = directory.listFiles(filter);

		if (files != null) {
			for (File f : files) {

				JsonObject save = readJsonFromFile(f);

				JsonObject level = null;

				//This is for compatibility with older file formats
				try {
					level = extractLevel(save);
				}
				catch (NullPointerException e){
					level = save;
				}

				int levelNumber = level.getInt("levelNumber");

				String id = f.getName().substring(0, f.getName().length()-5);

				StringBuilder sb = new StringBuilder();

				if (save.getString("LevelName") != null){
					sb.append(save.getString("LevelName"));
					sb.append(" - ");
				}

				sb.append("Level: ");
				sb.append(levelNumber);
				sb.append(" - ");

				long saveTime = Long.parseLong(id);

				Date date = new Date(saveTime);
				sb.append(date.toString());


				namesToId.put(saveTime, sb.toString());
			}
		}

		return Collections.unmodifiableMap(namesToId);
	}

	/**
	 * Check inside the levels folder and count how many levels have been installed.
	 * @return amount of installed levels
	 */
	public static int getAmountOfInstalledLevels(){
		File directory = new File(LEVELS_DIRECTORY);
		FileFilter filter = pathname -> pathname.isFile() && pathname.toString().endsWith(".json");
		File[] files = directory.listFiles(filter);

		if (files != null) {
			return files.length;
		}
		return 0;
	}

	// Private Methods

	/**
	 * Produces a Level object from the JSON input given
	 * 
	 * @param level Json representation of a game
	 * @return the Json deserialised back into Object form
	 */
	private static Level loadLevel(JsonObject level) {
		int levelNumber = level.getInt("levelNumber");
		long levelStartTime = Long.parseLong(level.getString("levelBeginTime"));
		long levelRunningTime = Long.parseLong(level.getString("totalRunningTime"));

		// Marker for a new level, set the starting time
		if (levelStartTime == -1) {
			levelStartTime = System.currentTimeMillis();
		}

		int timeAllowed = level.getInt("timeAllowed");

		boolean completed = level.getBoolean("completed");

		Maze maze = loadMaze(level.getJsonObject("maze"));

		return new Level(levelNumber, maze, levelStartTime, levelRunningTime, timeAllowed);
	}

	/**
	 * Searches through the directory of saves, finding the most recent saved game.
	 * 
	 * @return the most recent saved game's json file.
	 */
	private static File getMostRecentSave() {
		File newest = null;
		File directory = new File(SaveUtils.SAVES_DIRECTORY);
		FileFilter filter = pathname -> pathname.isFile() && pathname.toString().endsWith(".json");
		File[] files = directory.listFiles(filter);

		if (files != null) {
			for (File f : files) {
				if (newest == null || f.lastModified() > newest.lastModified()) {
					newest = f;
				}
			}
		}

		return newest;
	}

	/**
	 * Reads a file and reads it into a new JsonObject
	 * 
	 * @param file the file to read json from
	 * @return Json object version of that file, null if not found.
	 */
	private static JsonObject readJsonFromFile(File file) {
		if (file != null) {
			try {
				InputStream inputStream = new FileInputStream(file);
				JsonReader reader = Json.createReader(inputStream);

				return reader.readObject();

			}
			catch (FileNotFoundException e) {
				return null;
			}
		}

		return null;
	}

	/**
	 * Create a player object from JSON object notation, filling in their inventory
	 * and coordinates
	 * 
	 * @param player Player representation in JSON object notation
	 * @return a player object identical tho that represented in JSON
	 */
	private static Player loadPlayer(JsonObject player) {
		Player newPlayer = new Player(loadCoordinate(player.getJsonObject("Coordinate")));
		JsonArray inventory = player.getJsonArray("Inventory");

		for (int i = 0; i < inventory.size(); i++) {
			JsonObject inventoryItem = inventory.getJsonObject(i);
			newPlayer.addToInventory(loadEntity(inventoryItem));
		}

		return newPlayer;
	}

	/**
	 * Create a new Tile object from JSON object notation
	 * 
	 * @param tile the JSON object that contains all the information about this tile
	 * @return the Java tile Object
	 */
	private static Tile loadTile(JsonObject tile) {
		Tile newTile;
		Tile.TileType tileType = Tile.TileType.valueOf(tile.getString("TileType"));
		Coordinate tileCoordinate = loadCoordinate(tile.getJsonObject("Coordinate"));

		if (tileType == Tile.TileType.HINT) {
			newTile = new HintTile(tileCoordinate, tile.getString("Message"));
		}
		else {
			newTile = new Tile(tileCoordinate, tileType);
		}

		if (tile.getJsonObject("Entity") != null) {
			newTile.setEntity(loadEntity(tile.getJsonObject("Entity")));
		}
		return newTile;
	}

	/**
	 * Load a Coordinate object in from JSON format
	 * 
	 * @param coordinate as a json object
	 * @return coordinate in Java Object form
	 */
	private static Coordinate loadCoordinate(JsonObject coordinate) {
		return new Coordinate(coordinate.getInt("row"), coordinate.getInt("col"));
	}

	/**
	 * Load an Entity from JSON
	 * 
	 * @param entity the Json object representing an entity
	 * @return Java object identical to that represented in file form
	 */
	private static Entity loadEntity(JsonObject entity) {
		String entityClass = entity.getString("EntityClass");
		if (entityClass.equals("Key")) {
			BasicColor basicColor = BasicColor.valueOf(entity.getString("BasicColor"));
			return new Key(basicColor);
		}
		else if (entityClass.equals("KeyDoor")) {
			BasicColor basicColor = BasicColor.valueOf(entity.getString("BasicColor"));
			KeyDoor newKeyDoor = new KeyDoor(basicColor);
			boolean locked = entity.getBoolean("locked");
			if (!locked) {
				newKeyDoor.unlock();
			}
			return newKeyDoor;
		}
		else if (entityClass.equals("Treasure")) {
			return new Treasure();
		}
		else if (entityClass.equals("TreasureDoor")) {
			TreasureDoor treasureDoor = new TreasureDoor();
			boolean locked = entity.getBoolean("locked");
			if (!locked) {
				treasureDoor.unlock();
			}
			return treasureDoor;
		}
		else if (entityClass.equals("FireBoots")){
			return new FireBoots();
		}
		else if (entityClass.equals("IceBoots")){
			return new IceBoots();
		}
		return null;
	}

	/**
	 * Load a maze object from a JsonObject
	 * 
	 * @param maze the maze object in JSON object form
	 * @return a Maze object with the properties from the file.
	 */
	private static Maze loadMaze(JsonObject maze) {
		Player player = loadPlayer(maze.getJsonObject("player"));
		int rows = maze.getInt("rows");
		int cols = maze.getInt("cols");
		Tile[][] tiles = new Tile[rows][cols];

		JsonArray jsonArray = maze.getJsonArray("tiles");

		int index = 0;
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				tiles[row][col] = loadTile(jsonArray.getJsonObject(index++));
			}
		}

		JsonObject treasureData = maze.getJsonObject("treasureData");
		int totalInLevel = treasureData.getInt("totalInLevel");
		int totalCollected = treasureData.getInt("totalCollected");

		Treasure.setTreasureCountersUponLoad(totalInLevel, totalCollected);

		// TODO make a List of all the crates in the level and pass it into the maze consturctor
		return new Maze(tiles, player, null);
	}

	/**
	 * Extracts the Level Json Object from a Json Object.
	 * @param objectPlusSaveName the raw object to remove level from
	 * @return just the level in object form
	 */
	private static JsonObject extractLevel(JsonObject objectPlusSaveName){
		return objectPlusSaveName.getJsonObject("Level");
	}

}
