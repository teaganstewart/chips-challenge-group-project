package nz.ac.vuw.ecs.swen225.a3.maze;

public abstract class Entity {


    private boolean pickupable;

    public Entity(boolean pickupable){
        this.pickupable = pickupable;
    }

    /* Getters and Setters*/
    public boolean isPickupable() {
        return pickupable;
    }
}
