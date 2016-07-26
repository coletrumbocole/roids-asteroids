package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;
import android.graphics.RectF;

import edu.byu.cs.superasteroids.model.MovingObject;

/**
 * Created by ctrumbo on 7/12/16.
 * Determines the ship's speed and turn rate.
 */
public class Engine extends MovingObject{
    private PointF attachPoint;
    private int baseSpeed;
    private int baseTurnRate;

    public Engine (int id, int attachX, int attachY, String image, int imageWidth, int imageHeight, int baseSpeed, int baseTurnRate) {
        this(id, attachX, attachY, baseSpeed, baseTurnRate, 0f, 0, new RectF(0f,0f,0f,0f), new PointF(0,0), image, imageWidth, imageHeight);
    }

    public Engine (int id, int attachX, int attachY,
                   int baseSpeed, int baseTurnRate,
                   float direction, int speed, RectF boundary,
                   PointF position, String image, int imageWidth, int imageHeight) {
        super(id, position, image, imageWidth, imageHeight, direction, speed, boundary);
        this.attachPoint = new PointF(attachX, attachY);
        this.baseSpeed = baseSpeed;
        this.baseTurnRate = baseTurnRate;
    }


}
