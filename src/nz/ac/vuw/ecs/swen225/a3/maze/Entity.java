package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * The Entity abstract class, which can be extended by any class if the purpose of it
 * is to be featured in the maze. This includes the Player, any Treasures, Keys, Hints,
 * or any other objects we wish to create. Also allows player interacting with this Entity.
 * 
 * @author Ethan Munn
 *
 */
public abstract class Entity {

	private Tile tileOn; // Represents the tile this entity is on
	
	/**
	 * The default super constructor. All entities must use this.
	 */
	public Entity(Tile tileOn) {
		setTileOn(tileOn);
	}
	
	/**
	 * Gets the tile this entity is on.
	 * @return
	 * 		The tile.
	 */
	public Tile getTileOn() {
		return tileOn;
	}

	/**
	 * Sets the tile this entity is on.
	 * @param tileOn
	 * 		The tile.
	 */
	public void setTileOn(Tile tileOn) {
		this.tileOn = tileOn;
	}

	/**
	 * Method called when an entity touches another entity.
	 * For players, this method is called upon movement if
	 * the tile they have stepped on contains an Entity, 
	 * so their Entity parameter is whichever Entity they are
	 * interacting with
	 */
	public abstract void onTouch(Entity e);

	/**
	 * If we ever need to actually overwrite the toString method (which we probably will for saving)
	 */
	//public abstract String toString();
	
}
