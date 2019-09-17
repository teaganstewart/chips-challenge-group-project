package nz.ac.vuw.ecs.swen225.a3.maze;


public class Player extends Entity {
	
	public Player(int row, int col) {
		super(row, col);
		// TODO Auto-generated constructor stub
	}
	private int row, col;
	private int totalMoves;
	@Override
	public void onTouch(Entity e) {
		// TODO Auto-generated method stub
		e.onTouch(this);
	}

	
}
