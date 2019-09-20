package nz.ac.vuw.ecs.swen225.a3.maze;

public abstract class Moveable {
	
	public Moveable() {
	}
	
	public abstract boolean canWalkOn(Entity entity);

}
