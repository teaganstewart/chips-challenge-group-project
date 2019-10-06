package nz.ac.vuw.ecs.swen225.a3.maze;

import nz.ac.vuw.ecs.swen225.a3.persistence.Saveable;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 * @author Ethan
 * 
 *         Represents a tile of the maze
 *
 */
public class Tile implements Saveable {

	/**
	 * @author Ethan
	 *
	 */
	public enum TileType {

		/**
		 * Floor
		 */
		FLOOR, 
		/**
		 * Wall
		 */
		WALL, 
		/**
		 * Hint
		 */
		HINT, 
		/**
		 * Goal
		 */
		GOAL, 
		/**
		 * Fire
		 */
		FIRE, 
		/**
		 * Ice
		 */
		ICE

	}

	private final TileType type;
	private Coordinate coord;
	private Entity entity;

	/**
	 * Constructs a new Tile object
	 * 
	 * @param coord
	 * @param type
	 */
	public Tile(Coordinate coord, TileType type) {
		this.coord = coord;
		this.type = type;
	}

	/* Getters and Setters */

	/**
	 * @return coord
	 */
	public Coordinate getCoordinate() {
		return this.coord;
	}

	/**
	 * A simple getter for the Entity on this tile, if it exists.
	 * 
	 * @return The entity
	 */
	public Entity getEntity() {
		return entity;
	}

	/**
	 * Sets the entity on this tile to an object, or removes an object if one
	 * already existed here.
	 * 
	 * @param entity
	 */
	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	/**
	 * Return the type of tile this is
	 * 
	 * @return the TileType
	 */
	public TileType getType() {
		return type;
	}


	/**
	 * Produce a JSON derived string so that this Tile can be reloaded.
	 * 
	 * @return String in JSON format describing this tile.
	 */
	@Override
	public JsonObject toJSON() {
		JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
		jsonObjectBuilder.add("TileType", type.toString());
		jsonObjectBuilder.add("Coordinate", coord.toJSON());

		if (entity != null) {
			jsonObjectBuilder.add("Entity", entity.toJSON());
		}

		return jsonObjectBuilder.build();
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
