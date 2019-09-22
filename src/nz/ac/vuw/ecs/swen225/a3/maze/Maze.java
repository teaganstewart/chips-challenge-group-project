package nz.ac.vuw.ecs.swen225.a3.maze;

/**
 * @author Josh
 * 
 * Class which holds all information about the maze and its tiles, entities, player, ect.
 * Also has logic to determine what is allowed to happen with the objects in the maze
 *
 */
public class Maze {
    private Tile[][] tiles;
    private Player player;

    /**
     * @param tiles
     * @param player
     * 
     * Constructor for a maze. 
     */
    public Maze(Tile[][] tiles, Player player){
        this.tiles = tiles;
        this.player = player;
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
     * @param player 
     * @param tile 
     * @return validity
     * */
    public boolean canWalkOn(Player player, Tile tile){
        Entity entity = tile.getEntity();    
        
        if (entity == null) return true;
        else return player.canWalkOn(entity);
 
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
