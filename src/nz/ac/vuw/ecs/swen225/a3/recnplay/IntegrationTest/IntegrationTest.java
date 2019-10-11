////package nz.ac.vuw.ecs.swen225.a3.recnplay.IntegrationTest;
////
////import static org.junit.Assert.assertSame;
////import static org.junit.jupiter.api.Assertions.assertTrue;
////
////import java.awt.event.KeyEvent;
////import java.lang.reflect.InvocationTargetException;
////import java.util.ArrayList;
////import java.util.Arrays;
////import java.util.List;
////
////import javax.swing.SwingUtilities;
////
////import org.junit.jupiter.api.BeforeEach;
////import org.junit.jupiter.api.Test;
////
////import nz.ac.vuw.ecs.swen225.a3.application.ui.GUI;
////import nz.ac.vuw.ecs.swen225.a3.maze.Coordinate;
////import nz.ac.vuw.ecs.swen225.a3.maze.Crate;
////import nz.ac.vuw.ecs.swen225.a3.maze.Level;
////import nz.ac.vuw.ecs.swen225.a3.maze.Maze;
////import nz.ac.vuw.ecs.swen225.a3.maze.Player;
////import nz.ac.vuw.ecs.swen225.a3.maze.Tile;
////import nz.ac.vuw.ecs.swen225.a3.maze.Treasure;
////import nz.ac.vuw.ecs.swen225.a3.persistence.SaveUtils;
////import nz.ac.vuw.ecs.swen225.a3.recnplay.ActionRecord;
////import nz.ac.vuw.ecs.swen225.a3.recnplay.ReplayUtils;
////
/////**
//// * 
//// * Testing the recording functionality.
//// * 
//// * @author Joshua O'Hagan, Ethan Munn
//// *
//// */
////public class IntegrationTest {
////	
////	private GUI gui;
////	private Level level;
////    private Maze maze;
////    private Tile[][] tiles;
////    private Player player;
////    private List<Crate> crateList = new ArrayList<>();
////
////    /**
////     *  Creates a new maze before each test.
////     */
////    @BeforeEach
////    public void setUp() {
////    	gui = new GUI();
////
////        tiles = new Tile[9][9];
////        for (int row = 0; row < tiles.length; row++) {
////            for (int col = 0; col < tiles[0].length; col++) {
////                tiles[row][col] = new Tile(new Coordinate(row, col), Tile.TileType.FLOOR);
////            }
////        }
////        player = new Player(new Coordinate(3, 3));
////        crateList = new ArrayList<Crate>(Arrays.asList(
////                new Crate(new Coordinate(5, 3)),
////                new Crate(new Coordinate(5, 5))
////        ));
////
////        maze = new Maze(tiles, player, crateList,null);
////        
////        ReplayUtils.reset();
////        level = new Level(-1, maze, ReplayUtils.getStartTime(), 0, 1000);
////        gui.getGame().loadSave(level);
////        ReplayUtils.pushActionRecord(new ActionRecord(0, maze));
////        Treasure.reset();
////    }
////    
////    /**
////     * Test to see if player walks and this is saved
////     */
////    @Test
////    public void testActionRecordGoingRight() {
////        for (int i = 0; i < 3; i++) {
////            try {
////				SwingUtilities.invokeAndWait(() -> {
////				    KeyEvent key = new KeyEvent(GUI.main, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT,'Z');
////				    gui.inGameEvent(key);
////				});
////			} catch (InvocationTargetException | InterruptedException e) {}
////        }
////        
////		assertTrue(SaveUtils.saveGame(level, ""));
////        GUI.stopTimer();
////        
////        ReplayUtils.playBack(Long.toString(level.getBeginTime()));
////        assertSame(ReplayUtils.replaySize(), 4);
////        assertTrue(player.getCoordinate().equals(new Coordinate(3,6)));
////    }
//
////    @Test
////    public void resetingRecording() {
////        Maze maze = ReplayUtils.getActionRecord(0).getMaze();
////        assertEquals(new Coordinate(0, 1), maze.getPlayer().getCoordinate());
////    }
////
////    /*
////     * Checks whether the maze is in a valid state, for example checking that
////     * the player is standing on a valid tile
////     * */
////    private void checkValidMaze(Maze maze) {
////        Tile[][] mazeTiles = maze.getTiles();
////        Player mazePlayer = maze.getPlayer();
////
////        // Check validity of the player
////        Coordinate playerCoord = mazePlayer.getCoordinate();
////        Tile playerTile = mazeTiles[playerCoord.getRow()][playerCoord.getCol()];
////        // Make sure player is standing by themselves obn the tile
////        assertNull(playerTile.getEntity());
////        // Make sure playerTile is valid, e.g. not a wall
////        assertTrue(maze.checkType(mazePlayer, playerTile));
////
////        for (int row = 0; row < mazeTiles.length; row++) {
////            for (int col = 0; col < mazeTiles[0].length; col++) {
////                Tile tile = mazeTiles[row][col];
////            }
////        }
////    }
////
////    /*
////     * Method used for moving and recording the player's movements by pushing the action onto the ActionRecord
////     * */
////    private void movePlayerAndRecord(Direction dir) {
////        maze.movePlayer(dir);
//////        assertTrue(ReplayUtils.pushActionRecord(new ActionRecord((int) (System.currentTimeMillis()
//////                - ReplayUtils.getStartTime()), maze)));
////        new Thread(() -> ReplayUtils.pushActionRecord(new ActionRecord((int)(System.currentTimeMillis()
////                - ReplayUtils.getStartTime()), maze))).start();
////    }
////
////    /*
////     * Method used for clearing the Saves and Recordings folders. This prevents issues when testing the
////     * recordings
////     * */
////    private void clearSavesAndRecordingsFolders() {
////        // Clear the saves and recordings before testing further
////        File recordings_folder = new File("recordings");
////        if (recordings_folder != null) {
////            for (File file : recordings_folder.listFiles()) {
////                if (file != null) {
////                    for (File innerFile : file.listFiles()) {
////                        if (innerFile != null) {
////                            innerFile.delete();
////                        } else {
////                            System.out.println("recordings innermost folder is null");
////                        }
////                    }
////                    file.delete();
////                } else {
////                    System.out.println("recordings sub folder is null");
////                }
////            }
////        } else {
////            System.out.println("recordigns folder is null");
////        }
////
////        File save_folder = new File(SaveUtils.SAVES_DIRECTORY);
////        for (File file : save_folder.listFiles()) {
////            if (file != null) {
////                file.delete();
////            }
////        }
////    }
//
//
//}