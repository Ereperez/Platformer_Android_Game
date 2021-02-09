package com.ereperez.platformer.entities;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;

import com.ereperez.platformer.Game;
import com.ereperez.platformer.GameSettings;

public abstract class Entity {
    static final String TAG = "Entity";
    static final float DEFAULT_DIMENSION = GameSettings.DEFAULT_DIMENSION;
    public static Game game = null; //shared ref, managed by the Game-class!
    public float x = 0;
    public float y = 0;
    public float width = DEFAULT_DIMENSION;
    public float height = DEFAULT_DIMENSION;

    public void update(final double dt) {}
    public void render(final Canvas canvas, final Matrix transform, final Paint paint) {}
    public void onCollision(final Entity that) {}
    public void destroy() {}

    public float left() {
        return x;
    }
    public float right() {
        return x + width;
    }
    public float top() {
        return y;
    }
    public float bottom() {
        return y + height;
    }
    public float centerX() {
        return x + (width * 0.5f);
    }
    public float centerY() {
        return y + (height * 0.5f);
    }

    public void setLeft(final float leftEdgePosition) {
        x = leftEdgePosition;
    }
    public void setRight(final float rightEdgePosition) {
        x = rightEdgePosition - width;
    }
    public void setTop(final float topEdgePosition) {
        y = topEdgePosition;
    }
    public void setBottom(final float bottomEdgePosition) {
        y = bottomEdgePosition - height;
    }
    public void setCenter(final float x, final float y) {
        this.x = x - (width * 0.5f);
        this.y = y - (height * 0.5f);
    }

    public boolean isColliding(final Entity that) {
        if (this == that) {
            throw new AssertionError("isColliding: You shouldn't test Entities against themselves!");
        }
        return Entity.isAABBOverlapping(this, that);
    }

    //Some good reading on bounding-box intersection tests:
    //https://gamedev.stackexchange.com/questions/586/what-is-the-fastest-way-to-work-out-2d-bounding-box-intersection
    static boolean isAABBOverlapping( final Entity a, final Entity b) {
        return !(a.right() <= b.left()
                || b.right() <= a.left()
                || a.bottom() <= b.top()
                || b.bottom() <= a.top());
    }

    //AABB intersection test.
    //returns true on intersection, and sets the least intersecting axis in overlap
    static final PointF overlap = new PointF( 0 , 0 ); //Q&D PointF pool for collision detection. Assumes single threading.
    @SuppressWarnings ( "UnusedReturnValue" )
    static boolean getOverlap(final Entity a, final Entity b, final PointF overlap) {
        overlap.x = 0.0f;
        overlap.y = 0.0f;
        final float centerDeltaX = a.centerX() - b.centerX();
        final float halfWidths = (a.width + b.width) * 0.5f;
        float dx = Math.abs(centerDeltaX); //cache the abs, we need it twice

        if (dx > halfWidths) return false ; //no overlap on x == no collision

        final float centerDeltaY = a.centerY() - b.centerY();
        final float halfHeights = (a.height + b.height) * 0.5f;
        float dy = Math.abs(centerDeltaY);

        if (dy > halfHeights) return false ; //no overlap on y == no collision

        dx = halfWidths - dx; //overlap on x
        dy = halfHeights - dy; //overlap on y
        if (dy < dx) {
            overlap.y = (centerDeltaY < 0 ) ? -dy : dy;
        } else if (dy > dx) {
            overlap.x = (centerDeltaX < 0 ) ? -dx : dx;
        } else {
            overlap.x = (centerDeltaX < 0 ) ? -dx : dx;
            overlap.y = (centerDeltaY < 0 ) ? -dy : dy;
        }
        return true ;
    }
}
