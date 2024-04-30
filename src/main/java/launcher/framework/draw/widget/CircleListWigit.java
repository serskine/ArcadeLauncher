package launcher.framework.draw.widget;

import launcher.framework.util.HorizontalAlignment;
import launcher.framework.util.VerticalAlignment;

import java.awt.*;
import java.awt.geom.Point2D;

public class CircleListWigit extends SwitchWidget {

    private Point2D previousPos, currentPos, nextPos;

    private boolean isVertical = true;
    private HorizontalAlignment horizontalAlignment = HorizontalAlignment.LEFT;
    private VerticalAlignment verticalAlignment = VerticalAlignment.MIDDLE;

    public CircleListWigit(Widget parent) {
        super(parent);
    }

    public final Integer getPreviousIndex() {
        final int size = widgetList.size();
        if (size<1) {
            return null;
        }
        final int selectedIndex = getSelectedIndex();
        return (widgetList.size() + selectedIndex - 1) % selectedIndex;
    }

    public final Integer getNextIndex() {
        final int size = widgetList.size();
        if (size<1) {
            return null;
        }
        final int selectedIndex = getSelectedIndex();
        return (widgetList.size() + selectedIndex + 1) % selectedIndex;
    }

    public Widget getPreviousWidget() {
        final Integer previousIndex = getPreviousIndex();
        if (previousIndex==null) {
            return null;
        } else {
            return widgetList.get(previousIndex);
        }
    }
    public Widget getNextWidget() {
        final Integer nextIndex = getNextIndex();
        if (nextIndex==null) {
            return null;
        } else {
            return widgetList.get(nextIndex);
        }
    }


    public Point2D getNextPos() {
        return nextPos;
    }

    public void setNextPos(Point2D nextPos) {
        this.nextPos = nextPos;
    }

    public Point2D getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(Point2D currentPos) {
        this.currentPos = currentPos;
    }

    public Point2D getPreviousPos() {
        return previousPos;
    }

    public void setPreviousPos(Point2D previousPos) {
        this.previousPos = previousPos;
    }

    @Override
    public void onRender(Graphics2D g) {
        final Widget previousWidget = getPreviousWidget();
        final Widget selectedWidget = getSelectedWidget();
        final Widget nextWidget = getNextWidget();

        // It's possible the same widget will be rendered three times in three seperate positions
        if (previousWidget!=null) {
            previousWidget.setRelativeLocation(previousPos);
            previousWidget.onRender(g);
        }
        if (nextWidget!=null) {
            nextWidget.setRelativeLocation(nextPos);
            nextWidget.onRender(g);
        }
        if (selectedWidget!=null) {
            selectedWidget.setRelativeLocation(currentPos);
            selectedWidget.onRender(g);
        }
    }

    public VerticalAlignment getVerticalAlignment() {
        return verticalAlignment;
    }

    public void setVerticalAlignment(VerticalAlignment verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
    }

    public HorizontalAlignment getHorizontalAlignment() {
        return horizontalAlignment;
    }

    public void setHorizontalAlignment(HorizontalAlignment horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
    }

    public boolean isVertical() {
        return isVertical;
    }

}
