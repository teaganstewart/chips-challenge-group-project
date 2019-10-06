package nz.ac.vuw.ecs.swen225.a3.recnplay;

import java.util.*;
import nz.ac.vuw.ecs.swen225.a3.maze.Direction;

public class Replay {

	private static long levelStartTime;
	private List<ActionRecord> events;
	private int index;
	
	public Replay() {
		events = new ArrayList<>();
		setStartTime();
		setIndex(0);
	}
	
	public void setIndex(int i) {
		index = i;
	}

	public void addEvent(ActionRecord aR) {
		events.add(aR);
	}
	
	public Direction getMove(int time) {
		if (index < events.size() && time == events.get(index).getTime()) {
			Direction dir = events.get(index).getMove();
			setIndex(index+1);
			return dir;
		}
		return null;
	}
	
	private void setStartTime() {
		levelStartTime = System.currentTimeMillis();
	}
	
	public static Long getStartTime() {
		return levelStartTime;
	}
	
	
}
