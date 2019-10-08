package nz.ac.vuw.ecs.swen225.a3.maze;

import nz.ac.vuw.ecs.swen225.a3.application.Game;
import nz.ac.vuw.ecs.swen225.a3.persistence.LoadUtils;
import nz.ac.vuw.ecs.swen225.a3.persistence.Saveable;
import nz.ac.vuw.ecs.swen225.a3.render.Render;

import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

/**
 * @author Josh
 *
 *         Class which holds all information about the maze and its tiles,
 *         entities, player, ect. Also has logic to determine what is allowed to
 *         happen with the objects in the maze
 *
 */

public class Maze implements Saveable {

	private Tile[][] tiles;
	private Player player;
	private Game game;
	private boolean goalReached;
	private String hintMessage = "";
	private boolean onHint;
	// Check whether the level needs to be reset. This could be when the player dies, e.g. by
	// walking on a fire block
	private boolean resetLevel = false;
	private List<Crate> crateList;
	private List<Moveable> enemyList;

	/**
	 * @param tiles
	 * @param player
	 *
	 *               Constructor for a maze.
	 */
	public Maze(Tile[][] tiles, Player player, List<Crate> crateList, List<Moveable> enemyList) {
		this.tiles = tiles;
		this.player = player;
		this.goalReached = false;
		// TODO implement proper crate list so that this safeguard can be removed
		if(crateList != null){
            this.crateList = crateList;
        }else{
		    this.crateList = new ArrayList<Crate>();
        }
		
		if(crateList != null){
            this.enemyList = enemyList;
        }else{
		    this.enemyList = new ArrayList<Moveable>();
        }
		this.onHint = false;
	}

	/**
	 * Returns true if player was moved in the specified direction, else returns
	 * false if player was not moved. This method also automatically collects items
	 * 
	 * @param dir
	 * @return validity of move
	 */
	public boolean movePlayer(Direction dir) {
		// Set the players direction, regardless of whether they will actually move
		player.setDirection(dir);
		onHint = false;
		// Check for player moving out of bounds
		Coordinate dest = player.getNextPos();
		int rowDest = dest.getRow();
		int colDest = dest.getCol();
		if ((rowDest < 0) || (rowDest >= tiles.length) || (colDest < 0) || (colDest >= tiles[0].length)) {
			return false;
		}

		Tile endTile = tiles[rowDest][colDest];

		// Handle special case with sliding
		if (endTile.getType() == Tile.TileType.ICE) {
			if (player.isInInventory(new IceBoots())) {
				player.setCoordinate(endTile.getCoordinate());
				return true;
			} else {
				// Slide the player until there are no ice blocks left
				slidePlayer();
				return true;
			}
		}

		// Handle special case with pushing a crate
		for(Crate crate : crateList){
			if(crate.getCoordinate().equals(endTile.getCoordinate())){
				return pushCrate(crate);
			}
		}

		// Check if entity can be collected/walked on. If so, collect and move player.
		// "canWalkOn"
		// automatically collects item if this is possible
		if (canWalkOn(player, endTile)) {

			endTile.setEntity(null);

			player.setCoordinate(dest);

			return true;
		}

		return false;
	}

	/**
	 * Returns entity collected, else returns null for no entity collected
	 * 
	 * @param player
	 * @param tile
	 * @return validity
	 */
	public boolean canWalkOn(Player player, Tile tile) {
		// checks the type, first and foremost. if it can't be walked on, returns false
		// here
		if (!checkType(player, tile))
			return false;
		// then checks for the entity type
		else
			return player.canWalkOn(tile.getEntity());
	}

	/**
	 * Checks the tiletype first of all, and sees whether or not the tile should do
	 * something unique if the player steps on this.
	 * 
	 * @param player
	 * @param tile
	 * @return validity
	 */
	public boolean checkType(Player player, Tile tile) {
		
		Tile.TileType type = tile.getType();

		// can't walk on walls
		if (type == Tile.TileType.WALL)
			return false;

		if (type == Tile.TileType.FIRE) {
			if (player.isInInventory(new FireBoots())) {
				return true;
			} else {
				// Player has died. Restart the level
				resetLevel = true;
				return false;
			}
		}

		// -- ALL TRUE CASES, regardless --//

		// if it's a goal, set goalReached to true
		if (type == Tile.TileType.GOAL) {
			goalReached = true;
		}
		// if it's a hint tile, then set the hint message. otherwise, ensure it's blank
		// this can be referenced in the render class
		if (type == Tile.TileType.HINT) {
			onHint = true;
			HintTile hint = (HintTile) tile;
			hintMessage = hint.getMessage();
		} else
			hintMessage = "";

		return true;
	}

	/**
	 * Method which keeps moving the Player in the same direction until there are no more
	 * Ice tiles in that direction
	 */
	private void slidePlayer(){
		Tile destTile = tiles[player.getNextPos().getRow()][player.getNextPos().getCol()];
		while(destTile.getType() == Tile.TileType.ICE){
			player.setCoordinate(destTile.getCoordinate());
			// Update destTile
			destTile = tiles[player.getNextPos().getRow()][player.getNextPos().getCol()];
		}
		if(checkType(player, destTile)){
			movePlayer(player.getDirection());
		}
	}

	/*
	 * Attempts to push the crate in the players direction. Returns true for
	 *  a successful push, false for no push
	 * */
	private boolean pushCrate(Crate crate){
		// Check space in front of crate
		crate.setDirection(player.getDirection());
		Coordinate crateDest = crate.getNextPos();
		// Make sure crate is not being pushed off the edge of the map
		Tile crateDestTile;
		try{
			crateDestTile = tiles[crateDest.getRow()][crateDest.getCol()];
		}
		catch (IndexOutOfBoundsException e){
			return false;
		}
		if((crateDestTile != null) && (crateDestTile.getType() == Tile.TileType.FLOOR)
				&& (crateDestTile.getEntity() == null)){
			// Check there are no other crates on the destination tile
			for(Crate crateVar : crateList){
				// Skip over current crate
				if(crateVar.equals(crate)) continue;
				if(crateVar.getCoordinate().equals(crateDestTile.getCoordinate())) return false;
			}
			// Move crate
			crate.setCoordinate(crateDest);
			// Move Player
			player.setCoordinate(player.getNextPos());
			return true;
		}
		return false;
	}

	/**
	 * Returns whether or not the goal has been reached
	 * 
	 * @return whether or not the player has touched the goal tile
	 */
	public boolean isGoalReached() {
		return goalReached;
	}

	/**
	 * Returns whether or not the goal has been reached
	 * @return
	 * 		whether or not the player has touched the goal tile
	 */
	public boolean isOnHint() {
		return onHint;
	}

	/**
	 * Returns the hintMessage, regardless if it is blank or not
	 * 
	 * @return "", or an actual hint
	 */
	public String getHintMessage() {
		return hintMessage;
	}

	/**
	 * Serialise this Java Object to Json
	 * @return Json representation of this object.
	 */
	@Override
	public JsonObject toJSON() {
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

		for (int row = 0; row < tiles.length; row++){
			for (int col = 0; col < tiles[0].length; col++){
				arrayBuilder.add(tiles[row][col].toJSON());
			}
		}

		JsonObject build = Json.createObjectBuilder()
				.add("player", player.toJSON())
				.add("rows", tiles.length)
				.add("cols", tiles[0].length)
				.add("tiles", arrayBuilder)
				.add("treasureData", Treasure.toJSONStatic())
				.add("crates", createCratesObject())
				.build();
		return build;
	}

	/* Getters and Setters */

	/**
	 * Checks whether the level should be restarted. This is useful 
	 * when a player steps on a fire block and dies
	 * 
	 * @return resetLevel
	 */
	public boolean isResetLevel() {
		return resetLevel;
	}

	/**
	 *
	 * @return Returns the list of tiles for uses in other classes.
	 */
	public Tile[][] getTiles() {
		return tiles;
	}

	/**
	 *
	 * @param tiles
	 *
	 */
	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}

	/**
	 * @return The player in this maze.
	 */
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player p) {
		player = p;
	}

	/**
	 * @param g
	 */
	public void setGame(Game g) {
		game = g;
	}
	
	public List<Crate> getCrateList() {
		return crateList;
	}
	
	public List<Moveable> getEnemyList() {
		return enemyList;
	}

	/**
	 * Form a JsonArray of all the crates inside this Maze
	 * @return serialised Json Array of crates
	 * @author Matt
	 */
	private JsonArray createCratesObject(){
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		for (Crate c : crateList){
			arrayBuilder.add(c.toJSON());
		}
		return arrayBuilder.build();
	}

}
