package nz.ac.vuw.ecs.swen225.a3.application;

import nz.ac.vuw.ecs.swen225.a3.maze.Maze;
import nz.ac.vuw.ecs.swen225.a3.maze.Player;

public class Game {
    int level;
    int time;
    int treasures;
    boolean endGame;

  
    private Player player;
    private Maze maze;

    public void update(){

    }

    public void loadGame(){

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
