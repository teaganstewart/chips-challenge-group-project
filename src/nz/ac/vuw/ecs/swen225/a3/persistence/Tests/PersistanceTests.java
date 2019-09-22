package nz.ac.vuw.ecs.swen225.a3.persistence.Tests;

import nz.ac.vuw.ecs.swen225.a3.maze.*;
import nz.ac.vuw.ecs.swen225.a3.persistence.LoadUtils;
import nz.ac.vuw.ecs.swen225.a3.persistence.SaveUtils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class PersistanceTests {

    @Test
    public void test01(){
        Player player = new Player(new Coordinate(0, 0));
        Tile[][] tiles = new Tile[1][1];
        tiles[0][0] = new Tile(new Coordinate(0, 0), Tile.TileType.FLOOR);
        Maze maze = new Maze(tiles, player);
        Level level = new Level(1, maze, System.currentTimeMillis(), 0);

        assertTrue(SaveUtils.saveGame(level));
        
        Level levelReloaded = LoadUtils.resumeGame();
        assertNotNull(levelReloaded);
        assertTrue(levelReloaded.getLevel() == level.getLevel());
    }
    
    @Test
    public void test02(){
        Player player = new Player(new Coordinate(0, 0));
        Tile[][] tiles = new Tile[1][1];
        tiles[0][0] = new Tile(new Coordinate(0, 0), Tile.TileType.FLOOR);
        tiles[0][0].setEntity(new Key(BasicColor.GREEN));
        Maze maze = new Maze(tiles, player);
        Level level = new Level(1, maze, System.currentTimeMillis(), 0);

        assertTrue(SaveUtils.saveGame(level));
        
        Level levelReloaded = LoadUtils.resumeGame();
        assertNotNull(levelReloaded);
        assertTrue(levelReloaded.getLevel() == level.getLevel());
    }
}
