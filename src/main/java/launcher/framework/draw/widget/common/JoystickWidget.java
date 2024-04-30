package launcher.framework.draw.widget.common;

import launcher.framework.controls.state.ButtonJoystickState;
import launcher.framework.draw.widget.Widget;
import launcher.framework.util.Geom;

import java.awt.*;

public class JoystickWidget extends Widget {
    public static final Color COLOR_BACKGROUND = Color.BLACK;
    public static final int UNIT = 18;
    public static final int JOYSTICK_RADIUS = UNIT*3;
    public static final int JOYSTICK_DIAM = JOYSTICK_RADIUS * 2;
    public static final float JOYSTICK_THICKNESS = 10f;
    private ButtonJoystickState buttonJoystickState = ButtonJoystickState.ERROR;

    @Override
    public void onTick() {

    }

    @Override
    public void onRender(Graphics2D g) {
        final Point p = Geom.snap(getAbsoluteLocation());
        renderJoystick(g, p, getButtonJoystickState());
    }

    private void renderJoystick(Graphics2D g, Point p, ButtonJoystickState buttonJoystickState) {

        if (buttonJoystickState==null) {
            buttonJoystickState = ButtonJoystickState.ERROR;
        }

        final int innerRadius = JOYSTICK_RADIUS/4;
        final int innerDiam = innerRadius * 2;
        final int innerX = p.x + JOYSTICK_RADIUS - innerRadius;
        final int innerY = p.y + JOYSTICK_RADIUS - innerRadius;


        switch(buttonJoystickState) {
            case ERROR:
                g.setColor(Color.RED);
                break;
            case NEUTRAL:
                g.setColor(Color.YELLOW);
                break;
            default:
                g.setColor(Color.GREEN);
                break;
        }


        g.fillOval(p.x, p.y, JOYSTICK_DIAM, JOYSTICK_DIAM);

        g.setColor(COLOR_BACKGROUND);
        g.drawOval(p.x, p.y, JOYSTICK_DIAM, JOYSTICK_DIAM);

        final Point p1 = new Point(p.x + JOYSTICK_RADIUS, p.y + JOYSTICK_RADIUS);

        g.setColor(COLOR_BACKGROUND);
        g.fillOval(innerX, innerY, innerDiam, innerDiam);

        final Point p2 = getJoystickEnd(p1, buttonJoystickState);
        final Stroke prevStroke = g.getStroke();
        final Stroke thickStroke = new BasicStroke(JOYSTICK_THICKNESS);
        g.setStroke(thickStroke);

        g.drawLine(p1.x, p1.y, p2.x, p2.y);

        g.setStroke(prevStroke);
    }

    Point getJoystickEnd(Point p1, ButtonJoystickState buttonJoystickState) {
        final int unit = JOYSTICK_RADIUS;
        final int diag = (int) (unit * Math.sqrt(2D) / 2D);
        final int none = 0;

        switch (buttonJoystickState) {
            case ERROR:     return p1;
            case NEUTRAL:   return p1;
            case N:         return new Point(p1.x + none, p1.y - unit);
            case NE:        return new Point(p1.x + diag, p1.y - diag);
            case E:         return new Point(p1.x + unit, p1.y - none);
            case SE:        return new Point(p1.x + diag, p1.y + diag);
            case S:         return new Point(p1.x + none, p1.y + unit);
            case SW:        return new Point(p1.x - diag, p1.y + diag);
            case W:         return new Point(p1.x - unit, p1.y + none);
            case NW:        return new Point(p1.x - diag, p1.y - diag);
            default:        return p1;
        }
    }

    public ButtonJoystickState getButtonJoystickState() {
        return buttonJoystickState;
    }

    public void setButtonJoystickState(ButtonJoystickState buttonJoystickState) {
        this.buttonJoystickState = buttonJoystickState;
    }
}
