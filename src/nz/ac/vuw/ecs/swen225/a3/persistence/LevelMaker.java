package nz.ac.vuw.ecs.swen225.a3.persistence;

import nz.ac.vuw.ecs.swen225.a3.maze.*;

public class LevelMaker {
  Tile[][] tiles;

  public LevelMaker() {

    Player player = new Player(new Coordinate(9, 1));

    Integer[][] map = { {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
            {2,7,7,7,7,1,1,5,2,1,1,1,1,1,1,1,1,1,1,2},
            {2,7,7,7,2,1,1,2,2,1,1,2,1,1,1,2,2,1,1,2},
            {2,7,7,7,4,2,2,1,2,5,12,2,4,4,4,4,2,2,8,2},
            {2,1,1,1,4,1,1,1,2,2,2,2,2,1,2,2,2,11,1,2},
            {2,1,1,1,4,1,2,1,10,1,1,1,2,1,1,1,2,1,1,2},
            {2,1,1,1,4,1,2,1,2,1,1,5,2,5,1,1,2,5,1,2},
            {2,5,2,2,2,2,2,7,2,2,2,2,2,2,2,1,2,2,2,2},
            {2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,1,1},
            {2,3,1,1,1,2,2,2,2,14,2,1,1,1,1,1,2,1,1,1},
            {2,2,1,1,1,1,1,12,2,4,2,1,2,2,2,1,2,1,1,1},
            {2,2,2,9,2,2,1,1,2,13,2,1,2,1,1,5,2,1,1,1},
            {2,5,1,1,2,2,1,1,1,1,1,1,2,1,2,2,2,2,2,2},
            {2,2,1,2,2,2,5,1,1,1,1,1,1,1,8,1,1,1,1,2},
            {2,4,4,4,4,2,2,2,2,2,2,2,1,1,2,1,5,2,1,2},
            {2,2,1,2,2,5,4,1,4,7,4,2,2,2,2,1,2,2,6,2},
            {2,13,1,5,2,2,4,2,4,2,2,4,10,10,10,10,2,1,1,1},
            {2,2,1,2,2,1,4,5,4,2,2,5,10,10,10,10,2,1,1,1},
            {2,1,1,1,9,1,2,1,4,11,2,1,10,10,10,10,2,1,3,1},
            {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,1,1}};


		tiles = new Tile[map.length][map[0].length];
		generateMap(map);
		Maze maze = new Maze(tiles, player,null);
		Level level = new Level(1, maze, System.currentTimeMillis(), 0, 0);
		SaveUtils.saveGame(level, "Level x");
	}

  /**
   * Generate a map from 2d array
   * @param map
   */
  public void generateMap(Integer[][] map){
    for(int i = 0; i < map.length; i++){
      for(int j = 0; j < map[0].length; j++){
        tiles[i][j] = parseType(i,j,map[i][j]);
      }
    }

    //tiles[5][5].setEntity(new Key(BasicColor.BLUE));

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
    } else {
      Tile t = new Tile(new Coordinate(row, col), Tile.TileType.FLOOR);
      if (type == 5) {
        t.setEntity(new Treasure());
        return t;
      } else if (type == 6) {
        t.setEntity(new TreasureDoor());
        return t;
      }
      else if (type ==7) {
        t.setEntity(new KeyDoor(BasicColor.RED));
        return t;
      }
      else if (type ==8) {
        t.setEntity(new KeyDoor(BasicColor.YELLOW));
        return t;
      }
      else if (type ==9) {
        t.setEntity(new KeyDoor(BasicColor.GREEN));
        return t;
      }
      else if (type ==10) {
        t.setEntity(new KeyDoor(BasicColor.BLUE));
        return t;
      }else if (type ==11) {
        t.setEntity(new Key(BasicColor.RED));
        return t;
      }else if (type ==12) {
        t.setEntity(new Key(BasicColor.YELLOW));
        return t;
      }else if (type ==13) {
        t.setEntity(new Key(BasicColor.GREEN));
        return t;
      }else if (type ==14) {
        t.setEntity(new Key(BasicColor.BLUE));
        return t;
      }
    }
    return null;
  }

  public Tile[][] getTiles() {
    return tiles;
  }

}
