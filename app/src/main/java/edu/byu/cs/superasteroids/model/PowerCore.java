package edu.byu.cs.superasteroids.model;

import edu.byu.cs.superasteroids.model.MovingObject;

/**
 * Created by ctrumbo on 7/12/16.
 *
 * Determines the laser boost and engine boost of the ship.
 * Constructed during ship builder.
 * Lives through game play.
 */
public class PowerCore {
    private int id;
    private int cannonBoost;
    private int engineBoost;
    private String image;

    public PowerCore(int id, int cannonBoost, int engineBoost, String image) {
        this.id = id;
        this.cannonBoost = cannonBoost;
        this.engineBoost = engineBoost;
        this.image = image;
    }
}
