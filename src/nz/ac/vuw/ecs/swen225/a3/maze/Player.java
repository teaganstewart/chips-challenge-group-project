package nz.ac.vuw.ecs.swen225.a3.maze;

import nz.ac.vuw.ecs.swen225.a3.persistence.Saveable;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.*;

/**
 * @author Teagan
 * 
 *         Represents a Player
 *
 */
public class Player extends Moveable implements Saveable {

  private List<Entity> inventory;

  /**
   * @param coordinate
   */
  public Player(Coordinate coordinate) {
    super(coordinate);
    inventory = new ArrayList<Entity>();
  }

  /**
   * Sets the players inventory. More useful for tests.
   */
  public void setInventory(List<Entity> inv) {
    inventory = inv;
  }

  /**
   * @return Returns the players hand.
   */
  public List<Entity> getInventory() {
    return Collections.unmodifiableList(inventory);
  }

  /**
   * 
   * @param aInventory The item that needs to be added.
   * @return Returns a boolean saying whether the adding was successful.
   */
  public boolean addToInventory(Entity aInventory) {
    if (inventory.size() < 8) {
      inventory.add(aInventory);
      return true;
    }

    return false;

  }

  /**
   * 
   * @param index The index that the entity that you want is at.
   * @return Returns the entity at the given index.
   */
  public Entity getInventoryAt(int index) {
    if (index < inventory.size()) {
      return inventory.get(index);
    }
    return null;
  }

  /**
   * 
   * Remove the entity at the given index.
   * 
   * @param index The index that the entity that you want to remove is at.
   * @return validity
   */
  public boolean removeInventoryAt(int index) {
    if (index < inventory.size()) {
      inventory.remove(index);
      return true;
    }

    return false;
  }

  /**
   *
   * Check if the inventory contains a certain type of Entity. This additional method
   * is needed when the player is about to walk on a particular tile which requires
   * the player to have a particular type of entity in their inventory (e.g. Fire tile
   * requires FireBoots)
   *
   * @return validity
   */
  public boolean isInInventory(Entity queryEntity){
    for(Entity entity : inventory){
      if(entity.getClass().equals(queryEntity.getClass())){
        return true;
      }
    }
    return false;
  }

  /**
   * Returns whether or not the player can walk on this entity
   * 
   * @param entity the entity checked against
   * @return whether it can or can't overwrite the entity
   */
  @Override
  public boolean canWalkOn(Entity entity) {

    // null
    if (entity == null)
      return true;
    // key
    if (entity instanceof Key) {
      Key key = (Key) entity;
      addToInventory(key);
      return true;
    }
    // treasure
    else if (entity instanceof Treasure) {
      Treasure.incrTreasuresCollected();
      return true;
    }
    // any door
    else if (entity instanceof Door) {
      Door door = (Door) entity;
      return door.onTouch(this);
    }
    // Fire boots
    else if(entity instanceof FireBoots){
      addToInventory(entity);
      return true;
    }

    // ...

    // none of the above
    return false;
  }

  /**
   * Generate a Json representation of this Player
   * 
   * @return Json object player
   */
  @Override
  public JsonObject toJSON() {
    JsonArrayBuilder inventoryJson = Json.createArrayBuilder();
    for (Entity entity : inventory) {
      inventoryJson.add(entity.toJSON());
    }

    JsonObject value = Json.createObjectBuilder().add("Coordinate", getCoordinate().toJSON())
        .add("Inventory", inventoryJson).build();
    return value;
  }
}
