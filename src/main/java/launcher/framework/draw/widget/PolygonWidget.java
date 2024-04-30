package launcher.framework.draw.widget;

import launcher.framework.draw.Draw;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PolygonWidget extends DrawWidget {

    private final List<Point2D> relativePoints = new ArrayList<>();
    public PolygonWidget(Widget parent) {
        super(parent);
    }

    @Override
    public void onTick() {

    }

    @Override
    public void onRender(Graphics2D g) {
        final List<Point2D> absolutePoints = getAbsolutePoints();
        Draw.renderPolygon(g, getLineColor(), getFillColor(), absolutePoints);
    }

    public List<Point2D> getAbsolutePoints() {
        final Point2D parentAbsoluteLocation = getParent().getAbsoluteLocation();
        return relativePoints.stream().map(relativeLocation -> getAbsolutePoint(parentAbsoluteLocation, relativeLocation)).collect(Collectors.toList());
    }
}
