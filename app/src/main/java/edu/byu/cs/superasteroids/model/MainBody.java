package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;
import android.graphics.RectF;

import edu.byu.cs.superasteroids.model.MovingObject;

/**
 * Created by ctrumbo on 7/12/16.
 *
 * The ship's main body, attaches to engine, cannon, and extra part.
 * Constructed during ship builder.
 * Lives through game play.
 *
 */
public class MainBody extends MovingObject{
    private PointF cannonAttach = new PointF(0,0);
    private PointF engineAttach = new PointF(0,0);
    private PointF extraAttach = new PointF(0,0);

    public MainBody(int id, int cannonX, int engineX, int extraX, int cannonY, int engineY, int extraY, String image, int imageWidth, int imageHeight) {
        this(id, cannonX, engineX, extraX, cannonY, engineY, extraY, 0, 0, new RectF(0f,0f,0f,0f), new PointF(0,0), image, imageWidth, imageHeight);
    }


    public MainBody(int id, int cannonAttachX, int engineAttachX,
                    int extraAttachX, int cannonAttachY, int engineAttachY, int extraAttachY,
                    float direction, int speed, RectF boundary,
                    PointF position, String image, int imageWidth, int imageHeight) {
        super(id, position, image, imageWidth, imageHeight, direction, speed, boundary);
        this.cannonAttach = new PointF(cannonAttachX, cannonAttachY);
        this.engineAttach = new PointF(engineAttachX, engineAttachY);
        this.extraAttach = new PointF(extraAttachX, extraAttachY);
    }

}
