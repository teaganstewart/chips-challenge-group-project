package nz.ac.vuw.ecs.swen225.a3.maze;

import nz.ac.vuw.ecs.swen225.a3.persistence.Saveable;

import javax.json.JsonObject;

/*
* Class which represents a crate that can only be pushed by the player into free floor tiles
* */
public class Crate extends Moveable implements Saveable {

    /**
     * @param coordinate
     */
    public Crate(Coordinate coordinate) {
        super(coordinate);
    }

    @Override
    public boolean canWalkOn(Entity entity) {
        return false;
    }
    
    public String toString() {
    	return "Crate: " + super.getCoordinate().toString() + " moving to " + super.getNextPos();
    }

    @Override
    public JsonObject toJSON() {
        // TODO write this JSON code
        return null;
    }
}