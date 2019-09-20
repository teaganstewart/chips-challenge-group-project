package nz.ac.vuw.ecs.swen225.a3.maze;

public class Maze {
    private Tile[][] tiles;
    Player player;

    public Maze(Tile[][] tiles){
        this.tiles = tiles;
    }

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

        // Check whether destination is empty. If so, move player
        if(endTile.getEntity() == null){
            endTile.setEntity(player);
            return true;
        }

        // Check if item can be collected. If so, collect and move player
        if(collect(endTile) != null){
            endTile.setEntity(player);
            return true;
        }

        return false;
    }

    /**
     * Returns entity collected, else returns null for no entity collected
     * */
    public Entity collect(Tile tile){
        Entity entity = tile.getEntity();
        if(!entity.isPickupable()) return null;
        tile.setEntity(null);
        return entity;
    }


    // Testing proper maze-Josh commit
}
