package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * The Entity abstract class, which can be extended by any class if the purpose of it
 * is to be featured in the maze. This includes the Player, any Treasures, Keys, Hints,
 * or any other objects we wish to create. By default, this will allow every entity to
 * store its own row/col position, as well as what happens upon the player interacting
 * with this Entity.
 * 
 * @author Ethan Munn
 *
 */
public abstract class Entity {
		
	private Coordinate coord;
	
	/**
	 * The default super constructor. All entities must use this.
	 * @param row
	 * 		Row position.
	 * @param col
	 * 		Col position.
	 */
	public Entity(int row, int col) {
		setCoordinate(row,col);
	}
	
	/**
	 * Gets the row this entity is in.
	 * @return
	 * 		the row
	 */
	public int getRow() {
		return coord.getRow();
	}
	
	/**
	 * Gets the column this entity is in.
	 * @return
	 * 		the column
	 */
	public int getCol() {
		return coord.getCol();
	}
	
	/**
	 * Sets the row and col as a Coordinate object, for more compact storage.
	 * @param row
	 * 		the row
	 * @param col
	 * 		the column
	 */
	public void setCoordinate(int row, int col) {
		coord = new Coordinate(row,col);
	}
	
	/**
	 * If the entire coordinate is desired, one can also return this too as the
	 * grouping of the row and col
	 * @return
	 * 		the coordinate of this entity
	 */
	public Coordinate getCoordinate() {
		return coord;
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
