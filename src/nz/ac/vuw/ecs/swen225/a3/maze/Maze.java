package nz.ac.vuw.ecs.swen225.a3.maze;

import nz.ac.vuw.ecs.swen225.a3.persistence.Saveable;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

public class Maze implements Saveable {
    private Tile[][] tiles;
    private Player player;

    public Maze(Tile[][] tiles, Player player){
        this.tiles = tiles;
        this.player = player;
    }

    // NEEDS TO BE MOVED INTO APPLICATION

    /**
     * Returns true if player was moved in the specified direction,
     * else returns false if player was not moved. This method also automatically collects items
     */
    public boolean movePlayer(Direction dir){
        // Set the players direction, regardless of whether they will actually move
        player.setDirection(dir);

        // Check for player moving out of bounds
        Coordinate dest = player.getNextPos();
        int rowDest = dest.getRow();
        int colDest = dest.getCol();
        if((rowDest < 0) || (rowDest > tiles.length) || (colDest < 0) || (colDest > tiles[0].length)){
            return false;
        }

        Tile endTile = tiles[rowDest][colDest];

        // Check if entity can be collected/walked on. If so, collect and move player. "canWalkOn"
        // automatically collects item if this is possible
        if(canWalkOn(player, endTile)){

            // Move player onto destination tile
            player.setRow(endTile.getCoordinate().getRow());
            player.setCol(endTile.getCoordinate().getCol());
            return true;
        }

        return false;
    }

    /**
     * Returns entity collected, else returns null for no entity collected
     * */
    public boolean canWalkOn(Player player, Tile tile){
        Entity entity = tile.getEntity();

        if (entity == null) return true;
        else return player.canWalkOn(entity);

    }

    @Override
    public String toJSON() {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for (int row = 0; row < tiles[0].length; row++){
            for (int col = 0; col < tiles.length; col++){
                arrayBuilder.add(tiles[row][col].toJSON());
            }
        }

        JsonObject jsonObject = Json.createObjectBuilder()
                .add("player", player.toJSON())
                .add("tiles", arrayBuilder)
                .build();
        return jsonObject.toString();
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

}
