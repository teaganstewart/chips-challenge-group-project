package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.json.Json;
import javax.json.JsonObject;

/**
 * The key class, used to unlock doors matching the BasicColor of this object.
 * 
 * @author Ethan Munn
 *
 */
public class Key implements Entity {

  final BasicColor color;

  /**
   * Sets up a brand new key.
   * 
   * @param color The color of this key.
   */
  public Key(BasicColor color) {
    this.color = color;
  }

  /**
   * Returns the color of this key
   * 
   * @return The color.
   */
  public BasicColor getColor() {
    return color;
  }

  /**
   * Produce a JSON representation of this Key
   * 
   * @return Json object identical to this key
   */
  @Override
  public JsonObject toJSON() {
    JsonObject value = Json.createObjectBuilder().add("EntityClass", Key.class.getSimpleName())
        .add("BasicColor", color.toString()).build();
    return value;
  }
}
