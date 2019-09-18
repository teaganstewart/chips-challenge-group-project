package nz.ac.vuw.ecs.swen225.a3.maze;

public class Tile {

	private Entity entity;
	private int row;
	private int col;
	private boolean wall;
	
	public Tile(boolean wall) {
		this.wall = wall;
		entity = null;
	}

	/* Getters and Setters*/

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
}
