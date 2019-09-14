package nz.ac.vuw.ecs.swen225.a3.maze;

public abstract class Entity {
		
	private int row, col;
	
	public Entity(int row, int col) {
		setRow(row);
		setCol(col);
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public void setCol(int col) {
		this.col = col;
	}
	
	/**
	 * If we ever need to actually overwrite the toString method (which we probably will for saving)
	 */
	//public abstract String toString();
	
}
