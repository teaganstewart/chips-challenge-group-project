package nz.ac.vuw.ecs.swen225.a3.maze;

public class TestMaze {

    public static void main(String[] args) {
        Tile[][] tiles = new Tile[9][9];
        for(int row=0; row < 9; row++){
            for(int col=0; col < 9; col++) {
                tiles[row][col] = new Tile(false);
            }
        }
        Maze maze = new Maze(tiles);
        tiles[0][0].setEntity(new Player());
        assert(maze.move(0, 0, 4, 4));
    }
}
