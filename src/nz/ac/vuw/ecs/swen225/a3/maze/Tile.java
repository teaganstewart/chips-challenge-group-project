package nz.ac.vuw.ecs.swen225.a3.maze;

public class Tile {




	private Entity entity;
	private boolean wall;
	
	public Tile(boolean wall) {
		this.wall = wall;
		entity = null;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}
}
