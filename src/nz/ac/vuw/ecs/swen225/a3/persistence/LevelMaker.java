package nz.ac.vuw.ecs.swen225.a3.persistence;

import nz.ac.vuw.ecs.swen225.a3.maze.*;

import java.util.ArrayList;

public class LevelMaker {
  Tile[][] tiles;
  ArrayList<Crate> crateList = new ArrayList<>();
  public LevelMaker() {

    Player player = new Player(new Coordinate(5, 7));

    Integer[][] map = { {1,1,2,2,2,2,2,1,2,2,2,2,2,1,1},
            {1,1,2,1,1,1,2,2,2,1,1,1,2,1,1},
            {1,1,2,1,7,1,2,3,2,1,7,1,2,1,1},
            {2,2,2,2,2,11,2,8,2,11,2,2,2,2,2},
            {2,1,14,1,12,1,1,1,1,1,9,1,14,1,2},
            {2,1,7,1,2,16,1,1,1,13,2,1,7,1,2},
            {2,2,2,2,2,7,1,4,1,7,2,2,2,2,2},
            {2,1,7,1,2,16,1,1,1,13,2,1,7,1,2},
            {2,1,1,1,9,1,1,7,1,1,12,1,1,1,2},
            {2,2,2,2,2,2,10,2,10,2,2,2,2,2,2},
            {1,1,1,1,2,1,1,2,1,1,2,1,1,1,1},
            {1,1,1,1,2,1,7,2,7,1,2,1,1,1,1},
            {1,1,1,1,2,1,15,2,15,1,2,1,1,1,1},
            {1,1,1,1,2,2,2,2,2,2,2,1,1,1,1}};


		tiles = new Tile[map.length][map[0].length];
		generateMap(map);
		Maze maze = new Maze(tiles, player,crateList);
		Level level = new Level(2 , maze, System.currentTimeMillis(), 0, 60);
		SaveUtils.saveGame(level, "Level 1");
	}

  /**
   * Generate a map from 2d array
   * @param map
   */
  public void generateMap(Integer[][] map){
    for(int i = 0; i < map.length; i++){
      for(int j = 0; j < map[0].length; j++){
        tiles[i][j] = parseType(i,j,map[i][j]);
        if(map[i][j] == 19){
          crateList.add(new Crate(new Coordinate(i,j)));
        }
      }
    }


  }

  /**
   * Convert 2d map element to Tile
   * @param row
   * @param col
   * @param type
   * @return
   */
  public Tile parseType(int row, int col, int type){
    if( type == 1){
      return new Tile(new Coordinate(row, col), Tile.TileType.FLOOR);
    }else if(type == 2){
      return new Tile(new Coordinate(row, col), Tile.TileType.WALL);
    }else if(type == 3){
      return new Tile(new Coordinate(row, col), Tile.TileType.GOAL);
    }else if(type == 4){
      return new HintTile(new Coordinate(row, col), "Collect Treasures to Unlock the Treasure Door! Collect the right coloured keys to unlock doors");
    }else if(type == 5){
        return new Tile(new Coordinate(row, col), Tile.TileType.FIRE);
    }else if(type == 6){
        return new Tile(new Coordinate(row, col), Tile.TileType.ICE);
    }else if (type ==19) {
      return new Tile(new Coordinate(row, col), Tile.TileType.FLOOR);
    }else {
      Tile t = new Tile(new Coordinate(row, col), Tile.TileType.FLOOR);
      if (type == 7) {
        t.setEntity(new Treasure());
        return t;
      } else if (type == 8) {
        t.setEntity(new TreasureDoor());
        return t;
      }
      else if (type ==9) {
        t.setEntity(new KeyDoor(BasicColor.RED));
        return t;
      }
      else if (type ==10) {
        t.setEntity(new KeyDoor(BasicColor.YELLOW));
        return t;
      }
      else if (type ==11) {
        t.setEntity(new KeyDoor(BasicColor.GREEN));
        return t;
      }
      else if (type ==12) {
        t.setEntity(new KeyDoor(BasicColor.BLUE));
        return t;
      }else if (type ==13) {
        t.setEntity(new Key(BasicColor.RED));
        return t;
      }else if (type ==14) {
        t.setEntity(new Key(BasicColor.YELLOW));
        return t;
      }else if (type ==15) {
        t.setEntity(new Key(BasicColor.GREEN));
        return t;
      }else if (type ==16) {
        t.setEntity(new Key(BasicColor.BLUE));
        return t;
      }else if (type ==17) {
        t.setEntity(new FireBoots());
        return t;
      }else if (type ==18) {
        t.setEntity(new IceBoots());
        return t;
      }
    }
    return null;
  }

  public Tile[][] getTiles() {
    return tiles;
  }

}
