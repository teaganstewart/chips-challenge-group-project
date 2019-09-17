package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * The Entity abstract class, which can be extended by any class if the purpose of it
 * is to be featured in the maze. This includes the Player, any Treasures, Keys, Hints,
 * or any other objects we wish to create. By default, this will allow every entity to
 * store its own row/col position in a Coordinate, as well as what happens upon the
 * player interacting with this Entity.
 * 
 * @author Ethan Munn
 *
 */
public abstract class Entity {
		
	private Tile tileOn;
	private Coordinate coord;
	
	/**
	 * The default super constructor. All entities must use this.
	 * @param row
	 * 		Row position.
	 * @param col
	 * 		Col position.
	 */
	public Entity(int row, int col, Tile tileOn) {
		setCoord(row,col);
		setTileOn(tileOn);
	}
	
	/**
	 * Gets the coordinate this entity is at.
	 * @return
	 * 		the coordinate
	 */
	public Coordinate getCoord() {
		return coord;
	}

	/**
	 * Sets the coordinate to a new Coordinate at this row/col position.
	 * Saves the step of having to make the new Coordinate object if the
	 * user doesn't want to.
	 * 
	 * @param row
	 * 		the row
	 * @param col
	 * 		the col
	 */
	public void setCoord(int row, int col) {
		setCoord(new Coordinate(row,col));
	}
	
	/**
	 * Alternate setting of coord where the user just makes their own
	 * new Coordinate. Can just use the row/col version as well.
	 * @param coord
	 * 		the new Coordinate passed over
	 */
	public void setCoord(Coordinate coord) {
		this.coord = coord;
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
