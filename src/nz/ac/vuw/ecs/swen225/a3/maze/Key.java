package nz.ac.vuw.ecs.swen225.a3.maze;

public class Key extends Entity {
	
	final BasicColor color;
	
	public Key (int row, int col, BasicColor color) {
		super(row,col);
		this.color = color;
	}
	
	BasicColor getColor() {
		return color;
	}
	
}
