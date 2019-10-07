package nz.ac.vuw.ecs.swen225.a3.recnplay;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;
import nz.ac.vuw.ecs.swen225.a3.maze.Direction;
import nz.ac.vuw.ecs.swen225.a3.persistence.LoadUtils;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;

public class ReplayUtils {

	public static final String RECORD_DIRECTORY = "recordings";

	public static Map<Long, ActionRecord> loadReplay(String id){

		HashMap<Long, ActionRecord> recordHashMap = new HashMap<>();

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
						recordHashMap.put(actionRecord.getTimeSinceLevelStart(), actionRecord);
					}
				}
			}
		}

		return recordHashMap;
	}

	public static boolean pushActionRecord(ActionRecord actionRecord, String id){
		setupDirectory(id);
		File actionRecordSave = new File(RECORD_DIRECTORY+"\\"+id+"\\"+actionRecord.getTimeSinceLevelStart()+".json");
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

	public static boolean setupDirectory(String id){
		File directory = new File(RECORD_DIRECTORY+"\\"+id);
		if (!directory.exists()){
			return directory.mkdir();
		}
		return true;
	}



	
	
}
