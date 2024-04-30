package launcher.framework.draw.widget.common;

import launcher.framework.controls.state.ButtonState;
import launcher.framework.draw.widget.Widget;
import launcher.framework.util.Geom;

import java.awt.*;

public class CoinSlotWidget extends ButtonWidget {
    public static final int COIN_SLOT_WIDTH = BUTTON_DIAM;
    public static final int COIN_SLOT_HEIGHT = COIN_SLOT_WIDTH * 3 / 2;

    public CoinSlotWidget(Widget parent) {
        super(parent);
    }

    @Override
    public void onRender(Graphics2D g) {
        final Point p = Geom.snap(getAbsoluteLocation());
        renderCoinSlot(g, p, getButtonState());
    }

    private void renderCoinSlot(Graphics2D g, Point p, ButtonState buttonState) {

        g.setColor(buttonColor(buttonState));
        g.fillRect(p.x, p.y, COIN_SLOT_WIDTH, COIN_SLOT_HEIGHT);

        final int innerPadding = COIN_SLOT_WIDTH/4;
        final int innerWidth = COIN_SLOT_WIDTH/6;
        final int innerHeight = COIN_SLOT_HEIGHT - (innerPadding * 2);
        final int innerX = p.x + COIN_SLOT_WIDTH - innerPadding - innerWidth;
        final int innerY = p.y + innerPadding;

        g.setColor(COLOR_BACKGROUND);
        g.fillRect(innerX, innerY, innerWidth, innerHeight);
    }
}
