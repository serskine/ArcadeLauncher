package launcher.framework.draw.widget;

import launcher.framework.draw.Draw;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class ImageWidget extends Widget {
    private BufferedImage image;

    private double width, height;
    public ImageWidget(Widget parent) {
        super(parent);
    }

    @Override
    public void onTick() {

    }

    @Override
    public void onRender(Graphics2D g) {
        if (image != null) {
            final Point2D absoluteLocation = getAbsoluteLocation();
            Draw.drawImage(g, getAbsoluteBounds(), image);
        }
    }

    public Image getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Rectangle2D getAbsoluteBounds() {
        final Point2D absoluteLocation = getAbsoluteLocation();
        return new Rectangle2D.Double(
            absoluteLocation.getX(),
            absoluteLocation.getY(),
            width, height
        );
    }

    public Rectangle2D getRelativeBounds() {
        final Point2D relativeLocation = getRelativeLocation();
        return new Rectangle2D.Double(
                relativeLocation.getX(),
                relativeLocation.getY(),
                getWidth(),
                getHeight()
        );
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public final double getWidth() {
        return width;
    }

    public final double getHeight() {
        return height;
    }
}
