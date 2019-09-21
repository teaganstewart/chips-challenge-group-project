package nz.ac.vuw.ecs.swen225.a3.maze;

public class TreasureDoor extends Door {
	
	/**
	 * Creates a new door object, corresponding to a certain key
	 * colour. By default, every new door should be locked.
	 *
	 * @param color
	 * 		Color of the door, using BasicColor enum
	 */
	public TreasureDoor() {
	}	

	@Override
	public boolean onTouch(Entity pl) {

		// safety check that the player has touched the door
		if (!(pl instanceof Player)) return false;
		
		if (Treasure.allCollected()) {
			unlock();
			return true;
		}
		
		return false;
		
	}
	
	
}
