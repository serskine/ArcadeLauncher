package launcher.framework.draw.widget;

import launcher.framework.draw.Draw;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class CircleWidget extends DrawWidget {

    private double radius = 0D;

    public CircleWidget(Widget parent) {
        super(parent);
    }

    @Override
    public void onTick() {

    }

    @Override
    public void onRender(Graphics2D g) {
        if (radius>0D) {
            Draw.renderOval(g, getAbsoluteContainingBox(), getLineColor(), getFillColor());
        }
    }

    public double getRadius() {
        return radius;
    }

    public double getDiameter() {
        return getRadius()*2D;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public final Rectangle2D getAbsoluteContainingBox() {
        return getContainingBox(getAbsoluteLocation(), getRadius());
    }

    public final Rectangle2D getRelativeContainingBox() {
        return getContainingBox(getRelativeLocation(), getRadius());
    }

    public static final Rectangle2D getContainingBox(Point2D location, double radius) {
        final double diameter = radius*2;
        return new Rectangle2D.Double(
            location.getX() - radius,
            location.getY() - radius,
            diameter,
            diameter
        );
    }
}
