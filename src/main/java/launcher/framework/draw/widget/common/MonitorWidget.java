package launcher.framework.draw.widget.common;

import launcher.framework.draw.Draw;
import launcher.framework.draw.widget.Widget;
import launcher.framework.util.Geom;

import java.awt.*;

public class MonitorWidget extends Widget {


    public static final Color COLOR_ERROR = Color.RED;
    public static final Color COLOR_ACTIVE = Color.GREEN;
    public static final Color COLOR_FOREGROUND = Color.YELLOW;
    public static final Color COLOR_BACKGROUND = Color.BLACK;
    public static final Color COLOR_ACCENT = Color.WHITE;


    public static final int UNIT = 18;

    public static final int FLIPPER_WIDTH = UNIT * 12;
    public static final int FLIPPER_HEIGHT = UNIT;
    public static final int JOYSTICK_RADIUS = UNIT*3;
    public static final int JOYSTICK_DIAM = JOYSTICK_RADIUS * 2;
    public static final int BUTTON_RADIUS = UNIT;
    public static final int BUTTON_DIAM = BUTTON_RADIUS * 2;
    public static final int COIN_SLOT_WIDTH = BUTTON_DIAM;
    public static final int COIN_SLOT_HEIGHT = COIN_SLOT_WIDTH * 3 / 2;
    public static final int CONTROLLER_HEIGHT = (2 * UNIT) + Math.max(
            JOYSTICK_DIAM,
            (2 * BUTTON_DIAM) + BUTTON_RADIUS + UNIT + FLIPPER_HEIGHT
    );
    public static final int CONTROLLER_WIDTH = UNIT + JOYSTICK_DIAM + (UNIT + BUTTON_DIAM) * 6 + UNIT + COIN_SLOT_WIDTH;

    public static final int CONTROLS_AREA_WIDTH = CONTROLLER_WIDTH;
    public static final int CONTROLS_AREA_HEIGHT = CONTROLLER_HEIGHT*2 + UNIT + FLIPPER_HEIGHT + UNIT;

    public static final float JOYSTICK_THICKNESS = 10f;
    public static final int MONITOR_AREA_HEIGHT = CONTROLS_AREA_HEIGHT;
    public static final int MONITOR_AREA_WIDTH = 3 * MONITOR_AREA_HEIGHT / 4;
    public static final int BINDINGS_AREA_WIDTH = MONITOR_AREA_WIDTH + UNIT + CONTROLS_AREA_WIDTH;

    public static final int BINDING_WIDTH = BINDINGS_AREA_WIDTH /2;
    public static final int BINDING_HEIGHT = UNIT*2;

    public static final int TEXT_HEIGHT_TITLE = UNIT*2;
    public static final int TEXT_HEIGHT_MONITOR = TEXT_HEIGHT_TITLE;
    public static final int TEXT_HEIGHT_BINDING = UNIT;

    private Dimension monitorSize = null;
    private void renderMonitorState(Graphics2D g, Point p, Dimension screenSize) {
        int x = p.x;
        int y = p.y;
        int nW = MONITOR_AREA_WIDTH;
        int nH = MONITOR_AREA_HEIGHT;

        g.setColor(screenSize!=null ? COLOR_ACTIVE : COLOR_ERROR);

        g.fillRect(x, y, nW, nH);

        g.setColor(COLOR_FOREGROUND);
        g.drawRect(x, y, nW, nH);

        final String wText = "" + ((screenSize==null) ? "Unknown" : screenSize.width);
        final String hText = "" + ((screenSize==null) ? "Unknown" : screenSize.height);
        final String opText = "x";


//        if (screenSize!=null) {
            Dimension wSize = Draw.getTextSize(TEXT_HEIGHT_MONITOR, wText);
            Dimension hSize = Draw.getTextSize(TEXT_HEIGHT_MONITOR, hText);
            Dimension opSize = Draw.getTextSize(TEXT_HEIGHT_MONITOR, opText);

            final int pWidthX = p.x + (MONITOR_AREA_WIDTH - wSize.width) / 2;
            final int pOpX = p.x + (MONITOR_AREA_WIDTH - opSize.width) / 2;
            final int pHeightX = p.x + (MONITOR_AREA_WIDTH - hSize.width) / 2;

            final int innerHeight = MONITOR_AREA_HEIGHT - wSize.height - opSize.height - hSize.height - TEXT_HEIGHT_MONITOR - TEXT_HEIGHT_MONITOR;
            final int pWidthY = p.y + (MONITOR_AREA_HEIGHT - innerHeight) / 2;
            final int pOpY = pWidthY + wSize.height + TEXT_HEIGHT_MONITOR;
            final int pHeightY = pOpY + opSize.height + TEXT_HEIGHT_MONITOR;

            final Point pWidth = new Point(pWidthX, pWidthY);
            final Point pOp = new Point(pOpX, pOpY);
            final Point pHeight = new Point(pHeightX, pHeightY);

            Draw.renderRetroText(g, pWidth, TEXT_HEIGHT_MONITOR, COLOR_ACCENT, COLOR_FOREGROUND, wText);
            Draw.renderRetroText(g, pOp, TEXT_HEIGHT_MONITOR, COLOR_ACCENT, COLOR_FOREGROUND, opText);
            Draw.renderRetroText(g, pHeight, TEXT_HEIGHT_MONITOR, COLOR_ACCENT, COLOR_FOREGROUND, hText);
//        }
    }

    public MonitorWidget(final Widget parent) {
        super(parent);
    }
    @Override
    public void onTick() {

    }

    @Override
    public void onRender(Graphics2D g) {
        final Point p = Geom.snap(getAbsoluteLocation());
        final Dimension d = getScreenSize();
        renderMonitorState(g, p, d);
    }

    @Override
    public Dimension getScreenSize() {
        return monitorSize;
    }

    public final void setMonitorSize(Dimension monitorSize) {
        this.monitorSize = monitorSize;
    }

    public final Dimension getMonitorSize() {
        return this.monitorSize;
    }
}
