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
 * @author Ethan Munn, Matt Rothwell
 */
public class ReplayUtils {

	public static final String RECORD_DIRECTORY = "recordings";
	public static long startTime = 0;
	private static List<ActionRecord> replay;
	
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
	
	public static int roundTimeToTen(int time) {
		return ((time+5)/10)*10;
	}
	
	
	public static long getStartTime() {
		return startTime;
	}

	public static void setStartTime(long start) {
		startTime = start;
	}
	
	public static int replaySize() {
		return replay.size();
	}
	
	public static ActionRecord getActionRecord(int index) {
		return replay.get(index);
	}


	/**
	 * Delete the replay directory associated with the ID, including it's contents.
	 * @param id the id on the replay to delete.
	 * @return boolean stating whether or not directory was deleted.
	 */
	public static boolean deleteReplay(long id){
		String path = RECORD_DIRECTORY+"\\"+id;

		File dir = new File(path);
		System.out.println(id);
		File[] listFiles = dir.listFiles();
		if (listFiles != null){
			for (File f : listFiles){

				f.delete();
			}
		}
		return dir.delete();
	}

}
