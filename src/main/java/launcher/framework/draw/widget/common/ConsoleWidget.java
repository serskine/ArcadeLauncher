package launcher.framework.draw.widget.common;

import launcher.framework.draw.widget.*;
import launcher.framework.util.Logger;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ConsoleWidget extends DrawWidget {
    public final TextWidget textWidget;
    private List<String> lines = new LinkedList<>();

    private boolean locationOnBottom = false;

    private int maxLines = -1;
    public ConsoleWidget(Widget parent) {
        super(parent);
        textWidget = new TextWidget(this);
        textWidget.setRelativeLocation(new Point2D.Double(0D, 0D));
        textWidget.setFillColor(Color.YELLOW);
        textWidget.setLineColor(Color.RED);
    }

    @Override
    public void onTick() {

    }

    public void append(final String text) {
        final String[] tokens = text.split("\n");
        for(String token : tokens) {
            lines.add(token);
        }
        pruneToMax();
        updateText();
    }

    @Override
    public void onRender(Graphics2D g) {
        textWidget.onRender(g);
    }

    private void pruneToMax() {
        if (maxLines>=0) {
            while(lines.size()>maxLines) {
                lines.remove(0);
            }
        }
    }

    public int getMaxLines() {
        return maxLines;
    }

    public void setMaxLines(int maxLines) {
        this.maxLines = maxLines;
    }

    private void updateText() {
        final StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for(final String line : lines) {
            if (!isFirst) {
                sb.append('\n');
            }
            sb.append(line);
            isFirst = false;
        }
        textWidget.setText(sb.toString());
        if (locationOnBottom) {
            textWidget.setRelativeLocation(new Point2D.Double(0D, -textWidget.getTextHeight() * lines.size()));
        } else {
            textWidget.setRelativeLocation(new Point2D.Double(0D, 0D));
        }
    }

    public void setLocationOnBottom(boolean locationOnBottom) {
        this.locationOnBottom = locationOnBottom;
    }
}
