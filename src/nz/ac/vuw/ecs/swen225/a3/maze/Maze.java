package nz.ac.vuw.ecs.swen225.a3.maze;

public class Maze {
    private Tile[][] tiles;

    public Maze(Tile[][] tiles){
        this.tiles = tiles;
    }

    /**
     * MReturns true if entity was moved from start location to end location,
     * else returns false if entity was not moved
     */
    public boolean move(int startRow, int startCol, int endRow, int endCol){
        // Check for no entity in the start position
        Entity entity = tiles[startRow][startCol].getEntity();
        if(entity == null) return false;
        // Check for occupied destination
        if(tiles[endRow][endCol].getEntity() != null) return false;
        entity.setCol(endCol);
        entity.setRow(endRow);
        return true;
    }
}
