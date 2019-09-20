package nz.ac.vuw.ecs.swen225.a3.maze;

public class Tile {

	private final TileType type;
	private Coordinate coord;
	private Entity entity;
	//private Player player;
	
	/**
	 * Constructs a new Tile object
	 */
	public Tile(Coordinate coord, TileType type) {
		this.coord = coord;
		this.type = type;
	}

	/* Getters and Setters*/
	
	public void setCoordinate(Coordinate coord){
		this.coord = coord;
	}

	public Coordinate getCoordinate(){
		return this.coord;
	}
	
	/**
	 * A simple getter for the Entity on this tile, if it exists.
	 * @return
	 * 		The entity
	 */
	public Entity getEntity() {
		return entity;
	}
	
	/**
	 * Sets the entity on this tile to an object, or removes an object
	 * if one already existed here.
	 * @param entity
	 */
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
	/**
	 * Return the type of tile this is
	 * @return
	 * 		the TileType
	 */
	public TileType getType() {
		return type;
	}
	
	/**
	 * Returns whether or not this tile is a wall. Prevents the player from moving across it,
	 * if this is the case
	 * @return
	 */
	public boolean isWall() {
		return type == TileType.WALL;
	}

//	/**
//	 * Returns the player, if it exists on this tile
//	 * @return
//	 * 		the player, if they are on this tile
//	 */
//	public Player getPlayer() {
//		return player;
//	}
//
//	/**
//	 * Has storage for the Player and an Entity, as for some entities (like the hint),
//	 * the player is stored on top of these and doesn't remove it from the maze. By
//	 * default, this player variable is null, as having the player term inside of the
//	 *  constructor to only ever be used once per load is extraneous and messy.
//	 *  @param player
//	 *  	Typically null, will sometimes have the player on it
//	 */
//	public void setPlayer(Player player) {
//		this.player = player;
//	}

}
