package nz.ac.vuw.ecs.swen225.a3.maze;

import nz.ac.vuw.ecs.swen225.a3.persistence.Saveable;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 * A class used to set up the base board for the game, the game is made up of
 * tiles with entities placed on top. Also stores what type those tiles are.
 * 
 * @author Ethan Munn
 * 
 */
public class Tile implements Saveable {

	/**
	 * The type for the tile, used to check whether can be walked in and
	 * what image to render, easier then having separate classes and less code.
	 * 
	 * @author Ethan Munn
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
	 * Constructs a new Tile object.
	 * 
	 * @param coord The coordinate of the tile on the board.
	 * @param type The type of the Tile.
	 */
	public Tile(Coordinate coord, TileType type) {
		this.coord = coord;
		this.type = type;
	}

	/* Getters and Setters */

	/**
	 * The coordinate of the tile on the board.
	 * 
	 * @return coord Returns the coordinate.
	 */
	public Coordinate getCoordinate() {
		return this.coord;
	}

	/**
	 * A simple getter for the Entity on this tile, if it exists.
	 * 
	 * @return entity Returns the entity on the tile.
	 */
	public Entity getEntity() {
		return entity;
	}

	/**
	 * Used to add an entity to a tile, replaces the old one if there is one.
	 * 
	 * @param entity Return the entity to add to the tile.
	 */
	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	/**
	 * The type of the Tile.
	 * 
	 * @return type Returns the type of the tile.
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

}
