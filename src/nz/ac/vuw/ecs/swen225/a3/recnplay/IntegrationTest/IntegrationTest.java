package nz.ac.vuw.ecs.swen225.a3.recnplay.IntegrationTest;

import nz.ac.vuw.ecs.swen225.a3.application.Game;
import nz.ac.vuw.ecs.swen225.a3.application.ui.GUI;
import nz.ac.vuw.ecs.swen225.a3.maze.*;
import nz.ac.vuw.ecs.swen225.a3.maze.Tile;
import nz.ac.vuw.ecs.swen225.a3.persistence.SaveUtils;
import nz.ac.vuw.ecs.swen225.a3.recnplay.ActionRecord;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Josh
 * <p>
 * Class which contains integration tests for the record and play feature
 */
public class IntegrationTest {

    private Tile[][] tiles;
    private Player player;
    private Maze maze;
    private List<Crate> crateList = new ArrayList<>();
    private Game game;
    private GUI gui;

    /*
     * Create the infrastructure needed for record and play before each test
     * */
    @BeforeEach
    public void setUp() {
//        // CREATE THE CUSTOM TESTING MAZE
//        tiles = new Tile[9][9];
//        for (int row = 0; row < tiles.length; row++) {
//            for (int col = 0; col < tiles[0].length; col++) {
//                tiles[row][col] = new Tile(new Coordinate(row, col), Tile.TileType.FLOOR);
//            }
//        }
//        // Set the goal to enable the replaying
//        tiles[0][3] = new Tile(new Coordinate(3, 3), Tile.TileType.GOAL);
//
//        player = new Player(new Coordinate(0, 0));
//
//        // No crates or enemies in the level
//        maze = new Maze(tiles, player, new ArrayList<>(), new ArrayList<Skeleton>());
//
//        // CREATE A GAME WITH THE TEST MAZE
//        game = new Game(maze);
//
//        // START THE GAME AND SET IT UP WITH OUT TEST GAME
//        gui = new GUI();
//        gui.setGame(game);
    }

    @Test
    public void testInitializeRecordAndPlay() {
        Assumptions.assumeTrue(maze.equals(game.getMaze()));

        maze.movePlayer(Direction.RIGHT);
        maze.movePlayer(Direction.RIGHT);
        // Move Player into the goal
        maze.movePlayer(Direction.RIGHT);
        Assumptions.assumeTrue(maze.isGoalReached());


        gui.checkConditions(game.getMaze());
        assertTrue(gui.isReplayMode());
        assertFalse(maze.movePlayer(Direction.RIGHT));
    }

    @Test
    public void testActualGame() {
        // Clear the saves and recordings before testing further
        File recordings_folder = new File("recordings");
        for (File file : recordings_folder.listFiles()) {
            if (file != null) {
                for (File innerFile : file.listFiles()) {
                    if (innerFile != null) innerFile.delete();
                }
                file.delete();
            }
        }
        File save_folder = new File(SaveUtils.SAVES_DIRECTORY);
        for (File file : save_folder.listFiles()) {
            if (file != null) {
                for (File innerFile : file.listFiles()) {
                    if (innerFile != null) innerFile.delete();
                }
                file.delete();
            }
        }

        GUI gui = new GUI();
        Maze maze = gui.getGame().getMaze();
        Player player = maze.getPlayer();
        Tile[][] tiles = maze.getTiles();

        // Make sure we know where everything is in the maze
        assertEquals(new Coordinate(5, 7), player.getCoordinate());
        assertTrue(tiles[3][7].getEntity() instanceof TreasureDoor);

        // Cheat by removing the treasure door
        tiles[3][7].setEntity(null);
        maze.movePlayer(Direction.UP);
        maze.movePlayer(Direction.UP);
        maze.movePlayer(Direction.UP);
        assertTrue(maze.isGoalReached());

        gui.setRecIndex(0);
        gui.checkConditions(maze);
        assertTrue(gui.isReplayMode());
    }
}