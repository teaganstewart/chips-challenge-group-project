package nz.ac.vuw.ecs.swen225.a3.persistence.Tests;

import nz.ac.vuw.ecs.swen225.a3.maze.*;
import nz.ac.vuw.ecs.swen225.a3.persistence.LoadUtils;
import nz.ac.vuw.ecs.swen225.a3.persistence.SaveUtils;
import org.junit.jupiter.api.Test;
import static nz.ac.vuw.ecs.swen225.a3.maze.BasicColor.GREEN;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;

/**
 * 
 * Class which contains most tests that test classes in the persistence
 * directory.
 * 
 * @author Matt Rothwell - 300434822
 */
public class PersistanceTests {

	/**
	 * Test that if missing, the save method creates a new directory
	 */
	@Test
	public void test00() {
		Player player = new Player(new Coordinate(0, 0));
		Tile[][] tiles = new Tile[1][1];
		tiles[0][0] = new Tile(new Coordinate(0, 0), Tile.TileType.FLOOR);
		Maze maze = new Maze(tiles, player, null, null);
		Level level = new Level(1, maze, System.currentTimeMillis(), 0, 0);

		// Empty the folder
		File[] itemsInFolder = new File("saves").listFiles();
		if (itemsInFolder != null) {
			for (File f : itemsInFolder) {
				f.delete();
			}
		}

		// Delete it
		// assertTrue(new File("saves").delete());

		// Save the game
		assertTrue(SaveUtils.saveGame(level, ""));

		// Check that the saves directory exists
		assertTrue(new File("saves").exists());
	}

	/**
	 * Test that the saving and loading produce a non-null output
	 */
	@Test
	public void test01() {
		Player player = new Player(new Coordinate(0, 0));
		Tile[][] tiles = new Tile[1][1];
		tiles[0][0] = new Tile(new Coordinate(0, 0), Tile.TileType.FLOOR);
		Maze maze = new Maze(tiles, player, null, null);
		Level level = new Level(1, maze, System.currentTimeMillis(), 0, 0);

		assertTrue(SaveUtils.saveGame(level, ""));

		Level levelReloaded = LoadUtils.resumeGame();
		assertNotNull(levelReloaded);
		assertEquals(levelReloaded.getLevel(), level.getLevel());
	}

	/**
	 * Test that entities have the same properties after being saved and loaded
	 */
	@Test
	public void test02() {
		Player player = new Player(new Coordinate(0, 0));
		Tile[][] tiles = new Tile[1][1];
		tiles[0][0] = new Tile(new Coordinate(0, 0), Tile.TileType.FLOOR);
		tiles[0][0].setEntity(new Key(GREEN));
		Maze maze = new Maze(tiles, player, null, null);
		Level level = new Level(1, maze, System.currentTimeMillis(), 0, 0);

		assertTrue(SaveUtils.saveGame(level, ""));

		Level levelReloaded = LoadUtils.resumeGame();
		assertNotNull(levelReloaded);
		assertEquals(levelReloaded.getLevel(), level.getLevel());

		Key key = (Key) level.getMaze().getTiles()[0][0].getEntity();

		assertSame(GREEN, key.getColor());
	}


	/**
	 * Test that the player's inventory is identical
	 */
	@Test
	public void test03() {
		Player player = new Player(new Coordinate(0, 0));
		KeyDoor kd = new KeyDoor(GREEN);
		kd.unlock();
		player.addToInventory(kd);

		Tile[][] tiles = new Tile[1][1];
		tiles[0][0] = new Tile(new Coordinate(0, 0), Tile.TileType.FLOOR);

		Maze maze = new Maze(tiles, player, null, null);
		Level level = new Level(1, maze, System.currentTimeMillis(), 0, 0);

		assertTrue(SaveUtils.saveGame(level, ""));

		Level levelReloaded = LoadUtils.resumeGame();

		KeyDoor key = (KeyDoor) levelReloaded.getMaze().getPlayer().getInventory().get(0);

		assertFalse(key.isLocked());
		assertSame(key.getColor(), GREEN);
	}

	/**
	 * Test that a hintTile is a hintTile when saved and reloaded
	 */
	@Test
	public void test04() {
		Player player = new Player(new Coordinate(0, 0));

		Tile[][] tiles = new Tile[1][1];
		tiles[0][0] = new HintTile(new Coordinate(0, 0), "Howdy");

		Maze maze = new Maze(tiles, player, null, null);
		Level level = new Level(1, maze, System.currentTimeMillis(), 0, 0);

		assertTrue(SaveUtils.saveGame(level, ""));

		Level levelReloaded = LoadUtils.resumeGame();

		assertTrue(levelReloaded.getMaze().getTiles()[0][0] instanceof HintTile);
	}

	/**
	 * Test that a Treasure chest is reloaded
	 */
	@Test
	public void test05() {
		Player player = new Player(new Coordinate(0, 0));

		Tile[][] tiles = new Tile[1][1];
		tiles[0][0] = new Tile(new Coordinate(0, 0), Tile.TileType.WALL);
		tiles[0][0].setEntity(new Treasure());

		Maze maze = new Maze(tiles, player, null, null);
		Level level = new Level(1, maze, System.currentTimeMillis(), 0, 0);

		assertTrue(SaveUtils.saveGame(level, ""));

		Level levelReloaded = LoadUtils.resumeGame();

		assertTrue(levelReloaded.getMaze().getTiles()[0][0].getEntity() instanceof Treasure);
	}

	/**
	 * Test that a TreasureDoor is reloaded
	 */
	@Test
	public void test06() {
		Player player = new Player(new Coordinate(0, 0));

		Tile[][] tiles = new Tile[1][1];
		tiles[0][0] = new Tile(new Coordinate(0, 0), Tile.TileType.WALL);
		TreasureDoor door = new TreasureDoor();
		door.unlock();
		tiles[0][0].setEntity(door);

		Maze maze = new Maze(tiles, player, null, null);
		Level level = new Level(1, maze, System.currentTimeMillis(), 0, 0);

		assertTrue(SaveUtils.saveGame(level, ""));

		Level levelReloaded = LoadUtils.resumeGame();

		assertTrue(levelReloaded.getMaze().getTiles()[0][0].getEntity() instanceof TreasureDoor);
	}

	/**
	 * Loading fire boots and ice Boots
	 */
	@Test
	public void test07() {
		Player player = new Player(new Coordinate(0, 0));

		player.addToInventory(new IceBoots());
		player.addToInventory(new FireBoots());

		Tile[][] tiles = new Tile[1][2];
		tiles[0][0] = new Tile(new Coordinate(0, 0), Tile.TileType.FLOOR);
		tiles[0][1] = new Tile(new Coordinate(0, 1), Tile.TileType.FLOOR);
		tiles[0][0].setEntity(new IceBoots());
		tiles[0][1].setEntity(new FireBoots());

		Maze maze = new Maze(tiles, player, null, null);
		Level level = new Level(1, maze, System.currentTimeMillis(), 0, 0);

		assertTrue(SaveUtils.saveGame(level, ""));

		assertEquals(level.getMaze().getPlayer().getInventoryAt(0).toString(), new IceBoots().toString());

		assertEquals(level.getMaze().getPlayer().getInventoryAt(1).toString(), new FireBoots().toString());

	}

	/**
	 * 
	 */
	@Test
	public void testSaveLevel() {



		for(Long l : LoadUtils.getSavesByID().values()) {

			Level level = LoadUtils.loadById(l);

			//checks that the level loaded properly
			assertTrue(level.getMaze()!=null);

		}

		assertEquals(SaveUtils.saveLevel(1),true);
		// checking that the levels are correct
		assertEquals(LoadUtils.getAmountOfInstalledLevels(), 2);

	}
}