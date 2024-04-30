package launcher.framework.draw.widget.common;

import launcher.framework.controls.state.ButtonState;
import launcher.framework.draw.Draw;
import launcher.framework.draw.widget.BoxWidget;
import launcher.framework.draw.widget.Widget;
import launcher.framework.util.Geom;

import java.awt.*;

public class ButtonWidget extends BoxWidget {

    public static final Color COLOR_ERROR = Color.RED;
    public static final Color COLOR_ACTIVE = Color.GREEN;
    public static final Color COLOR_FOREGROUND = Color.YELLOW;
    public static final Color COLOR_BACKGROUND = Color.BLACK;
    public static final int UNIT = 18;
    public static final int BUTTON_RADIUS = UNIT;
    public static final int BUTTON_DIAM = BUTTON_RADIUS * 2;

    private ButtonState buttonState = ButtonState.ERROR;


    public ButtonWidget(Widget parent) {
        super(parent);
    }

    @Override
    public void onRender(Graphics2D g) {
        final Point p = Geom.snap(getAbsoluteLocation());
        renderButton(g, p, getButtonState());
    }

    protected void renderButton(Graphics2D g, Point p, ButtonState buttonState) {
        if (buttonState != null) {
            g.setColor(buttonColor(buttonState));
            g.fillOval(p.x, p.y, BUTTON_DIAM, BUTTON_DIAM);

            g.setColor(COLOR_BACKGROUND);
            g.drawOval(p.x, p.y, BUTTON_DIAM, BUTTON_DIAM);
        }
    }

    protected Color buttonColor(ButtonState buttonState) {
        switch (buttonState) {
            case ERROR:
                return COLOR_ERROR;
            case RELEASED:
                return COLOR_FOREGROUND;
            case PRESSED:
                return COLOR_ACTIVE;
            default:
                throw new IllegalStateException("Unexpected value: " + buttonState);
        }
    }

    public ButtonState getButtonState() {
        return (buttonState==null) ? ButtonState.ERROR : buttonState;
    }

    public void setButtonState(ButtonState buttonState) {
        this.buttonState = buttonState;
    }
}
