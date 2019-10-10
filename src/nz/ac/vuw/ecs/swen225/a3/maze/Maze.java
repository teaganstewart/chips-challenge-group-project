package nz.ac.vuw.ecs.swen225.a3.maze;

import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import nz.ac.vuw.ecs.swen225.a3.persistence.Saveable;

/**
 *
 * Class which holds all information about the maze and its tiles,
 * entities, player, etc. Also has logic to determine what is allowed to
 * happen with the objects in the maze
 *         
 * @author Josh O'Hagan
 *
 */
public class Maze implements Saveable {

	private Tile[][] tiles;
	private Player player;
	private boolean goalReached;
	private String hintMessage = "";
	private boolean onHint;
	private boolean onIce;
	// Check whether the level needs to be reset. This could be when the player dies, e.g. by
	// walking on a fire block
	private boolean resetLevel = false;
	private List<Crate> crateList;
	private List<Skeleton> enemyList;

	/**
	 * Constructs a maze object.
	 * 
	 * @param tiles The board, represented as a 2d array of tiles.
	 * @param player The player.
	 * @param crateList The list of crates.
	 * @param enemyList The list of skeleton enemies.
	 */
	public Maze(Tile[][] tiles, Player player, List<Crate> crateList, List<Skeleton> enemyList) {
		this.tiles = tiles;
		this.player = player;
		this.goalReached = false;
		this.crateList = (crateList==null) ? new ArrayList<Crate>() : crateList;
		this.enemyList = (enemyList==null) ? new ArrayList<Skeleton>() : enemyList;
		this.onHint = false;
		this.onIce = false;
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
		onIce = false;
		// Check for player moving out of bounds
		Coordinate dest = player.getNextPos();
		int rowDest = dest.getRow();
		int colDest = dest.getCol();
		if ((rowDest < 0) || (rowDest >= tiles.length) || (colDest < 0) || (colDest >= tiles[0].length)) {
			return false;
		}

		Tile endTile = tiles[rowDest][colDest];

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
	 * @param player The player to test movement of.
	 * @param tile The tile the player is moving to.
	 * @return Returns true if can move onto tile, false if not.
	 */
	public boolean canWalkOn(Player player, Tile tile) {
		// checks the type, if it can't be walked on, returns false
		if (!checkType(player, tile))
			return false;
		// then checks for enemies in the way
		if(!checkEnemy(tile)) 
			return false;
		// then checks for the entity type
		else
			return player.canWalkOn(tile.getEntity());
	}

	/**
	 * Checks the list of enemies in the maze and sees if the player is 
	 * going to walk into one.
	 * 
	 * @param tile The tile the player is moving to.
	 * @return Returns false if there is an enemy.
	 */
	private boolean checkEnemy(Tile tile) {
		Coordinate tileCoordinate = tile.getCoordinate();

		for(Skeleton s : enemyList) {
			if(s.getCoordinate().equals(tileCoordinate)) {
				resetLevel = true;
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks whether movement is possible based on the TileType.
	 * 
	 * @param player The player we are moving.
	 * @param tile The TileType of the tile the player is moving onto.
	 * @return Returns true if the tile can be walked on.
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

		// if it's on ice, set on ice to true
		if (type == Tile.TileType.ICE) onIce = true;

		// if it's a goal, set goalReached to true
		if (type == Tile.TileType.GOAL) goalReached = true;

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
	 * Attempts to push the crate in the players direction. Returns true for
	 *  a successful push, false for no push.
	 *  
	 * @param crate The crate that is being pushed.
	 * @return Returns true if crate can be pushed.
	 */
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
	 * If the goal has been reached.
	 * 
	 * @return Returns true if player is on a tile of type Goal.
	 */
	public boolean isGoalReached() {
		return goalReached;
	}

	/**
	 * If the player is on the hint tile.
	 * 
	 * @return Returns true if the player is on a hint tile.
	 */
	public boolean isOnHint() {
		return onHint;
	}

	/**
	 * If the player is on an ice tile.
	 * 
	 * @return Returns true if player is on a tile of type Ice.
	 */
	public boolean isOnIce() {
		return onIce;
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
				.add("enemies", createEnemiesObject())
				.build();
		return build;
	}

	/* Getters and Setters */

	/**
	 * Checks whether the level should be restarted. This is useful 
	 * when a player steps on a fire block and dies
	 * 
	 * @return resetLevel Returns true if the level should be restarted.
	 */
	public boolean isResetLevel() {
		return resetLevel;
	}

	/**
	 * Boolean to say whether the level needs to be reset.
	 * 
	 * @param reset True if should be reset.
	 */
	public void setResetLevel(boolean reset) {
		resetLevel = reset;
	}

	/**
	 * Returns the 2d array of tiles, representing the board.
	 * 
	 * @return Returns tiles for uses in other classes.
	 */
	public Tile[][] getTiles() {
		return tiles;
	}

	/**
	 * Sets the tiles for the board.
	 * 
	 * @param tiles The new array of tiles.
	 */
	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}

	/**
	 * Gets the player in the maze.
	 * 
	 * @return The player in this maze.
	 */
	public Player getPlayer() {
		return player;
	}

	
	/**
	 * Sets the player in this maze. 
	 * 
	 * @param p The new player.
	 */
	public void setPlayer(Player p) {
		player = p;
	}

	/**
	 * Gets the list of crates on the board.
	 * 
	 * @return Returns the list of crates.
	 */
	public List<Crate> getCrateList() {
		return crateList;
	}

	/**
	 * Sets the list of crates on the board.
	 * 
	 * @param crates What we want the list to be set to.
	 */
	public void setCrateList(List<Crate> crates) {
		crateList = crates;
	}

	/**
	 * Gets the list of enemies on the board.
	 * 
	 * @return Returns the list of enemies.
	 */
	public List<Skeleton> getEnemyList() {

		return enemyList;
	}

	/**
	 * Sets the list of enemies on the board.
	 *  
	 * @param enemies What we want the list to be set to.
	 */
	public void setEnemyList(List<Skeleton> enemies) {
		enemyList = enemies;
	}

	/**
	 * Form a JsonArray of all the crates inside this Maze.
	 * 
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

	/**
	 * Form a JsonArray of all the skeletons inside this Maze.
	 * 
	 * @return serialized Json Array of Skeletons
	 */
	private JsonArray createEnemiesObject(){
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		for (Skeleton c : enemyList){
			arrayBuilder.add(c.toJSON());
		}
		return arrayBuilder.build();
	}

}
