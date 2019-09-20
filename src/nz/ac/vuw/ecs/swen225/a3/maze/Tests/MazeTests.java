package nz.ac.vuw.ecs.swen225.a3.maze.Tests;

import nz.ac.vuw.ecs.swen225.a3.maze.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class MazeTests {

    private Tile[][] tiles;
    private Maze maze;

    public MazeTests(){
        // Setup

        tiles = new Tile[9][9];
        for(int row=0; row < 9; row++){
            for(int col=0; col < 9; col++) {
                tiles[row][col] = new Tile(new Coordinate(row, col), false);
            }
        }
        maze = new Maze(tiles);
    }

    @Test
    public void testMovePlayerValid(){
        tiles[0][0].setEntity(new Player());
        assert(maze.movePlayer(0, 0, 4, 4));
    }

    // TODO Array Index Out of Bounds means this test fails
    @Test
    public void testMovePlayerNotValid(){
        tiles[0][0].setEntity(new Player());
        assertFalse(maze.movePlayer(0, 0, -1, -1));
    }

    @Test
    public void testMovePlayerAndCollectValid(){
        tiles[0][0].setEntity(new Player());
        tiles[4][4].setEntity(new Key(BasicColor.YELLOW));

        assert(maze.movePlayer(0, 0, 4, 4));
        // TODO assert correct item is collected
    }

    @Test
    public void testMovePlayerAndCollectNotValid(){
        tiles[0][0].setEntity(new Player());
        tiles[4][4].setEntity(new Door(BasicColor.RED));
        assertFalse(maze.movePlayer(0, 0, 4, 4));
    }
}
