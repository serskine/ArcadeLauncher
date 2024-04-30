package launcher.framework.draw.widget.common;

import launcher.framework.controls.state.ButtonState;
import launcher.framework.draw.widget.Widget;
import launcher.framework.util.Geom;

import java.awt.*;

public class FlipperWidget extends ButtonWidget {
    public static final int UNIT = 18;

    public static final Color COLOR_ERROR = Color.RED;
    public static final Color COLOR_ACTIVE = Color.GREEN;
    public static final Color COLOR_FOREGROUND = Color.YELLOW;

    public static final int FLIPPER_WIDTH = UNIT * 12;
    public static final int FLIPPER_HEIGHT = BUTTON_DIAM;

    private boolean isLeftFlipper = false;

    public FlipperWidget(Widget parent) {
        super(parent);
    }

    @Override
    public void onRender(Graphics2D g) {
        Point p = Geom.snap(getAbsoluteLocation());
        renderFlipper(g, p, getButtonState());
    }

    private void renderFlipper(Graphics2D g, Point p, ButtonState leftFlipper) {

        switch(leftFlipper) {
            case RELEASED:
                g.setColor(COLOR_FOREGROUND);
                break;
            case PRESSED:
                g.setColor(COLOR_ACTIVE);
                break;
            case ERROR:
                g.setColor(COLOR_ERROR);
                break;
        }

        final int flipperBallDiameter = FLIPPER_HEIGHT;
        final int flipperUnit = flipperBallDiameter/4;

        if (isLeftFlipper) {
            g.fillOval(p.x, p.y, flipperBallDiameter, flipperBallDiameter);
            g.fillRect(p.x + flipperUnit, p.y + flipperUnit, FLIPPER_WIDTH - flipperUnit, flipperUnit*2);
        } else {
            g.fillOval(p.x+FLIPPER_WIDTH - flipperBallDiameter, p.y, flipperBallDiameter, flipperBallDiameter);
            g.fillRect(p.x, p.y + flipperUnit, FLIPPER_WIDTH - flipperUnit, flipperUnit*2);
        }
    }

    public boolean isLeftFlipper() {
        return isLeftFlipper;
    }

    public void setLeftFlipper(boolean leftFlipper) {
        isLeftFlipper = leftFlipper;
    }
}
