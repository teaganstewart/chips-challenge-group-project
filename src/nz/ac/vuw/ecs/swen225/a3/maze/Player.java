package nz.ac.vuw.ecs.swen225.a3.maze;

import java.util.List;


public class Player extends Entity {
	

	private int row, col;
	private int totalMoves;
	private Direction direction;
	
	private List<Entity> inventory;
	
	public Player(int row, int col) {
		super(row, col);
	}
	
	public void setInventory(List<Entity> inv) {
		inventory = inv;
	}
	
	public List<Entity> getInventory() {
		return inventory;
	}
	
	public Direction getDirection(){
		return direction;
	}
	
	public void setDirection(Direction dir){
		direction = dir;
	}
	
	
	@Override
	public void onTouch(Entity e) {
		// TODO Auto-generated method stub
		e.onTouch(this);
	}

	
}
