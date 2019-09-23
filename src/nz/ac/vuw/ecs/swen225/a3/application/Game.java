package nz.ac.vuw.ecs.swen225.a3.application;

import nz.ac.vuw.ecs.swen225.a3.maze.Level;
import nz.ac.vuw.ecs.swen225.a3.maze.Maze;
import nz.ac.vuw.ecs.swen225.a3.maze.Player;
import nz.ac.vuw.ecs.swen225.a3.persistence.LoadUtils;

public class Game {
    int level;
    int time;
    int treasures;

    private Player player;
    private Maze maze;
    boolean endGame;

    public Game(){
        loadGame();
    }

    public Maze getMaze(){
        return maze;
    }

    public Player getPlayer(){
        return player;
    }

    public void update(){

    }

    public void loadGame(){
        Level level = LoadUtils.resumeGame();
        maze = level.getMaze();
        player = maze.getPlayer();


    }

    public void saveGame(){

    }

    public void setTime(int t){
        time = t;
    }
    public void setLevel(int l){
        level = l;
    }
    public void setTreasures(int t){
        treasures = t;
    }

    public int getTime(){
        return time;
    }
    public int getLevel(){
        return level;
    }
    public int getTreasures(){
        return treasures;
    }
}
