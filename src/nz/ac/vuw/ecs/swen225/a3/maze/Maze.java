package nz.ac.vuw.ecs.swen225.a3.maze;


import nz.ac.vuw.ecs.swen225.a3.application.Game;
import nz.ac.vuw.ecs.swen225.a3.persistence.LoadUtils;
import nz.ac.vuw.ecs.swen225.a3.persistence.Saveable;
import nz.ac.vuw.ecs.swen225.a3.render.Render;

import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
/**
 * @author Josh
 *
 * Class which holds all information about the maze and its tiles, entities, player, ect.
 * Also has logic to determine what is allowed to happen with the objects in the maze
 *
 */

public class Maze implements Saveable {

    private Tile[][] tiles;
    private Player player;
    private Game game;
    private boolean goalReached;
    private String hintMessage = "";

    /**
     * @param tiles
     * @param player
     *
     * Constructor for a maze.
     */
    public Maze(Tile[][] tiles, Player player){
        this.tiles = tiles;
        this.player = player;
        this.goalReached = false;
    }


    /**
     * Returns true if player was moved in the specified direction,
     * else returns false if player was not moved. This method also automatically collects items
     * @param dir
     * @return validity of move
     */
    public boolean movePlayer(Direction dir){
        // Set the players direction, regardless of whether they will actually move
        player.setDirection(dir);



        // Check for player moving out of bounds
        Coordinate dest = player.getNextPos();
        int rowDest = dest.getRow();
        int colDest = dest.getCol();
        if((rowDest < 0) || (rowDest >= tiles.length) || (colDest < 0) || (colDest >= tiles[0].length)){
            return false;
        }

        Tile endTile = tiles[rowDest][colDest];

        // Check if entity can be collected/walked on. If so, collect and move player. "canWalkOn"
        // automatically collects item if this is possible
        if(canWalkOn(player, endTile)){
        	endTile.setEntity(null);
        	// get the destination coordinates
        	
        	player.setCoordinate(dest);

            return true;
        }

        return false;
    }

    /**
     * Returns entity collected, else returns null for no entity collected
     * @param player
     * @param tile
     * @return validity
     * */
    public boolean canWalkOn(Player player, Tile tile){
        // checks the type, first and foremost. if it can't be walked on, returns false here
        if(!checkType(player,tile)) return false;
        // then checks for the entity type
        else return player.canWalkOn(tile.getEntity());
    }

    /**
     * Checks the tiletype first of all, and sees whether or not the tile should do
     * something unique if the player steps on this.
     * @param player
     * @param tile
     * @return validity
     */
    public boolean checkType(Player player, Tile tile) {
    	Tile.TileType type = tile.getType();

    	// can't walk on walls
    	if (type == Tile.TileType.WALL) return false;

    	//-- ALL TRUE CASES, regardless --//

    	// if it's a goal, set goalReached to true 
    	if (type == Tile.TileType.GOAL) {
    		goalReached = true;  
    	}
    	// if it's a hint tile, then set the hint message. otherwise, ensure it's blank
    	// this can be referenced in the render class
    	if (type == Tile.TileType.HINT) {
    		HintTile hint = (HintTile) tile;
    		hintMessage = hint.getMessage();
    	}
    	else hintMessage = "";

    	return true;
    }

    /**
     * Returns whether or not the goal has been reached
     * @return
     * 		whether or not the player has touched the goal tile
     */
    public boolean isGoalReached() {
    	return goalReached;
    }

    /**
     * Returns the hintMessage, regardless if it is blank or not
     * @return
     * 		"", or an actual hint
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
                .build();
        return build;
    }


    /* Getters and Setters */

    /**
     *
     * @return
     * 		Returns the list of tiles for uses in other classes.
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
     * @return
     *       The player in this maze.
     */
    public Player getPlayer() {
        return player;
    }
    
    public void setGame(Game g) {
    	game = g;
    }
    
     

}
