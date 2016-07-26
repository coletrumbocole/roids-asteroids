package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;

import edu.byu.cs.superasteroids.model.PositionedObject;

/**
 * Created by ctrumbo on 7/12/16.
 *
 * Represents other background objects besides the background image.
 */
public class LevelObject extends PositionedObject{
    private Float scale;

    public LevelObject(int id, String image) {
        this(id, image, 0, 0, 1f);
    }

    public LevelObject(int id, String image, int positionX, int positionY, float scaleFloat) {
        super(id, new PointF(positionX, positionY), image, 100, 100);
        this.scale = scaleFloat;
    }
}
