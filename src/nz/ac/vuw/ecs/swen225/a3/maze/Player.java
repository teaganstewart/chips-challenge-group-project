package nz.ac.vuw.ecs.swen225.a3.maze;

import java.util.*;

public class Player extends Moveable {

	private List<Entity> inventory;

	public Player(Tile tile) {
		super(tile);
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
	 * @param aInventory 
	 * 		The item that needs to be added.
	 * @return 
	 * 		Returns a boolean saying whether the adding was successful.
	 */
	public boolean addToInventory(Entity aInventory)
	{
		if(inventory.size() < 8) {
			inventory.add(aInventory);
			return true;
		}
		
		return false;

	}
	
	/**
	 * 
	 * @param index
	 * 		The index that the entity that you want is at.
	 * @return
	 * 		Returns the entity at the given index.
	 */
	public Entity getInventoryAt(int index) {
		if(index<inventory.size()) {
			return inventory.get(index);
		}
		return null;
	}
	
	/**
	 * 
	 * Remove the entity at the given index.
	 * 
	 * @param index
	 * 		The index that the entity that you want to remove is at.	
	 */
	public boolean removeInventoryAt(int index) {
		if(index<inventory.size()) {
			inventory.remove(index);
			return true;
		}
		
		return false;
	}


	@Override
	public void onTouch(Entity e) {
		e.onTouch(this);
	}


}
