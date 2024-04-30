package launcher.framework.draw.widget;

import launcher.framework.Actor;
import launcher.framework.Game;
import launcher.framework.Sprite;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class Widget implements Sprite, Actor {

    private Point2D location = new Point2D.Double(0D, 0D);
    protected Game game;
    private Widget parent;

    public Widget() {
        this.game = null;
        this.parent = null;
    }
    public Widget(final Widget parent) {
        if (parent==null) {
            this.game = (this instanceof Game) ? (Game) this : null;
            this.parent = null;
        } else {
            this.parent = parent;
            this.game = parent.getGame();
        }
    }

    protected final Game getGame() {
        return this.game;
    }

    /**
     * This returns the relative location to the parent widget
     * @return {@link Point2D} relative to the parent location
     */
    public final Point2D getRelativeLocation() {
        return location;
    }

    public final void setRelativeLocation(Point2D location) {
        if (location != null) {
            this.location = location;
        } else {
            this.location = new Point(0,0);
        }
    }

    public Widget getParent() {
        return parent;
    }

    public void setParent(Widget parent) {
        this.parent = parent;
    }

    /**
     * This returns the actual location relative to the game and should match the screen coordinates
     * @return {@link Point2D} representing screen location
     */
    public Point2D getAbsoluteLocation() {
        if (getParent()==null) {
            return getRelativeLocation();
        } else {
            final Point2D parentAbsoluteLocation = parent.getAbsoluteLocation();
            return getAbsolutePoint(parentAbsoluteLocation, getRelativeLocation());
        }
    }

    /**
     * Changes the relative location to it's parent such that it's absolute location is as expected.
     * @param absoluteLocation is the expected absolute location on the screen for the widget
     */
    public final void setAbsoluteLocation(final Point2D absoluteLocation) {
        if (getParent()==null) {
            setRelativeLocation(absoluteLocation);
        } else {
            final Point2D parentAbsoluteLocation = parent.getAbsoluteLocation();
            setRelativeLocation(getRelativePoint(parentAbsoluteLocation, absoluteLocation));
        }
    }

    public static final Point2D getAbsolutePoint(final Point2D parentAbsoluteLocation, final Point2D relativePoint) {
        return new Point2D.Double(
                parentAbsoluteLocation.getX() + relativePoint.getX(),
                parentAbsoluteLocation.getY() + relativePoint.getY()
        );
    }

    public static final Point2D getRelativePoint(final Point2D parentAbsoluteLocation, final Point2D absolutePoint) {
        return new Point2D.Double(
                absolutePoint.getX() - parentAbsoluteLocation.getX(),
                absolutePoint.getY() - parentAbsoluteLocation.getY()
        );
    }

    public Dimension getScreenSize() {
        return game.getScreenSize();
    }

}
