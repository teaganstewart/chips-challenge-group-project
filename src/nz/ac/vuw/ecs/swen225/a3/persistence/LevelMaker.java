package nz.ac.vuw.ecs.swen225.a3.persistence;

import nz.ac.vuw.ecs.swen225.a3.maze.*;

public class LevelMaker {
    Tile[][] tiles;

    public LevelMaker() {

        Player player = new Player(new Coordinate(6, 7));

        Integer[][] map = { {0,0,1,1,1,1,1,0,1,1,1,1,1,0,0},
			                {0,0,1,0,0,0,1,1,1,0,0,0,1,0,0},
			                {0,0,1,0,4,0,1,2,1,0,4,0,1,0,0},
			                {1,1,1,1,1,1,1,0,1,1,1,1,1,1,1},
			                {1,0,0,0,1,0,0,0,0,0,1,0,0,0,1},
			                {1,0,4,0,1,0,0,0,0,0,1,0,4,0,1},
			                {1,1,1,1,1,4,0,0,0,4,1,1,1,1,1},
			                {1,0,4,0,1,0,0,0,0,0,1,0,4,0,1},
			                {1,0,0,0,1,0,0,4,0,0,1,0,0,0,1},
			                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			                {0,0,0,0,1,0,0,1,0,0,1,0,0,0,0},
			                {0,0,0,0,1,0,4,1,4,0,1,0,0,0,0},
			                {0,0,0,0,1,0,0,1,0,0,1,0,0,0,0},
			                {0,0,0,0,1,1,1,1,1,1,1,0,0,0,0}};

        tiles = new Tile[map.length][map[0].length];
        generateMap(map);
        Maze maze = new Maze(tiles, player);
        Level level = new Level(1, maze, System.currentTimeMillis(), 0, 0);
        SaveUtils.saveGame(level);
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
        
        tiles[5][5].setEntity(new Key(BasicColor.YELLOW));
    }

    /**
     * Convert 2d map element to Tile
     * @param row
     * @param col
     * @param type
     * @return
     */
    public Tile parseType(int row, int col, int type){
        if( type == 0){
            return new Tile(new Coordinate(row, col), Tile.TileType.FLOOR);
        }else if(type == 1){
            return new Tile(new Coordinate(row, col), Tile.TileType.WALL);
        }else if(type == 2){
            return new Tile(new Coordinate(row, col), Tile.TileType.GOAL);
        }else if(type == 3){
            return new HintTile(new Coordinate(row, col), "Collect Treasures to Unlock the Treasure Door! Collect the right coloured keys to unlock doors");
        } else {
            Tile t = new Tile(new Coordinate(row, col), Tile.TileType.FLOOR);
            if (type == 4) {
                t.setEntity(new Treasure());
                return t;
            } else if (type == 5) {
                t.setEntity(new TreasureDoor());
                return t;
            }
        }
        return null;
    }
    
    public Tile[][] getTiles() {
    	return tiles;
    }


}
