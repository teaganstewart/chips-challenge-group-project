package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.json.Json;
import javax.json.JsonObject;

/**
 * @author Ethan
 * 
 *         Represents a Door which is locked until the player collects all the
 *         treasure
 *
 */
public class TreasureDoor extends Door {

  /**
   * Creates a new door object, corresponding to a certain key colour. By default,
   * every new door should be locked.
   *
   */
  public TreasureDoor() {
  }

  @Override
  public boolean onTouch(Player pl) {

    // safety check that the player has touched the door
    if (pl == null)
      return false;

    if (Treasure.allCollected()) {
      unlock();
      return true;
    }

    return false;

  }

  /**
   * Produce a JSON object for this TreasureDoor
   * 
   * @return Json object representing this TreasureDoor
   */
  @Override
  public JsonObject toJSON() {
    JsonObject value = Json.createObjectBuilder().add("EntityClass", TreasureDoor.class.getSimpleName())
        .add("locked", isLocked()).build();
    return value;
  }
}
