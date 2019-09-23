package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.json.Json;
import javax.json.JsonObject;

public class KeyDoor extends Door {

	private final BasicColor color;
	
	/**
	 * Creates a new door object, corresponding to a certain key
	 * colour. By default, every new door should be locked.
	 *
	 * @param color
	 * 		Color of the door, using BasicColor enum
	 */
	public KeyDoor(BasicColor color) {
		super();
		this.color = color;
	}
	
	/**
	 * Gets the BasicColour of the door.
	 * @return
	 * 		Door colour.
	 */
	public BasicColor getColor() {
		return color;
	}
	
	/**
	 * Checks a keyColor to see if it matches the door.
	 * @param key
	 * 		Color of the key, using the BasicColor enum
	 * @return
	 * 		Whether or not the player can unlock this door.
	 */
	public boolean isRightKey(Key key) {
		return key.getColor() == color;
	}
	
	@Override
	public boolean onTouch(Player player) {

		if (player == null) return false;
		
		Key key = null;
		for (Entity e : player.getInventory()) {
			if (e instanceof Key) {
				Key temp = (Key) e;
				if (isRightKey(temp)) {
					unlock();
					key = temp;
					break;
				}
			}
		}
		
		if (key != null) {
			return true;
		}
		
		return false;
		
	}

	/**
	 * Creates a Json representation of this KeyDoor.
	 * @return Json object representation of this KeyDoor.
	 */
	@Override
	public JsonObject toJSON() {
		JsonObject value = Json.createObjectBuilder()
				.add("EntityClass", KeyDoor.class.getSimpleName())
				.add("locked", isLocked())
				.add("BasicColor", color.toString())
				.build();
		return value;
	}
}
