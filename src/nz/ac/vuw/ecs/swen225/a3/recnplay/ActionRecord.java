package nz.ac.vuw.ecs.swen225.a3.recnplay;

import nz.ac.vuw.ecs.swen225.a3.maze.*;

/**
 * Used for the recnplay feature
 * 
 * @author Ethan Munn
 *
 */
public class ActionRecord {

	private final int time;
	private final Direction move;
	
	public ActionRecord(long time, Direction move) {
		this.time = convertToMilli(time);
		this.move = move;
	}

	private int convertToMilli(long time) {
		double n = (double)(time - Replay.getStartTime());
		return (int)((n+5)/10)*10;
	}
	
	public int getTime() {
		return time;
	}

	public Direction getMove() {
		return move;
	}
	
	public String toString() {
		return Integer.toString(time) + ": " + move;
	}

	
}
