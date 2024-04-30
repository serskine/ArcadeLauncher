package launcher.framework.draw.widget;

import launcher.framework.draw.Draw;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class BoxWidget extends DrawWidget {

    private double width, height;

    public BoxWidget(Widget parent) {
        super(parent);
    }

    @Override
    public void onTick() {

    }

    @Override
    public void onRender(Graphics2D g) {
        Draw.renderRectangle(g, getAbsoluteContainingBox(), getLineColor(), getFillColor());
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public final Rectangle2D getAbsoluteContainingBox() {
        final Point2D absoluteLocation = getAbsoluteLocation();
        return new Rectangle2D.Double(absoluteLocation.getX(), absoluteLocation.getY(), getWidth(), getHeight());
    }

    public final Rectangle2D getRelativeContainingBox() {
        final Point2D relativeLocation = getRelativeLocation();
        return new Rectangle2D.Double(relativeLocation.getX(), relativeLocation.getY(), getWidth(), getHeight());
    }
}
