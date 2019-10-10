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
    private Coordinate tempPlayerCoord1;
    private Coordinate tempPlayerCoord2;
    private Coordinate tempPlayerCoord3;

    /*
     * Create the infrastructure needed for record and play before each test
     * */
    @BeforeEach
    public void setUp() {
        clearSavesAndRecordingsFolders();

        // CREATE THE CUSTOM TESTING MAZE
        tiles = new Tile[9][9];
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[0].length; col++) {
                tiles[row][col] = new Tile(new Coordinate(row, col), Tile.TileType.FLOOR);
            }
        }
        // Set the goal to enable the replaying
        tiles[0][3] = new Tile(new Coordinate(3, 3), Tile.TileType.GOAL);

        player = new Player(new Coordinate(0, 0));

        // No crates or enemies in the level
        maze = new Maze(tiles, player, new ArrayList<>(), new ArrayList<Skeleton>());

        // START THE GAME AND SET IT UP WITH OUR TEST GAME
        gui = new GUI();
        gui.setGame(new Game(maze));
        game = gui.getGame();
        Render render = new Render(game,game.getMaze());
        game.setRender(render);

        movePlayerAndRecord(Direction.RIGHT);
        tempPlayerCoord1 = maze.getPlayer().getCoordinate();
        movePlayerAndRecord(Direction.RIGHT);
        tempPlayerCoord2 = maze.getPlayer().getCoordinate();
        movePlayerAndRecord(Direction.RIGHT);
        tempPlayerCoord3 = maze.getPlayer().getCoordinate();
        assertTrue(maze.isGoalReached());

        gui.checkConditions(maze);
        assertTrue(gui.isReplayMode());
    }

    @AfterEach
    public void tearDown(){
        // End recording
        if (game.getLevelNum() < 2) {
            gui.setReplayMode(false);
            game.loadLevel(gui.getGamePanel(), game.getLevelNum()+1);
        }

        assertEquals(2, game.getLevelNum());
        // Assert player can move again in level 2
        assertTrue(maze.movePlayer(Direction.RIGHT));

        // Clean up after recording
        clearSavesAndRecordingsFolders();
    }

    @Test
    public void ActionRecordsCorrect() {
        ActionRecord action1 = ReplayUtils.getActionRecord(0);
        // Assert Player is in the correct position
        assertEquals(tempPlayerCoord1, action1.getMaze().getPlayer().getCoordinate());
        ActionRecord action2 = ReplayUtils.getActionRecord(1);
        // Assert Player is in the correct position
        assertEquals(tempPlayerCoord2, action2.getMaze().getPlayer().getCoordinate());
        ActionRecord action3 = ReplayUtils.getActionRecord(2);
        // Assert Player is in the correct position
        assertEquals(tempPlayerCoord3, action3.getMaze().getPlayer().getCoordinate());

        // Make sure this is the end of the action record
        try{
            ReplayUtils.getActionRecord(3);
            // This next line should not be triggered
            assertFalse(true);
        }catch(IndexOutOfBoundsException e){

        }

    }

    @Test
    public void resetingRecording(){
        gui.setRecIndex(0);
        Maze maze = ReplayUtils.getActionRecord(gui.getRecIndex()).getMaze();
        assertEquals(tempPlayerCoord1, maze.getPlayer().getCoordinate());
    }

    @Test
    public void recIndex(){
        
    }

    /*
    * Method used for moving and recording the player's movements by pushing the action onto the ActionRecord
    * */
    private void movePlayerAndRecord(Direction dir){
        maze.movePlayer(dir);
        assertTrue(ReplayUtils.pushActionRecord(new ActionRecord((int)(System.currentTimeMillis()
                - ReplayUtils.getStartTime()), maze)));
    }

    /*
    * Method used for clearing the Saves and Recordings folders. This prevents issues when testing the
    * recordings
    * */
    private void clearSavesAndRecordingsFolders(){
        // Clear the saves and recordings before testing further
        File recordings_folder = new File("recordings");
        if (recordings_folder != null) {
            for (File file : recordings_folder.listFiles()) {
                if (file != null) {
                    for (File innerFile : file.listFiles()) {
                        if (innerFile != null) {
                            innerFile.delete();
                        }else{
                            System.out.println("recordings innermost folder is null");
                        }
                    }
                    file.delete();
                }else{
                    System.out.println("recordings sub folder is null");
                }
            }
        }else{
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