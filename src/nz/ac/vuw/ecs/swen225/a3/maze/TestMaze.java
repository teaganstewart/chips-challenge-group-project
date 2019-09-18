package nz.ac.vuw.ecs.swen225.a3.maze;

public class TestMaze {

    // This is the layout to how a Tile[][] should be constructed :)
    public static void main(String[] args) {
        Tile[][] tiles = new Tile[9][9];
        for(int row=0; row < 9; row++){
            for(int col=0; col < 9; col++) {
                Tile tile = tiles[row][col];
                tile = new Tile(false);
                tile.setRow(row);
                tile.setCol(col);
            }
        }
        Maze maze = new Maze(tiles);
        tiles[0][0].setEntity(new Player());
        assert(maze.movePlayer(0, 0, 4, 4));
    }

    // testing change in gitlab sdkcvdsjv
}
