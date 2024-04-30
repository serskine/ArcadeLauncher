package launcher.framework.util;

import java.awt.*;
import java.awt.geom.Point2D;

public class Geom {
    public static Point snap(Point2D p) {
        if (p==null) {
            return null;
        }
        return new Point(snap(p.getX()), snap(p.getY()));
    }

    public static int snap(double v) { return Math.toIntExact(Math.round(v));   }

    public static Point2D sumVectors(Point2D... points) {
        double x=0;
        double y=0;
        for(Point2D p : points) {
            x += p.getX();
            y += p.getY();
        }
        return new Point2D.Double(x, y);
    }
}
