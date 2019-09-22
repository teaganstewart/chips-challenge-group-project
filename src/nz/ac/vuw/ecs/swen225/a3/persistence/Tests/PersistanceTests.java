package nz.ac.vuw.ecs.swen225.a3.persistence.Tests;

import nz.ac.vuw.ecs.swen225.a3.maze.Coordinate;
import nz.ac.vuw.ecs.swen225.a3.maze.Maze;
import nz.ac.vuw.ecs.swen225.a3.maze.Player;
import nz.ac.vuw.ecs.swen225.a3.maze.Tile;
import nz.ac.vuw.ecs.swen225.a3.persistence.LoadUtils;
import nz.ac.vuw.ecs.swen225.a3.persistence.SaveUtils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class PersistanceTests {

    @Test
    public void test01(){
        Tile[][] tiles = new Tile[1][1];
        tiles[0][0] = new Tile(new Coordinate(0,0), Tile.TileType.FLOOR);
        Maze maze = new Maze(tiles, new Player(new Coordinate(0, 0)));

        SaveUtils.saveGame(maze);

        Maze maze2 = LoadUtils.resumeGame();

        SaveUtils.saveGame(maze2);

        Maze maze3 = LoadUtils.resumeGame();

        assertEquals(maze2, maze3);

    }
}
