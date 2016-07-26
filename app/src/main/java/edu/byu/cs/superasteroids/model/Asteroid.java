package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;
import android.graphics.RectF;

import edu.byu.cs.superasteroids.model.MovingObject;

/**
 * Created by ctrumbo on 7/12/16.
 *
 * Holds info about the damage taken, behavior of different types of asteroids.
 */
public class Asteroid extends MovingObject {
    String type = "";
    String name = "";
    int hitsTaken = 0;

    public Asteroid (int id, String name, String image, int imageWidth, int imageHeight, String type) {
        this(id, name, type, 0, 0f, 0, new RectF(1f,1f,1f,1f), new PointF(0,0), image, imageWidth, imageHeight);
    }


    public Asteroid (int id, String name, String type, int hitsTaken,
                    float direction, int speed, RectF boundary,
                    PointF position, String image, int imageWidth, int imageHeight) {
        super(id, position, image, imageWidth, imageHeight, direction, speed, boundary);
        this.name = name;
        this.type = type;
        this.hitsTaken = hitsTaken;
    }

}
