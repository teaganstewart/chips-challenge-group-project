package nz.ac.vuw.ecs.swen225.a3.recnplay;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;
import nz.ac.vuw.ecs.swen225.a3.persistence.LoadUtils;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;


/**
 * ReplayUtils contains functionality for managing the recording and
 * playback of replays using JSON files.
 *
 * @author Ethan Munn - 300367257, Matt Rothwell - 300434822
 */
public class ReplayUtils {

	/**
	 * Used to access the recordings for loading and saving.
	 */
	public static final String RECORD_DIRECTORY = "recordings";
	
	/**
	 * Sets the start time.
	 */
	public static long startTime = 0;
	
	/**
	 * Sets the difference.
	 */
	public static long difference;
	
	/**
	 * Sets the pauseTime;
	 */
	public static long pauseTime;
	
	/**
	 * A list of all the action records in the replay.
	 */
	private static List<ActionRecord> replay;
	
	/**
	 * Updates the list of action records when starting a new replay.
	 * 
	 * @param id The name of the file we are loading.
	 */
    public static void playBack(String id) {
    	if (replay != null) replay.clear();
    	replay = loadReplay(id);
    }

	/**
	 * Load a replay's ActionRecord's from File.
	 * @param id of the replay to load, usually a long representing time of creation.
	 * @return a list of ActionRecords representing the Maze at it's various states.
	 */
	private static List<ActionRecord> loadReplay(String id){

		List<ActionRecord> record = new ArrayList<>();

		File file = new File(RECORD_DIRECTORY+"\\"+id);
		if (file.isDirectory()){
			FileFilter filter = pathname -> pathname.isFile() && pathname.toString().endsWith(".json");
			File[] files = file.listFiles(filter);

			if (files != null) {
				for (File actionRecordFile : files) {
					JsonObject arJson = LoadUtils.readJsonFromFile(actionRecordFile);
					ActionRecord actionRecord;
					if (arJson != null) {
						actionRecord = LoadUtils.loadActionRecord(arJson);
						record.add(actionRecord);
					}
				}
			}
		}

		return record;
		
	}

	/**
	 * Push a new ActionRecord object to the recordings directory to save it.
	 * @param actionRecord the actionRecord to save.
	 * @return whether or not saving was successful.
	 */
	public static boolean pushActionRecord(ActionRecord actionRecord){
		String id = Long.toString(getStartTime());
		String form = String.format("%06d", actionRecord.getTimeSinceLevelStart());
		
		makeRecordingDir();
		setupDirectory(id);
		File actionRecordSave = new File(RECORD_DIRECTORY+"\\"+id+"\\"+form+".json");
		try {
			JsonWriter jsonWriter = Json.createWriter(new PrintStream(actionRecordSave));
			jsonWriter.write(actionRecord.toJSON());
			jsonWriter.close();
			return true;
		}
		catch (FileNotFoundException e) {
			return false;
		}
	}


	/**
	 * Create the RECORD_DIRECTORY if it does not exist.
	 * @return true if directory exists after running on this function.
	 */
	public static boolean makeRecordingDir(){
		File directory = new File(RECORD_DIRECTORY);
		if (!directory.exists()){
			return directory.mkdir();
		}
		return true;
	}

	/**
	 * Create the directory in which all the individual JSON files will be
	 * stored inside the recordings directory.
	 * @param id the names of the directory, also the ID for the replay.
	 * @return true if successfully created.
	 */
	public static boolean setupDirectory(String id){
		File directory = new File(RECORD_DIRECTORY+"\\"+id);
		if (!directory.exists()){
			return directory.mkdir();
		}
		return true;
	}
	
	/**
	 * Rounds the time to ten, so that the game board doesn't need
	 * to update randomly, it rounds the time of user inputs to the nearest
	 * 10 so it can update less and uniformly.
	 * 
	 * @param time The time we are trying to round.
	 * @return The rounded time.
	 */
	public static int roundTimeToTen(int time) {
		return ((time+5)/10)*10;
	}
	
	/**
	 * Gets the start time of the replaying.
	 * 
	 * @return startTime The time.
	 */
	public static long getStartTime() {
		return startTime;
	}

	/** 
	 * Sets the start time of the replaying.
	 * 
	 * @param start The new start time.
	 */
	public static void setStartTime(long start) {
		startTime = start;
	}
	
	/**
	 * The amount of action records used for the GUI.
	 * 
	 * @return Returns the size of the list.
	 */
	public static int replaySize() {
		return replay.size();
	}
	
	/**
	 * Gets the action record at the current index.
	 * 
	 * @param index The index, based on the timer.
	 * @return Returns the associated ActionRecord.
	 */
	public static ActionRecord getActionRecord(int index) {
		return replay.get(index);
	}

	/**
	 * Sets the time in which the level was paused
	 */
	public static void setPause() {
		pauseTime = System.currentTimeMillis();
	}
	
	/**
	 * Updates the difference value between when the game was paused
	 */
	public static void updateDifference() {
		difference += (System.currentTimeMillis() - pauseTime);
	}
	
	/**
	 * Gets the difference calculation for the level
	 * @return
	 * 		the difference as a long (which can be converted to int)
	 */
	public static long getDifference() {
		return difference;
	}
	
	/**
	 * Resets all of the variables for a new level upon startup
	 */
	public static void reset() {
    	if (replay != null) replay.clear();
    	setStartTime(System.currentTimeMillis());
		pauseTime = 0;
		difference = 0;
	}
	
	/**
	 * Delete the replay directory associated with the ID, including it's contents.
	 * @param id the id on the replay to delete.
	 * @return boolean stating whether or not directory was deleted.
	 */
	public static boolean deleteReplay(long id){
		String path = RECORD_DIRECTORY+"\\"+id;

		File dir = new File(path);

		File[] listFiles = dir.listFiles();
		if (listFiles != null){
			for (File f : listFiles){
				f.delete();
			}
		}
		return dir.delete();
	}

}
