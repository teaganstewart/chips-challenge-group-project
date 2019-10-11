package nz.ac.vuw.ecs.swen225.a3.application;

import nz.ac.vuw.ecs.swen225.a3.application.ui.GUI;
import nz.ac.vuw.ecs.swen225.a3.application.ui.GamePanel;
import nz.ac.vuw.ecs.swen225.a3.maze.*;
//import nz.ac.vuw.ecs.swen225.a3.persistence.LevelMaker;
import nz.ac.vuw.ecs.swen225.a3.persistence.LoadUtils;
import nz.ac.vuw.ecs.swen225.a3.recnplay.ActionRecord;
import nz.ac.vuw.ecs.swen225.a3.recnplay.ReplayUtils;
import nz.ac.vuw.ecs.swen225.a3.render.Render;

/**
 * The game class, used for storing most of the data that the maze
 * uses.
 * 
 * @author Meng Veng Taing - 300434816
 *
 */
public class Game {
	int levelNum;
	private int time = 0;

	private Player player;
	private Maze maze;
	private Render render;
	private Level level;
	boolean endGame;

	/**
	 * The constructor for the game object, links packages.
	 */
	public Game() {
		loadGame();
	}

	/**
	 * Returns the maze for the game.
	 * 
	 * @return maze Returns the maze.
	 */
	public Maze getMaze() {
		return maze;
	}

	/**
	 * Sets the tiles, useful for tests.
	 * 
	 * @param t The new array of tiles.
	 */
	public void setTiles(Tile[][] t) {
		this.maze.setTiles(t);
	}

	/**
	 * The player in the game.
	 * 
	 * @return player Returns the player.
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Setting the player, useful for tests and changing levels.
	 * 
	 * @param p The new player.
	 */
	public void setPlayer(Player p) {
		player = p;
	}

	/**
	 * Setting the maze, useful for tests and changing levels.
	 * 
	 * @param m The new maze.
	 */
	public void setMaze(Maze m) {
		maze = m;
	}

	/**
	 * Updates the game board 4 times a second, used to move enemies
	 * and for ice animation.
	 * 
	 * @param shiftIfOnIce If the player is on ice.
	 * @param enemyToggle If need to check for enemies.
	 */
	public void update(boolean shiftIfOnIce, boolean enemyToggle) {
		boolean saveReplay = false;

		// moving chip if he's on ice
		if (shiftIfOnIce) {
			if (maze.isOnIce() && !player.isInInventory(new IceBoots())) {
				maze.movePlayer(player.getDirection());
				saveReplay = true;
			}
		}

		// moving of all the enemy's
		if (enemyToggle) {
			Tile[][] board = maze.getTiles();
			for(Moveable enemy : maze.getEnemyList()) {
				Coordinate nextPosition = enemy.getNextPos();
				if(nextPosition.equals(maze.getPlayer().getCoordinate())) {
					maze.setResetLevel(true);
					return;
				}
				Tile curr = board[nextPosition.getRow()][nextPosition.getCol()];
				if (curr.getType() == Tile.TileType.WALL) {  }
				else if(enemy.canWalkOn(curr.getEntity())) {
					enemy.setCoordinate(nextPosition);
					saveReplay = true;
					continue;
				}

					// changes direction if returns false and moves back
					enemy.setDirection(enemy.getDirection().inverse());
					enemy.setCoordinate(enemy.getNextPos());
					saveReplay = true;
				}
		}
		if (saveReplay) {
			new Thread(() -> ReplayUtils.pushActionRecord(new ActionRecord((int)(System.currentTimeMillis() - ReplayUtils.getStartTime()), getMaze()))).start();
		}
	}

	/**
	 * Loads the game when the game is first option, checks to see if 
	 * there are any valid saves to load first (must be 9x9). If there
	 * isn't it just loads the first level.
	 */
	public void loadGame(){

		try{
			level = LoadUtils.resumeGame();
			setLevelNum(level.getLevel());
			
			// check if the save is valid
			if(level.getMaze().getTiles().length<9 || level.getMaze().getTiles()[0].length<9) {
				throw new NullPointerException();
			}
		}
		catch (NullPointerException e){
			level = LoadUtils.loadLevel(1);
			setLevelNum(1);
		}

		maze = level.getMaze();
		player = maze.getPlayer();
		setLevelNum(level.getLevel());

		startLevel(level);

	}

	/**
	 * A new timer for the level.
	 * 
	 * @param t The new time.
	 */
	public void setTime(int t) {
		time = t;
	}

	/**
	 * Makes sure the game knows what level it is meant to be on.
	 * Useful for loading, saving and updating the maze.
	 * 
	 * @param l The new level number.
	 */
	public void setLevelNum(int l) {
		levelNum = l;
	}

	/**
	 * The time of the level.
	 * 
	 * @return time Returns the time of the level.
	 */
	public int getTime() {
		return time;
	}

	/**
	 * The level number for loading and saving.
	 * 
	 * @return levelNum The level.
	 */
	public int getLevelNum() {
		return levelNum;
	}

	/**
	 * The actual level, contains all the time variables and a maze.
	 * 
	 * @param l The new level.
	 */
	public void setLevel(Level l) {
		level = l;
	}
	
	/**
	 * Gets the current level.
	 * 
	 * @return level The level.
	 */
	public Level getLevel(){
		return level;
	}

	/**
	 * Method that sets the render, used for tests and in GamePanel.
	 * 
	 * @param r The new render.
	 */
	public void setRender(Render r) {
		render = r;
	}
	
	/**
	 * The render, used in many other classes.
	 * 
	 * @return render Returns the render.
	 */
	public Render getRender() {
		return render;
	}

	/**
	 * Used  for loading a new level from the levels folder, especially 
	 * useful for the GUI and menu buttons.
	 * 
	 * @param gp The gamePanel.
	 * @param num The level number.
	 */
	public void loadLevel(GamePanel gp, int num) {
		setLevelNum(num);
		Level l = LoadUtils.loadLevel(getLevelNum());
		loadSave(l);
		
	}

	/**
	 * Loads a save from the file, using a level object instead of a 
	 * number. Used for loading levels that the user has saved and
	 * named.
	 * 
	 * @param lvl The level loading.
	 */
	public void loadSave(Level lvl){
		setMaze(lvl.getMaze());
		render.setMaze(lvl.getMaze());
		setPlayer(lvl.getMaze().getPlayer());
		setTiles(lvl.getMaze().getTiles());
		setLevelNum(lvl.getLevel());
		startLevel(lvl);
	}

	/**
	 * Starts the new level, including setting the level and making
	 * sure that the timer is running.
	 * 
	 * @param l The level we are loading.
	 */
	public void startLevel(Level l) {
		setTime(l.getTimeAllowed());
		setLevel(l);
		
		GUI.stopTimer();
		GUI.setReplayMode(false);
		GUI.startTimer();
	}



}
