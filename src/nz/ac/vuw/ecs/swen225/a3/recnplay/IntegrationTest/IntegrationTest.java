package nz.ac.vuw.ecs.swen225.a3.recnplay.IntegrationTest;

import nz.ac.vuw.ecs.swen225.a3.application.Game;
import nz.ac.vuw.ecs.swen225.a3.application.ui.GUI;
import nz.ac.vuw.ecs.swen225.a3.maze.*;
import nz.ac.vuw.ecs.swen225.a3.maze.Tile;
import nz.ac.vuw.ecs.swen225.a3.persistence.SaveUtils;
import nz.ac.vuw.ecs.swen225.a3.recnplay.ActionRecord;
import nz.ac.vuw.ecs.swen225.a3.recnplay.ReplayUtils;
import nz.ac.vuw.ecs.swen225.a3.render.Render;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private Game game;
    private GUI gui;

    /*
     * Create the infrastructure needed for record and play before each test. Sets up a simple
     * scenario, where the player starts at (0, 0) and moves right 3 steps to reach the goal
     * */
    @BeforeEach
    public void setUp() {

        // CREATE THE CUSTOM TESTING MAZE
        tiles = new Tile[9][9];
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[0].length; col++) {
                tiles[row][col] = new Tile(new Coordinate(row, col), Tile.TileType.FLOOR);
            }
        }
        // Set the goal to finish level 1 and enable the replaying
        tiles[0][3] = new Tile(new Coordinate(3, 3), Tile.TileType.GOAL);

        player = new Player(new Coordinate(0, 0));

        // No crates or enemies in the level
        maze = new Maze(tiles, player, new ArrayList<>(), new ArrayList<Skeleton>());

        game = new Game(maze);
        Render render = new Render(game, game.getMaze());
        game.setRender(render);

        // Record first Action of the game
        new Thread(() -> ReplayUtils.pushActionRecord(new ActionRecord(0, game.getMaze()))).start();

        movePlayerAndRecord(Direction.RIGHT);
        movePlayerAndRecord(Direction.RIGHT);
        movePlayerAndRecord(Direction.RIGHT);
        assertTrue(maze.isGoalReached());

        ReplayUtils.playBack(Long.toString(ReplayUtils.getStartTime()));

//        clearSavesAndRecordingsFolders();
    }

    @AfterEach
    public void tearDown() {
//        // End recording
//        if (game.getLevelNum() < 2) {
//            gui.setReplayMode(false);
//            game.loadLevel(gui.getGamePanel(), game.getLevelNum() + 1);
//        }
//
//        assertEquals(2, game.getLevelNum());
//        Maze levelTwoMaze = game.getMaze();
//        assertTrue(levelTwoMaze.movePlayer(Direction.RIGHT));
//
//        // Clean up after recording
//        clearSavesAndRecordingsFolders();
    }

    @Test
    public void testingSetupAndTearDown(){

    }

    @Test
    public void ActionRecordsCorrect() {
        ActionRecord action1 = ReplayUtils.getActionRecord(0);
        checkValidMaze(action1.getMaze());
        ActionRecord action2 = ReplayUtils.getActionRecord(1);
        checkValidMaze(action2.getMaze());
        ActionRecord action3 = ReplayUtils.getActionRecord(2);
        checkValidMaze(action3.getMaze());

        // Make sure this is the end of the action record
        try {
            ReplayUtils.getActionRecord(3);
            // This next line should not be triggered if this test is to pass
            assertFalse(true);
        } catch (IndexOutOfBoundsException e) {

        }

    }

    @Test
    public void resetingRecording() {
        Maze maze = ReplayUtils.getActionRecord(0).getMaze();
        assertEquals(new Coordinate(0, 1), maze.getPlayer().getCoordinate());
    }

    /*
     * Checks whether the maze is in a valid state, for example checking that
     * the player is standing on a valid tile
     * */
    private void checkValidMaze(Maze maze) {
        Tile[][] mazeTiles = maze.getTiles();
        Player mazePlayer = maze.getPlayer();

        // Check validity of the player
        Coordinate playerCoord = mazePlayer.getCoordinate();
        Tile playerTile = mazeTiles[playerCoord.getRow()][playerCoord.getCol()];
        // Make sure player is standing by themselves obn the tile
        assertNull(playerTile.getEntity());
        // Make sure playerTile is valid, e.g. not a wall
        assertTrue(maze.checkType(mazePlayer, playerTile));

        for (int row = 0; row < mazeTiles.length; row++) {
            for (int col = 0; col < mazeTiles[0].length; col++) {
                Tile tile = mazeTiles[row][col];
            }
        }
    }

    /*
     * Method used for moving and recording the player's movements by pushing the action onto the ActionRecord
     * */
    private void movePlayerAndRecord(Direction dir) {
        maze.movePlayer(dir);
//        assertTrue(ReplayUtils.pushActionRecord(new ActionRecord((int) (System.currentTimeMillis()
//                - ReplayUtils.getStartTime()), maze)));
        new Thread(() -> ReplayUtils.pushActionRecord(new ActionRecord((int)(System.currentTimeMillis()
                - ReplayUtils.getStartTime()), maze))).start();
    }

    /*
     * Method used for clearing the Saves and Recordings folders. This prevents issues when testing the
     * recordings
     * */
    private void clearSavesAndRecordingsFolders() {
        // Clear the saves and recordings before testing further
        File recordings_folder = new File("recordings");
        if (recordings_folder != null) {
            for (File file : recordings_folder.listFiles()) {
                if (file != null) {
                    for (File innerFile : file.listFiles()) {
                        if (innerFile != null) {
                            innerFile.delete();
                        } else {
                            System.out.println("recordings innermost folder is null");
                        }
                    }
                    file.delete();
                } else {
                    System.out.println("recordings sub folder is null");
                }
            }
        } else {
            System.out.println("recordigns folder is null");
        }

        File save_folder = new File(SaveUtils.SAVES_DIRECTORY);
        for (File file : save_folder.listFiles()) {
            if (file != null) {
                file.delete();
            }
        }
    }


}