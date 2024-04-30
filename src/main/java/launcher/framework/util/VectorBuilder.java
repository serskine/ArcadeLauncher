package launcher.framework.util;

import java.awt.geom.Point2D;

public class VectorBuilder {

    private double[] v;
    public VectorBuilder() {
        this(2);
    }
    public VectorBuilder(final int numDimensions) {
        v = new double[numDimensions];
    }
    public VectorBuilder(final Point2D p) {
        this(2);
        v[0] = p.getX();
        v[1] = p.getY();
    }

    public VectorBuilder translate(double... t) {
        verifyDimensions(t);
        for(int i=0; i<v.length; i++) {
            v[i] += t[i];
        }
        return this;
    }

    public VectorBuilder translate(Point2D t) {
        return translate(t.getX(), t.getY());
    }
    public VectorBuilder scale(double scalar) {
        for(int i = 0; i< v.length; i++) {
            v[i] *= scalar;
        }
        return this;
    }

    public VectorBuilder reflect() {
        return scale(-1);
    }

    void verifyDimensions(double... dimensions) {
        if (dimensions==null) {
            throw new IllegalArgumentException("Require " + v.length + " dimensions.");
        }
    }

    public Point2D buildPoint2D() {
        return new Point2D.Double(v[0], v[1]);
    }

    public double[] build() {
        return v;
    }
}
