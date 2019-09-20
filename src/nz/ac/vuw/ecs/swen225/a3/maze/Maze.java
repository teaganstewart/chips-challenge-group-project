package nz.ac.vuw.ecs.swen225.a3.maze;

public class Maze {
    private Tile[][] tiles;
    Player player;

    public Maze(Tile[][] tiles){
        this.tiles = tiles;
    }
    
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

    // NEEDS TO BE MOVED INTO APPLICATION
    
    /**
     * Returns true if player was moved from start location to end location,
     * else returns false if player was not moved. This method also allows for collecting items
     */
    public boolean movePlayer(int startRow, int startCol, int endRow, int endCol){
        // Check for no entity in the start position
        Tile startTile = tiles[startRow][startCol];
        Tile endTile = tiles[endRow][endCol];
        Entity startEntity = startTile.getEntity();
        if(!(startEntity instanceof Player)) return false;
        // Safe to do a cast
        Player player = (Player) startEntity;

        // Check if entity can be collected/walked on. If so, collect and move player
        if(canWalkOn(player, endTile)){
            endTile.setEntity(player);
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


    // Testing proper maze-Josh commit
}
