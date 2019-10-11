package nz.ac.vuw.ecs.swen225.a3.maze;

import nz.ac.vuw.ecs.swen225.a3.persistence.Saveable;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.*;

/**
 * Class for the player, has things essential for only a player.
 * 
 * @author Teagan Stewart - 300407769
 *
 */
public class Player extends Moveable implements Saveable {

	private List<Entity> inventory;
	private Coordinate startCoordinate;

	/**
	 * Constructs the Player Object.
	 * 
	 * @param coordinate Where the player should spawn on the board.
	 */
	public Player(Coordinate coordinate) {
		super(coordinate);
		startCoordinate = coordinate;
		inventory = new ArrayList<Entity>();
	}

	/**
	 * Sets the players inventory. More useful for tests.
	 * 
	 * @param inv The new inventory of the player.
	 */
	public void setInventory(List<Entity> inv) {
		inventory = inv;
	}

	/**
	 * Gets the inventory of the player.
	 * 
	 * @return Returns the players hand, can't be modified.
	 */
	public List<Entity> getInventory() {
		return Collections.unmodifiableList(inventory);
	}

	/**
	 * A method for adding things to inventory.
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
	 * Gets inventory item at specific place. Useful for tests.
	 * 
	 * @param index The index that the entity that you want is at.
	 * @return Returns the entity at the given index.
	 */
	public Entity getInventoryAt(int index) {
		if ((0 <= index) && (index < inventory.size())) {
			return inventory.get(index);
		}
		throw new RuntimeException("Out of bounds inventory index for getInventoryAt() method");
	}

	/**
	 * Remove the entity at the given index.
	 * 
	 * @param index The index that the entity that you want to remove is at.
	 * @return Whether the inventory item was removed properly or not.
	 */
	public boolean removeInventoryAt(int index) {
		if ((0 <= index) && (index < inventory.size())) {
			inventory.remove(index);
			return true;
		}
		throw new RuntimeException("Out of bounds inventory index for removeInventoryAt() method");
	}

	/**
	 * Check if the inventory contains a certain type of Entity. This additional
	 * method is needed when the player is about to walk on a particular tile which
	 * requires the player to have a particular type of entity in their inventory.
	 * 
	 * @param queryEntity The item we want to check if inventory contains.
	 * @return Whether it is or not.
	 */
	public boolean isInInventory(Entity queryEntity) {
		for (Entity entity : inventory) {
			if (entity.getClass().equals(queryEntity.getClass())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns where the key is in the inventory.
	 * 
	 * @param key What key you want to find.
	 * @return The index of the key
	 */
	public int getKeyIndex(Key key){
		for(int i = 0; i < inventory.size(); i++){
			if(inventory.get(i).getClass().equals(key.getClass())){
				if(((Key) inventory.get(i)).getColor() == key.getColor()){
					return i;
				}
			}
		}
		return 0;
	}

	
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
		// Fire or Ice boots
		else if (entity instanceof FireBoots || entity instanceof IceBoots) {
			addToInventory(entity);
			return true;
		}

		// ...

		// none of the above
		return false;
	}

	/**
	 * Getter for the player's start coordinate
	 * 
	 * @return startCoordinate
	 */
	public Coordinate getStartCoordinate() {
		return startCoordinate;
	}

   public String toString() {
    	return "Player: " + super.toString() + "facing " + super.getNextPos();
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
				.add("Inventory", inventoryJson)
				.add("direction", getDirection().toString()).build();
		return value;
	}
}