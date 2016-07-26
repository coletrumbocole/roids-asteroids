package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;
import android.graphics.RectF;

import edu.byu.cs.superasteroids.model.MovingObject;

/**
 * Created by ctrumbo on 7/12/16.
 *
 * The non-cannon wing of the ship.
 * Constructed during ship builder.
 * Lives through game play.
 */
public class ExtraPart extends MovingObject{
    private PointF attachPoint;

    public ExtraPart(int id, int attachX, int attachY, String image, int imageWidth, int imageHeight) {
        this(id, attachX, attachY, 0f, 0, new RectF(0f,0f,0f,0f), new PointF(0,0), image, imageWidth, imageHeight);
    }


    public ExtraPart(int id, int attachX, int attachY,
                     float direction, int speed, RectF boundary,
                     PointF position, String image, int imageWidth, int imageHeight) {
        super(id, position, image, imageWidth, imageHeight, direction, speed, boundary);
        this.attachPoint = new PointF(attachX, attachY);
    }
}
