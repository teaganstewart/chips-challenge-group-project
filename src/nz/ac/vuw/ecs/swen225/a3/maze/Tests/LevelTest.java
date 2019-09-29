package nz.ac.vuw.ecs.swen225.a3.maze.Tests;

import nz.ac.vuw.ecs.swen225.a3.maze.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Josh
 *
 *         Class which contains tests for the Level class
 *
 */
public class LevelTest {

  /**
   * Test the timer
   */
  @Test
  public void testTimer() {
    // Setup

    Tile[][] tiles = new Tile[9][9];
    for (int row = 0; row < tiles.length; row++) {
      for (int col = 0; col < tiles[0].length; col++) {
        tiles[row][col] = new Tile(new Coordinate(row, col), Tile.TileType.FLOOR);
      }
    }

    Player player = new Player(new Coordinate(3, 3));
    Maze maze = new Maze(tiles, player);

    Level level;

    // Make a new Level?
  }
}
