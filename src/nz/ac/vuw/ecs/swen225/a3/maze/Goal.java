package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * The goal class, used to trigger when a level is completed
 * Upon touch, sets the completed status to true, which can
 * be used to determine when to switch a level to the next
 * or call any even (display end screen) upon level completion.
 * 
 * @author Ethan Munn
 *
 */
public class Goal extends Entity {
	
	private boolean completed;
	
	public Goal(int row, int col, Tile tileOn) {
		super(row, col, tileOn);
		completed = false;
	}

	/**
	 * No public setter, we only want to change this from false to true.
	 */
	@Override
	public void onTouch(Entity player) {
		//if (Treasure.getTotalCollected() == Level.getTreasuresNeeded())
		completed = true;
	}

	/**
	 * Returns whether or not the end goal status has been set to
	 * completed.
	 * @return
	 * 		true/false depending on whether this entity has been
	 * 		stepped on or not
	 */
	public boolean isCompleted() {
		return completed;
	}

}