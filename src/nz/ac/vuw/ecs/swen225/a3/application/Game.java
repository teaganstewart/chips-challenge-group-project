package nz.ac.vuw.ecs.swen225.a3.application;

import nz.ac.vuw.ecs.swen225.a3.application.ui.GUI;
import nz.ac.vuw.ecs.swen225.a3.application.ui.GamePanel;
import nz.ac.vuw.ecs.swen225.a3.maze.*;
import nz.ac.vuw.ecs.swen225.a3.persistence.LevelMaker;
import nz.ac.vuw.ecs.swen225.a3.persistence.LoadUtils;
import nz.ac.vuw.ecs.swen225.a3.render.Render;

public class Game {
	int levelNum;
	private int time = 0;

	private Player player;
	private Maze maze;
	private Render render;
	Level level;
	boolean endGame;

	public Game() {
		loadGame();
	}

	public Maze getMaze() {
		return maze;
	}

	/** Just for integration day **/
	public void setTiles(Tile[][] t) {
		this.maze.setTiles(t);
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player p) {
		player = p;
	}

	public void setMaze(Maze m) {
		maze = m;
	}

	public void update() {
		// moving of all the enemy's
		Tile[][] board = maze.getTiles();
		for(Moveable enemy : maze.getEnemyList()) {
			Coordinate nextPosition = enemy.getNextPos();
			if(enemy.canWalkOn(board[nextPosition.getRow()][nextPosition.getCol()].getEntity())) {
				enemy.setCoordinate(nextPosition);
			}
			else {
				// changes direction if returns false and moves back
				enemy.setDirection(enemy.getDirection().inverse());
				enemy.setCoordinate(enemy.getNextPos());
			}
		}
	}

	public void loadGame(){


		try{
			level = LoadUtils.resumeGame();
			if(level.getMaze().getTiles().length<9 || level.getMaze().getTiles()[0].length<9) {
				throw new NullPointerException();
			}
		}
		catch (NullPointerException e){
			level = LoadUtils.loadLevel(1);
			setLevel(1);
		}

		maze = level.getMaze();
		player = maze.getPlayer();

		// set the time appropriately here
		setTime(level.getTimeAllowed());
		GUI.startTimer();

	}

	public void setTime(int t) {
		time = t;
	}

	public void setLevel(int l) {
		levelNum = l;
	}

	public int getTime() {
		return time;
	}

	public int getLevelNum() {
		return levelNum;
	}

	public Level getLevel(){
		return level;
	}

	public void setRender(Render r) {
		render = r;
	}
	public Render getRender() {
		return render;
	}
	
	public void loadLevel(GamePanel gp, int num) {
		GUI.stopTimer();

		setLevel(num); 
		Level l = LoadUtils.loadLevel(getLevelNum());
		render.setMaze(l.getMaze());
		setMaze(l.getMaze());
		setPlayer(l.getMaze().getPlayer());
		setTiles(l.getMaze().getTiles());

		setTime(l.getTimeAllowed());
		GUI.startTimer();

	}

}
