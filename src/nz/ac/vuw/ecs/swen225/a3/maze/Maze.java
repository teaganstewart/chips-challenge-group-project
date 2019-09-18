package nz.ac.vuw.ecs.swen225.a3.maze;

public class Maze {
    private Tile[][] tiles;

    public Maze(Tile[][] tiles){
        this.tiles = tiles;
    }

    /**
     * Returns true if player was moved from start location to end location,
     * else returns false if player was not moved
     */
    public boolean movePlayer(int startRow, int startCol, int endRow, int endCol){
        // Check for no entity in the start position
        Tile startTile = tiles[startRow][startCol];
        Tile endTile = tiles[endRow][endCol];
        Entity startEntity = startTile.getEntity();
        if(!(startEntity instanceof Player)) return false;
        // Check for occupied destination
        if(endTile.getEntity() != null) return false;
        endTile.setEntity(startEntity);
        return true;
    }

    /**
     * Returns entity collected, else returns null for no entity collected
     * */
    public Entity collect(int row, int col){
        Entity entity = tiles[row][col].getEntity();
        if(entity instanceof Player || entity instanceof Door) return null;
        tiles[row][col].setEntity(null);
        return entity;
    }


}
