package launcher.framework.draw.widget;

import java.awt.*;

public abstract class DrawWidget extends Widget {
    private Color lineColor = null;
    private Color fillColor = null;

    public DrawWidget(Widget parent) {
        super(parent);
    }

    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

}
