package nz.ac.vuw.ecs.swen225.a3.maze;

import javax.json.Json;
import javax.json.JsonObject;

import nz.ac.vuw.ecs.swen225.a3.persistence.Saveable;

/**
 * A class that provides where skeletons are able to move and allows them
 * to be saved. 
 * 
 * @author Teagan Stewart - 300407769
 *
 */
public class Skeleton extends Moveable implements Saveable {

	/**
	 * The constructor for a Skeleton object.
	 * 
	 * @param coordinate Coordinate skeleton spawns at.
	 * @param start The direction skeleton moves in at start.
	 */
	public Skeleton(Coordinate coordinate, Direction start) {
		super(coordinate);
		super.setDirection(start);
	}

	@Override
	public boolean canWalkOn(Entity entity) {
		if (entity == null)
			return true;
		
		if(entity instanceof Key || entity instanceof Treasure || 
				entity instanceof FireBoots || entity instanceof IceBoots) 
			return true;
		
		return false;
	}

	/**
	 * Serialise a skeleton object to json object for the purpose of saving.
	 * @return Json object skeleton.
	 */
	@Override
	public JsonObject toJSON() {
		JsonObject jsonObject = Json.createObjectBuilder()
				.add("coordinate", getCoordinate().toJSON())
				.add("direction", getDirection().toString())
				.build();
		return jsonObject;
	}

}
