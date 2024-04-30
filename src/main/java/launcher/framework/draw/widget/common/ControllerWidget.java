package launcher.framework.draw.widget.common;

import launcher.framework.controls.GameController;
import launcher.framework.controls.machine.sf2.Sf2AxisId;
import launcher.framework.controls.machine.sf2.Sf2ButtonId;
import launcher.framework.controls.machine.sf2.Sf2JoystickId;
import launcher.framework.controls.state.ButtonJoystickState;
import launcher.framework.controls.state.ButtonState;
import launcher.framework.draw.Draw;
import launcher.framework.draw.widget.Widget;
import launcher.framework.draw.widget.common.JoystickWidget;
import launcher.framework.util.Geom;

import java.awt.*;

public class ControllerWidget extends Widget {
    public static final Color COLOR_ERROR = Color.RED;
    public static final Color COLOR_ACTIVE = Color.GREEN;
    public static final Color COLOR_FOREGROUND = Color.YELLOW;
    public static final Color COLOR_BACKGROUND = Color.BLACK;
    public static final int UNIT = 18;
    public static final int FLIPPER_HEIGHT = UNIT;
    public static final int FLIPPER_WIDTH = UNIT * 12;

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
    private GameController<Sf2ButtonId, Sf2AxisId, Sf2JoystickId> controller;

    private final JoystickWidget joystickWidget;

    private final ButtonWidget extraLeft, extraRight, topLeft, topMiddle, topRight, bottomLeft, bottomMiddle, bottomRight, startButton;
    private final CoinSlotWidget coinSlot;

    private final FlipperWidget flipperLeft, flipperRight;

    public ControllerWidget(final Widget parent) {
        super(parent);

        this.controller = null;
        this.joystickWidget = new JoystickWidget();
        joystickWidget.setParent(this);

        final Point p = Geom.snap(getRelativeLocation());

        final int innerWidth = CONTROLLER_WIDTH - UNIT - UNIT;
        final int innerHeight = CONTROLLER_HEIGHT - UNIT - UNIT;
        final Point pInnerOrigin = new Point(p.x + UNIT, p.y + UNIT);
        final int joyPad = Math.min(innerHeight/2 - JOYSTICK_RADIUS, innerWidth/2 - JOYSTICK_RADIUS);
        final Point pJoy = new Point(pInnerOrigin.x + joyPad, pInnerOrigin.y + joyPad);

        joystickWidget.setRelativeLocation(pJoy);

        final Point pCoinSlot = new Point(pInnerOrigin.x + JOYSTICK_DIAM + UNIT, pInnerOrigin.y + JOYSTICK_RADIUS - BUTTON_RADIUS);
        final Point pStartButton = new Point(pCoinSlot.x + BUTTON_DIAM + UNIT, pCoinSlot.y + JOYSTICK_RADIUS - BUTTON_RADIUS);
        final Point pExtraLeft = new Point(pStartButton.x + COIN_SLOT_WIDTH + UNIT, pStartButton.y);
        final Point pExtraRight = new Point(pExtraLeft.x + BUTTON_DIAM + UNIT, pExtraLeft.y);

        final Point pTopLeftButton = new Point(pExtraRight.x + BUTTON_DIAM + UNIT, pInnerOrigin.y);
        final Point pTopMiddleButton = new Point(pTopLeftButton.x + BUTTON_DIAM + UNIT, pTopLeftButton.y);
        final Point pTopRightButton = new Point(pTopMiddleButton.x + BUTTON_DIAM + UNIT, pTopMiddleButton.y);

        final Point pBottomLeftButton = new Point(pTopLeftButton.x + UNIT, pTopLeftButton.y + BUTTON_DIAM + UNIT);
        final Point pBottomMiddleButton = new Point(pBottomLeftButton.x + BUTTON_DIAM + UNIT, pBottomLeftButton.y);
        final Point pBottomRightButton = new Point(pBottomMiddleButton.x + BUTTON_DIAM + UNIT, pBottomMiddleButton.y);

        final int padFlipper = UNIT;

        int flipperY = p.y - UNIT + CONTROLLER_HEIGHT;

        final int flipperLeftJustify = JOYSTICK_RADIUS/2;
        final Point pLeftFlipper = new Point(p.x + padFlipper + flipperLeftJustify, flipperY);
        final Point pRightFlipper = new Point(p.x + CONTROLLER_WIDTH + flipperLeftJustify - padFlipper - FLIPPER_WIDTH, flipperY);

        coinSlot = new CoinSlotWidget(this);
        startButton = new ButtonWidget(this);
        extraLeft = new ButtonWidget(this);
        extraRight = new ButtonWidget(this);
        topLeft = new ButtonWidget(this);
        topMiddle = new ButtonWidget(this);
        topRight = new ButtonWidget(this);
        bottomLeft = new ButtonWidget(this);
        bottomMiddle = new ButtonWidget(this);
        bottomRight = new ButtonWidget(this);

        flipperLeft = new FlipperWidget(this);
        flipperRight = new FlipperWidget(this);

        coinSlot.setRelativeLocation(pCoinSlot);
        startButton.setRelativeLocation(pStartButton);

        extraLeft.setRelativeLocation(pExtraLeft);
        extraRight.setRelativeLocation(pExtraRight);
        topLeft.setRelativeLocation(pTopLeftButton);
        topMiddle.setRelativeLocation(pTopMiddleButton);
        topRight.setRelativeLocation(pTopRightButton);
        bottomLeft.setRelativeLocation(pBottomLeftButton);
        bottomMiddle.setRelativeLocation(pBottomMiddleButton);
        bottomRight.setRelativeLocation(pBottomRightButton);

        flipperLeft.setRelativeLocation(pLeftFlipper);
        flipperLeft.setLeftFlipper(true);

        flipperRight.setRelativeLocation(pRightFlipper);
        flipperRight.setLeftFlipper(false);
    }

    public void setController(GameController<Sf2ButtonId, Sf2AxisId, Sf2JoystickId> controller) {
        this.controller = controller;
    }
    @Override
    public void onTick() {
        if (controller!=null) {
            this.controller.poll();
        }
    }

    @Override
    public void onRender(Graphics2D g) {
        final Point p = Geom.snap(getAbsoluteLocation());
        if (controller == null) {
            Draw.renderRetroText(g, p, UNIT, Color.YELLOW, null, "No Controller");
            return;
        }

        startButton.setButtonState(controller.getButtonState(Sf2ButtonId.PLAYER_START));
        coinSlot.setButtonState(controller.getButtonState(Sf2ButtonId.PLAYER_COIN));

        topLeft.setButtonState(controller.getButtonState(Sf2ButtonId.PUNCH_1));
        topMiddle.setButtonState(controller.getButtonState(Sf2ButtonId.PUNCH_2));
        topRight.setButtonState(controller.getButtonState(Sf2ButtonId.PUNCH_3));
        bottomLeft.setButtonState(controller.getButtonState(Sf2ButtonId.KICK_1));
        bottomMiddle.setButtonState(controller.getButtonState(Sf2ButtonId.KICK_2));
        bottomRight.setButtonState(controller.getButtonState(Sf2ButtonId.KICK_3));
        extraLeft.setButtonState(controller.getButtonState(Sf2ButtonId.PLAYER_START));
        extraRight.setButtonState(controller.getButtonState(Sf2ButtonId.PLAYER_COIN));

        //
        // Do the rendering
        //

        joystickWidget.onRender(g);

        startButton.onRender(g);
        coinSlot.onRender(g);

        topLeft.onRender(g);
        topMiddle.onRender(g);
        topRight.onRender(g);
        bottomLeft.onRender(g);
        bottomMiddle.onRender(g);
        bottomRight.onRender(g);
        extraLeft.onRender(g);
        topLeft.onRender(g);
        extraRight.onRender(g);

        flipperLeft.onRender(g);
        flipperRight.onRender(g);
    }

}
